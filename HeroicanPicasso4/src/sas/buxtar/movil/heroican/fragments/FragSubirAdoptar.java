package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTitleBar;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.MediaUtil;
import sas.buxtar.movil.heroican.util.NetWorkState;
import sas.buxtar.movil.heroican.vivelabs.CacheUtils;
import sas.buxtar.movil.heroican.vivelabs.ProcessImageV2;
import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragSubirAdoptar extends Fragment implements OnClickListener, EvtEndInsert{
	
	private String usu_id;
	private EditText txtNombre, txtDescripcion, txtContacto;
	private ImageView img;
	private Spinner spinRaza, spinTamano, spinSexo, spinEdad;
	private TextView txtEdad, txtSexo, txtTamano, txtRaza;
	private DialogLoad dialLoad;
	private ProcessImageV2 processImage=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_subirmascota, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		paths=new ArrayList<String>();
		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
		}
		usu_id=getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE).getString("usu_id", "null");
		initViews(view);
		if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);
		}
//		clear();
	}
	
	private LruCache<String,Bitmap> memory;
	private void setMemory(LruCache<String,Bitmap> memoryCache) {
		this.memory=memoryCache;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		quitAllFocus();
		img.setImageBitmap(null);
		clearMemory();
		super.onDestroy();
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static FragSubirAdoptar newInstace(VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear) {
		FragSubirAdoptar fragSubirAdoptar=new FragSubirAdoptar();
		fragSubirAdoptar.setEvtRequestTitleBar(ert);
		fragSubirAdoptar.setEvtActivityResult(ear);
		fragSubirAdoptar.setEvtRequestSelection(ers);
		fragSubirAdoptar.setVars(vars);
		fragSubirAdoptar.setMemory(memoryCacheMultimedia);
		fragSubirAdoptar.setRetainInstance(true);
		return fragSubirAdoptar;
	}
	
	public static void restoreInstace(FragSubirAdoptar fragSubirAdoptar, VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear) {
		fragSubirAdoptar.setEvtRequestTitleBar(ert);
		fragSubirAdoptar.setEvtActivityResult(ear);
		fragSubirAdoptar.setEvtRequestSelection(ers);
		fragSubirAdoptar.setVars(vars);
		fragSubirAdoptar.setMemory(memoryCacheMultimedia);
		fragSubirAdoptar.setRetainInstance(true);
	}
	
	private VARS vars;
	private void setVars(VARS vars) {
		this.vars=vars;
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
	
	private void quitFocus(EditText txt) {
		//QUITAR FOCO
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);
	}
	
	private void quitAllFocus() {
		// TODO Auto-generated method stub
//		try {
//			quitFocus(txtContacto);
//			quitFocus(txtDescripcion);
//			quitFocus(txtNombre);
//		} catch (Exception e) {}
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	private void initViews(View view) {
		((Button)view.findViewById(R.id.fragAdop_guardar)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragAdop_camara)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragAdop_galeria)).setOnClickListener(this);
		img=(ImageView) view.findViewById(R.id.fragAdop_img);
		spinEdad=(Spinner) view.findViewById(R.id.fragAdop_SpinEdad);
		spinRaza=(Spinner) view.findViewById(R.id.fragAdop_SpinRaza);
		spinSexo=(Spinner) view.findViewById(R.id.fragAdop_SpinSexo);
		spinTamano=(Spinner) view.findViewById(R.id.fragAdop_SpinTamano);
		txtContacto=(EditText) view.findViewById(R.id.fragAdop_contacto);
		txtDescripcion=(EditText) view.findViewById(R.id.fragAdop_descripcion);
		txtNombre=(EditText) view.findViewById(R.id.fragAdop_nombre);
		
		txtRaza=(TextView) view.findViewById(R.id.fragAdop_txtRaza);
		txtSexo=(TextView) view.findViewById(R.id.fragAdop_txtSexo);
		txtTamano=(TextView) view.findViewById(R.id.fragAdop_txtTamano);
		txtEdad=(TextView) view.findViewById(R.id.fragAdop_txtEdad);
		
		ArrayAdapter adp;		
		
		adp = new ArrayAdapter<String>(getActivity(),  R.layout.spin_text, getResources().getStringArray(R.array.edades));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinEdad.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(),  R.layout.spin_text, getResources().getStringArray(R.array.razas));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinRaza.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(),  R.layout.spin_text, getResources().getStringArray(R.array.genero));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinSexo.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(),  R.layout.spin_text, getResources().getStringArray(R.array.tamano));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinTamano.setAdapter(adp);
		
		mas=new ClsMascota("", usu_id, "", "", "", "", "", "", "", "", "", 0, 0, ClsMascota.MAS_ADOPTAR, "");
		mas.setEvtEndInsert(this);
		
		dialLoad=new DialogLoad();
	}
	
	private NetWorkState netWorkState=null;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragAdop_guardar:
			if(validate()){
	    		if(netWorkState==null){
	    			netWorkState=new NetWorkState();
	    		}
	    		if(netWorkState.isOnLine(getActivity())){
					dialLoad.show(getFragmentManager(), "dialLoad");
					mas.insert();		
	    		}else{
	    			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
	    		}
			}
			break; 

		case R.id.fragAdop_galeria:
			MediaUtil.takeFromGallery(getActivity());
			break;
			
		case R.id.fragAdop_camara:
			if(evtActivityResult!=null){
				evtActivityResult.onEvtActivityResult(-1);
			}
			break;
		}
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
	
	private String nombre, descripcion, contacto;
	private int edad, sexo, raza, tamano;
	private ClsMascota mas;
	
    private boolean validate() {
    	clearErrors();
    	
    	nombre=txtNombre.getText().toString().trim().toLowerCase();
    	descripcion=txtDescripcion.getText().toString().trim().toLowerCase();
    	contacto=txtContacto.getText().toString().trim().toLowerCase();
		mas.setMas_color(""+0);
    	edad=spinEdad.getSelectedItemPosition();
    	sexo=spinSexo.getSelectedItemPosition();
    	raza=spinRaza.getSelectedItemPosition();
    	tamano=spinTamano.getSelectedItemPosition();
    	if(vars.path.length()>0){
    		mas.setMas_pathimage(vars.path);
        	if(nombre.length()>0){
        		if(nombre.length()<getResources().getInteger(R.integer.chars_nombres_mascotas)){
        			mas.setMas_nombre(nombre);
            		if(descripcion.length()>0){
            			if(descripcion.length()<getResources().getInteger(R.integer.chars_descripcion)){
            				mas.setMas_descripcion(descripcion);
                			if(edad!=0){
                				mas.setMas_edad(""+edad);
                				if(sexo!=0){
                					mas.setMas_sexo(""+sexo);
            						if(raza!=0){
            							mas.setMas_raza(""+raza);
            							if(tamano!=0){
            								mas.setMas_tamano(""+tamano);
            								if(contacto.length()>0){
            									if(contacto.length()<getResources().getInteger(R.integer.chars_114)){
            										mas.setMas_contacto(contacto);
                									return true;	
            									}else{
            										txtContacto.setError(getString(R.string.error_chars));
            									}
            								}else{
            									txtContacto.setError(getString(R.string.campo_requerido));
            								}
            							}else{
            								txtTamano.setError(getString(R.string.campo_requerido));
            							}
            						}else{
                							txtRaza.setError(getString(R.string.campo_requerido));
                						}
                				}else{
                					txtSexo.setError(getString(R.string.campo_requerido));
                				}
                			}else{
                				txtEdad.setError(getString(R.string.campo_requerido));
                			}
            			}else{
            				txtDescripcion.setError(getString(R.string.error_chars));
            			}
            		}else{
            			txtDescripcion.setError(getString(R.string.campo_requerido));
            		}	
        		}else{
        			txtNombre.setError(getResources().getString(R.string.campo_requerido));
        		}
        	}else{
        		txtNombre.setError(getString(R.string.campo_requerido));
        	}	
    	}else{
    		Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneImagen), 0).show();
    	}
    	return false;
	}
    
    private void clearErrors() {
    	txtContacto.setError(null);
    	txtDescripcion.setError(null);
    	txtNombre.setError(null);
    	
    	txtEdad.setError(null);
    	txtSexo.setError(null);
    	txtTamano.setError(null);
    	txtRaza.setError(null);
	}
    
    private void clear() {
    	clearErrors();
    	vars.path="";
    	txtDescripcion.setText("");
    	txtNombre.setText("");
    	txtContacto.setText("");
    	img.setImageResource(R.drawable.empty_photo);
    	spinEdad.setSelection(0);
    	spinRaza.setSelection(0);
    	spinSexo.setSelection(0);
    	spinTamano.setSelection(0);
	}
	
	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			clear();
			if(evtRequestSelection!=null){
				evtRequestSelection.onEvtRequestSelection(R.id.drawer_btnAdopta);
				if(evtRequestTitleBar!=null){
					evtRequestTitleBar.onEvtRequestTitleBar(getString(R.string.drawer_Adopta));
				}
			}
		}else{
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
		if(dialLoad==null){
			dialLoad=(DialogLoad) (DialogLoad)getFragmentManager().findFragmentByTag("dialLoad");
		}
		if(dialLoad!=null){
			try {
				dialLoad.getDialog().cancel();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private EvtRequestSelection evtRequestSelection;
	private void setEvtRequestSelection(EvtRequestSelection evt) {
		this.evtRequestSelection=evt;
	}
	
	private EvtRequestTitleBar evtRequestTitleBar;
	private void setEvtRequestTitleBar(EvtRequestTitleBar evt) {
		this.evtRequestTitleBar=evt;
	}
	
	private EvtActivityResult evtActivityResult;
	private void setEvtActivityResult(EvtActivityResult evt) {
		this.evtActivityResult=evt;
	}

}
