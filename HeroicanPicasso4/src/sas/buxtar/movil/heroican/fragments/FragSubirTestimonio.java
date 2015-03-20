package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsTestimonio;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FragSubirTestimonio extends Fragment implements OnClickListener, EvtEndInsert{

	private EditText txtTitulo, txtDescripcion;
	private ImageView img;
	private String usu_id;
	private DialogLoad dialLoad;
	private ProcessImageV2 processImage=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_subirtestimonio, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		paths=new ArrayList<String>();
		usu_id=getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE).getString("usu_id", "null");
		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
		}
		txtTitulo = (EditText) view.findViewById(R.id.fragTes_titulo);
        txtDescripcion = (EditText) view.findViewById(R.id.fragTes_descripcion);
        img = (ImageView) view.findViewById(R.id.fragTes_img);
        
        ((Button)view.findViewById(R.id.fragTes_guardar)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.fragTes_galeria)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.fragTes_camara)).setOnClickListener(this);
        
        tes=new ClsTestimonio("", usu_id,"", "", "", "");
        tes.setEvtEndInsert(this);
        dialLoad=new DialogLoad();
        if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);
		}
//        clear();
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
	
	public static FragSubirTestimonio newInstace(VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear) {
		FragSubirTestimonio fragSubirTestimonio=new FragSubirTestimonio();
		fragSubirTestimonio.setEvtRequestTitleBar(ert);
		fragSubirTestimonio.setEvtRequestSelection(ers);
		fragSubirTestimonio.setEvtActivityResult(ear);
		fragSubirTestimonio.setVars(vars);
		fragSubirTestimonio.setMemory(memoryCacheMultimedia);
		fragSubirTestimonio.setRetainInstance(true);
		return fragSubirTestimonio;
	}
	
	public static void restoreInstace(FragSubirTestimonio fragSubirTestimonio, VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear) {
		fragSubirTestimonio.setEvtRequestTitleBar(ert);
		fragSubirTestimonio.setEvtRequestSelection(ers);
		fragSubirTestimonio.setEvtActivityResult(ear);
		fragSubirTestimonio.setVars(vars);
		fragSubirTestimonio.setMemory(memoryCacheMultimedia);
		fragSubirTestimonio.setRetainInstance(true);
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
	
	private void quitAllFocus() {
		// TODO Auto-generated method stub
		try {
			quitFocus(txtTitulo);
			quitFocus(txtDescripcion);
		} catch (Exception e) {}
	}
	
	private void quitFocus(EditText txt) {
		//QUITAR FOCO
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);
	}
	
	private NetWorkState netWorkState=null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragTes_guardar:
			if(validate()){
	    		if(netWorkState==null){
	    			netWorkState=new NetWorkState();
	    		}
	    		if(netWorkState.isOnLine(getActivity())){
					dialLoad.show(getFragmentManager(), "dialLoad");
					tes.insert();		
	    		}else{
	    			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
	    		}
			}
			break; 

		case R.id.fragTes_galeria:
			MediaUtil.takeFromGallery(getActivity());
			break;
			
		case R.id.fragTes_camara:
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
	
	
	private String titulo, descripcion;
	private ClsTestimonio tes;
	
    private boolean validate() {
    	txtTitulo.setError(null);
    	txtDescripcion.setError(null);
    	
    	titulo=txtTitulo.getText().toString().trim().toLowerCase();
    	descripcion=txtDescripcion.getText().toString().trim().toLowerCase();
    	if(vars.path.length()>0){
    		tes.setTes_pathimage(vars.path);
        	if(titulo.length()>0){
        		if(titulo.length()<getResources().getInteger(R.integer.chars_114)){
        			tes.setTes_titulo(titulo);
            		if(descripcion.length()>0){
            			if(descripcion.length()<getResources().getInteger(R.integer.chars_descripcion)){
                			tes.setTes_descripcion(descripcion);
            				return true;	
            			}else{
            				txtDescripcion.setError(getString(R.string.error_chars));		
            			}
            		}else{
            			txtDescripcion.setError(getString(R.string.campo_requerido));
            		}	
        		}else{
        			txtTitulo.setError(getString(R.string.error_chars));
        		}
        	}else{
        		txtTitulo.setError(getString(R.string.campo_requerido));
        	}	
    	}else{
    		Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneImagen), 0).show();
    	}
    	return false;
	}
    
    private void clear() {
    	txtTitulo.setError(null);
    	txtDescripcion.setError(null);
    	vars.path="";
    	txtDescripcion.setText("");
    	txtTitulo.setText("");
    	img.setImageResource(R.drawable.empty_photo);
	}

	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			clear();	
			if(evtRequestSelection!=null){
				evtRequestSelection.onEvtRequestSelection(R.id.drawer_btnTestimonios);
				if(evtRequestTitleBar!=null){
					evtRequestTitleBar.onEvtRequestTitleBar(getString(R.string.drawer_Testimonios));
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
