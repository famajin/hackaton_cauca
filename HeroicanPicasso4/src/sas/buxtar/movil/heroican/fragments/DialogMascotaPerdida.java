package sas.buxtar.movil.heroican.fragments;


import java.util.ArrayList;

import sas.buxtar.movil.heroican.ImageActivity;
import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.clases.ClsFavorito;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsRegistro;
import sas.buxtar.movil.heroican.clases.FavoritoManager;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtCancelDialog;
import sas.buxtar.movil.heroican.interfaces.EvtEndConsult;
import sas.buxtar.movil.heroican.interfaces.EvtEndFavorito;
import sas.buxtar.movil.heroican.redes.DialogShare;
import sas.buxtar.movil.heroican.redes.ShareContend;
import sas.buxtar.movil.heroican.redes.ShareUtil;
import sas.buxtar.movil.heroican.util.Data;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import sas.buxtar.movil.heroican.views.ScrollFace;
import sas.buxtar.movil.heroican.views.ScrollFace.EvtCloseDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;


public class DialogMascotaPerdida extends DialogFragment implements OnShowListener, EvtCloseDialog, OnKeyListener, OnClickListener, EvtEndConsult, EvtEndFavorito, AnimationListener{
	
	private ViewGroup container, contenido;
	private Animation animIn, animOutBottom, animOutTop, animPivot;
	private boolean isShow=false;
	
    private ClsMascota mascota;
    private ImageView img;
    private TextView txtNombre, txtEdad, txtSexo, txtRaza, txtTamano, txtDescripcion, txtContacto, txtColor, txtFavorito;
    private Button btnAceptar;
    private ClsCard card;
    private ImageView btnFavorito;
    private FavoritoManager favoritoManager;
    private View progress=null;
    
    public DialogMascotaPerdida() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dialog_mascota, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initDialog();
		initAnimations();
		initViews(view);
		
		if(netWorkState==null){
			netWorkState=new NetWorkState();
		}
		if(favoritoManager==null){
			favoritoManager=new FavoritoManager();
		}
		if(extra!=null){
			if(extra.regIdExternal==null){
				progress.setVisibility(View.GONE);
				contenido.setVisibility(View.VISIBLE);
				set(view);
				if(card.isChecked()){
					btnFavorito.setImageResource(R.drawable.selector_favorito2);
				}else{
					btnFavorito.setImageResource(R.drawable.selector_favorito1);
				}
			}else{
				progress.setVisibility(View.VISIBLE);
				contenido.setVisibility(View.GONE);
				String reg_id=extra.regIdExternal;
				if(registro==null){
					registro=new ClsRegistro();
				}
				if(mascota==null){
					mascota=new ClsMascota();
				}
				if(GeneralData.USU_ID.equals("")){
					SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
			    	GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");	
				}
				mascota.setReg_id(reg_id);
				mascota.setMas_tipe(GeneralData.MAS_PERDIDA);
				Object[] params=new Object[]{GeneralData.MAS_PERDIDA, mascota};
				registro.consultRegistro(GeneralData.USU_ID, reg_id, params, this);
			}
		}
	}
	
	private Extra extra;
	private void setExtra(Extra extra) {
		this.extra=extra;
		try {
			card=extra.card;
			mascota=(ClsMascota) extra.objectSelected;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private ClsRegistro registro=null;
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void set(View view) {
		if(GeneralData.IS_SEARCH_ACTIVE.equals("")){
			SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
			GeneralData.IS_SEARCH_ACTIVE=preferences.getString(GeneralData.PREF_ESCUADRON, "");
		}
		setInfo();
	}
	
	private void initDialog() {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setCancelable(false);
		getDialog().setOnKeyListener(this);
		getDialog().setOnShowListener(this);
	}
	
	private void initAnimations() {
		animPivot=AnimationUtils.loadAnimation(getActivity(), R.anim.dial_in_pivot);
		animPivot.setAnimationListener(this);
		animIn=AnimationUtils.loadAnimation(getActivity(), R.anim.dial_in);
		animIn.setAnimationListener(new animInListener());
		animOutBottom=AnimationUtils.loadAnimation(getActivity(), R.anim.dial_outbottom);
		animOutBottom.setAnimationListener(new animOutListener());
		animOutTop=AnimationUtils.loadAnimation(getActivity(), R.anim.dial_outtop);
		animOutTop.setAnimationListener(new animOutListener());
	}
	
	private ScrollFace scroll;
	private void initViews(View view) {
		((ViewGroup)view.findViewById(R.id.dialMas_linRecompensa)).setVisibility(View.VISIBLE);
		container=(ViewGroup) view.findViewById(R.id.dialMas_container);
		contenido=(ViewGroup) view.findViewById(R.id.dialMas_lin);
		scroll =(ScrollFace) view.findViewById(R.id.dialMas_scrollFace);
		scroll.setContainers(container, contenido);
		scroll.setEnabled(false);
		scroll.setEvtCloseDialog(this);
		scroll.smoothScrollTo(0, 0);
		btnAceptar=((Button)view.findViewById(R.id.dialMas_btnAceptar));
		btnAceptar.setOnClickListener(this);
		btnFavorito=(ImageView) view.findViewById(R.id.dialMas_btnFavorito);
		txtNombre=(TextView) view.findViewById(R.id.dialMas_nombre);
        txtEdad = (TextView) view.findViewById(R.id.dialMas_edad);
        txtSexo = (TextView) view.findViewById(R.id.dialMas_sexo);
        txtRaza = (TextView) view.findViewById(R.id.dialMas_raza);
        txtTamano = (TextView) view.findViewById(R.id.dialMas_tamano);
        txtColor=(TextView) view.findViewById(R.id.dialMas_color);
        txtDescripcion = (TextView) view.findViewById(R.id.dialMas_descripcion);
        txtContacto = (TextView) view.findViewById(R.id.dialMas_contacto);
        txtRaza = (TextView) view.findViewById(R.id.dialMas_raza);
        img = (ImageView) view.findViewById(R.id.dialMas_img);
        ((ImageView)view.findViewById(R.id.dialMas_btnCompartir)).setOnClickListener(this);
        ((ImageView)view.findViewById(R.id.dialMas_btnFavorito)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.dialMas_btnMap)).setOnClickListener(this);
        ((ViewGroup)view.findViewById(R.id.selback)).setBackgroundResource(R.color.rojo);
        ((ViewGroup)view.findViewById(R.id.selbackAceptar)).setBackgroundResource(R.color.rojo);
        ((ViewGroup)view.findViewById(R.id.dialMas_bar)).setBackgroundResource(R.color.rojo);
        ((ImageView)view.findViewById(R.id.dialMas_icono)).setImageResource(R.drawable.ic_huellablanca);
        ((TextView) view.findViewById(R.id.dialMas_txtTitulo)).setText(getString(R.string.drawer_MascotasPerdidas));
        txtFavorito=((TextView) view.findViewById(R.id.dialMas_txtFavorito));
        progress=view.findViewById(R.id.dialMas_progress);
        btnFavorito=((ImageView)view.findViewById(R.id.dialMas_btnFavorito));
        btnFavorito.setOnClickListener(this);
        btnFavorito.setEnabled(true);
    	//CONVERT PIXELS IN DP
//    	Resources r = mContext.getResources();
//    	int px = (int) TypedValue.applyDimension(
//    	        TypedValue.COMPLEX_UNIT_DIP,
//    	        yourdpmeasure, 
//    	        r.getDisplayMetrics()
//    	);
        if(getResources().getBoolean(R.bool.isTablet)){
            if(GeneralData.marginX==0){
            	int w_screen=getResources().getDisplayMetrics().widthPixels;
            	int h_screen=getResources().getDisplayMetrics().heightPixels;
            	GeneralData.marginX=(w_screen*GeneralData.marginX_tablet)/100;
            	GeneralData.marginY=(h_screen*GeneralData.marginY_tablet)/100;
            }
            ViewGroup margin=(ViewGroup) view.findViewById(R.id.dialMas_margin);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.setMargins(GeneralData.marginX, GeneralData.marginY, GeneralData.marginX, GeneralData.marginY);
            margin.setLayoutParams(params);	
        }
        
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(), ImageActivity.class);
				i.putExtra("url", GeneralData.SERVER_IMAGES+mascota.getReg_id()+"_"+mascota.getMas_id()+".jpg");
				startActivity(i);
			}
		});
	}
	
	private void setInfo() {
		if(mascota!=null){
	        Picasso.with(getActivity())
			.load(GeneralData.SERVER_IMAGES+mascota.getReg_id()+"_"+mascota.getMas_id()+".jpg")
			.into(img);
			
			txtNombre.setText(mascota.getMas_nombre());
			txtEdad.setText(getResources().getStringArray(R.array.edades)[Integer.parseInt(mascota.getMas_edad())]);
			txtSexo.setText(getResources().getStringArray(R.array.genero)[Integer.parseInt(mascota.getMas_sexo())]);
			txtRaza.setText(getResources().getStringArray(R.array.razas)[Integer.parseInt(mascota.getMas_raza())]);			
			txtTamano.setText(getResources().getStringArray(R.array.tamano)[Integer.parseInt(mascota.getMas_tamano())]);
			txtColor.setText(getResources().getStringArray(R.array.colores)[Integer.parseInt(mascota.getMas_color())]);
			txtDescripcion.setText(mascota.getMas_descripcion());
			txtContacto.setText(mascota.getMas_contacto());
			txtFavorito.setText(""+card.getFavCount());
			
	        img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getActivity(), ImageActivity.class);
					i.putExtra("url", GeneralData.SERVER_IMAGES+mascota.getReg_id()+"_"+mascota.getMas_id()+".jpg");
					startActivity(i);
				}
			});
		}
	}

	@Override
	public void onShow(DialogInterface dialog) {
		// TODO Auto-generated method stub
		container.clearAnimation();
		container.startAnimation(animIn);
	}

	@Override
	public void OnCloseDialog(int restoreTo) {
		// TODO Auto-generated method stub
		if(restoreTo==ScrollFace.TOP){
			isShow=false;
			container.clearAnimation();
			container.startAnimation(animOutBottom);	
		}else{
			isShow=false;
			container.clearAnimation();
			container.startAnimation(animOutTop);
		}
		
		if(evtCancelDialog!=null){
			evtCancelDialog.OnCancelDialog(null, "");
		}
	}
	
	private class animInListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			container.clearAnimation();
			container.startAnimation(animPivot);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
		
	}
	
	private class animOutListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			getDialog().cancel();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && isShow && KeyEvent.ACTION_UP ==event.getAction()){
			OnCloseDialog(ScrollFace.TOP);
		}
		return false;
	}
	
	private EvtCancelDialog evtCancelDialog=null;
	private void setEvtCancelDialog(EvtCancelDialog evt) {
		evtCancelDialog=evt;
	}
	
	public static DialogMascotaPerdida newInstace(EvtCancelDialog ecd, Extra extra) {
		DialogMascotaPerdida dial=new DialogMascotaPerdida();
		dial.setEvtCancelDialog(ecd);
		dial.setExtra(extra);
		return dial;
	}
	
	public static void restoreInstace(DialogMascotaPerdida dialAdoptar, EvtCancelDialog ecd, Extra extra) {
		dialAdoptar.setEvtCancelDialog(ecd);
		dialAdoptar.setExtra(extra);
	}
	
	private void share() {
		if(shareContend==null){
			shareContend=new ShareContend();
		}
    	
		if(GeneralData.LOGIN_TIPE==-1){
			SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
			GeneralData.LOGIN_TIPE=preferences.getInt(GeneralData.PREF_LOGIN_TIPE, -1);	
		}
    	
		shareContend.setClass(mascota, GeneralData.MASCOTA);
		
		if(GeneralData.LOGIN_TIPE==GeneralData.LOGIN_FACE){
			Bundle args=new Bundle();
			args.putParcelable("shareContend", shareContend);
			DialogShare dialShare=new DialogShare();
			dialShare.setArguments(args);
			dialShare.show(getFragmentManager(), "dialShare");
		}else{
			shareContend.prepareShareOthers();
			ShareUtil.share(getActivity(), shareContend);
		}
	}
	
	private SupportMapFragment map;
	private ShareContend shareContend;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialMas_btnCompartir:
			share();
			break;

		case R.id.dialMas_btnFavorito:
			int tipe=card.getTipe();
			//SI ES ALGUN TIPO DE MASCOTA (SUBCATEGORIA) DEBEMOS MANDAR 1 QUE ES US CATEGORIA
			//MAS_ADOPTAR=8, MAS_PERDIDA=9, MAS_ENCONTRADA=10, MAS_AGREGADA=11
			if(tipe==GeneralData.MAS_ADOPTAR || tipe==GeneralData.MAS_AGREGADA || tipe==GeneralData.MAS_ENCONTRADA || tipe==GeneralData.MAS_PERDIDA || tipe==GeneralData.MASCOTA){
				tipe=GeneralData.MASCOTA;
			}
			btnFavorito.setEnabled(false);
			ArrayList<Object> tag=new ArrayList<Object>();
			tag.add(card);
			if(card.isChecked()){
				sendFavorito(card.getId(), tipe, 0, tag);
			}else{
				sendFavorito(card.getId(), tipe, 1, tag);
			}
			break;
		case R.id.dialMas_btnAceptar:
	    	getChildFragmentManager().beginTransaction()
	    	.setCustomAnimations(R.anim.anim_in3, R.anim.anim_out3)
	    	.remove(map).commit();
	    	btnAceptar.setVisibility(View.GONE);
			break;

		case R.id.dialMas_btnMap:
			map=new SupportMapFragment();
	    	getChildFragmentManager().beginTransaction()
	    	.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
	    	.add(R.id.dialMas_frame, map).commit();
	    	hilador();
			break;
		}
	}
	
	
	
	private void hilador(){
		Thread thread =new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(GeneralData.TIME_WAITMAP);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.post(runnable);
			}
		};
		thread.start();
	}
	final Handler handler =new Handler();
	final Runnable runnable= new Runnable() {
		public void run() {
			btnAceptar.setVisibility(View.VISIBLE);
			LatLng point=new LatLng(mascota.getMas_latitud(), mascota.getMas_longitud());
			map.getMap().setMyLocationEnabled(true);
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(point)
		    .zoom(15)                   // Sets the zoom
		    //.bearing(90)                // Sets the orientation of the camera to east
		    .tilt(45)                   // Sets the tilt of the camera to 30 degrees
		    .build();                   // Creates a CameraPosition from the builder
			map.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			map.getMap().addMarker(new MarkerOptions().position(point));
		}
	};
	
	private NetWorkState netWorkState=null;
	
	private void sendFavorito(String card_id, int tipe, int increment, Object tag) {
		if(GeneralData.USU_ID.equals("")){
			SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
	    	GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");	
		}
		favoritoManager.setEvtEndFavorito(this);
		favoritoManager.setFavIncrement(increment);
		favoritoManager.setUsu_id(GeneralData.USU_ID);
		favoritoManager.setCard_id(card_id);
		favoritoManager.setFav_tipe(""+tipe);
		favoritoManager.insert(tag);
	}
	
	@Override
	public void OnEndConsult(Boolean does) {
		// TODO Auto-generated method stub
		card=new ClsCard(mascota.getReg_id(), mascota.getMas_id(), "", "", "", mascota.getMas_tipe(), mascota.getFav_count());
		if(mascota.isCheck.equals("1")){
			card.setChecked(true);	
		}else{
			card.setChecked(false);
		}
		set(getView());
		progress.setVisibility(View.GONE);
		contenido.setVisibility(View.VISIBLE);
	}
	
	private Data db;
	private ClsFavorito fav;
	
	@Override
	public void OnEndFavorito(Boolean does, Object tag) {
		// TODO Auto-generated method stub
		if(does){
			try {
				ArrayList<Object> tags=(ArrayList<Object>) tag;
				ClsCard card=(ClsCard) tags.get(0);
				int count=0;
				if(card.isChecked()){
					count=card.getFavCount()-1;
				}else{
					count=card.getFavCount()+1;
				}
				card.setFavCount(count);
				if(db==null){
					db=new Data(getActivity());
				}
				db.exeDML("UPDATE tbl_mascota SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
				
				String query="";
				if(fav==null){
					fav=new ClsFavorito();
				}
				fav.init(mascota.getMas_id(), GeneralData.MASCOTA);
				
				if(card.isChecked()){
					card.setChecked(false);
					query=fav.getSQLDel();
					try {
						btnFavorito.setImageResource(R.drawable.selector_favorito1);
					} catch (Exception e) {}
				}else{
					card.setChecked(true);
					query=fav.getSQL(db.getMax("tbl_favorito"));
					try {
						btnFavorito.setImageResource(R.drawable.selector_favorito2);
						Toast.makeText(getActivity(), getResources().getString(R.string.tegusta), 0).show();
					} catch (Exception e) {}
				}
				db.exeDML(query);
				txtFavorito.setText(""+card.getFavCount());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
		try {
			btnFavorito.clearAnimation();
        	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.favorito_animation);
        	btnFavorito.startAnimation(animation);
			btnFavorito.setEnabled(true);	
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		scroll.smoothScrollTo(0, 0);
		scroll.setEnabled(true);
		isShow=true;
		
		btnFavorito.clearAnimation();
    	Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.favorito_animation);
    	btnFavorito.startAnimation(a);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
	}

}
