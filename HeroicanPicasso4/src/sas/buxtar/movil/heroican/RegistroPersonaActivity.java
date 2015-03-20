package sas.buxtar.movil.heroican;


import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.clases.ClsPersona;
import sas.buxtar.movil.heroican.fragments.DialogLoad;
import sas.buxtar.movil.heroican.fragments.DialogSetData;
import sas.buxtar.movil.heroican.fragments.DialogSetData.EvtSetData;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class RegistroPersonaActivity extends ActionBarActivity implements EvtSetData, OnClickListener, EvtEndInsert{

    private EditText txtPassword, txtReplayPassword, txtNacimiento, txtNombre, txtEmail;
    private TextView txtGenero, txtCiudad;
    private String email, password, replayPassword, nombre, genero, ciudad, nac;
    private Spinner spinGenero, spinCiudad;
    private String[] fuenteGenero, fuenteCiudades;
    private DialogLoad dialLoad;
    private ClsPersona per;
    private CheckBox checkTerminos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
		
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        per=new ClsPersona("", "", "", "", "", "", "", "");
        dialLoad=new DialogLoad();
        spinGenero=(Spinner) findViewById(R.id.registro_spinGenero);
        spinCiudad=(Spinner) findViewById(R.id.registro_spinCiudad);
        txtEmail = (EditText) findViewById(R.id.registro_txtEmail2);
        txtGenero=(TextView) findViewById(R.id.registro_txtGenero);
        txtCiudad=(TextView) findViewById(R.id.registro_txtCiudad);
        checkTerminos=(CheckBox) findViewById(R.id.registro_checkTerminos);
        txtNacimiento = (EditText) findViewById(R.id.registro_txtNacimiento);
        txtPassword = (EditText) findViewById(R.id.registro_txtPassword);
        txtReplayPassword = (EditText) findViewById(R.id.registro_txtReplayPassword);
        txtNombre=(EditText) findViewById(R.id.registro_txtNombre);
        ((Button) findViewById(R.id.registro_btnRegistrar)).setOnClickListener(this);
        ((Button) findViewById(R.id.registro_btnTerminos)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.registro_btnFecha)).setOnClickListener(this);
        
        fuenteGenero=getResources().getStringArray(R.array.genero_personas);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, R.layout.spin_text, fuenteGenero);
        adp.setDropDownViewResource(R.layout.spin_dropdown);
        spinGenero.setAdapter(adp);
        
        
        fuenteCiudades=getResources().getStringArray(R.array.ciudades);
        ArrayAdapter<String> adpCiudad=new ArrayAdapter<String>(this, R.layout.spin_text, fuenteCiudades);
        adpCiudad.setDropDownViewResource(R.layout.spin_dropdown);
        spinCiudad.setAdapter(adpCiudad);
        
        dialLoad=new DialogLoad();
        quitFocus3();
//        SendAnalytics();
    }
    
	private void quitFocus3() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
	        t.setScreenName(getResources().getString(R.string.screen_RegistroPersonaActivity));
	        t.send(new HitBuilders.AppViewBuilder().build());	
		}
	}
    
    private boolean validate() {
    	
    	txtPassword.setError(null);
    	txtEmail.setError(null);
    	txtNombre.setError(null);
    	txtReplayPassword.setError(null);
    	txtNacimiento.setError(null);
    	txtGenero.setError(null);
    	txtCiudad.setError(null);
    	checkTerminos.setError(null);
    	
    	email=txtEmail.getText().toString().trim().toLowerCase();
    	password=txtPassword.getText().toString();
    	replayPassword=txtReplayPassword.getText().toString();
    	nombre=txtNombre.getText().toString().trim().toLowerCase();
    	genero=""+spinGenero.getSelectedItemPosition();
    	ciudad=""+spinCiudad.getSelectedItemPosition();
    	nac=txtNacimiento.getText().toString();
    	
    	if(nombre.length()>0){
    		if(nombre.length()<getResources().getInteger(R.integer.chars_60)){
        		per.setPer_nombre(nombre);
        		if(email.length()>0){
        			if(email.length()<getResources().getInteger(R.integer.chars_60)){
        				if(email.contains("@")){
        					per.setPer_email(email);
        					if(spinGenero.getSelectedItemPosition()!=0){
        						per.setPer_genero(genero);
        						if(spinCiudad.getSelectedItemPosition()!=0){
        							per.setPer_ciudad(ciudad);
        							if(txtNacimiento.getText().toString().trim().length()>0){
        								per.setPer_fecha(nac);
        								if(password.length()>0){
        									if(password.length()<getResources().getInteger(R.integer.chars_60)){
        										if(password.length()>4){
            			    						if(replayPassword.equals(password)){
            			    							per.setPer_password(password);
            			    							if(checkTerminos.isChecked()){
            			    								return true;
            			    							}else{
            			    								checkTerminos.setError(getString(R.string.error_terminos));	
            			    							}
            			    						}else{
            			    							txtReplayPassword.setError(getString(R.string.campo_nocoincide));	
            			    						}
            			    					}else{
            			    						txtPassword.setError(getString(R.string.campo_corto));	
            			    					}	
        									}else{
        										txtPassword.setError(getString(R.string.error_chars));
        									}
        			    				}else{
        			    					txtPassword.setError(getString(R.string.campo_requerido));	
        			    				}
        			    			}else{
        			    				txtNacimiento.setError(getString(R.string.campo_requerido));
        			    			}
        						}else{
        							txtCiudad.setError(getString(R.string.seleccioneCiudad));	
        						}
        					}else{
        						txtGenero.setError(getString(R.string.seleccioneGenero));
        					}
            			}else{
            				txtEmail.setError(getString(R.string.email_incorrecto));	
            			}
        			}else{
        				txtEmail.setError(getString(R.string.error_chars));
        			}
        		}else{
        			txtEmail.setError(getString(R.string.campo_requerido));
        		}
    		}else{
    			txtNombre.setError(getString(R.string.error_chars));	
    		}
    	}else{
    		txtNombre.setError(getString(R.string.campo_requerido));
    	}
    	return false;
	}
    
    public void showFechaDialog() {
    	DialogSetData newFragment = new DialogSetData();
		newFragment.setEvtSetFecha(this);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	} 
    
    private NetWorkState netWorkState=null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.registro_btnFecha:
			showFechaDialog();
			break; 

		case R.id.registro_btnRegistrar:
			if(validate()){
	    		if(netWorkState==null){
	    			netWorkState=new NetWorkState();
	    		}
	    		if(netWorkState.isOnLine(this)){
					dialLoad.show(getSupportFragmentManager(), "");
					per.setEvtEndInsert(this);
					per.setGcm("");
					per.insert();
	    		}else{
	    			Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
	    		}
			}
			break;
			
		case R.id.registro_btnTerminos:
			startActivity(new Intent(this, TerminosActivity.class));
			break;
		}
	}
	
	@Override
	public void OnEndInsert(String request) {
		// TODO Auto-generated method stub
		if(request!=null){
			if(request.equals(GeneralData.WORK)){
				dialLoad.getDialog().cancel();
				finish();
				Toast.makeText(this, getResources().getString(R.string.registrado), 0).show();
			}else if(request.equals("exists")){
				dialLoad.getDialog().cancel();
				txtEmail.setError(getResources().getString(R.string.email_registrado));
				Toast.makeText(this, getResources().getString(R.string.email_registrado), 0).show();
			}else{
				dialLoad.getDialog().cancel();
				Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
			}	
		}else{
			Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
		}
	}

	@Override
	public void onEvtSetData(String fecha) {
		// TODO Auto-generated method stub
		txtNacimiento.setText(fecha);
	}
}
