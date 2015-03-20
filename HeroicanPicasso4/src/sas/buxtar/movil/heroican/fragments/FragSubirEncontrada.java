package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragSubirEncontrada extends Fragment implements OnClickListener, EvtEndInsert{

	private EditText txtDescripcion, txtContacto;
	private TextView txtEdad, txtSexo, txtTamano, txtRaza, txtMap;
	private ImageView img, btnMap;
	private Spinner spinRaza, spinTamano, spinSexo, spinEdad;
	private ScrollView scrollView;
	private String usu_id;
	private DialogLoad dialLoad;
	private String TAG="MASCOTA_ENCONTRADA";
	private ProcessImageV2 processImage=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_subirperdida, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		paths=new ArrayList<String>();
		usu_id=getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE).getString("usu_id", "null");
//		if(processImage==null){
			processImage=new ProcessImageV2(getActivity(), memory, false);
//		}
		initViews(view);
		if(vars!=null){
			if(vars.path.length()>0){
				processImage.loadImage(vars.path, img);
			}	
		}
	}
	
	public static FragSubirEncontrada newInstace(VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestDialog evd, EvtRequestTitleBar ert, EvtActivityResult ear) {
		FragSubirEncontrada fragsubirEncontrada=new FragSubirEncontrada();
		fragsubirEncontrada.setEvtRequestSelection(ers);
		fragsubirEncontrada.setEvtRequestDialog(evd);
		fragsubirEncontrada.setVars(vars);
		fragsubirEncontrada.setEvtRequestTitleBar(ert);
		fragsubirEncontrada.setEvtActivityResult(ear);
		fragsubirEncontrada.setMemory(memoryCacheMultimedia);
		fragsubirEncontrada.setRetainInstance(true);
		return fragsubirEncontrada;
	}
	
	public static void restoreInstace(FragSubirEncontrada fragsubirEncontrada, VARS vars, LruCache<String, Bitmap> memoryCacheMultimedia, EvtRequestSelection ers, EvtRequestDialog evd, EvtRequestTitleBar ert, EvtActivityResult ear) {
		fragsubirEncontrada.setEvtRequestTitleBar(ert);
		fragsubirEncontrada.setEvtRequestSelection(ers);
		fragsubirEncontrada.setEvtRequestDialog(evd);
		fragsubirEncontrada.setVars(vars);
		fragsubirEncontrada.setEvtActivityResult(ear);
		fragsubirEncontrada.setMemory(memoryCacheMultimedia);
		fragsubirEncontrada.setRetainInstance(true);
	}
	
	private VARS vars;
	private void setVars(VARS vars) {
		this.vars=vars;
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
		} catch (Exception e) {}
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
		try {
			quitFocus(txtContacto);
			quitFocus(txtDescripcion);
		} catch (Exception e) {}
	}
	
	private void initViews(View view) {
		scrollView=(ScrollView)getView().findViewById(R.id.fragEnc_scrollV);
		((Button)view.findViewById(R.id.fragEnc_guardar)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEnc_camara)).setOnClickListener(this);
		((ImageView)view.findViewById(R.id.fragEnc_galeria)).setOnClickListener(this);
		btnMap=((ImageView)view.findViewById(R.id.fragEnc_map));
		btnMap.setOnClickListener(this);
		img=(ImageView) view.findViewById(R.id.fragEnc_img);
		spinEdad=(Spinner) view.findViewById(R.id.fragEnc_SpinEdad);
		spinRaza=(Spinner) view.findViewById(R.id.fragEnc_SpinRaza);
		spinSexo=(Spinner) view.findViewById(R.id.fragEnc_SpinSexo);
		spinTamano=(Spinner) view.findViewById(R.id.fragEnc_SpinTamano);
		txtContacto=(EditText) view.findViewById(R.id.fragEnc_contacto);
		txtDescripcion=(EditText) view.findViewById(R.id.fragEnc_descripcion);
		
		txtRaza=(TextView) view.findViewById(R.id.fragEnc_txtRaza);
		txtSexo=(TextView) view.findViewById(R.id.fragEnc_txtSexo);
		txtTamano=(TextView) view.findViewById(R.id.fragEnc_txtTamano);
		txtEdad=(TextView) view.findViewById(R.id.fragEnc_txtEdad);
		txtMap=(TextView) view.findViewById(R.id.fragEnc_txtMap);
		
		
		ArrayAdapter adp;
		
		adp = new ArrayAdapter<String>(getActivity(), R.layout.spin_text, getResources().getStringArray(R.array.edades));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinEdad.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(), R.layout.spin_text, getResources().getStringArray(R.array.razas));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinRaza.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(), R.layout.spin_text, getResources().getStringArray(R.array.genero));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinSexo.setAdapter(adp);
		
		adp = new ArrayAdapter<String>(getActivity(), R.layout.spin_text, getResources().getStringArray(R.array.tamano));
		adp.setDropDownViewResource(R.layout.spin_dropdown);
		spinTamano.setAdapter(adp);
		
		mas=new ClsMascota("", usu_id, "", "", "", "", "", "", "", "", "", 0, 0, ClsMascota.MAS_ENCONTRADA, "");
		mas.setEvtEndInsert(this);
		
		dialLoad=new DialogLoad();
	}
	
	
	
	public void setLocation(double lat, double lon) {
		mas.setMas_latitud(lat);
		mas.setMas_longitud(lon);
	}
	
	private NetWorkState netWorkState=null;
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
			processImage=new ProcessImageV2(getActivity(), memory, false);
			img.setImageBitmap(null);
			clearMemory();
			vars.path=MediaUtil.getPathFromURI(getActivity(), data.getData());
			processImage.loadImage(vars.path, img);
			paths.add(vars.path);
			break;
		}
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
		args.putInt("color", R.color.verdemanzana);
		args.putInt("icono", R.drawable.ic_camarablanca);
		Extra e=new Extra();
		e.tipe=GeneralData.MAP;
		evtRequestDialog.onEvtRequestDialog(e, args);	
	}
	
	public void setMapValues(Bundle args) {
		if(mas!=null && args!=null){
			mas.setMas_latitud(args.getDouble("lat"));
			mas.setMas_longitud(args.getDouble("lon"));
//			Toast.makeText(getActivity(), "SET MAP\n"+mas.getMas_latitud()+"\n"+mas.getMas_longitud(), 0).show();
		}
	}
	
	private EvtRequestDialog evtRequestDialog;
	private void setEvtRequestDialog(EvtRequestDialog evt) {
		evtRequestDialog=evt;
	}
	
	private String descripcion, contacto;
	private int edad, sexo, raza, tamano;
	private ClsMascota mas;
	
    private boolean validate() {
    	clearErrors();
    	
    	descripcion=txtDescripcion.getText().toString().trim().toLowerCase();
    	contacto=txtContacto.getText().toString().trim().toLowerCase();
    	edad=spinEdad.getSelectedItemPosition();
    	sexo=spinSexo.getSelectedItemPosition();
    	raza=spinRaza.getSelectedItemPosition();
    	tamano=spinTamano.getSelectedItemPosition();
    	if(vars.path.length()>0){
    		mas.setMas_pathimage(vars.path);
    		if(descripcion.length()<getResources().getInteger(R.integer.chars_descripcion)){
    			mas.setMas_descripcion(descripcion);	
    		}else{
    			descripcion=descripcion.substring(0, getResources().getInteger(R.integer.chars_descripcion));
    			mas.setMas_descripcion(descripcion);
    			txtDescripcion.setError(getResources().getString(R.string.error_chars));
    		}
     		mas.setMas_edad(""+edad);
        	mas.setMas_sexo(""+sexo);
       		mas.setMas_color("");
       		mas.setMas_raza(""+raza);
     		mas.setMas_tamano(""+tamano);
			if(contacto.length()>0){
				if(contacto.length()<getResources().getInteger(R.integer.chars_114)){
					mas.setMas_contacto(contacto);
					if(mas.getMas_latitud()!=0){
						return true;
					}else{
						quitAllFocus();
						Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneLaUbicacion), 0).show();
						btnMap.setBackgroundResource(R.color.rojo);
						scrollView.smoothScrollTo(0, 0);
					}
				}else{
					txtContacto.setError(getString(R.string.error_chars));
				}
			}else{
				Toast.makeText(getActivity(), getResources().getString(R.string.faltaContacto), 0).show();
				txtContacto.setError(getString(R.string.campo_requerido));
			}
    	}else{
    		quitAllFocus();
    		scrollView.smoothScrollTo(0, 0);
    		Toast.makeText(getActivity(), getResources().getString(R.string.seleccioneImagen), 0).show();
    	}
    	return false;
	}
    
    private void clearErrors() {
    	txtContacto.setError(null);
    	txtDescripcion.setError(null);
    	btnMap.setBackgroundResource(R.drawable.selector_map);
	}
    
    private void clear() {
    	clearErrors();
    	vars.path="";
    	txtDescripcion.setText("");
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
				evtRequestSelection.onEvtRequestSelection(R.id.drawer_btnMasEncontradas);
				if(evtRequestTitleBar!=null){
					evtRequestTitleBar.onEvtRequestTitleBar(getString(R.string.drawer_MascotasEncontradas));
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
