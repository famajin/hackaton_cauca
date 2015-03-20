package sas.buxtar.movil.heroican.fragments;


import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import sas.buxtar.movil.heroican.ImageActivity;
import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.clases.ClsFavorito;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.FavoritoManager;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtCancelDialog;
import sas.buxtar.movil.heroican.interfaces.EvtEndFavorito;
import sas.buxtar.movil.heroican.process.ImageFetcher;
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

public class DialogMascotaAdoptar extends DialogFragment implements OnShowListener, EvtCloseDialog, OnKeyListener, OnClickListener, EvtEndFavorito, AnimationListener{
	
	private ViewGroup container, contenido;
	private Animation animIn, animOutBottom, animOutTop, animPivot;
	private boolean isShow=false;
	
    private ClsMascota mascota_seleccionada;
    private ImageView img;
    private TextView txtNombre, txtEdad, txtSexo, txtRaza, txtTamano, txtDescripcion, txtContacto;
    
    public DialogMascotaAdoptar() {
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
		setInfo();
		if(netWorkState==null){
			netWorkState=new NetWorkState();
		}
		if(favoritoManager==null){
			favoritoManager=new FavoritoManager();
		}
	}
	
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
	
	private void setExtra(Extra extra) {
		card=extra.card;
		mascota_seleccionada=(ClsMascota) extra.objectSelected;
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
		((Button)view.findViewById(R.id.dialMas_btnMap)).setVisibility(View.GONE);
		container=(ViewGroup) view.findViewById(R.id.dialMas_container);
		contenido=(ViewGroup) view.findViewById(R.id.dialMas_lin);
		scroll =(ScrollFace) view.findViewById(R.id.dialMas_scrollFace);
		scroll.setContainers(container, contenido);
		scroll.setEvtCloseDialog(this);
		scroll.setEnabled(false);
		scroll.smoothScrollTo(0, 0);
		txtFavorito=(TextView) view.findViewById(R.id.dialMas_txtFavorito);
		txtNombre=(TextView) view.findViewById(R.id.dialMas_nombre);
        txtEdad = (TextView) view.findViewById(R.id.dialMas_edad);
        txtSexo = (TextView) view.findViewById(R.id.dialMas_sexo);
        txtRaza = (TextView) view.findViewById(R.id.dialMas_raza);
        txtTamano = (TextView) view.findViewById(R.id.dialMas_tamano);
        txtDescripcion = (TextView) view.findViewById(R.id.dialMas_descripcion);
        txtContacto = (TextView) view.findViewById(R.id.dialMas_contacto);
        img = (ImageView) view.findViewById(R.id.dialMas_img);
        
        ((ImageView)view.findViewById(R.id.dialMas_btnCompartir)).setOnClickListener(this);
        btnFavorito=((ImageView)view.findViewById(R.id.dialMas_btnFavorito));
        btnFavorito.setOnClickListener(this);
        btnFavorito.setEnabled(true);
        
        ((ViewGroup)view.findViewById(R.id.dialMas_bar)).setBackgroundResource(R.color.morado);
        ((ImageView)view.findViewById(R.id.dialMas_icono)).setImageResource(R.drawable.ic_huesoblanco);
        ((TextView) view.findViewById(R.id.dialMas_txtTitulo)).setText(getString(R.string.drawer_Adopta));
        
        Picasso.with(getActivity())
		.load(GeneralData.SERVER_IMAGES+mascota_seleccionada.getReg_id()+"_"+mascota_seleccionada.getMas_id()+".jpg")
		.into(img);
        
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
				i.putExtra("url", GeneralData.SERVER_IMAGES+mascota_seleccionada.getReg_id()+"_"+mascota_seleccionada.getMas_id()+".jpg");
				startActivity(i);
			}
		});
	}
	
	private void setInfo() {
		txtNombre.setText(mascota_seleccionada.getMas_nombre());
		txtEdad.setText(getResources().getStringArray(R.array.edades)[Integer.parseInt(mascota_seleccionada.getMas_edad())]);
		txtSexo.setText(getResources().getStringArray(R.array.genero)[Integer.parseInt(mascota_seleccionada.getMas_sexo())]);
		txtRaza.setText(getResources().getStringArray(R.array.razas)[Integer.parseInt(mascota_seleccionada.getMas_raza())]);			
		txtTamano.setText(getResources().getStringArray(R.array.tamano)[Integer.parseInt(mascota_seleccionada.getMas_tamano())]);
		txtDescripcion.setText(mascota_seleccionada.getMas_descripcion());
		txtContacto.setText(mascota_seleccionada.getMas_contacto());
		if(card.isChecked()){
			btnFavorito.setImageResource(R.drawable.selector_favorito2);
		}else{
			btnFavorito.setImageResource(R.drawable.selector_favorito1);
		}
		txtFavorito.setText(""+card.getFavCount());
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

	public static DialogMascotaAdoptar newInstace(EvtCancelDialog ecd, Extra extra) {
		DialogMascotaAdoptar dial=new DialogMascotaAdoptar();
		dial.setEvtCancelDialog(ecd);
		dial.setExtra(extra);
		return dial;
	}
	
	public static void restoreInstace(DialogMascotaAdoptar dialAdoptar, EvtCancelDialog ecd, Extra extra) {
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
    	
		shareContend.setClass(mascota_seleccionada, GeneralData.MASCOTA);
		
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
		}
	}
	
	private ImageView btnFavorito;
	private TextView txtFavorito;
	private NetWorkState netWorkState=null;
	private FavoritoManager favoritoManager=null;
	private ClsCard card;
	
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
				fav.init(mascota_seleccionada.getMas_id(), GeneralData.MASCOTA);
				
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
				btnFavorito.clearAnimation();
            	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.favorito_animation);
            	btnFavorito.startAnimation(animation);
				txtFavorito.setText(""+card.getFavCount());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
		try {
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