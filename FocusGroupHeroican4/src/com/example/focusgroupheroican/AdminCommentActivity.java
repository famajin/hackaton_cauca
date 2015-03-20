package com.example.focusgroupheroican;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.focusgroupheroican.adapters.AdpAdminComments;
import com.example.focusgroupheroican.clases.ClsComment;
import com.example.focusgroupheroican.util.Data;

public class AdminCommentActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_comment);
		ListView lst=(ListView) findViewById(R.id.adminCom_lst);
		Data db=new Data(this);
		
		int fun_id=getIntent().getExtras().getInt("fun_id");
        int ques_id=getIntent().getExtras().getInt("ques_id");
		
		Toast.makeText(getApplicationContext(), ""+ques_id+"\n"+fun_id, 0).show();
		ArrayList<ClsComment> comments=db.getComments(""+ques_id, fun_id);
		lst.setAdapter(new AdpAdminComments(this, comments));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_comment, menu);
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
}
