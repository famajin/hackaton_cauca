package com.example.focusgroupheroican;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusgroupheroican.clases.ClsConfig;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.fragments.DialogConfig;
import com.example.focusgroupheroican.fragments.DialogConfig.EvtCloseDialog;
import com.example.focusgroupheroican.fragments.FragChartBool;
import com.example.focusgroupheroican.fragments.FragChartRang;
import com.example.focusgroupheroican.util.Data;

public class AdminQuestionActivity extends ActionBarActivity implements EvtCloseDialog{

	Toolbar toolbar;
	private String title="";
	private ClsConfig config;
	private FragmentManager fragmentManager;
	
	private FragChartBool fragBool;
	private FragChartRang fragRang;
	
	private int ques_tipe, ques_id, fun_id;
	private ClsQuestion ques;
	private Data db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_question);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        db=new Data(this);
        
        fragmentManager=getSupportFragmentManager();
        
        config=new ClsConfig(0, 0, 0);
        title=(String) getIntent().getExtras().getString("title");
        ((TextView)findViewById(R.id.adminQues_txtDescription)).setText(title);
        
        fun_id=getIntent().getExtras().getInt("fun_id");
        
        ques_tipe=getIntent().getExtras().getInt("ques_tipe");
        ques_id=getIntent().getExtras().getInt("ques_id");
        ques=db.getQuestion(""+ques_id, fun_id, config);
        
        if(ques!=null){
        	if(ques_tipe==ClsQuestion.BOOLEAN){
                fragBool=FragChartBool.newInstace();
                fragmentManager.beginTransaction()
                .add(R.id.adminQues_frame, fragBool, FragChartBool.TAG).commit();
            }else{
            	fragRang=FragChartRang.newInstace(ques);
                fragmentManager.beginTransaction()
                .add(R.id.adminQues_frame, fragRang, FragChartRang.TAG).commit();        	
            }	
        }else{
        	Toast.makeText(getApplicationContext(), "NULL", 0).show();
        }
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if(ques_tipe==ClsQuestion.BOOLEAN){
			String yes_txt=getString(R.string.yes)+"\n"+ques.porcent_yes+"%"+"\n"+ques.personas_yes+" Personas";
			String not_txt=getString(R.string.not)+"\n"+ques.porcent_not+"%"+"\n"+ques.personas_not+" Personas";
			fragBool.reload(ques.porcent_yes, ques.porcent_not, yes_txt, not_txt,LinearLayout.VERTICAL);
		}else{
			fragRang.reload();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_question, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_config:
			DialogConfig dialConfig=new DialogConfig();
			dialConfig.setEvtCloseDialog(this);
			
			Bundle b=new Bundle();
			b.putInt("carg", config.carg_id);
			b.putInt("edad", config.edad_id);
			b.putInt("sex", config.gender_id);
			
			dialConfig.setArguments(b);
			dialConfig.show(getSupportFragmentManager(), "dialConfig");
			break;
			
		case R.id.menu_comments:
			Intent i=new Intent(AdminQuestionActivity.this, AdminCommentActivity.class);
			i.putExtra("fun_id", fun_id);
			i.putExtra("ques_id", ques_id);
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void OnEvtCloseDialog(ClsConfig config, Boolean set) {
		// TODO Auto-generated method stub
		this.config=null;
		this.config=config;

		this.ques=null;
		
		
		this.ques=db.getQuestion(""+ques_id, fun_id, config);
		
		if(ques_tipe==ClsQuestion.BOOLEAN){
			String yes_txt=getString(R.string.yes)+"\n"+ques.porcent_yes+"%"+"\n"+ques.personas_yes+" Personas";
			String not_txt=getString(R.string.not)+"\n"+ques.porcent_not+"%"+"\n"+ques.personas_not+" Personas";
			fragBool.reload(ques.porcent_yes, ques.porcent_not, yes_txt, not_txt,LinearLayout.VERTICAL);
		}else{
			fragRang.setQuestion(ques);
			fragRang.reload();
		}
	}
}
