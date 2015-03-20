package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTitleBar;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.MediaUtil;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragSubirPerdida extends Fragment implements OnClickListener{

	private EditText txtNombre, txtDescripcion, txtContacto;
	private TextView txtEdad, txtColor, txtSexo, txtTamano, txtRaza, txtMap;
	private ImageView img, btnMap;
	private Spinner spinRaza, spinColor, spinTamano, spinSexo, spinEdad;
	private String usu_id;
	private ProcessImageV2 processImage=null;
	private Bundle bundle;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_subirencontrada, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		paths=new ArrayList<String>();
		mas=null;
		vars.callInfo=false;
		bundle=new Bundle();
		usu_id=getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME, Context.MODE_PRIVATE).getString(GeneralData.PREF_USUID, "null");
		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
		}
		initViews(view);
		if(vars.path.length()>0){
			processImage.loadImage(vars.path, img);
		}
//		clear();
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
	
	public static FragSubirPerdida newInstace(VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestDialog evd, EvtRequestTitleBar ert, EvtActivityResult ear, EvtRequestDialog erd, EvtRequestEscuadron ere) {
		FragSubirPerdida fragSubirPerdida=new FragSubirPerdida();
		fragSubirPerdida.setEvtRequestEscuadron(ere);
		fragSubirPerdida.setEvtRequestSelection(ers);
		fragSubirPerdida.setEvtRequestDialog(erd);
		fragSubirPerdida.setEvtActivityResult(ear);
		fragSubirPerdida.setVars(vars);
		fragSubirPerdida.setMemory(memoryCacheMultimedia);
		fragSubirPerdida.setRetainInstance(true);
		return fragSubirPerdida;
	}
	
	public static void restoreInstace(FragSubirPerdida fragSubirPerdida, VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestDialog evd, EvtRequestTitleBar ert, EvtActivityResult ear, EvtRequestDialog erd, EvtRequestEscuadron ere) {
		fragSubirPerdida.setEvtRequestEscuadron(ere);
		fragSubirPerdida.setEvtRequestSelection(ers);
		fragSubirPerdida.setEvtRequestDialog(erd);
		fragSubirPerdida.setEvtActivityResult(ear);
		fragSubirPerdida.setVars(vars);
		fragSubirPerdida.setMemory(memoryCacheMultimedia);
		fragSubirPerdida.setRetainInstance(true);
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
	
	private void quitAllFocus() {
		// TODO Auto-generated method stub
		try {
			quitFocus(txtContacto);
			quitFocus(txtDescripcion);
			quitFocus(txtNombre);
		} catch (Exception e) {}
	}
	
	private void initViews(View view) {
		((Button)view.findViewById(R.id.fragEnc_guardar)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEnc_camara)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEnc_galeria)).setOnClickListener(this);
		btnMap=((ImageView)view.findViewById(R.id.fragEnc_map));
		btnMap.setOnClickListener(this);
		img=(ImageView) view.findViewById(R.id.fragEnc_img);
		spinColor=(Spinner) view.findViewById(R.id.fragEnc_SpinColor);
		spinEdad=(Spinner) view.findViewById(R.id.fragEnc_SpinEdad);
		spinRaza=(Spinner) view.findViewById(R.id.fragEnc_SpinRaza);
		spinSexo=(Spinner) view.findViewById(R.id.fragEnc_SpinSexo);
		spinTamano=(Spinner) view.findViewById(R.id.fragEnc_SpinTamano);
		txtContacto=(EditText) view.findViewById(R.id.fragEnc_contacto);
		txtDescripcion=(EditText) view.findViewById(R.id.fragEnc_descripcion);
		txtNombre=(EditText) view.findViewById(R.id.fragEnc_nombre);
		
		txtColor=(TextView) view.findViewById(R.id.fragEnc_txtColor);
		txtRaza=(TextView) view.findViewById(R.id.fragEnc_txtRaza);
		txtSexo=(TextView) view.findViewById(R.id.fragEnc_txtSexo);
		txtTamano=(TextView) view.findViewById(R.id.fragEnc_txtTamano);
		txtEdad=(TextView) view.findViewById(R.id.fragEnc_txtEdad);
		txtMap=(TextView) view.findViewById(R.id.fragEnc_txtMap);
		txtMap.setText(getResources().getString(R.string.donde_seperdio));
		
		ArrayAdapter adp;
		
		adp = new ArrayAdapter<String>(getActivity(),  R.layout.spin_text, getResources().getStringArray(R.array.colores));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinColor.setAdapter(adp);
		
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
		
		mas=new ClsMascota("", usu_id, "", "", "", "", "", "", "", "", "", 0, 0, ClsMascota.MAS_PERDIDA, "");
	}
	
	public void setLocation(double lat, double lon) {
		
		mas.setMas_latitud(lat);
		mas.setMas_longitud(lon);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragEnc_map:
			requestMap();
			break;
		case R.id.fragEnc_guardar:
			if(validate()){
				quitFocus(txtDescripcion);
				quitFocus(txtNombre);
				quitFocus(txtContacto);
				bundle.putParcelable("extra", mas);
				vars.callInfo=true;
				if(evtRequestEscuadron!=null){
					evtRequestEscuadron.onEvtRequestEscuadron(true, bundle);
				}
			}
			break; 

		case R.id.fragEnc_galeria:
			MediaUtil.takeFromGallery(getActivity());
			break;
			
		case R.id.fragEnc_camara:
			if(evtActivityResult!=null){
				evtActivityResult.onEvtActivityResult(-1);
			}
			break;
		}
	}
	
	private void requestMap(){
		Bundle args=new Bundle();
		args.putDouble("lat", mas.getMas_latitud());
		args.putDouble("lon", mas.getMas_longitud());
		args.putInt("color", R.color.rojo);
		args.putInt("icono", R.drawable.ic_huellablanca);
		Extra e=new Extra();
		e.tipe=GeneralData.MAP;
		evtRequestDialog.onEvtRequestDialog(e, args);
	}
	
	public void setMapValues(Bundle args) {
		if(mas!=null && args!=null){
			mas.setMas_latitud(args.getDouble("lat"));
			mas.setMas_longitud(args.getDouble("lon"));
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
	
	private String nombre, descripcion, contacto;
	private int edad, sexo, color, raza, tamano;
	private ClsMascota mas=null;
	
    private boolean validate() {
    	clearErrors();
    	
    	nombre=txtNombre.getText().toString().trim().toLowerCase();
    	descripcion=txtDescripcion.getText().toString().trim().toLowerCase();
    	contacto=txtContacto.getText().toString().trim().toLowerCase();
    	color=spinColor.getSelectedItemPosition();
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
                					if(color!=0){
                						mas.setMas_color(""+color);
                						if(raza!=0){
                							mas.setMas_raza(""+raza);
                							if(tamano!=0){
                								mas.setMas_tamano(""+tamano);
                								if(contacto.length()>0){
                									if(contacto.length()<getResources().getInteger(R.integer.chars_114)){
                										mas.setMas_contacto(contacto);
                    									if(mas.getMas_latitud()!=0 && mas.getMas_longitud()!=0){
                    										return true;
                    									}else{
                    										Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneLaUbicacion), 0).show();
                    										btnMap.setBackgroundResource(R.color.rojo);
                    									}	
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
                						txtColor.setError(getString(R.string.campo_requerido));
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
        			txtNombre.setError(getString(R.string.error_chars));	
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
    	txtColor.setError(null);
    	btnMap.setBackgroundResource(R.drawable.selector_map);
	}
    
    private void clear() {
    	clearErrors();
    	vars.path="";
    	txtDescripcion.setText("");
    	txtNombre.setText("");
    	txtContacto.setText("");
    	img.setImageResource(R.drawable.empty_photo);
    	spinColor.setSelection(0);
    	spinEdad.setSelection(0);
    	spinRaza.setSelection(0);
    	spinSexo.setSelection(0);
    	spinTamano.setSelection(0);
	}
	
	public interface EvtRequestEscuadron{
		void onEvtRequestEscuadron(boolean request, Bundle args);
	}
	private EvtRequestEscuadron evtRequestEscuadron;
	private void setEvtRequestEscuadron(EvtRequestEscuadron evt) {
		this.evtRequestEscuadron=evt;
	}
	
	private EvtRequestSelection evtRequestSelection;
	private void setEvtRequestSelection(EvtRequestSelection evt) {
		this.evtRequestSelection=evt;
	}
	private EvtActivityResult evtActivityResult;
	private void setEvtActivityResult(EvtActivityResult evt) {
		this.evtActivityResult=evt;
	}
}
