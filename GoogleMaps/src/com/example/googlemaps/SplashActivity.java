package com.example.googlemaps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity {

	private final int TIME_WAIT=1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		String usu_cedula=preferences.getString(GeneralData.PREF_CEDULA, "null");
		
		if(usu_cedula.equals("null")){
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(GeneralData.PREF_CEDULA, "");
			gonLogin();
		}else if(usu_cedula.equals("")){
			gonLogin();
		}else{
			goMain();
		}
	}
	
	private void goMain() {
		Thread t = new Thread() {
            @Override
            public void run() {
            	try {
            		Thread.sleep(TIME_WAIT);
				} catch (Exception e) {}
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        t.start();
	}
	
	private void gonLogin() {
		Thread t = new Thread() {
            public void run() {
            	try {
            		Thread.sleep(TIME_WAIT);
				} catch (Exception e) {}
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        };
        t.start();
	}
	
}
