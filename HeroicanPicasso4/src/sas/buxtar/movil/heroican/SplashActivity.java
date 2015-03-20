package sas.buxtar.movil.heroican;


import sas.buxtar.movil.heroican.redes.FacebookManager;
import sas.buxtar.movil.heroican.redes.FacebookManager.EvtSessionFaceBook;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity implements EvtSessionFaceBook{

	private final int TIME_WAIT=1000;
	private int login_tipe;
	private FacebookManager facebookManager=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_splash);
		
		facebookManager=new FacebookManager(this);
		facebookManager.onCreate(savedInstanceState);
		facebookManager.setEvtSessionFaceBook(this);
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		String usu_state=preferences.getString(GeneralData.PREF_STATE, "null");
		String usu_email=preferences.getString(GeneralData.PREF_EMAIL, "@");
		login_tipe=preferences.getInt(GeneralData.PREF_LOGIN_TIPE, -1);
		
		if(usu_state.equals("null")){
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(GeneralData.PREF_EMAIL, "@");
			editor.putString(GeneralData.PREF_USUID, "***");
			editor.putString(GeneralData.PREF_DEVICEID, "***");
			editor.putString(GeneralData.PREF_DEVICETAG, "***");
			editor.putString(GeneralData.PREF_ESCUADRON, ""+GeneralData.INACTIVE);
			editor.putString(GeneralData.PREF_STATE, ""+GeneralData.DESCONNECTED);
			editor.putString(GeneralData.PREF_USUCIUDAD, "0");
			editor.putInt(GeneralData.PREF_LOGIN_TIPE, -1);
			editor.putInt(GeneralData.PREF_TUTORIAL, 0);
			editor.putInt(GeneralData.PREF_TUTORIAL_CAMARA, 0);
			editor.commit();
			goToLogin();
		}else if(!usu_email.equals("@") && login_tipe==GeneralData.LOGIN_APP){
			goToMain();
		}else if(login_tipe==-1){
			goToLogin();
		}
		//716 DAD5 98A 882
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    facebookManager.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    facebookManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    facebookManager.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    facebookManager.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    facebookManager.onSaveInstanceState(outState);
	}
	
	private void goToLogin() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                	try {
                		Thread.sleep(TIME_WAIT);
//                        Looper.prepare();	
					} catch (Exception e) {}
            		try {
            			System.gc();	
            		} catch (Exception e) {}
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
//                    try {
//                    	Looper.loop();
//                        Looper.myLooper().quit();	
//					} catch (Exception e) {}
                } catch (Exception e) {
                	
                }
            }
        };
        t.start();
    }
	
	private void goToMain() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                	try {
                		Thread.sleep(TIME_WAIT);
					} catch (Exception e) {}
            		try {
            			System.gc();	
            		} catch (Exception e) {}
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    try {
//                    	Looper.loop();
//                        Looper.myLooper().quit();	
					} catch (Exception e) {}
                } catch (Exception e) {
                	
                }
            }
        };
        t.start();
    }

	@Override
	public void onEvtSessionFaceBook(boolean isOpened) {
		// TODO Auto-generated method stub
		if(isOpened){
			goToMain();
		}else if(login_tipe==GeneralData.LOGIN_FACE){
			goToLogin();
		}
	}
}
