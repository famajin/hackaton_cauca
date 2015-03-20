package sas.buxtar.movil.heroican.pager;


import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsRegistro;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.fragments.FragHome.InfoPagina;
import sas.buxtar.movil.heroican.interfaces.EvtEndConsult;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTutorial;
import sas.buxtar.movil.heroican.process.RecyclingImageView;
import sas.buxtar.movil.heroican.tutorial.DialogTutorial;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import sas.buxtar.movil.heroican.views.DropListView.EvtShowDetail;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FragItemPager extends Fragment implements OnClickListener, EvtEndConsult, EvtShowDetail{

	private static final String IMAGEN = "imagen", POS = "index", TEXT = "text", COLOR="color", SELECTION="selection", TIPE="tipe";

	private int color, selection, tipe;
	private String text, imagen;
	private TextView txt, tira;
	private RecyclingImageView img;
	private ClsMascota mascota;
	private ClsRegistro registro;
	private NetWorkState netWorkState=null;
	
	private static String imagenRandom="";
	private static int consult=0;
	
	public static FragItemPager newInstance(InfoPagina info, EvtRequestSelection evtRequestSelection, EvtRequestDialog evtRequestDialog, EvtRequestTutorial evtRequestTutorial, OnTouchListener touch) {
		FragItemPager fragment = new FragItemPager();
		Bundle bundle = new Bundle();
		bundle.putString(IMAGEN, info.imagen);
		bundle.putString(TEXT, info.titulo);
		bundle.putInt(POS, info.pos);
		bundle.putInt(COLOR, info.color);
		bundle.putInt(SELECTION, info.selection);
		bundle.putInt(TIPE, info.tipe);
		fragment.setArguments(bundle);
		fragment.setEvtRequestDialog(evtRequestDialog);
		fragment.setEvtRequestSelection(evtRequestSelection);
		fragment.setEvtRequestTutorial(evtRequestTutorial);
		fragment.setEvtTouch(touch);
		fragment.setRetainInstance(true);
		return fragment;
	}
	
	public static void restoreInstance(FragItemPager fragment, EvtRequestSelection evtRequestSelection, EvtRequestDialog evtRequestDialog, EvtRequestTutorial evtRequestTutorial, OnTouchListener touch) {
		fragment.setEvtRequestDialog(evtRequestDialog);
		fragment.setEvtRequestSelection(evtRequestSelection);
		fragment.setEvtRequestTutorial(evtRequestTutorial);
		fragment.setEvtTouch(touch);
		fragment.setRetainInstance(true);
	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		if(getArguments()!=null){
//			if(getArguments().containsKey(IMAGEN)){
//				imagen=getArguments().getString(IMAGEN);	
//			}else{
//				imagen="";
//			}
//			tipe=getArguments().getInt(TIPE);
//			text = getArguments().getString(TEXT);
//			color=getArguments().getInt(COLOR);
//			selection=getArguments().getInt(SELECTION);
//		}
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.item_pager, container, false);

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(getArguments()!=null){
			if(getArguments().containsKey(IMAGEN)){
				imagen=getArguments().getString(IMAGEN);	
			}else{
				imagen="";
			}
			tipe=getArguments().getInt(TIPE);
			text = getArguments().getString(TEXT);
			color=getArguments().getInt(COLOR);
			selection=getArguments().getInt(SELECTION);
		}
		tira=(TextView) view.findViewById(R.id.pagerItem_tira);
		txt=(TextView) view.findViewById(R.id.pagerItem_Txt);
		img=(RecyclingImageView) view.findViewById(R.id.pagerItem_img);
		((Button)view.findViewById(R.id.pagerItem_btn)).setOnTouchListener(touch);
		((Button)view.findViewById(R.id.pagerItem_btn)).setOnClickListener(this);
		reLoad();
	}
	
	public void reLoad() {
		try {
			txt.setText(text);
			if(tipe==InfoPagina.SPECIAL){
				if(netWorkState==null){
					netWorkState=new NetWorkState();
				}
				if(netWorkState.isOnLine(getActivity()) && consult%2==0){
					if(registro==null){
						registro=new ClsRegistro();
					}
					if(mascota==null){
						mascota=new ClsMascota();
					}
					registro.consultRandom(mascota, this);
				}else if(!imagenRandom.equals("")){
		            Picasso.with(getActivity())
		    		.load(GeneralData.SERVER_IMAGES+imagenRandom)
		    		.into(img);
				}
				consult++;
			}else{
				if(!imagen.equals("")){
		            Picasso.with(getActivity())
		    		.load(GeneralData.SERVER_IMAGES+imagen)
		    		.into(img);
				}
			}
			tira.setBackgroundResource(color);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(registro!=null){
			registro.cancelRandom();
		}
		super.onDestroy();
	}

	@Override
	public void OnEndConsult(Boolean does) {
		// TODO Auto-generated method stub
		if(does){
			imagenRandom=mascota.getReg_id()+"_"+mascota.getMas_id()+".jpg";
            Picasso.with(getActivity())
    		.load(GeneralData.SERVER_IMAGES+imagenRandom)
    		.into(img);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(tipe==InfoPagina.SPECIAL && !imagenRandom.equals("")){
			if(evtRequestDialog!=null){
				if(mascota!=null){
					Extra e=new Extra();
					e.tipe=GeneralData.MAS_PERDIDA;
					e.regIdExternal=mascota.getReg_id();
					e.regId=mascota.getReg_id();
					evtRequestDialog.onEvtRequestDialog(e, null);	
				}
			}
		}else if(tipe==InfoPagina.NORMAL){
			if(selection!=-1){
				switch (selection) {
				case R.id.drawer_btnMascotaEncontrada:
					SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
					int shows=preferences.getInt(GeneralData.PREF_TUTORIAL_CAMARA, 0);
					
					if(shows<GeneralData.SHOWMAX_TUTORIAL){
						if(evtRequestTutorial!=null){
							shows++;
							SharedPreferences.Editor editor = preferences.edit();
							editor.putInt(GeneralData.PREF_TUTORIAL_CAMARA, shows);
							editor.commit();
							evtRequestTutorial.onEvtEvtRequestTutorial(DialogTutorial.TUT_CAMARA);	
						}
					}else{
						evtRequestSelection.onEvtRequestSelection(selection);	
					}
					break;
				case R.id.drawer_btnEscuadron:
					
					evtRequestTutorial.onEvtEvtRequestTutorial(DialogTutorial.TUT_ESCUADRON);
					
					break;
				case R.id.drawer_btnAdopta:
					if(evtRequestSelection!=null){
						evtRequestSelection.onEvtRequestSelection(selection);
					}
					break;
				}
			}
		}
	}
	
	private EvtRequestSelection evtRequestSelection;
	public void setEvtRequestSelection(EvtRequestSelection evt) {
		this.evtRequestSelection=evt;
	}
	
	private EvtRequestDialog evtRequestDialog;
	public void setEvtRequestDialog(EvtRequestDialog evt) {
		evtRequestDialog=evt;
	}
	
	private EvtRequestTutorial evtRequestTutorial;
	public void setEvtRequestTutorial(EvtRequestTutorial evt) {
		evtRequestTutorial=evt;
	}
	private OnTouchListener touch;
	public void setEvtTouch(OnTouchListener evt) {
		touch=evt;
	}

	@Override
	public void onEvtShowDetail(boolean is) {
		// TODO Auto-generated method stub
		try {
			reLoad();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
