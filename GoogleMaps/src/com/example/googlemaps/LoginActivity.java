package com.example.googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends ActionBarActivity implements OnClickListener{

	private int rol=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		rol=GeneralData.ROL_CAFICULTOR;
		((Button)findViewById(R.id.log_btnRegistro)).setOnClickListener(this);
		((Button)findViewById(R.id.log_btnEntrar)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.log_btnEntrar:
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			finish();
			break;

		case R.id.log_btnRegistro:
			Intent i=new Intent(LoginActivity.this, RegistroActivity.class);
			i.putExtra("rol", rol);
			startActivity(i);
			break;
		}
	}
}
