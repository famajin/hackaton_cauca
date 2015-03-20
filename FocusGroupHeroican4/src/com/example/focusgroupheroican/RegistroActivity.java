package com.example.focusgroupheroican;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusgroupheroican.clases.ClsUsuario;
import com.example.focusgroupheroican.fragments.DialogLoad;
import com.example.focusgroupheroican.util.Data;
import com.example.focusgroupheroican.util.EvtEndInsert;
import com.example.focusgroupheroican.util.GeneralData;

public class RegistroActivity extends ActionBarActivity implements OnClickListener, OnSeekBarChangeListener, EvtEndInsert{

	private ClsUsuario usu;
	private Button btnFem, btnMas;
	private EditText txtEmail, txtNombre;
	private TextView txtEdad;
	private SeekBar seekEdad;
	private Spinner spinCargo;
	private Data db;
	private ArrayList<String> edades;
	private FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		db=new Data(this);
		usu=new ClsUsuario();
		
		fragmentManager=getSupportFragmentManager();
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
        ((Button)findViewById(R.id.reg_btn)).setOnClickListener(this);
        
		txtEdad=(TextView) findViewById(R.id.reg_txtEdad);
		txtNombre=(EditText) findViewById(R.id.reg_txtNombre);
		txtEmail=(EditText) findViewById(R.id.reg_txtEmail);
		
		btnFem=(Button)findViewById(R.id.reg_btnFem);
		btnFem.setOnClickListener(this);
		
		btnMas=(Button)findViewById(R.id.reg_btnMas);
		btnMas.setOnClickListener(this);
		
		
		ArrayList<String> array=db.getArray("SELECT carg_description FROM tbl_cargo");
		ArrayList<String> cargos=new ArrayList<String>();
		cargos.add("Seleccione");
		for (String s : array) {
			cargos.add(s);
		}
		
		ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cargos);
		adp.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spinCargo=(Spinner) findViewById(R.id.reg_spinCargo);
		spinCargo.setAdapter(adp);
		
		edades=db.getArray("SELECT edad_description FROM tbl_edad");
		
		seekEdad=(SeekBar) findViewById(R.id.reg_seekEdad);
		seekEdad.setMax(edades.size()-1);
		seekEdad.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
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

	private DialogLoad dialLoad;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.reg_btnMas:
			btnMas.setBackgroundResource(R.color.press_bool_not);
			btnFem.setBackgroundResource(R.drawable.selector_light);
			usu.usu_sex=1;
			break;
			
		case R.id.reg_btnFem:
			btnFem.setBackgroundResource(R.color.press_bool_yes);
			btnMas.setBackgroundResource(R.drawable.selector_light);
			usu.usu_sex=2;
			break;
			
		case R.id.reg_btn:
			usu.usu_email=txtEmail.getText().toString().trim().toLowerCase();
			usu.usu_nombre=txtNombre.getText().toString().trim().toLowerCase();
			usu.carg_id=spinCargo.getSelectedItemPosition();
			if(usu.edad_id!=-1 && usu.carg_id!=0 && usu.edad_id!=-1 && usu.usu_sex!=-1 && usu.usu_email.length()>0&& usu.usu_nombre.length()>0){
				dialLoad=new DialogLoad();
				dialLoad.show(getSupportFragmentManager(), "DialogLoad");
				
				usu.exists(this);
				
//				if(!db.existsUsuario(usu.usu_email)){
//					if(db.exeDML(usu.getSQL())){
//						Intent i=new Intent(RegistroActivity.this, EncuestaActivity.class);
//						i.putExtra("usu_email", usu.usu_email);
//						startActivity(i);	
//					}else{
//						Toast.makeText(getApplicationContext(), "Error\nIntenta de nuevo", 0).show();	
//					}
//				}else{
//					Toast.makeText(getApplicationContext(), "Ya llenaste la encuesta", 0).show();	
//				}
			}else{
				Toast.makeText(getApplicationContext(), "Completa todos los campos", 0).show();
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		txtEdad.setText(getString(R.string.edad)+"\n"+edades.get(progress)+" años");
		usu.edad_id=progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {}

	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		try {
			fragmentManager.beginTransaction()
			.remove(dialLoad).commit();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(was!=null){
			if(was.equals(GeneralData.WORK)){
				Intent i=new Intent(RegistroActivity.this, EncuestaActivity.class);
				i.putExtra("usu_email", usu.usu_email);
				i.putExtra("usu_nombre", usu.usu_nombre);
				i.putExtra("edad_id", usu.edad_id);
				i.putExtra("usu_sex", usu.usu_sex);
				i.putExtra("carg_id", usu.carg_id);
				startActivity(i);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.ya_llenaste), 0).show();	
			}
		}else{
			Toast.makeText(getApplicationContext(), getString(R.string.error_conexion),0).show();
		}
	}
	
}
