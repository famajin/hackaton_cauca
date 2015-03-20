package com.example.focusgroupheroican;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.focusgroupheroican.clases.ClsUsuario;
import com.example.focusgroupheroican.clases.Encuesta;
import com.example.focusgroupheroican.clases.Funcionalidad;
import com.example.focusgroupheroican.fragments.DialogLoad;
import com.example.focusgroupheroican.fragments.FragFinal;
import com.example.focusgroupheroican.fragments.FragFuncionalidad;
import com.example.focusgroupheroican.fragments.FragGenerales;
import com.example.focusgroupheroican.util.Data;
import com.example.focusgroupheroican.util.EvtEndInsert;

public class EncuestaActivity extends ActionBarActivity implements OnClickListener, EvtEndInsert{

	private ArrayList<Funcionalidad> funcionalidades;
	private int sel_fun=0, part=0;
	private FragFuncionalidad fragFuncionalidad;
	private FragGenerales fragGenerales;
	private FragFinal fragFinal;
	private FragmentManager fragmentManger;
	private Toolbar toolbar;
	private Data db;
	
	private Encuesta encuesta;
	private ClsUsuario usu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encuesta);
		
		db=new Data(getApplicationContext());
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
        encuesta=new Encuesta();
        usu=new ClsUsuario();
        usu.usu_email=getIntent().getExtras().getString("usu_email");
        usu.usu_nombre=getIntent().getExtras().getString("usu_nombre");
        usu.edad_id=getIntent().getExtras().getInt("edad_id");
        usu.usu_sex=getIntent().getExtras().getInt("usu_sex");
        usu.carg_id=getIntent().getExtras().getInt("carg_id");
        
		fragmentManger=getSupportFragmentManager();
		
		encuesta.send.add(usu.getJson());
		
		initFuncionalidades();
		
		if(savedInstanceState==null){
			fragGenerales=FragGenerales.newInstace();
			replace(fragGenerales, FragGenerales.TAG, "HEROICAN");
		}else{
//			fragFuncionalidad=(FragFuncionalidad) fragmentManger.findFragmentByTag(FragFuncionalidad.TAG);
		}
		((Button)findViewById(R.id.enc_btnNext)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.encuesta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_ver) {
			Toast.makeText(getApplicationContext(), "CHOICES COUNT"+db.test("SELECT COUNT(*) from tbl_choice")+"\nCOMMENTS COUNT"+db.test("SELECT COUNT(*) from tbl_comment"), 0).show();
			return true;
		}else if(id == R.id.menu_t){
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void replace(Fragment frag, String TAG, String title) {
		toolbar.setTitle(title);
		getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
		.replace(R.id.enc_frame, frag, TAG).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (part) {
		case 0:
			boolean pass=false;
			if(fragFuncionalidad==null){
				if(fragGenerales.validate()){
					pass=true;
//					fragGenerales.insertChoice_comments(usu.usu_email, -1);
					encuesta.send.add(fragGenerales.getChoice_comments(usu.usu_email, -1));
				}
			}else if(fragFuncionalidad.validate()){
				pass=true;
//				fragFuncionalidad.insertChoice_comments(usu.usu_email, sel_fun);
				encuesta.send.add(fragFuncionalidad.getChoice_comments(usu.usu_email, sel_fun));
			}
			if(pass){
				if(sel_fun<funcionalidades.size()){
					try {
						fragmentManger.beginTransaction()
						.remove(fragFuncionalidad);
						fragFuncionalidad=null;
					} catch (Exception e) {
						// TODO: handle exception
					}
					fragFuncionalidad=FragFuncionalidad.newInstace();
					replace(fragFuncionalidad, FragFuncionalidad.TAG, funcionalidades.get(sel_fun).fun_description);
				}else{
					try {
						fragmentManger.beginTransaction()
						.remove(fragFuncionalidad);
						fragFuncionalidad=null;
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					((Button)findViewById(R.id.enc_btnNext)).setText(getString(R.string.enviar_encuesta));
					
					fragFinal=FragFinal.newInstace();
					replace(fragFinal, FragFinal.TAG, "...");
					part++;
				}
				sel_fun++;
			}else{
				Toast.makeText(getApplicationContext(), "responde todas las preguntas por favor", 0).show();
			}
			break;
		case 1:
			//ENVIAR DATOS
			encuesta.insert(this);
			dialLoad=new DialogLoad();
			dialLoad.show(fragmentManger, "DialogLoad");
			break;
		}
	}
	
	private void initFuncionalidades() {
		funcionalidades=new ArrayList<Funcionalidad>();
		funcionalidades.add(new Funcionalidad(1, getString(R.string.fun_1)));
		funcionalidades.add(new Funcionalidad(2, getString(R.string.fun_2)));
		funcionalidades.add(new Funcionalidad(3, getString(R.string.fun_3)));
		funcionalidades.add(new Funcionalidad(4, getString(R.string.fun_4)));
		funcionalidades.add(new Funcionalidad(5, getString(R.string.fun_5)));
		funcionalidades.add(new Funcionalidad(6, getString(R.string.fun_6)));
		funcionalidades.add(new Funcionalidad(7, getString(R.string.fun_7)));
		funcionalidades.add(new Funcionalidad(8, getString(R.string.fun_8)));
		funcionalidades.add(new Funcionalidad(9, getString(R.string.fun_9)));
		funcionalidades.add(new Funcionalidad(10, getString(R.string.fun_10)));
		funcionalidades.add(new Funcionalidad(11, getString(R.string.fun_11)));
		funcionalidades.add(new Funcionalidad(12, getString(R.string.fun_12)));
		funcionalidades.add(new Funcionalidad(13, getString(R.string.fun_13)));
		funcionalidades.add(new Funcionalidad(14, getString(R.string.fun_14)));
		funcionalidades.add(new Funcionalidad(15, getString(R.string.fun_15)));
	}

	private DialogLoad dialLoad;
	     
	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			try {
				fragmentManger.beginTransaction()
				.remove(dialLoad).commit();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Log.e("", was);
			Toast.makeText(getApplicationContext(), was, 0).show();
		}else{
			Toast.makeText(getApplicationContext(), getString(R.string.error_conexion), 0).show();
		}
	}
}
