package com.example.googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegistroActivity extends ActionBarActivity implements OnClickListener{

	int pag=1, rol;
	private FragmentManager fragmentManager;
	private FragRegSlide1 fragRegSlide1;
	private FragRegSlide2 fragRegSlide2;
	private FragReg_CompradorProveedor fragComPro;
	private Button btnBack, btnNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		rol=getIntent().getIntExtra("rol", -1);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        fragmentManager=getSupportFragmentManager();
        
        btnBack=(Button)findViewById(R.id.reg_btnBack);
		btnBack.setOnClickListener(this);
		btnNext=(Button)findViewById(R.id.reg_btnNext);
		btnNext.setOnClickListener(this);
        
		btnBack.setVisibility(View.GONE);
		
		switch (rol) {
		case GeneralData.ROL_CAFICULTOR:
	        fragRegSlide1=new FragRegSlide1();
	        fragRegSlide2=new FragRegSlide2();
	        replace(fragRegSlide1, FragRegSlide1.TAG);
			break;
		case GeneralData.ROL_COMPRADOR:
			fragComPro=new FragReg_CompradorProveedor();
	        replace(fragRegSlide1, FragReg_CompradorProveedor.TAG);
			break;
		case GeneralData.ROL_PROVEEDOR:
			
			break;
		}
	}
	
	public void replaceNormal(Fragment frag, String tag) {
		fragmentManager.beginTransaction()
		.setCustomAnimations(R.anim.anim_in2, R.anim.anim_out2)
		.replace(R.id.reg_frame, frag, tag)
		.commit();	
	}
	
	public void replace(Fragment frag, String tag) {
		if(pag==1){
			fragmentManager.beginTransaction()
			.setCustomAnimations(R.anim.anim_in2, R.anim.anim_out2)
			.replace(R.id.reg_frame, frag, tag)
			.commit();	
		}else{
			fragmentManager.beginTransaction()
			.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
			.replace(R.id.reg_frame, frag, tag)
			.commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.reg_btnBack:
			if(pag==2){
				pag=1;
				replace(fragRegSlide1, FragRegSlide1.TAG);
				btnBack.setVisibility(View.GONE);
			}
			break;

		case R.id.reg_btnNext:
			if(pag==1){
				pag=2;
				replace(fragRegSlide2, FragRegSlide1.TAG);
				btnBack.setVisibility(View.VISIBLE);
			}else{
				startActivity(new Intent(RegistroActivity.this, MainActivity.class));
			}
			break;
		}
	}
	
}
