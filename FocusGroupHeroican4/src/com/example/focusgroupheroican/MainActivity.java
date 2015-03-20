package com.example.focusgroupheroican;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.focusgroupheroican.util.Data;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	private Toolbar toolbar;
	private Data db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db=new Data(getApplicationContext());
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
		((Button)findViewById(R.id.main_btnAdmin)).setOnClickListener(this);
		((Button)findViewById(R.id.main_btnEncuesta)).setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_ver) {
			Toast.makeText(getApplicationContext(), "CHOICES COUNT"+db.test("SELECT COUNT(*) from tbl_choice")+"\nCOMMENTS COUNT"+db.test("SELECT COUNT(*) from tbl_comment")+"\nUSUARIOS:"+db.test("SELECT COUNT(*) from tbl_usuario"), 0).show();
			return true;
		}else if(id == R.id.menu_t){
//			db.T();
			return true;
		}else if(id==R.id.menu_test){
			String cad="";
			ArrayList<String> a=db.getArray("SELECT ans_id FROM tbl_choice WHERE fun_id=1");
			for (String s : a) {
				cad+="ans_id: "+s+"\n";
			}
//			ArrayList<String> a=db.getArray("SELECT usu_email FROM tbl_usuario WHERE usu_sex=2");
//			for (String s : a) {
//				cad+="ans_id: "+s+"\n";
//			}
			Toast.makeText(getApplicationContext(), cad, 0).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_btnAdmin:
			startActivity(new Intent(MainActivity.this, AdminActivity.class));
			break;
		case R.id.main_btnEncuesta:
			startActivity(new Intent(MainActivity.this, RegistroActivity.class));
			break;		
		}
	}
}
