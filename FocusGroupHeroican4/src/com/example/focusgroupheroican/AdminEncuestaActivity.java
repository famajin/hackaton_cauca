package com.example.focusgroupheroican;

import java.util.ArrayList;

import com.example.focusgroupheroican.adapters.AdpAdminQuestions;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.util.Data;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AdminEncuestaActivity extends ActionBarActivity implements OnItemClickListener{

	Toolbar toolbar;
	private String title="";
	private Data db;
	private int pos;
	private ArrayList<ClsQuestion> questions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_encuesta);
		db=new Data(this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(String) getIntent().getExtras().getString("title");
        pos= getIntent().getExtras().getInt("pos");
        if(pos==0){
        	questions=db.getQuestions("'0', '1', '2', '3', '4', '5'");
        }else{
        	questions=db.getQuestions("'6', '7', '8', '9', '10', '11', '12'");
        }
        AdpAdminQuestions adp=new AdpAdminQuestions(this, questions);
        ListView lst=(ListView)findViewById(R.id.adminEnc_lst);
        lst.setOnItemClickListener(this);
        lst.setAdapter(adp);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		toolbar.setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_encuesta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent i=new Intent(AdminEncuestaActivity.this, AdminQuestionActivity.class);
		i.putExtra("ques_id", questions.get(position).ques_id);
		i.putExtra("ques_tipe", questions.get(position).ques_tipe);
		i.putExtra("title", questions.get(position).ques_description);
		if(pos==0){
			i.putExtra("fun_id", -1);
		}else{
			i.putExtra("fun_id", pos);
		}
		startActivity(i);
	}
}
