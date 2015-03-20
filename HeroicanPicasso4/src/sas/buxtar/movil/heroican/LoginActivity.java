package sas.buxtar.movil.heroican;

import org.json.JSONObject;

import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.GCMIntentService.EvtResultGCM;
import sas.buxtar.movil.heroican.clases.ClsPersona;
import sas.buxtar.movil.heroican.clases.ClsUsuario;
import sas.buxtar.movil.heroican.fragments.DialogLoad;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.process.RecyclingImageView;
import sas.buxtar.movil.heroican.redes.FacebookManager.EvtLoginFaceBook;
import sas.buxtar.movil.heroican.redes.FragFacebook;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

@SuppressLint("NewApi")
public class LoginActivity extends ActionBarActivity implements OnClickListener, EvtEndInsert, EvtResultGCM, EvtLoginFaceBook{
	
    private EditText txtUsuario, txtPassword;
    private ClsUsuario usu_app;
    private DialogLoad dialLoad;
    private NetWorkState netWorkState;
    private ViewGroup linRegistro, frameLoginFacebook;
	private RecyclingImageView fondo;
	private FragFacebook mainFragment;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		try {
			System.gc();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		frameLoginFacebook=(ViewGroup) findViewById(R.id.login_frameFacebook);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new FragFacebook();
	        mainFragment.setEvtLoginFaceBook(this);
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(R.id.login_frameFacebook, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (FragFacebook) getSupportFragmentManager()
	        .findFragmentById(R.id.login_frameFacebook);
	        mainFragment.setEvtLoginFaceBook(this);
	    }
		
		netWorkState=new NetWorkState();
		usu_app=new ClsUsuario();
		usu_app.setEvtEndRequest(this);
		
		usu_redes=new ClsUsuario();
		usu_redes.setEvtEndRequest(this);
		
		fondo=(RecyclingImageView) findViewById(R.id.login_fondo);
		((Button) findViewById(R.id.login_btnFundacion)).setOnClickListener(this);
        ((Button) findViewById(R.id.login_btnPersona)).setOnClickListener(this);
        ((Button) findViewById(R.id.login_btnRegistrate)).setOnClickListener(this);
        ((Button) findViewById(R.id.login_btnLogin)).setOnClickListener(this);
//        ((ImageView)findViewById(R.id.login_btnFacebook)).setOnClickListener(this);
//        ((Button) findViewById(R.id.login_btnGooglePlus)).setOnClickListener(this);
        txtPassword=(EditText) findViewById(R.id.login_txtPassword);
        txtUsuario=(EditText) findViewById(R.id.login_txtUsuario);
        linRegistro=(ViewGroup) findViewById(R.id.login_linRegistro);
		GCMIntentService.setEvtResultGCM(this);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		
		quitFocus3();
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		login_tipe=preferences.getInt(GeneralData.PREF_LOGIN_TIPE, -1);
		if(login_tipe==GeneralData.LOGIN_FACE){
			logOutFacebook();
		}
		login_tipe=-1;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void quitFocus3() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	 @Override
	 protected void onStart() {
	 // TODO Auto-generated method stub
		 super.onStart();
		 if(GeneralData.SEND_ANALYTICS){
			try {
				 GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStart(this);
				 SendAnalytics();
			} catch (Exception e) {
				// TODO: handle exception
			} 
		 }
	 }


	 @Override
	 protected void onStop() {
	 // TODO Auto-generated method stub
		 if(GeneralData.SEND_ANALYTICS){
			try {
				 GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStop(this);	
			} catch (Exception e) {
				// TODO: handle exception
			}
		 }
		 super.onStop();
	 }
	
	private void SendAnalytics() {
		if(GeneralData.SEND_ANALYTICS){
			Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
	                TrackerName.APP_TRACKER);
	        t.setScreenName(getResources().getString(R.string.screen_LoginActivity));
	        t.send(new HitBuilders.AppViewBuilder().build());	
		}
	}
	
	private int login_tipe=-1;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.login_btnGooglePlus:
//			
//			break;
			
		case R.id.login_btnLogin:
			login_tipe=GeneralData.LOGIN_APP;
			usu_app.reset();
			
    		if(netWorkState==null){
    			netWorkState=new NetWorkState();
    		}
			if(netWorkState.isOnLine(this)){
				txtPassword.setError(null);
				txtUsuario.setError(null);
				if(txtUsuario.getText().length()>0){
					if(txtPassword.getText().length()>0){
						if(dialLoad==null){
							dialLoad=new DialogLoad();
						}
						dialLoad.show(getSupportFragmentManager(), "dialLoad");
						SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
						String tag=preferences.getString(GeneralData.PREF_DEVICETAG, "***");
						if(!tag.equals("***")){
							requestLogOut(tag);
						}else{
							requestLogin(GeneralData.LOGIN_APP);
						}
					}else{
						txtPassword.setError(getResources().getString(R.string.campo_requerido));
					}
				}else{
					txtUsuario.setError(getResources().getString(R.string.campo_requerido));
				}
			}else{
				Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
			}
			break;
			
		case R.id.login_btnRegistrate:
			linRegistro.setVisibility(View.VISIBLE);
			v.setVisibility(View.GONE);
			break;
			
		case R.id.login_btnFundacion:
			startActivity(new Intent(this, RegistroFundacionActivity.class));
			break;
			
		case R.id.login_btnPersona:
			startActivity(new Intent(this, RegistroPersonaActivity.class));
			break;
		}
	}
	
	private void requestLogin(int login_tipe) {
		// TODO Auto-generated method stub
		this.login_tipe=login_tipe;
		String gcmRegId = GCMRegistrar.getRegistrationId(this);
		if(gcmRegId.equals("")){
			GCMRegistrar.register(this, GCMIntentService.SENDER_ID);	
		}else{
			switch (login_tipe) {
			case GeneralData.LOGIN_APP:
				validateLoginApp(gcmRegId);	
				break;

			case GeneralData.LOGIN_FACE:
				validateLoginRedes(gcmRegId);
				break;
			}
		}
	}
	
	private void validateLoginRedes(String gcm) {
		usu_redes.setGcm(gcm);
		usu_redes.setEvtEndRequest(this);
		usu_redes.requestLoginRedes();
	}

	private void validateLoginApp(String gcm) {
		usu_app.setUsu_email(txtUsuario.getText().toString().trim().toLowerCase());
		usu_app.setUsu_password(txtPassword.getText().toString());
		usu_app.setGcm(gcm);
		usu_app.requestLoginApp();
	}
	
	private void login(ClsUsuario usuario, String usu_id, String usu_escuadron, String dev_id, String usu_ciudad) {
		
		usuario.setUsu_id(usu_id);
		usuario.setUsu_escuadron(usu_escuadron);
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(GeneralData.PREF_EMAIL, usuario.getUsu_email());
		editor.putString(GeneralData.PREF_USUID, usuario.getUsu_id());
		editor.putString(GeneralData.PREF_ESCUADRON, usuario.getUsu_escuadron());
		editor.putString(GeneralData.PREF_DEVICEID, dev_id);
		editor.putString(GeneralData.PREF_USUCIUDAD, usu_ciudad);
		editor.putString(GeneralData.PREF_DEVICETAG, "***");
		editor.putString(GeneralData.PREF_STATE, ""+GeneralData.CONNECTED);
		editor.putInt(GeneralData.PREF_LOGIN_TIPE, login_tipe);
		editor.commit();
		
		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
		
		Toast.makeText(getApplicationContext(), "ciudad: "+usu_ciudad, 0).show();
		goToMain();
	}
	
	private void requestLogOut(String dev_id) {
		ClsUsuario usuLogOut=new ClsUsuario();
		usuLogOut.setUsu_id(dev_id);
		usuLogOut.setEvtEndRequest(endLogout);
		usuLogOut.requestLogOut();
	}
	
	int i=0;
	EvtEndInsert endLogout=new EvtEndInsert() {
		
		@Override
		public void OnEndInsert(String was) {
			// TODO Auto-generated method stub
			requestLogin(login_tipe);
		}
	};
	
	private void goToMain() {
		//DESTRUIR LA ACTIVIDAD DEL TODO
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
            		Intent intentMain=new Intent(LoginActivity.this, MainActivity.class);
            		intentMain.putExtra("fromLogin", true);
            		intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            		intentMain.putExtra("needSesion", true);
            		finish();
                    startActivity(intentMain);
                    Looper.loop();
                    Looper.myLooper().quit();
                } catch (Exception e) {}
            }
        };
        t.start();
	}
	
	@Override
	public void OnEndInsert(String request) {
		// TODO Auto-generated method stub
		if(request!=null){
			if(request.equals(GeneralData.NOT_EXIST) && login_tipe==GeneralData.LOGIN_FACE){
				if(graphUser!=null){
					String genero="1";
					try {
						genero=graphUser.getProperty("gender").toString();
						if(genero.equals("male")){
							genero="1";
						}else{
							genero="2";
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					String[]fuenteCiudades=getResources().getStringArray(R.array.ciudades);
					int ciudad=0;
//					String usu_ciudad=String.format("Location: %s\n\n", graphUser.getLocation().getProperty("name"));
					String usu_ciudad=String.format("%s\n\n", graphUser.getLocation().getProperty("name"));
					usu_ciudad=usu_ciudad.toLowerCase();
					for (int i = 0; i < fuenteCiudades.length; i++) {
						if(usu_ciudad.contains(fuenteCiudades[i].toLowerCase())){
							ciudad=i;
							i=fuenteCiudades.length;
						}
					}
					if(ciudad!=0){
						usu_redes.setUsu_ciudad(""+usu_ciudad);
						ClsPersona per=new ClsPersona("", "", "", "", "", "", "", "");
						per.setPer_email(usu_redes.getUsu_email());
						per.setGcm(usu_redes.getGcm());
						per.setPer_ciudad(""+ciudad);
						per.setPer_genero(genero);
						per.setPer_fecha(graphUser.getBirthday());
						per.setPer_password("***");
						per.setPer_nombre(graphUser.getName());
						per.setEvtEndInsert(insertUsuarioRedes);
						
						per.insert();
					}else{
						if(dialLoad==null){
							dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
						}
						if(dialLoad!=null){
							try {
								dialLoad.getDialog().cancel();
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						usu_redes.reset();
						logOutFacebook();
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.ciudad_no_disponible), 0).show();
					}
				}
			}else if(!request.contains("error")){
				
				JSONObject jsonObject=null;
				String json=request;
				try {
					jsonObject = new JSONObject(json);	
				} catch (Exception e) {}
				
				if(jsonObject!=null){
					String usu_id="", usu_escuadron="", dev_id="", usu_ciudad="";
					try {
						usu_id=jsonObject.optString("usu_id");
						usu_ciudad=jsonObject.optString("usu_ciudad");
						usu_escuadron=jsonObject.optString("usu_escuadron");
						dev_id=jsonObject.optString("dev_id");
						
						if(login_tipe==GeneralData.LOGIN_APP){
							login(usu_app, usu_id, usu_escuadron, dev_id, usu_ciudad);	
						}else{
							login(usu_redes, usu_id, usu_escuadron, dev_id, usu_ciudad);
						}
						
//						Toast.makeText(getApplicationContext(), usu_id+"\n"+usu_escuadron+"\n"+usu_ciudad+"\n"+dev_id, 0).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.conexion_error), 0).show();
					}
					if(dialLoad==null){
						dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
					}
					if(dialLoad!=null){
						try {
							dialLoad.getDialog().cancel();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}else if(login_tipe==GeneralData.LOGIN_FACE){
				logOutFacebook();
				if(dialLoad==null){
					dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
				}
				if(dialLoad!=null){
					try {
						dialLoad.getDialog().cancel();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.conexion_error), 0).show();
			}else{
				if(dialLoad==null){
					dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
				}
				if(dialLoad!=null){
					try {
						dialLoad.getDialog().cancel();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_campos), 0).show();
			}
		}else{
			if(dialLoad==null){
				dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
			}
			if(dialLoad!=null){
				try {
					dialLoad.getDialog().cancel();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
		}
	}
	
	@Override
	public void OnEvtGCM(String gcm) {
		// TODO Auto-generated method stub
		switch (login_tipe) {
		case GeneralData.LOGIN_APP:
			validateLoginApp(gcm);
			break;

		case GeneralData.LOGIN_FACE:
			validateLoginRedes(gcm);
			break;
		}
	}

	private ClsUsuario usu_redes;
	private GraphUser graphUser; 
	
	@Override
	public void onEvtLoginFaceBook(GraphUser user) {
		// TODO Auto-generated method stub
		login_tipe=GeneralData.LOGIN_FACE;
		this.graphUser=user;
		
		usu_redes.reset();
		
		usu_redes.setUsu_email(user.getProperty("email").toString());
		
		if(dialLoad==null){
			dialLoad=new DialogLoad();
		}
		dialLoad.show(getSupportFragmentManager(), "");
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		String tag=preferences.getString(GeneralData.PREF_DEVICETAG, "***");
		if(!tag.equals("***")){
			requestLogOut(tag);
		}else{
			requestLogin(GeneralData.LOGIN_FACE);
		}
	}
	
	private void logOutFacebook() {
		try {
		    Session session = Session.getActiveSession();
		    if(session != null) {
		        if(!session.isClosed()){
		            session.closeAndClearTokenInformation();
		        }
		    }else{
		        session = new Session(this);
		        Session.setActiveSession(session);
		        session.closeAndClearTokenInformation();
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	EvtEndInsert insertUsuarioRedes=new EvtEndInsert() {
		
		@Override
		public void OnEndInsert(String request) {
			// TODO Auto-generated method stub
			if(request!=null){
				if(request.contains("true")){
					requestLogin(GeneralData.LOGIN_FACE);
				}else if(request.contains("exists")){
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.email_registrado), 0).show();
				}else{
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.conexion_error), 0).show();
				}
			}else{
				Toast.makeText(LoginActivity.this, getResources().getString(R.string.conexion_error), 0).show();
			}
			if(dialLoad==null){
				dialLoad=(DialogLoad) (DialogLoad)getSupportFragmentManager().findFragmentByTag("dialLoad");
			}
			if(dialLoad!=null){
				try {
					dialLoad.getDialog().cancel();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	};
}
