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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdminActivity extends ActionBarActivity implements OnItemClickListener{

	private ArrayList<String> fuente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lst=(ListView)findViewById(R.id.admin_lst);
        fuente=new ArrayList<String>();
        fuente.add("Preguntas generales");
        fuente.add(getString(R.string.fun_1));
        fuente.add(getString(R.string.fun_2));
        fuente.add(getString(R.string.fun_3));
        fuente.add(getString(R.string.fun_4));
        fuente.add(getString(R.string.fun_5));
        fuente.add(getString(R.string.fun_6));
        fuente.add(getString(R.string.fun_7));
        fuente.add(getString(R.string.fun_8));
        fuente.add(getString(R.string.fun_9));
        fuente.add(getString(R.string.fun_10));
        fuente.add(getString(R.string.fun_11));
        fuente.add(getString(R.string.fun_12));
        fuente.add(getString(R.string.fun_13));
        fuente.add(getString(R.string.fun_14));
        fuente.add(getString(R.string.fun_15));
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fuente);
        lst.setAdapter(adp);
        lst.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_load) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent i=new Intent(AdminActivity.this, AdminEncuestaActivity.class);
		i.putExtra("title", fuente.get(position));
		i.putExtra("pos", position);
		startActivity(i);
	}
}
