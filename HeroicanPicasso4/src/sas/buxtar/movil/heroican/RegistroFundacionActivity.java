package sas.buxtar.movil.heroican;


import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.fragments.DialogLoad;
import sas.buxtar.movil.heroican.fragments.FragRegFun_1;
import sas.buxtar.movil.heroican.fragments.FragRegFun_2;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.MediaUtil;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class RegistroFundacionActivity extends ActionBarActivity implements OnClickListener, EvtEndInsert, EvtActivityResult{

	private FragRegFun_1 fragRegFun_1;
	private FragRegFun_2 fragRegFun_2;
	
	private VARS_FUNDACION vars;
	private DialogLoad dialLoad;
	
	private ClsFundacion fun;
	
	private ViewGroup conBack;
	
	private LruCache<String, Bitmap> memoryCacheMultimedia;
	private FragmentManager fragmentManager;
	
	private boolean restoreFromOncreate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_fundacion);
		restoreFromOncreate=false;
		fragmentManager=getSupportFragmentManager();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		initMemoryMultimedia();
		dialLoad=new DialogLoad();
		
		if(savedInstanceState==null){
			initInstace();
		}else{
			restoreFromOncreate=true;
			restoreInstance(savedInstanceState);
		}
		conBack=((ViewGroup)findViewById(R.id.regFun_back));
		((Button)findViewById(R.id.regFun_btnBack)).setOnClickListener(this);
		((Button)findViewById(R.id.regFun_btnNext)).setOnClickListener(this);
		quitFocus3();
	}
	
	private void initInstace() {
		vars=new VARS_FUNDACION();
		fun=new ClsFundacion("", "", "", "", "", "", "", "", "0", "", "", "", "", "");
		
		fragRegFun_1=FragRegFun_1.newInstace(vars, this, memoryCacheMultimedia, fun);
		
		fragmentManager.beginTransaction()
    	.replace(R.id.regFun_frame, fragRegFun_1, "fragRegFun_1").commit();
	}
	
	public static class VARS_FUNDACION{
		public String path="";
		public String fragTag="";
		public int a=0;
		public VARS_FUNDACION() {
			// TODO Auto-generated constructor stub
		}
	}
	
	private void saveInstace(Bundle savedInstanceState) {
		try {
			savedInstanceState.putString("setFun_cedularepresentante", fun.getFun_cedularepresentante());
			savedInstanceState.putString("setFun_ciudad", fun.getFun_ciudad());
			savedInstanceState.putString("setFun_descripcion", fun.getFun_descripcion());
			savedInstanceState.putString("setFun_direccion", fun.getFun_direccion());
			savedInstanceState.putString("setFun_email", fun.getFun_email());
			savedInstanceState.putString("setFun_id", fun.getFun_id());
			savedInstanceState.putString("setFun_nit", fun.getFun_nit());
			savedInstanceState.putString("setFun_nombre", fun.getFun_nombre());
			savedInstanceState.putString("setFun_password", fun.getFun_password());
			savedInstanceState.putString("setFun_pathimage", fun.getFun_pathimage());
			savedInstanceState.putString("setFun_razonsocial", fun.getFun_razonsocial());
			savedInstanceState.putString("setFun_representante", fun.getFun_representante());
			savedInstanceState.putString("setFun_telefono", fun.getFun_telefono());
			
			savedInstanceState.putString("fragTag", vars.fragTag);
			savedInstanceState.putString("path", vars.path);
			savedInstanceState.putInt("a", vars.a);

			if(vars.fragTag.equals("fragRegFun_1")){
				if(fragRegFun_1!=null){
					fragmentManager.putFragment(savedInstanceState, vars.fragTag, fragRegFun_1);	
				}
			}else if(vars.fragTag.equals("fragRegFun_2")){
				if(fragRegFun_2!=null){
					fragmentManager.putFragment(savedInstanceState, vars.fragTag, fragRegFun_2);	
				}
			}	
		}catch (Exception e) {
			// TODO: handle exception
			finish();
		}
	}
	
	private void restoreInstance(Bundle savedInstanceState) {
		try {
			fun=new ClsFundacion("", "", "", "", "", "", "", "", "0", "", "", "", "", "");
			
			fun.setFun_cedularepresentante(savedInstanceState.getString("setFun_cedularepresentante"));
			fun.setFun_ciudad(savedInstanceState.getString("setFun_ciudad"));
			fun.setFun_descripcion(savedInstanceState.getString("setFun_descripcion"));
			fun.setFun_direccion(savedInstanceState.getString("setFun_direccion"));
			fun.setFun_email(savedInstanceState.getString("setFun_email"));
			fun.setFun_id(savedInstanceState.getString("setFun_id"));
			fun.setFun_nit(savedInstanceState.getString("setFun_nit"));
			fun.setFun_nombre(savedInstanceState.getString("setFun_nombre"));
			fun.setFun_password(savedInstanceState.getString("setFun_password"));
			fun.setFun_pathimage(savedInstanceState.getString("setFun_pathimage"));
			fun.setFun_razonsocial(savedInstanceState.getString("setFun_razonsocial"));
			fun.setFun_representante(savedInstanceState.getString("setFun_representante"));
			fun.setFun_telefono(savedInstanceState.getString("setFun_telefono"));
			
			vars=new VARS_FUNDACION();
			vars.fragTag=savedInstanceState.getString("fragTag");
			vars.a=savedInstanceState.getInt("a");
			vars.path=savedInstanceState.getString("path");
			if(vars.fragTag.equals("fragRegFun_1")){
				fragRegFun_1=(FragRegFun_1) fragmentManager.getFragment(savedInstanceState, vars.fragTag);
				FragRegFun_1.restoreInstace(vars, fragRegFun_1, this, memoryCacheMultimedia, fun);
			}else if(vars.fragTag.equals("fragRegFun_2")){
				fragRegFun_2=(FragRegFun_2) fragmentManager.getFragment(savedInstanceState, vars.fragTag);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			finish();
		}
	}
	
	private void quitFocus3() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
 	private void initMemoryMultimedia() {
		// Obtenemos el maximo de memoria disponible para la aplicación
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Usamos solo 1/8 para el cache de bitmaps
	    final int cacheSize = maxMemory / 10;
		
	    memoryCacheMultimedia = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            //Metodo que retorna el tamaño de cada Bitmap almacenado en memoria cache
	        	// el tamaño se expresa en kilobytes
	            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
	        }
	    };
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
	        t.setScreenName(getResources().getString(R.string.screen_RegistroFundacionActivity));
	        t.send(new HitBuilders.AppViewBuilder().build());	
		}
	}
	
    private void replaceAnimNext(Fragment frag, String tag) {
		// TODO Auto-generated method stub
    	vars.fragTag=tag;
    	fragmentManager.beginTransaction()
    	.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
    	.replace(R.id.regFun_frame, frag, tag).commit();
	}
    
    private void replaceAnimBack(Fragment frag, String tag) {
		// TODO Auto-generated method stub
    	vars.fragTag=tag;
    	fragmentManager.beginTransaction()
    	.setCustomAnimations(R.anim.anim_out2, R.anim.anim_in2)
    	.replace(R.id.regFun_frame, frag, tag).commit();
	}

    private NetWorkState netWorkState=null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regFun_btnBack:
			switch (vars.a) {
			case 1:
				conBack.setVisibility(View.GONE);
				replaceAnimBack(fragRegFun_1, "fragRegFun_1");
				vars.a--;
				break;
			}
			break;
		case R.id.regFun_btnNext:
			switch (vars.a) {
			case 0:
				if(fragRegFun_1.validate()){
					if(fragRegFun_2==null){
						fragRegFun_2=FragRegFun_2.newInstace(fun);
					}
					conBack.setVisibility(View.VISIBLE);
					replaceAnimNext(fragRegFun_2, "fragRegFun_2");
					vars.a++;
				}
				break;
			case 1:
				if(fragRegFun_2==null){
					fragRegFun_2=FragRegFun_2.newInstace(fun);
				}
				if(fragRegFun_2.validate()){
		    		if(netWorkState==null){
		    			netWorkState=new NetWorkState();
		    		}
		    		if(netWorkState.isOnLine(this)){
						fun.setGcm("");
						fun.setEvtEndInsert(this);
						fun.insert();
						dialLoad.show(fragmentManager, "");		
		    		}else{
		    			Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
		    		}
				}
				break;
			}
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(fragRegFun_1!=null){
				fragRegFun_1.ActivityResult(requestCode, data);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		saveInstace(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if(!restoreFromOncreate){
			restoreInstance(savedInstanceState);
		}else{
			restoreFromOncreate=false;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void OnEndInsert(String request) {
		// TODO Auto-generated method stub
		if(request!=null){
			if(request.contains("true")){
				dialLoad.getDialog().cancel();
				finish();
				Toast.makeText(this, getResources().getString(R.string.registrado), 0).show();
			}else if(request.equals("exists")){
				dialLoad.getDialog().cancel();
				try {
					fragRegFun_2.setErrorEmail(getResources().getString(R.string.email_registrado));	
				} catch (Exception e) {
					// TODO: handle exception
				}
				Toast.makeText(this, getResources().getString(R.string.email_registrado), 0).show();
			}else{
				dialLoad.getDialog().cancel();
				Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
			}
		}else{
			dialLoad.getDialog().cancel();
			Toast.makeText(this, getResources().getString(R.string.conexion_error), 0).show();
		}
	}
	
	@Override
	public void onEvtActivityResult(int tipe) {
		// TODO Auto-generated method stub
		switch (tipe) {
		case MediaUtil.CAMERA:
			vars.path=MediaUtil.takeFromCamera(this);
			break;
	
		case MediaUtil.GALLERY:
			MediaUtil.takeFromGallery(this);
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(vars.a==1){
			conBack.setVisibility(View.GONE);
			replaceAnimBack(fragRegFun_1, "fragRegFun_1");
			vars.a--;
		}else{
			super.onBackPressed();	
		}
	}
	
}
