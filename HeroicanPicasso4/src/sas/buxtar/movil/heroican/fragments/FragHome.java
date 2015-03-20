package sas.buxtar.movil.heroican.fragments;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.adapters.AdpRecycler;
import sas.buxtar.movil.heroican.adapters.AdpRecycler.Holder.EvtRecyclerClic;
import sas.buxtar.movil.heroican.adapters.DividerRecycler;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.clases.ClsEvento;
import sas.buxtar.movil.heroican.clases.ClsFavorito;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsRegistro;
import sas.buxtar.movil.heroican.clases.ClsTestimonio;
import sas.buxtar.movil.heroican.clases.ClsTip;
import sas.buxtar.movil.heroican.clases.FavoritoManager;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.interfaces.EvtEndConsult;
import sas.buxtar.movil.heroican.interfaces.EvtEndFavorito;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTutorial;
import sas.buxtar.movil.heroican.pager.FragPager;
import sas.buxtar.movil.heroican.redes.DialogShare;
import sas.buxtar.movil.heroican.redes.ShareContend;
import sas.buxtar.movil.heroican.redes.ShareUtil;
import sas.buxtar.movil.heroican.util.Data;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import sas.buxtar.movil.heroican.views.DropListView;
import sas.buxtar.movil.heroican.views.DropListView.EvtDrop;
import sas.buxtar.movil.heroican.views.DropListView.EvtEndListView;
import sas.buxtar.movil.heroican.views.DropListView.EvtMoveScroll;
import sas.buxtar.movil.heroican.vivelabs.CacheUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class FragHome extends Fragment implements OnClickListener, OnItemClickListener, EvtEndConsult, EvtDrop, EvtEndListView, EvtMoveScroll, EvtEndFavorito, EvtRecyclerClic{

	private Data db;
    private ViewGroup framePager, nonet;
    private FragPager pager=null;
    private View progressBar;
    private DropListView listView;
    public static final String ARG_COD_CATEGORIA = "cod_categoria";
    public static final String ARG_COD_DROP = "cod_drop";
  	private ViewGroup contLst;
    private View selected=null;
	private CacheUtils cacheUtils=null;
	private NetWorkState netWorkState=null;
    private FavoritoManager favoritoManager;
    private Animation animCardDown=null, animCardUp=null;
    private ArrayList<ClsCard> lstCardsOriginal, lstCardsAux;
    private ArrayList<ClsRegistro> lstRegistros;
	private ArrayList<ClsMascota> lstMascotas;
	private ArrayList<ClsEvento> lstEventos;
	private ArrayList<ClsFundacion> lstFundaciones;
	private ArrayList<ClsTip> lstTips;
	private ArrayList<ClsTestimonio> lstTestimonios;
	private ArrayList<ClsFavorito> lstFavoritos;
    private ClsRegistro reg;
    private ArrayList<Object> objetos=null;
	
	private boolean isActive=false;
    private int H_ORIGINAL, H_AFTER;
    private AdpRecycler adapter;
    private boolean justDown=false;
    
    public FragHome() {
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_home, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		H_ORIGINAL=H_AFTER=-1;
		selected=null;
		db=new Data(getActivity());
        INITGALLERY(getView());
        init(getView());
	}
	
	private VARS vars;
	private void setVars(VARS vars) {
		this.vars=vars;
	}
	public void setSource(ArrayList<ClsCard> lstCardsOriginal, ArrayList<ClsCard> lstCardsAux, ArrayList<ClsRegistro> lstRegistros, ArrayList<ClsMascota> lstMascotas, ArrayList<ClsEvento> lstEventos, ArrayList<ClsFundacion> lstFundaciones, ArrayList<ClsTip> lstTips, ArrayList<ClsTestimonio> lstTestimonios, ArrayList<ClsFavorito> lstFavoritos){
		this.lstCardsOriginal=lstCardsOriginal;
		this.lstCardsAux=lstCardsAux;
		this.lstRegistros=lstRegistros;
		this.lstMascotas=lstMascotas;
		this.lstEventos=lstEventos;
		this.lstFundaciones=lstFundaciones;
		this.lstTips=lstTips;
		this.lstTestimonios=lstTestimonios;
		this.lstFavoritos=lstFavoritos;
	}
	
	private void init(View view) {
		
    	cacheUtils=new CacheUtils(getActivity());
    	cacheUtils.updatePathCacheSource();
    	netWorkState=new NetWorkState();
    	favoritoManager=new FavoritoManager();
    	
    	reg=null;
    	
    	framePager=(ViewGroup) view.findViewById(R.id.home_framePager);
    	contLst=(ViewGroup)view.findViewById(R.id.home_cont_lst);
    	nonet=(ViewGroup)getView().findViewById(R.id.home_nonet);
    	progressBar=view.findViewById(R.id.home_progress);
    	((Button)view.findViewById(R.id.home_btnActualizar)).setOnClickListener(this);
    	progressBar.setVisibility(View.GONE);
    	isActive=true;
    	
//		TRUE SI LOS CAMBIOS DE ADAPTADOR NO PUEDEN AFECTAR EL TAMAÑO DE LA RECYCLERVIEW
    	

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        
    	int div=4;
    	if(getResources().getBoolean(R.bool.isTablet)){
    		div=8;
    	}
        
		listView = (DropListView) view.findViewById(R.id.home_grid);
    	listView.setHasFixedSize(true);
    	listView.setContainer_(contLst);
    	listView.addItemDecoration(new DividerRecycler(getActivity(), div));
    	listView.setEvtEndListView(this);
    	listView.setEvtShowDetail(pager);
    	listView.setHasFixedSize(true);
    	listView.setEvtDrop(this);
    	listView.setEvtMoveScroll(this);
    	listView.setLayoutManager(manager);
//    	listView.setScrollY(0);
    	
    	adapter=new AdpRecycler(getActivity(), this, lstCardsAux, this);
		
		adapter.registerAdapterDataObserver(new AdapterDataObserver() {
			
    		@Override
    		public void onChanged() {
    			// TODO Auto-generated method stub
    			super.onChanged();
				try {
					if(vars.refresh || vars.scrollTo){
						if(!justDown){
							listView.smoothScrollToPosition(0);	
						}
						justDown=false;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
    		}
		});

		
		vars.size=lstCardsAux.size();
    	listView.setVars(vars);
    	listView.setAdapter(adapter);
    	
    	if(lstCardsOriginal.size()>0){
    		filtrateSource(vars.codeCategory, true);
    	}
        
        setParams();
        
        if(vars.doDrop){
        	progressBar.setVisibility(View.VISIBLE);
        	OnEvtDrop();
    	}
	}
	
    public static FragHome newInstance(EvtRequestDialog evtDialog, EvtRequestTutorial evtTutorial, EvtRequestSelection evtSelection, EvtLoadCards evtLoadCards, VARS vars) {
		FragHome fragHome=new FragHome();
		fragHome.setEvtRequestTutorial(evtTutorial);
		fragHome.setEvtRequestSelection(evtSelection);
		fragHome.setVars(vars);
		fragHome.setEvtLoadCards(evtLoadCards);
		fragHome.setEvtRequestDialog(evtDialog);
		fragHome.setRetainInstance(true);
        return fragHome;
    }
    
    public static void restoreInstance(FragHome fragHome, boolean doDrop, EvtRequestDialog evtDialog, EvtRequestTutorial evtTutorial, EvtRequestSelection evtSelection, EvtLoadCards evtLoadCards, VARS vars) {
		fragHome.setEvtRequestTutorial(evtTutorial);
		fragHome.setEvtRequestSelection(evtSelection);
		fragHome.setVars(vars);
		fragHome.setEvtLoadCards(evtLoadCards);
		fragHome.setEvtRequestDialog(evtDialog);
		fragHome.setRetainInstance(true);
    }
	
    @Override
    public void onResume() {
        super.onResume();
        isActive=true;
    }

	@Override
	public void onDestroy() {
		isActive=false;
		if(reg!=null){
			try {
			reg.cancelConsult();
			reg=null;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		try {
			getActivity().getSupportFragmentManager()
			.beginTransaction().remove(pager)
			.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	    super.onDestroy();
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {}
	}
	 
	 public void filtrateSource(int codCategoria, boolean doAfterFiltrate){		vars.codeCategory=codCategoria;
		//////////////////////////
		adapter.setAnimate(true);
		///////////////////////
		lstCardsAux.clear();	
		int size=lstCardsOriginal.size();
		for (int i = 0; i < size; i++) {
			ClsCard card = lstCardsOriginal.get(i);
			if(vars.codeCategory==GeneralData.ALL){
				lstCardsAux.add(card);
			}else if (card.getTipe()==codCategoria) {
				lstCardsAux.add(card);
			}
		}
		if(doAfterFiltrate){
			afterFiltrate();
		}
	}
	 
	 private void afterFiltrate(){
		//NOTIFICAMOS LOS CAMBIOS AL ADAPTER
		setParams();
		try {
			adapter.notifyDataSetChanged();
			listView.wasDrop=false;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int s=lstCardsAux.size();
		vars.size=s;
		
		int h_total=s*getResources().getDimensionPixelSize(R.dimen.H_card);;
		
		if(listView.getHeight()+100>h_total || s==0){
//			Toast.makeText(getActivity(), "FROM FILTRATE", 0).show();
			onEvtEndListView(true);
		}else{
			onEvtEndListView(false);
		}
	 }

	private void notifyDataSetChanges() {
		//NOTIFICAMOS LOS CAMBIOS AL ADAPTER
		 setParams();
		if(vars.scrollTo){
            try {
        		adapter.notifyDataSetChanged();
            	listView.wasDrop=false;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			adapter.setAnimate(false);
    		adapter.notifyDataSetChanged();
		}

		vars.size=lstCardsAux.size();
	}
	    
	    private void INITGALLERY(View view) {
		pager=new FragPager();
		pager.setEvtRequestDialog(evtRequestDialog);
		pager.setEvtRequestTutorial(evtRequestTutorial);
		pager.setEvtRequestSelection(evtRequestSelection);
		getActivity().getSupportFragmentManager()
		.beginTransaction().replace(R.id.home_framePager, pager)
		.commit();
	}
    
	private EvtRequestTutorial evtRequestTutorial;
	private void setEvtRequestTutorial(EvtRequestTutorial evt) {
		evtRequestTutorial=evt;
	}
    
    private EvtRequestSelection evtRequestSelection;
    private void setEvtRequestSelection(EvtRequestSelection evt) {
		this.evtRequestSelection=evt;
	}
	
	private EvtRequestDialog evtRequestDialog;
	private void setEvtRequestDialog(EvtRequestDialog evt) {
		evtRequestDialog=evt;
	}
	
	public static class InfoPagina{
		public static int NORMAL=0, SPECIAL=1;
		public int color, pos, selection;
		public String titulo, imagen;
		public int tipe;
		
		public InfoPagina(int pos, int tipe, String imagen, String titulo, int color, int selection) {
			// TODO Auto-generated constructor stub
			this.imagen=imagen;
			this.titulo=titulo;
			this.tipe=tipe;
			this.color=color;
			this.selection=selection;
			this.pos=pos;
		}
	}

	public void cancelDown() {
		if(selected!=null){
			try {
				animCardUp=null;
		    	animCardUp=AnimationUtils.loadAnimation(getActivity(), R.anim.card_animation_up);
		    	animCardUp.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						justDown=true;
						adapter.setAnimate(false);
						adapter.notifyDataSetChanged();
					}
				});
				selected.clearAnimation();
				selected.startAnimation(animCardUp);
				selected=null;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
//			Toast.makeText(getActivity(), "SELECTED WAS NULL", 0).show();
			justDown=true;
			adapter.setAnimate(false);
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onEvtEndListView(boolean is) {
		// TODO Auto-generated method stub
		if(is){
//			Toast.makeText(getActivity(), "END LISTVIEW", 0).show();
			progressBar.setVisibility(View.VISIBLE);
			vars.scrollTo=false;
			vars.refresh=false;
			consult();
		}else{
			if(reg!=null){
				try {
					reg.cancelConsult();
					try {
						reg=null;	
					} catch (Exception e) {
						// TODO: handle exception
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			progressBar.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void OnEndConsult(Boolean does) {
		// TODO Auto-generated method stub
		try {
	        System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//CARGAR DATOS
		if(does){
	        if(evtLoadCards!=null){
	        	evtLoadCards.onEvtLoadCards();
	        }
	        filtrateSource(vars.codeCategory, false);
			try {
				reg.cancelConsult();
			} catch (Exception e) {
				// TODO: handle exception
			}
			reg=null;
			///////////////////////77
			if(nonet.getVisibility()==View.VISIBLE){
				progressBar.setVisibility(View.GONE);
			}
			if(isActive){
				progressBar.setVisibility(View.GONE);
				notifyDataSetChanges();
				Toast.makeText(getActivity(), getResources().getString(R.string.listo), 0).show();	
			}
			
		}else{
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
	}
	
    private void consult() {
    	if(GeneralData.USU_ID.equals("")){
    		SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
    		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
    	}
		if(netWorkState==null){
			netWorkState=new NetWorkState();
		}
		if(netWorkState.isOnLine(getActivity())){
			if(objetos==null){
		        objetos=new ArrayList<Object>();
		        objetos.add(lstRegistros);
		        objetos.add(lstTestimonios);
		        objetos.add(lstTips);
		        objetos.add(lstMascotas);
		        objetos.add(lstFundaciones);
		        objetos.add(lstEventos);
		        objetos.add(lstFavoritos);
		        
		        objetos.add(lstCardsOriginal);
		        objetos.add(lstCardsAux);
			}
			if(progressBar.getVisibility()==View.GONE){
				progressBar.setVisibility(View.VISIBLE);
			}
			if(vars.refresh){
				try {
					reg.cancelConsult();
					reg=null;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			if(reg==null || vars.refresh){
				if(GeneralData.USU_CIUDAD.equals("")){
					SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
					GeneralData.USU_CIUDAD=preferences.getString(GeneralData.PREF_USUCIUDAD, "");
				}
				if(GeneralData.DEV_ID.equals("")){
					SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
					GeneralData.DEV_ID=preferences.getString(GeneralData.PREF_DEVICEID, "");
				}
				if(nonet.getVisibility()==View.VISIBLE){
					nonet.setVisibility(View.GONE);
				}
				reg=new ClsRegistro(getActivity());
    	        reg.setEvtEndConsult(this);
    	        progressBar.setVisibility(View.VISIBLE);
    	        reg.consult(objetos, GeneralData.USU_CIUDAD, vars.codeCategory, vars.refresh, GeneralData.DEV_ID);
    	        vars.refresh=false;
    	        Toast.makeText(getActivity(), getResources().getString(R.string.actualizando), 0).show();	
			}else{
				//CANCELAR
				Toast.makeText(getActivity(), getResources().getString(R.string.inprocess), 0).show();
			}
		}else{
			if(lstCardsOriginal.size()==0){
				nonet.setVisibility(View.VISIBLE);
				if(progressBar.getVisibility()==View.VISIBLE){
					progressBar.setVisibility(View.GONE);
				}
			}
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
		animCardDown=null;
		animCardDown = AnimationUtils.loadAnimation(getActivity(), R.anim.card_animation_down);
		selected=(v.findViewById(R.id.itemCard_container));
		selected.clearAnimation();
		
		Extra extra=new Extra();
    	ClsCard card = lstCardsAux.get(arg2);
    	
    	extra.card=card;
    	extra.tipe=card.getTipe();
    	
    	switch (extra.tipe) {
		case GeneralData.EVENTO:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.EVENTO);
			break;
		case GeneralData.MAS_ADOPTAR:
//				Toast.makeText(getActivity(), "sel reg_id"+card.getReg_id(), 0).show();
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.MAS_ENCONTRADA:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.MAS_PERDIDA:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.FUNDACION:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.FUNDACION);
			break;
		case GeneralData.TESTIMONIO:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.TESTIMONIO);
			break;
		case GeneralData.TIP:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.TIP);
			break;
		}
    	
    	selected.startAnimation(animCardDown);
		
		if(evtRequestDialog!=null){
			evtRequestDialog.onEvtRequestDialog(extra, null);
		}
    }
	
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
	
	public void setParams() {
		if(H_ORIGINAL==-1){
			vars.heightObject=((int)getActivity().getResources().getDimensionPixelSize(R.dimen.H_detail));
			H_ORIGINAL=vars.heightContLst;
			H_AFTER=vars.heightContLst+vars.heightObject-(2*getResources().getDimensionPixelSize(R.dimen.P_home));
		}
		if(vars.codeCategory==GeneralData.ALL){
			contLst.getLayoutParams().height=H_AFTER;
			framePager.setVisibility(View.VISIBLE);
			listView.setEnabledDrop(true);
			listView.setParams(vars.heightObject);
			listView.restoreBottom();
		}else{
			contLst.getLayoutParams().height=H_ORIGINAL;
			contLst.setY(0);
			listView.getLayoutParams().height=android.widget.AbsListView.LayoutParams.FILL_PARENT;
			framePager.setVisibility(View.GONE);
			listView.setEnabledDrop(false);
			listView.wasDrop=true;
			listView.setParams(0);
		}
	}
	
	private ShareContend shareContend=null;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.home_btnActualizar:
	    	if(netWorkState==null){
	    		netWorkState=new NetWorkState();
	    	}
			if(netWorkState.isOnLine(getActivity())){
				progressBar.setVisibility(View.GONE);
				OnEvtDrop();
			}else{
				if(progressBar.getVisibility()==View.VISIBLE){
					progressBar.setVisibility(View.GONE);	
				}
				Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
			}
			break;
		case R.id.itemCard_btnCompartir:
			
			share(v);
			
			break;

		case R.id.itemCard_btnFavorito:
			ClsCard card=(ClsCard)v.getTag();
			int tipe=card.getTipe();
			//SI ES ALGUN TIPO DE MASCOTA (SUBCATEGORIA) DEBEMOS MANDAR 1 QUE ES US CATEGORIA
			//MAS_ADOPTAR=8, MAS_PERDIDA=9, MAS_ENCONTRADA=10, MAS_AGREGADA=11
			if(tipe==GeneralData.MAS_ADOPTAR || tipe==GeneralData.MAS_AGREGADA || tipe==GeneralData.MAS_ENCONTRADA || tipe==GeneralData.MAS_PERDIDA || tipe==GeneralData.MASCOTA){
				tipe=GeneralData.MASCOTA;
			}else if(tipe==GeneralData.FUNDACION){
				tipe=GeneralData.USUARIO;
			}
			((ImageView)v).setEnabled(false);
			ArrayList<Object> tag=new ArrayList<Object>();
			tag.add(card);
			tag.add(v);
			if(card.isChecked()){
				sendFavorito(card.getId(), tipe, 0, tag);
			}else{
				sendFavorito(card.getId(), tipe, 1, tag);
			}
			break;
		}
	}
	
	private void share(View v) {
		if(shareContend==null){
			shareContend=new ShareContend();
		}
    	ClsCard cardd = (ClsCard) v.getTag();
    	int t=cardd.getTipe();
    	if(t==GeneralData.MAS_ADOPTAR || t==GeneralData.MAS_ENCONTRADA || t==GeneralData.MAS_ADOPTAR || t==GeneralData.MAS_PERDIDA){
    		t=GeneralData.MASCOTA;
    	}
    	
		if(GeneralData.LOGIN_TIPE==-1){
			SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
			GeneralData.LOGIN_TIPE=preferences.getInt(GeneralData.PREF_LOGIN_TIPE, -1);	
		}
    	
		shareContend.setClass(evtLoadCards.getObjectSelected(cardd.getReg_id(), t), t);
		
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

	@Override
	public void onEvtMoveScroll() {
		// TODO Auto-generated method stub
		adapter.setAnimate(true);
	}
	
	@Override
	public void OnEvtDrop() {
		// TODO Auto-generated method stub
		//PARA REFRESCAR
		vars.refresh=true;
		vars.scrollTo=true;
		consult();
	}

	private ClsFavorito fav;
	@Override
	public void OnEndFavorito(Boolean does, Object tag) {
		// TODO Auto-generated method stub
		if(does){
			try {
				ArrayList<Object> tags=(ArrayList<Object>) tag;
				ClsCard card=(ClsCard) tags.get(0);
				View v=(View) tags.get(1);
				int count=0;
				if(card.isChecked()){
					count=card.getFavCount()-1;
				}else{
					count=card.getFavCount()+1;
				}
				card.setFavCount(count);
				int tipe=card.getTipe();
				if(tipe==GeneralData.MAS_ADOPTAR || tipe==GeneralData.MAS_AGREGADA || tipe==GeneralData.MAS_ENCONTRADA || tipe==GeneralData.MAS_PERDIDA || tipe==GeneralData.MASCOTA){
					tipe=GeneralData.MASCOTA;
				}
				switch (tipe) {
				case GeneralData.MASCOTA:
					db.exeDML("UPDATE tbl_mascota SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
					break;
				case GeneralData.TESTIMONIO:
					db.exeDML("UPDATE tbl_testimonio SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
					break;
				case GeneralData.TIP:
					db.exeDML("UPDATE tbl_tip SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
					break;
				case GeneralData.FUNDACION:
					db.exeDML("UPDATE tbl_fundacion SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
					break;
				case GeneralData.EVENTO:
					db.exeDML("UPDATE tbl_evento SET fav_count="+count+" WHERE reg_id='"+card.getReg_id()+"'");
					break;
				}
				String query="";
				if(fav==null){
					fav=new ClsFavorito();
				}
				fav.init(card.getId(), tipe);
				if(card.isChecked()){
					card.setChecked(false);
					query=fav.getSQLDel();
					if(v!=null){
						try {
							((ImageView)v).setEnabled(true);
							((ImageView)v).setImageResource(R.drawable.selector_favorito1);
						} catch (Exception e) {}
					}
				}else{
					card.setChecked(true);
					query=fav.getSQL(db.getMax("tbl_favorito"));
					if(v!=null){
						try {
							((ImageView)v).setEnabled(true);
							((ImageView)v).setImageResource(R.drawable.selector_favorito2);
							Toast.makeText(getActivity(), getResources().getString(R.string.tegusta), 0).show();
						} catch (Exception e) {}
					}
				}
				db.exeDML(query);
				((ImageView)v).clearAnimation();
            	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.favorito_animation);
				((ImageView)v).startAnimation(animation);
				((TextView)((View)v.getParent().getParent().getParent()).findViewById(R.id.itemCard_txtFavorito)).setText(""+card.getFavCount());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
		}
	}
	
	public interface EvtLoadCards{
		public void onEvtLoadCards();
		public Object getObjectSelected(String id, int tipe);
	}
	private EvtLoadCards evtLoadCards;
	private void setEvtLoadCards(EvtLoadCards evt) {
		this.evtLoadCards=evt;
	}

	@Override
	public void onEvtRecyclerClic(View recyclerItem, int position) {
		// TODO Auto-generated method stub
		animCardDown=null;
		animCardDown = AnimationUtils.loadAnimation(getActivity(), R.anim.card_animation_down);
		selected=recyclerItem;
		selected.clearAnimation();
		
		Extra extra=new Extra();
    	ClsCard card = lstCardsAux.get(position);
    	
    	extra.card=card;
    	extra.tipe=card.getTipe();
    	
    	switch (extra.tipe) {
		case GeneralData.EVENTO:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.EVENTO);
			break;
		case GeneralData.MAS_ADOPTAR:
//				Toast.makeText(getActivity(), "sel reg_id"+card.getReg_id(), 0).show();
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.MAS_ENCONTRADA:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.MAS_PERDIDA:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.MASCOTA);
			break;
		case GeneralData.FUNDACION:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.FUNDACION);
			break;
		case GeneralData.TESTIMONIO:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.TESTIMONIO);
			break;
		case GeneralData.TIP:
			extra.objectSelected=evtLoadCards.getObjectSelected(card.getReg_id(), GeneralData.TIP);
			break;
		}
    	
    	selected.startAnimation(animCardDown);
		
		if(evtRequestDialog!=null){
			evtRequestDialog.onEvtRequestDialog(extra, null);
		}
	}

}
