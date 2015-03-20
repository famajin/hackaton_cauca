package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.RegistroFundacionActivity.VARS_FUNDACION;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.util.MediaUtil;
import sas.buxtar.movil.heroican.vivelabs.CacheUtils;
import sas.buxtar.movil.heroican.vivelabs.ProcessImageV2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragRegFun_1 extends Fragment implements OnClickListener{

	private EditText txtNombre, txtNit, txtTelefono, txtDireccion;
	private String nom, nit, tel;
	private ClsFundacion fun;
	private ImageView img;
	private ProcessImageV2 processImage=null;
	private Spinner spinCiudad;
	private TextView txtCiudad;
	private String ciudad, direccion;
	
	public FragRegFun_1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_regfun1, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		paths=new ArrayList<String>();
		txtDireccion=(EditText)view.findViewById(R.id.regFun_txtDireccion);
		spinCiudad=(Spinner)view.findViewById(R.id.regFun_spinCiudad);
		txtCiudad=(TextView)view.findViewById(R.id.regFun_txtCiudad);
		ArrayAdapter adp;
		
		adp = new ArrayAdapter<String>(getActivity(), R.layout.spin_text, getResources().getStringArray(R.array.ciudades));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinCiudad.setAdapter(adp);
		img=(ImageView) view.findViewById(R.id.regFun_img);
		txtNombre=(EditText)view.findViewById(R.id.regFun_txtNombre);
		txtTelefono=(EditText)view.findViewById(R.id.regFun_txtTelefono);
		txtNit=(EditText)view.findViewById(R.id.regFun_txtNit);
		((ImageView)view.findViewById(R.id.regFun_camara)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.regFun_galeria)).setOnClickListener(this);
		
		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
		}
		
		if(!vars.path.equals("")){
			try {
				processImage.loadImage(vars.path, img);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		txtNit.setText(fun.getFun_nit());
		txtTelefono.setText(fun.getFun_telefono());
		txtDireccion.setText(fun.getFun_direccion());
		spinCiudad.setSelection(Integer.parseInt(fun.getFun_ciudad()));
		txtNombre.setText(fun.getFun_nombre());
		if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);	
		}
		
		if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);
		}
		txtDireccion.requestFocus();
		txtNombre.requestFocus();
		clearErrors();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {}
		img.setImageBitmap(null);
		clearMemory();
		super.onDestroy();
	}
	
	private LruCache<String,Bitmap> memory;
	private void setMemory(LruCache<String,Bitmap> memoryCache) {
		this.memory=memoryCache;
	}

	public void ActivityResult(int requestCode, Intent data) {
		switch (requestCode) {
		case MediaUtil.CAMERA:
			processImage=new ProcessImageV2(getActivity(), memory, false);
			if(this.paths==null){
				paths=new ArrayList<String>();
			}
			img.setImageBitmap(null);
			clearMemory();
			processImage.loadImage(vars.path, img);
			paths.add(vars.path);
			break;
	
		case MediaUtil.GALLERY:
			img.setImageBitmap(null);
			clearMemory();
			vars.path=MediaUtil.getPathFromURI(getActivity(), data.getData());
			processImage.loadImage(vars.path, img);
			paths.add(vars.path);
			break;
		}
	}
	
	private ArrayList<String> paths;
	private void clearMemory() {
		if(paths!=null){
			if(paths.size()>0){
				try {
					CacheUtils cacheUtils=new CacheUtils(getActivity());
					cacheUtils.clearCacheMemory(paths, memory);
					cacheUtils=null;
					paths.clear();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private void clearErrors() {
		txtNit.setError(null);
		txtNombre.setError(null);
		txtTelefono.setError(null);
		txtDireccion.setError(null);
		txtCiudad.setError(null);
	}
	
	public boolean validate() {
		clearErrors();
		nom=txtNombre.getText().toString().toLowerCase();
		tel=txtTelefono.getText().toString().toLowerCase();
		nit=txtNit.getText().toString().toLowerCase();
		direccion=txtDireccion.getText().toString().toLowerCase();
		ciudad=""+spinCiudad.getSelectedItemPosition();
		if(nom.length()>0){
			if(nom.length()<getResources().getInteger(R.integer.chars_60)){
				fun.setFun_nombre(nom);
				if(nit.length()>0){
					if(nit.length()<getResources().getInteger(R.integer.chars_ids)){
						fun.setFun_nit(nit);
						if(tel.length()>0){
							if(tel.length()<getResources().getInteger(R.integer.chars_60)){
								fun.setFun_telefono(tel);
								if(!ciudad.equals("0")){
									fun.setFun_ciudad(ciudad);
									if(direccion.length()>0){
										if(direccion.length()<getResources().getInteger(R.integer.chars_114)){
											fun.setFun_direccion(direccion);
											if(vars.path.length()>0){
												fun.setFun_pathimage(vars.path);
												return true;
											}else{
												Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneImagen), 0).show();
											}	
										}else{
											txtDireccion.setError(getResources().getString(R.string.error_chars));	
										}
									}else{
										txtDireccion.setError(getResources().getString(R.string.campo_requerido));
									}
								}else{
									txtCiudad.setError(getResources().getString(R.string.campo_requerido));
								}
							}else{
								txtTelefono.setError(getResources().getString(R.string.error_chars));
							}
						}else{
							txtTelefono.setError(getResources().getString(R.string.campo_requerido));
						}	
					}else{
						txtNit.setError(getResources().getString(R.string.error_chars));
					}
				}else{
					txtNit.setError(getResources().getString(R.string.campo_requerido));
				}	
			}else{
				txtNombre.setError(getResources().getString(R.string.error_chars));	
			}
		}else{
			txtNombre.setError(getResources().getString(R.string.campo_requerido));
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			
		case R.id.regFun_galeria:
			if(evtActivityResult!=null){
				evtActivityResult.onEvtActivityResult(MediaUtil.GALLERY);
			}
			
			break;
			
		case R.id.regFun_camara:
			if(evtActivityResult!=null){
				evtActivityResult.onEvtActivityResult(MediaUtil.CAMERA);
			}
			break;
		}
	}
	
	public static FragRegFun_1 newInstace(VARS_FUNDACION vars, EvtActivityResult evt, LruCache<String,Bitmap> memoryCache, ClsFundacion fundation) {
		FragRegFun_1 frag=new FragRegFun_1();
		frag.setEvtActivityResult(evt);
		frag.setFundation(fundation);
		frag.setVars(vars);
		frag.setMemory(memoryCache);
		frag.setRetainInstance(true);
		return frag;
	}
	
	public static void restoreInstace(VARS_FUNDACION vars, FragRegFun_1 frag, EvtActivityResult evt, LruCache<String,Bitmap> memoryCache, ClsFundacion fundation){
		frag.setEvtActivityResult(evt);
		frag.setMemory(memoryCache);
		frag.setFundation(fundation);
		frag.setVars(vars);
		frag.setRetainInstance(true);
	}
	private void setFundation(ClsFundacion fundation) {
		this.fun=fundation;
	}
	private VARS_FUNDACION vars;
	private void setVars(VARS_FUNDACION vars) {
		this.vars=vars;
	}
	
	private EvtActivityResult evtActivityResult;
	private void setEvtActivityResult(EvtActivityResult evt) {
		this.evtActivityResult=evt;
	}
	
}



