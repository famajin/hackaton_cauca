package sas.buxtar.movil.heroican;


import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class TerminosActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminos);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void SendAnalytics() {
		if(GeneralData.SEND_ANALYTICS){
			Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
	                TrackerName.APP_TRACKER);
	        t.setScreenName(getResources().getString(R.string.screen_Terminos));
	        t.send(new HitBuilders.AppViewBuilder().build());	
		}
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
}




