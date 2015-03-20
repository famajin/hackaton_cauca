package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsEvento;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDataTime;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTitleBar;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.LocationConexion.EvtChangeLocation;
import sas.buxtar.movil.heroican.util.LocationConexion.EvtStateGps;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class FragSubirEvento extends Fragment implements OnClickListener, EvtEndInsert, EvtChangeLocation, EvtStateGps{
	
	private EditText txtTitulo, txtDescripcion, txtFecha, txtLugar, txtContacto;
	private String usu_id;
	private ImageView img, btnMap;
	private DialogLoad dialLoad;
	private String titulo, descripcion, fecha, lugar, contacto, direccion="";
	private ClsEvento evt;
	private ProcessImageV2 processImage=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_subirevento, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
		}
		paths=new ArrayList<String>();
		usu_id=getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE).getString("usu_id", "null");
		((Button)view.findViewById(R.id.fragEvt_guardar)).setOnClickListener(this);
		btnMap=((ImageView)view.findViewById(R.id.fragEvt_map));
		btnMap.setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEvt_camara)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEvt_galeria)).setOnClickListener(this);
		
		txtTitulo = (EditText) view.findViewById(R.id.fragEvt_titulo);
	    txtDescripcion = (EditText) view.findViewById(R.id.fragEvt_descripcion);
	    txtFecha = (EditText) view.findViewById(R.id.fragEvt_fecha);
	    txtLugar = (EditText) view.findViewById(R.id.fragEvt_lugar);
	    txtContacto = (EditText) view.findViewById(R.id.fragEvt_contacto);
	    img=(ImageView) view.findViewById(R.id.fragEvt_img);
	    ((ImageView)view.findViewById(R.id.fragEvt_btnFecha)).setOnClickListener(this);
		dialLoad=new DialogLoad();
		evt=new ClsEvento("", usu_id, "", "", "", "", "", "", 2.447575639600874, -76.60899639129639, "");
		evt.setEvtEndInsert(this);
		
		if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);
		}
		txtLugar.setText(direccion);
//		clear();
	}

	private void quitAllFocus() {
		// TODO Auto-generated method stub
		try {
			quitFocus(txtContacto);
			quitFocus(txtDescripcion);
			quitFocus(txtFecha);
			quitFocus(txtLugar);
			quitFocus(txtTitulo);
		} catch (Exception e){}
	}
	
	public static FragSubirEvento newInstace(VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear, EvtRequestDialog erd, EvtRequestDataTime erdt) {
		FragSubirEvento fragSubirEvento=new FragSubirEvento();
		fragSubirEvento.setEvtRequestTitleBar(ert);
		fragSubirEvento.setEvtRequestSelection(ers);
		fragSubirEvento.setEvtRequestDialog(erd);
		fragSubirEvento.setVars(vars);
		fragSubirEvento.setEvtRequestDataTime(erdt);
		fragSubirEvento.setMemory(memoryCacheMultimedia);
		fragSubirEvento.setEvtActivityResult(ear);
		fragSubirEvento.setRetainInstance(true);
		return fragSubirEvento;
	}
	
	public static void restoreInstace(FragSubirEvento fragSubirEvento, VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestTitleBar ert, EvtActivityResult ear, EvtRequestDialog erd, EvtRequestDataTime erdt) {
		fragSubirEvento.setEvtRequestTitleBar(ert);
		fragSubirEvento.setEvtRequestSelection(ers);
		fragSubirEvento.setEvtRequestDialog(erd);
		fragSubirEvento.setVars(vars);
		fragSubirEvento.setEvtActivityResult(ear);
		fragSubirEvento.setMemory(memoryCacheMultimedia);
		
		fragSubirEvento.setEvtRequestDataTime(erdt);
		fragSubirEvento.setRetainInstance(true);
	}
	
	private VARS vars;
	private void setVars(VARS vars) {
		this.vars=vars;
	}
	
	private LruCache<String,Bitmap> memory;
	private void setMemory(LruCache<String,Bitmap> memoryCache) {
		this.memory=memoryCache;
	}

	private void quitFocus(EditText txt) {
		//QUITAR FOCO
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		txtLugar=null;
		quitAllFocus();
		img.setImageBitmap(null);
		clearMemory();
		super.onDestroy();
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {}
	}
	
	public void setLocation(String direccion, double lat, double lon) {
		if(!direccion.equals("")){
			this.direccion=direccion;	
		}
		if(txtLugar!=null){
			txtLugar.setText(direccion);
			evt.setEvt_latitud(lat);
			evt.setEvt_longitud(lon);
		}
	}

	public void setFecha(String fecha) {
		txtFecha.setText(fecha);
	}
	private NetWorkState netWorkState=null;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragEvt_guardar:
			if(validate()){
	    		if(netWorkState==null){
	    			netWorkState=new NetWorkState();
	    		}
	    		if(netWorkState.isOnLine(getActivity())){
					dialLoad.show(getFragmentManager(), "dialLoad");
					evt.insert();	
	    		}else{
	    			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
	    		}
			}
			break;
		case R.id.fragEvt_map:
			requestMap();
			break;
			
		case R.id.fragEvt_btnFecha:
			if(evtRequestDataTime!=null){
				evtRequestDataTime.onEvtRequestDataTime();
			}
			break;
			
		case R.id.fragEvt_galeria:
			MediaUtil.takeFromGallery(getActivity());
			break;
			
		case R.id.fragEvt_camara:
			if(evtActivityResult!=null){
				evtActivityResult.onEvtActivityResult(-1);
			}
			break;
		}
	}
	
	private void requestMap(){
		Bundle args=new Bundle();
		args.putDouble("lat", evt.getEvt_latitud());
		args.putDouble("lon", evt.getEvt_longitud());
		args.putInt("color", R.color.amarillo);
		args.putInt("icono", R.drawable.ic_regaloblanco);
		Extra e=new Extra();
		e.tipe=GeneralData.MAP;
		evtRequestDialog.onEvtRequestDialog(e, args);
	}
	
	public void setMapValues(Bundle args) {
		if(evt!=null && args!=null){
			direccion=args.getString("direccion");
			evt.setEvt_latitud(args.getDouble("lat"));
			evt.setEvt_longitud(args.getDouble("lon"));
			if(txtLugar!=null){
				try {
					txtLugar.setText(direccion);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private EvtRequestDialog evtRequestDialog;
	private void setEvtRequestDialog(EvtRequestDialog evt) {
		evtRequestDialog=evt;
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
	
	private boolean validate() {
    	
    	titulo=txtTitulo.getText().toString().trim().toLowerCase();
    	descripcion=txtDescripcion.getText().toString().trim().toLowerCase();
    	fecha=txtFecha.getText().toString().trim().toLowerCase();
    	lugar=txtLugar.getText().toString().trim().toLowerCase();
    	contacto=txtContacto.getText().toString().trim().toLowerCase();
    	
    	if(vars.path.length()>0){
    		evt.setEvt_pathimage(vars.path);
        	if(titulo.length()>0){
        		if(titulo.length()<getResources().getInteger(R.integer.chars_114)){
        			evt.setEvt_titulo(titulo);
            		if(descripcion.length()>0){
            			if(descripcion.length()<getResources().getInteger(R.integer.chars_descripcion)){
            				evt.setEvt_descripcion(descripcion);
            				if(fecha.length()>0){
                				evt.setEvt_fecha(fecha);
                				if(lugar.length()>0){
                					if(lugar.length()<getResources().getInteger(R.integer.chars_114)){
                						evt.setEvt_lugar(lugar);
                    					if(contacto.length()>0){
                    						if(contacto.length()<getResources().getInteger(R.integer.chars_114)){
                    							evt.setEvt_contacto(contacto);
                								if(evt.getEvt_latitud()!=0){
                									return true;
                								}else{
                									Toast.makeText(getActivity(), getResources().getString(R.string.error_ubicacion), 0).show();
                									btnMap.setBackgroundResource(R.color.rojo);
                								}
                    						}else{
                    							txtContacto.setError(getString(R.string.error_chars));
                    						}
                    					}else{
                    						txtContacto.setError(getString(R.string.campo_requerido));	
                    					}	
                					}else{
                						txtLugar.setError(getString(R.string.error_chars));
                					}
                				}else{
                					txtLugar.setError(getString(R.string.campo_requerido));
                				}
                			}else {
        						txtFecha.setError(getString(R.string.campo_requerido));
        					}	
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
    
	private void clearErrors() {
		txtContacto.setError(null);
		txtDescripcion.setError(null);
		txtFecha.setError(null);
		txtLugar.setError(null);
		txtTitulo.setError(null);
		btnMap.setBackgroundResource(R.drawable.selector_map);
	}
	
    private void clear() {
    	clearErrors();
		txtDescripcion.setText("");
		txtTitulo.setText("");
		txtFecha.setText("");
		txtContacto.setText("");
		txtLugar.setText("");
		lugar="";
		vars.path="";
		img.setImageResource(R.drawable.empty_photo);
    }

	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			clear();
			if(evtRequestSelection!=null){
				evtRequestSelection.onEvtRequestSelection(R.id.drawer_btnEventos);
				if(evtRequestTitleBar!=null){
					evtRequestTitleBar.onEvtRequestTitleBar(getString(R.string.drawer_Eventos));
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
	
	

	@Override
	public void OnStateGps(boolean isActivated) {
		// TODO Auto-generated method stub
//		Toast.makeText(getActivity(), "GPS Is Activated "+isActivated, 0).show();
	}

	@Override
	public void OnChangeLocation(double latitud, double longitud,
			String direccion) {
		// TODO Auto-generated method stub
//		Toast.makeText(getActivity(), "LAT: "+latitud+"\nLONG: "+longitud+"\nDIRECCION: "+direccion, 0).show();
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
	
	private EvtRequestDataTime evtRequestDataTime;
	private void setEvtRequestDataTime(EvtRequestDataTime evt) {
		this.evtRequestDataTime=evt;
	}	

}
