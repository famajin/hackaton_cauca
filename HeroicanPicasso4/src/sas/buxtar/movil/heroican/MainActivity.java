package sas.buxtar.movil.heroican;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.clases.ClsEvento;
import sas.buxtar.movil.heroican.clases.ClsFavorito;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsRegistro;
import sas.buxtar.movil.heroican.clases.ClsTestimonio;
import sas.buxtar.movil.heroican.clases.ClsTip;
import sas.buxtar.movil.heroican.clases.ClsUsuario;
import sas.buxtar.movil.heroican.clases.EscuadronRefresh;
import sas.buxtar.movil.heroican.clases.EscuadronRefresh.EvtEscuadronRefresh;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.clases.VARS.Extra;
import sas.buxtar.movil.heroican.fragments.DialogEvento;
import sas.buxtar.movil.heroican.fragments.DialogFundacion;
import sas.buxtar.movil.heroican.fragments.DialogLoad;
import sas.buxtar.movil.heroican.fragments.DialogMap;
import sas.buxtar.movil.heroican.fragments.DialogMascotaAdoptar;
import sas.buxtar.movil.heroican.fragments.DialogMascotaEncontrada;
import sas.buxtar.movil.heroican.fragments.DialogMascotaPerdida;
import sas.buxtar.movil.heroican.fragments.DialogSetData;
import sas.buxtar.movil.heroican.fragments.DialogSetData.EvtSetData;
import sas.buxtar.movil.heroican.fragments.DialogSetTime;
import sas.buxtar.movil.heroican.fragments.DialogSetTime.EvtSetTime;
import sas.buxtar.movil.heroican.fragments.DialogTestimonio;
import sas.buxtar.movil.heroican.fragments.DialogTip;
import sas.buxtar.movil.heroican.fragments.FragDrawer;
import sas.buxtar.movil.heroican.fragments.FragDrawer.EvtDrawer;
import sas.buxtar.movil.heroican.fragments.FragDrawer.EvtDrawerUs;
import sas.buxtar.movil.heroican.fragments.FragEscuadronActivar;
import sas.buxtar.movil.heroican.fragments.FragEscuadronActivar.EvtRequestInActive;
import sas.buxtar.movil.heroican.fragments.FragEscuadronActivo;
import sas.buxtar.movil.heroican.fragments.FragEscuadronActivo.EvtRequestOtro;
import sas.buxtar.movil.heroican.fragments.FragHome;
import sas.buxtar.movil.heroican.fragments.FragHome.EvtLoadCards;
import sas.buxtar.movil.heroican.fragments.FragSubirAdoptar;
import sas.buxtar.movil.heroican.fragments.FragSubirEncontrada;
import sas.buxtar.movil.heroican.fragments.FragSubirEvento;
import sas.buxtar.movil.heroican.fragments.FragSubirPerdida;
import sas.buxtar.movil.heroican.fragments.FragSubirPerdida.EvtRequestEscuadron;
import sas.buxtar.movil.heroican.fragments.FragSubirTestimonio;
import sas.buxtar.movil.heroican.fragments.FragSubirTip;
import sas.buxtar.movil.heroican.interfaces.EvtActivityResult;
import sas.buxtar.movil.heroican.interfaces.EvtCancelDialog;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDataTime;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTitleBar;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTutorial;
import sas.buxtar.movil.heroican.tutorial.DialogTutorial;
import sas.buxtar.movil.heroican.util.Data;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.MediaUtil;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.Session;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends ActionBarActivity implements EvtDrawer, OnClickListener, EvtEndInsert, EvtRequestDialog, EvtCancelDialog, EvtRequestEscuadron, EvtRequestSelection, EvtRequestTutorial, EvtRequestOtro, EvtRequestInActive, EvtDrawerUs, EvtRequestTitleBar, EvtActivityResult, EvtSetData, EvtSetTime ,EvtRequestDataTime, EvtLoadCards{

	private VARS vars;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private String appName="";
	private FragDrawer fragDrawer;
	private DialogTutorial dialTutorial=null;
	private ViewGroup frame, buttons;
	private FragHome fragHome=null;
	private FragSubirPerdida fragSubirPerdida;
	private FragSubirAdoptar fragSubirAdoptar;
	private FragSubirEvento fragSubirEvento;
	private FragEscuadronActivo fragEscuadronInActive;
	private FragEscuadronActivar fragEscuadronActive;
	private FragSubirTestimonio fragSubirTestimonio;
	private FragSubirTip fragSubirTip;
	private FragSubirEncontrada fragsubirEncontrada;
	private LruCache<String, Bitmap> memoryCacheMultimedia;
	private Animation animInButtons, animOutButtons;
	private DialogLoad dialLoad;
	private ClsUsuario usu;
	private String showDilog_regId="";
	private int showDialog_tipe=-1;
	private NetWorkState netWorkState=null;
  	private DialogMascotaPerdida dialMascotaPerdida;
  	private DialogMascotaAdoptar dialAdoptarMascota;
  	private DialogMascotaEncontrada dialogMascotaEncontrada;
  	private DialogFundacion dialFundacion;
  	private DialogTip dialTip;
  	private DialogEvento dialEvento;
  	private DialogTestimonio dialTestimonio;
  	private DialogMap dialMap;
    private ArrayList<ClsRegistro> lstRegistros;
	private ArrayList<ClsMascota> lstMascotas;
	private ArrayList<ClsEvento> lstEventos;
	private ArrayList<ClsFundacion> lstFundaciones;
	private ArrayList<ClsTip> lstTips;
	private ArrayList<ClsTestimonio> lstTestimonios;
	private ArrayList<ClsFavorito> lstFavoritos;
	private FragmentManager fragmentManager;
    private ArrayList<ClsCard> lstCardsOriginal, lstCardsAux;
	private Data db;
    private boolean restoreFromOncreate;
    private ClsFavorito favAux;
	boolean find=false;
	private String title_adopta, title_encontrada, title_perdida, title_fundacion, title_evento, title_testimonio, title_tip;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	if(GeneralData.USU_ID.equals("")){
    		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
    		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
    	}
		
//		if(savedInstanceState==null){
//			refreshEscuadron();
//		}
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(GeneralData.PREF_ESCUADRON, "1");
		editor.commit();
    	
		title_adopta=getResources().getString(R.string.card_title_adopta);
		title_encontrada=getResources().getString(R.string.card_title_encontrada);
		title_evento=getResources().getString(R.string.card_title_evento);
		title_fundacion=getResources().getString(R.string.card_title_fundacion);
		title_perdida=getResources().getString(R.string.card_title_perdida);
		title_testimonio=getResources().getString(R.string.card_title_testimonio);
		title_tip=getResources().getString(R.string.card_title_tip);
		
		restoreFromOncreate=false;
		db= new Data(this);
		fragmentManager=getSupportFragmentManager();
		
		initMemoryMultimedia();
		
		if(savedInstanceState==null){
			vars=new VARS();
//			CLEAR ALL CACHE
//			mImageFetcher.clearCache();
			db.clear();
			vars.setDimens=false;
			vars.isShowButtons=false;
			vars.isBack=false;
			initSource();
			initFragments();
		}else{
			//RESTAURAMOS DESDE EL ONCREATE
			restoreFromOncreate=true;
//			Toast.makeText(getApplicationContext(), "RESTORE FROM ONCREATE", 0).show();
			restoreInstance(savedInstanceState);
		}
		
		initDrawer();
		reciveArgs(getIntent());
		init();
	}
	
	public void refreshEscuadron() {
		EscuadronRefresh er=new EscuadronRefresh();
		er.refresh(GeneralData.USU_ID, new EvtEscuadronRefresh() {
			@Override
			public void onEvtEscuadronRefresh(String escuadronActive) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "es: "+escuadronActive, 0).show();
				SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(GeneralData.PREF_ESCUADRON, escuadronActive);
				editor.commit();
			}
		});
	}
	
	private void initSource() {
		lstRegistros=new ArrayList<ClsRegistro>();
		lstMascotas=new ArrayList<ClsMascota>();
		lstEventos=new ArrayList<ClsEvento>();
		lstFundaciones=new ArrayList<ClsFundacion>();
		lstTestimonios=new ArrayList<ClsTestimonio>();
		lstFavoritos=new ArrayList<ClsFavorito>();
		lstTips=new ArrayList<ClsTip>();
		lstCardsAux=new ArrayList<ClsCard>();
		lstCardsOriginal=new ArrayList<ClsCard>();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		saveInstance(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onRestoreInstanceState(savedInstanceState);
			//SINO RESTAURAMOS DESDE EL ONCREATE
			if(!restoreFromOncreate){
				try {
					restoreInstance(savedInstanceState);	
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("ERRRORRR", e.getMessage().toString());
				}
//				Toast.makeText(getApplicationContext(), "RESTORE", 0).show();	
			}else{
				//CAMBIAMOS EL ESTADO DE VARIABLE PARA QUE LA PROXIMA
				//VEZ PASE
				restoreFromOncreate=false;
			}
	}

	public void saveInstance(Bundle outState) {
		try {
			saveSource(outState);
			saveFragments(outState);	
		} catch (Exception e) {
			// TODO: handle exception
//			startActivity(new Intent(getApplicationContext(), SplashActivity.class));
//			finish();
		}
	}
	public void restoreInstance(Bundle savedInstanceState) {
		try {
			restoreSource(savedInstanceState);
			restoreFragments(savedInstanceState);	
		} catch (Exception e) {
			// TODO: handle exception
			startActivity(new Intent(getApplicationContext(), SplashActivity.class));
			finish();
		}
	}
	
	private void saveSource(Bundle outState) {
		outState.putBoolean("refresh", vars.refresh);
		outState.putInt("h_items", vars.h_items);
		outState.putInt("h_item", vars.h_item);
		outState.putInt("size", vars.size);
		outState.putInt("selected", vars.selected);
		outState.putInt("heightObject", vars.heightObject);
		outState.putInt("heightContLst", vars.heightContLst);
		outState.putInt("code", vars.codeCategory);
		outState.putBoolean("picturefromThis", vars.picturefromThis);
		outState.putBoolean("isShowButtons", vars.isShowButtons);
		outState.putBoolean("setDimens", vars.setDimens);
		outState.putBoolean("isBack", vars.isBack);
		outState.putBoolean("doDrop", vars.doDrop);
		outState.putString("titleActionBar", vars.titleActionBar);
		outState.putString("fragTag", vars.fragTag);
		outState.putString("dialTag", vars.dialTag);
		outState.putString("path", vars.path);
		outState.putString("dataTime", vars.dataTime);
		outState.putBoolean("scrollTo", vars.scrollTo);
		outState.putInt("sizeExtra", vars.extras.size());
		
		for (int i = 0; i < vars.extras.size(); i++) {
			Extra e=vars.extras.get(i);
			outState.putString("e_dialTag"+i, e.dialTag);
			outState.putInt("e_tipe"+i, e.tipe);
			String r="";
			if(e.regIdExternal!=null){
				r=e.regIdExternal;
				outState.putString("e_regIdExternal"+i, r);
			}else{
				r=e.card.getReg_id();
				outState.putString("e_regId"+i, r);	
			}
			Log.e("SAVE", "save reg_id: "+r);
//			Toast.makeText(getApplicationContext(), "save reg_id: "+r, 0).show();
		}
	}
	
	private void restoreSource(Bundle savedInstanceState) {
		vars=new VARS();
		vars.refresh=savedInstanceState.getBoolean("refresh");
		vars.scrollTo=savedInstanceState.getBoolean("scrollTo");
		vars.h_items=savedInstanceState.getInt("h_items");
		vars.h_item=savedInstanceState.getInt("h_item");
		vars.size=savedInstanceState.getInt("size");
		vars.heightContLst=savedInstanceState.getInt("heightContLst");
		vars.heightObject=savedInstanceState.getInt("heightObject");
		vars.selected=savedInstanceState.getInt("selected");
		vars.titleActionBar=savedInstanceState.getString("titleActionBar");
		vars.dataTime=savedInstanceState.getString("dataTime");
		vars.codeCategory=savedInstanceState.getInt("code");
		vars.path=savedInstanceState.getString("path");
		vars.fragTag=savedInstanceState.getString("fragTag");
		vars.dialTag=savedInstanceState.getString("dialTag");
		vars.isBack=savedInstanceState.getBoolean("isBack");
		vars.picturefromThis=savedInstanceState.getBoolean("picturefromThis");
		vars.doDrop=savedInstanceState.getBoolean("doDrop");
		vars.setDimens=savedInstanceState.getBoolean("setDimens");
		vars.isShowButtons=savedInstanceState.getBoolean("isShowButtons");
		vars.sizeExtra=savedInstanceState.getInt("sizeExtra");
		lstRegistros=new ArrayList<ClsRegistro>();
		lstMascotas=new ArrayList<ClsMascota>();
		lstEventos=new ArrayList<ClsEvento>();
		lstFundaciones=new ArrayList<ClsFundacion>();
		lstTestimonios=new ArrayList<ClsTestimonio>();
		lstFavoritos=new ArrayList<ClsFavorito>();
		lstTips=new ArrayList<ClsTip>();
		lstCardsAux=new ArrayList<ClsCard>();
		lstCardsOriginal=new ArrayList<ClsCard>();
		
		db.loadSource(lstRegistros, lstMascotas, lstEventos, lstFundaciones, lstTips, lstTestimonios, lstFavoritos);
		onEvtLoadCards();
		
		vars.extras=new ArrayList<Extra>();
		for (int i = 0; i < vars.sizeExtra; i++) {
			Extra e=new Extra();
			e.dialTag=savedInstanceState.getString("e_dialTag"+i);
			e.tipe=savedInstanceState.getInt("e_tipe"+i);
			String regIdExternal=savedInstanceState.getString("e_regIdExternal"+i);
			if(regIdExternal!=null){
				e.regIdExternal=regIdExternal;
				e.regId=e.regIdExternal;
			}else{
				e.regId=savedInstanceState.getString("e_regId"+i);
			}
			e.card=getCardSelected(e.regId);
			int tipe=e.tipe;
			switch (tipe) {
			case GeneralData.MAS_ADOPTAR:
				tipe=GeneralData.MASCOTA;
				break;
			case GeneralData.MAS_ENCONTRADA:
				tipe=GeneralData.MASCOTA;
				break;
			case GeneralData.MAS_PERDIDA:
				tipe=GeneralData.MASCOTA;
				break;
			}
			e.objectSelected=getObjectSelected(e.regId, tipe);
			Log.e("RESTORE", "restore reg_id:"+e.regId);
			if(e.card==null){
				Log.e("IS NULL", "CARD");
			}
			if(e.objectSelected==null){
				Log.e("IS NULL", "OBJECT");
			}
			
			Log.e("RESTORE", "restore reg_id:"+e.regId);
			vars.extras.add(e);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	private void removeFrag(Fragment frag) {
		try {
			fragmentManager.beginTransaction()
			.remove(frag).commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
    private void replaceAnim(Fragment frag2, String tag) {
		// TODO Auto-generated method stub
    	fragmentManager.beginTransaction()
    	.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
    	.replace(R.id.main_content, frag2, tag)
    	.commit();
    	nullabledFragment(tag);
	}
    
    private void replaceAnimBack(Fragment frag, String tag) {
		// TODO Auto-generated method stub
    	fragmentManager.beginTransaction()
		.setCustomAnimations(R.anim.anim_out2, R.anim.anim_in2)
		.replace(R.id.main_content, frag, tag).commit();
    	nullabledFragment(tag);
	}
    
    private void nullabledFragment(String tag) {
    	if(vars.selected==R.id.drawer_btnInicio || vars.selected==R.id.drawer_btnEventos || vars.selected==R.id.drawer_btnFundaciones || vars.selected==R.id.drawer_btnMasEncontradas || vars.selected==R.id.drawer_btnMasPerdidas || vars.selected==R.id.drawer_btnAdopta || vars.selected==R.id.drawer_btnTips || vars.selected==R.id.drawer_btnTestimonios){
    		if(fragHome!=null){
    			removeFrag(fragHome);
    			fragHome=null;
    		}
    	}else{
    		switch (vars.selected) {
    		case R.id.drawer_btnMascotaEncontrada:
        		if(fragsubirEncontrada!=null){
        			removeFrag(fragsubirEncontrada);
        			fragsubirEncontrada=null;
        		}
    			break;
    		case R.id.drawer_btnEscuadron:
    			if(vars.fragTag.equals("fragEscuadronInActive")){
    				removeFrag(fragEscuadronInActive);
    				fragEscuadronInActive=null;
    			}else if(vars.fragTag.equals("fragSubirPerdida")){
        			removeFrag(fragSubirPerdida);
        			fragSubirPerdida=null;
    			}
    			break;
    		case R.id.drawer_btnSubirAdopcion:
    			if(fragSubirAdoptar!=null){
        			removeFrag(fragSubirAdoptar);
        			fragSubirAdoptar=null;
        		}
    			break;
    		case R.id.drawer_btnSubirEvento:
    			if(fragSubirEvento!=null){
        			removeFrag(fragSubirEvento);
        			fragSubirEvento=null;
        		}
    			break;
    		case R.id.drawer_btnSubirTestimonio:
    			if(fragSubirTestimonio!=null){
        			removeFrag(fragSubirTestimonio);
        			fragSubirTestimonio=null;
        		}
    			break;
    		case R.id.drawer_btnSubirTip:
    			if(fragSubirTip!=null){
        			removeFrag(fragSubirTip);
        			fragSubirTip=null;
        		}
    			break;
    		}	
    	}
    	vars.fragTag=tag;
	}

	private void initDrawer() {
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    	// between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                toolbar,            /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
				getSupportActionBar().setTitle(vars.titleActionBar);
                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
 
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(appName.equals("")){
                	appName=getResources().getString(R.string.app_name);
                }
				getSupportActionBar().setTitle(appName);
                supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private void initFragments() {
		fragDrawer=new FragDrawer();
		fragDrawer.setEvtDrawer(this);
		fragDrawer.setVars(vars);
		fragDrawer.setEvtDrawerUs(this);
		fragDrawer.setRetainInstance(true);
		fragmentManager.beginTransaction()
		.add(R.id.main_drawer, fragDrawer, "fragDrawer")
		.commit();
	}
	
	private void saveFragments(Bundle outState) {
		if(vars.fragTag.equals("fragHome") && fragHome!=null){
				fragmentManager.putFragment(outState, vars.fragTag, fragHome);
		}else if(vars.fragTag.equals("fragSubirEvento") && fragSubirEvento!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragSubirEvento);
		}else if(vars.fragTag.equals("fragEscuadronInActive") && fragEscuadronInActive!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragEscuadronInActive);
		}else if(vars.fragTag.equals("fragSubirAdoptar") && fragSubirAdoptar!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragSubirAdoptar);
		}else if(vars.fragTag.equals("fragsubirEncontrada") && fragsubirEncontrada!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragsubirEncontrada);
		}else if(vars.fragTag.equals("fragSubirPerdida") && fragsubirEncontrada!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragSubirPerdida);
		}else if(vars.fragTag.equals("fragSubirTestimonio") && fragSubirTestimonio!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragSubirTestimonio);
		}else if(vars.fragTag.equals("fragSubirTip") && fragSubirTip!=null){
			fragmentManager.putFragment(outState, vars.fragTag, fragSubirTip);
		}
		if(fragDrawer!=null){
			fragmentManager.putFragment(outState, "fragDrawer", fragDrawer);	
		}
		try {
			if(!vars.dialTag.equals("")){
				if(vars.dialTag.equals("dialEvento") && dialEvento!=null){
					if(dialEvento.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialEvento);
					}
				}else if(vars.dialTag.equals("dialAdoptarMascota") && dialAdoptarMascota!=null){
					if(dialAdoptarMascota.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialAdoptarMascota);
					}
				}else if(vars.dialTag.equals("dialFundacion") && dialFundacion!=null){
					if(dialFundacion.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialFundacion);
					}
				}else if(vars.dialTag.equals("dialMascotaPerdida") && dialMascotaPerdida!=null){
					if(dialMascotaPerdida.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialMascotaPerdida);
					}
				}else if(vars.dialTag.equals("dialogMascotaEncontrada") && dialogMascotaEncontrada!=null){
					if(dialogMascotaEncontrada.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialogMascotaEncontrada);
					}
				}else if(vars.dialTag.equals("dialTestimonio") && dialTestimonio!=null){
					if(dialTestimonio.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialTestimonio);
					}
				}else if(vars.dialTag.equals("dialTip") && dialTip!=null){
					if(dialTip.getDialog().isShowing()){
						fragmentManager.putFragment(outState, vars.dialTag, dialTip);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void restoreFragments(Bundle s) {
		fragmentManager.executePendingTransactions();
		fragDrawer = (FragDrawer) fragmentManager.getFragment(s, "fragDrawer");
		fragDrawer.setVars(vars);
		fragDrawer.setEvtDrawer(this);
		fragDrawer.setEvtDrawerUs(this);
		fragDrawer.setRetainInstance(true);
		
		if(vars.fragTag.equals("fragHome")){
			vars.doDrop=false;
			vars.scrollTo=false;
			fragHome = (FragHome) fragmentManager.getFragment(s, vars.fragTag);
			fragHome.setSource(lstCardsOriginal, lstCardsAux, lstRegistros, lstMascotas, lstEventos, lstFundaciones, lstTips, lstTestimonios, lstFavoritos);
			FragHome.restoreInstance(fragHome, false, this, this, this, this, vars);
		}else if(vars.fragTag.equals("fragSubirEvento")){
			fragSubirEvento=(FragSubirEvento) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirEvento.restoreInstace(fragSubirEvento, vars, memoryCacheMultimedia, this, this, this, this, this);
		}else if(vars.fragTag.equals("fragEscuadronInActive")){
			fragEscuadronInActive=(FragEscuadronActivo) fragmentManager.getFragment(s, vars.fragTag);
			FragEscuadronActivo.restoreInstance(fragEscuadronInActive, this);
		}else if(vars.fragTag.equals("fragSubirAdoptar")){
			fragSubirAdoptar=(FragSubirAdoptar) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirAdoptar.restoreInstace(fragSubirAdoptar, vars, memoryCacheMultimedia, this, this, this);
		}else if(vars.fragTag.equals("fragsubirEncontrada")){
			fragsubirEncontrada=(FragSubirEncontrada) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirEncontrada.restoreInstace(fragsubirEncontrada, vars, memoryCacheMultimedia, this, this, this, this);
		}else if(vars.fragTag.equals("fragSubirPerdida")){
			fragSubirPerdida=(FragSubirPerdida) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirPerdida.restoreInstace(fragSubirPerdida, vars, memoryCacheMultimedia, this, this, this, this, this, this);
		}else if(vars.fragTag.equals("fragSubirTestimonio")){
			fragSubirTestimonio=(FragSubirTestimonio) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirTestimonio.restoreInstace(fragSubirTestimonio, vars, memoryCacheMultimedia, this, this, this);
		}else if(vars.fragTag.equals("fragSubirTip")){
			fragSubirTip=(FragSubirTip) fragmentManager.getFragment(s, vars.fragTag);
			FragSubirTip.restoreInstace(fragSubirTip, vars, memoryCacheMultimedia, this, this, this);
		}
		
		try {
			if(!vars.dialTag.equals("")){
				Extra extra=null;
				for (int i = 0; i < vars.extras.size(); i++) {
					Extra e=vars.extras.get(i);
					if(e.dialTag.equals(vars.dialTag)){
						extra=e;							
						i=vars.extras.size();
					}
				}
				if(extra==null){
//					Toast.makeText(getApplicationContext(), "E = NULL", 0).show();
				}
				
				if(vars.dialTag.equals("dialEvento")){
					dialEvento=(DialogEvento) fragmentManager.getFragment(s, vars.dialTag);
					DialogEvento.restoreInstace(dialEvento, this, extra);
				}else if(vars.dialTag.equals("dialAdoptarMascota")){
					dialAdoptarMascota=(DialogMascotaAdoptar) fragmentManager.getFragment(s, vars.dialTag);
					DialogMascotaAdoptar.restoreInstace(dialAdoptarMascota, this, extra);
				}else if(vars.dialTag.equals("dialFundacion")){
					dialFundacion=(DialogFundacion) fragmentManager.getFragment(s, vars.dialTag);
					DialogFundacion.restoreInstace(dialFundacion, this, extra);
				}else if(vars.dialTag.equals("dialMascotaPerdida")){
					dialMascotaPerdida=(DialogMascotaPerdida) fragmentManager.getFragment(s, vars.dialTag);
					DialogMascotaPerdida.restoreInstace(dialMascotaPerdida, this, extra);
				}else if(vars.dialTag.equals("dialogMascotaEncontrada")){
					dialogMascotaEncontrada=(DialogMascotaEncontrada) fragmentManager.getFragment(s, vars.dialTag);
					DialogMascotaEncontrada.restoreInstace(dialogMascotaEncontrada, this, extra);
				}else if(vars.dialTag.equals("dialTestimonio")){
					dialTestimonio=(DialogTestimonio) fragmentManager.getFragment(s, vars.dialTag);
					DialogTestimonio.restoreInstace(dialTestimonio, this, extra);
				}else if(vars.dialTag.equals("dialTip")){
					dialTip=(DialogTip) fragmentManager.getFragment(s, vars.dialTag);
					DialogTip.restoreInstace(dialTip, this, extra);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(drawerLayout.isDrawerOpen(Gravity.START)){
			getMenuInflater().inflate(R.menu.menu_null, menu);
		}else{
			if(vars.fragTag.equals("fragHome")){
				if(vars.selected==R.id.drawer_btnInicio){
					getMenuInflater().inflate(R.menu.menu_main, menu);
				}else if(vars.selected !=R.id.drawer_btnFundaciones){
					getMenuInflater().inflate(R.menu.menu_subir, menu);	
				}
			}else{
				getMenuInflater().inflate(R.menu.menu_null, menu);
			}
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}else if(item.getItemId()==R.id.menu_subir){
			int sel=-1;
			switch (vars.selected) {
				case R.id.drawer_btnMasPerdidas:
					sel=R.id.drawer_btnEscuadron;
					break;
				case R.id.drawer_btnEventos:
					sel=R.id.drawer_btnSubirEvento;
					break;
				case R.id.drawer_btnMasEncontradas:
					sel=R.id.drawer_btnMascotaEncontrada;
					break;
				case R.id.drawer_btnAdopta:
					sel=R.id.drawer_btnSubirAdopcion;
					break;
				case R.id.drawer_btnTips:
					sel=R.id.drawer_btnSubirTip;
					break;
				case R.id.drawer_btnTestimonios:
					sel=R.id.drawer_btnSubirTestimonio;
					break;
			}	
			item.setVisible(false);
			if(sel!=-1){
				fragDrawer.setSelection(sel, true);
				getSupportActionBar().setTitle(vars.titleActionBar);
			}
		}else if(item.getItemId()==R.id.menu_tutorial){
			onEvtEvtRequestTutorial(-1);
		}
		
		return true;
	}
	
    @Override
	 protected void onStart() {
	 // TODO Auto-generated method stub
		 super.onStart();
		 if(GeneralData.SEND_ANALYTICS){
			try {
				 GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStart(this);
//				 SEND_ANALYTICS();
			} catch (Exception e) {
				// TODO: handle exception
			} 
		 }
	 }
	 
	@SuppressLint("NewApi")
	private void init() {
		reciveArgs(getIntent());
		netWorkState=new NetWorkState();
		
		dialLoad=new DialogLoad();
		usu=new ClsUsuario();
		
		animInButtons=AnimationUtils.loadAnimation(this, R.anim.anim_in_buttons);
		animOutButtons=AnimationUtils.loadAnimation(this, R.anim.anim_out_buttons);
		animOutButtons.setAnimationListener(out);
		frame =(ViewGroup) findViewById(R.id.main_content);
		((ImageButton)findViewById(R.id.main_btnCamera)).setOnClickListener(this);
		((ImageButton)findViewById(R.id.main_btnEscuadron)).setOnClickListener(this);
		
		buttons=(ViewGroup) findViewById(R.id.main_buttons);
		
		vars.picturefromThis=true;
		frame.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(){ 
		@Override 
        public  void onGlobalLayout(){ 
        	if(!vars.setDimens){
        		vars.setDimens=true;
        		vars.heightContLst=frame.getHeight()-getResources().getDimensionPixelSize(R.dimen.H_cancel);
//        		Toast.makeText(getApplicationContext(), "heightContLst "+vars.heightContLst, 0).show();
        		fragDrawer.setSelection(R.id.drawer_btnInicio, true);
    			
				SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
				int shows=preferences.getInt(GeneralData.PREF_TUTORIAL, 0);
				if(shows<GeneralData.SHOWMAX_TUTORIAL){
					shows++;
					SharedPreferences.Editor editor = preferences.edit();
					editor.putInt(GeneralData.PREF_TUTORIAL, shows);
					editor.commit();
					onEvtEvtRequestTutorial(-1);
				}
        	}else{
        		try {
                    ViewTreeObserver obs = frame.getViewTreeObserver ();
                    if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { 
                        obs.removeOnGlobalLayoutListener(this); 
                    }  else  { 
                        obs.removeGlobalOnLayoutListener (this); 
                    } 	
				} catch (Exception e) {
					// TODO: handle exception
				}
        	}
        }
    });
		
		if(!vars.isShowButtons){
			buttons.setVisibility(View.GONE);
		}else{
			buttons.setVisibility(View.VISIBLE);
		}
	}
	
  	private void initMemoryMultimedia() {
		// Obtenerride
//            protmos el maximo de memoria disponible para la aplicación
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Usamos solo 1/8 para el cache de bitmaps
	    final int cacheSize = maxMemory / 10;
		
	    memoryCacheMultimedia = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            //Metodo que retorna el tamaño de cada Bitmap almacenado en memoria cache
	        	// el tamaño se expresa en kilobytes
	            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
	        }
//            @Oveected void entryRemoved(boolean evicted, String key,
//            		Bitmap oldValue, Bitmap newValue) {
//            	// TODO Auto-generated method stub
//            	super.entryRemoved(evicted, key, oldValue, newValue);
//        		try {
//            		if(evicted && oldValue!=null){
//	            		oldValue.recycle();
//	            		oldValue=null;
//	            		if(memoryCacheMultimedia.get(key)!=null){
//	            			memoryCacheMultimedia.remove(key);	
//	            		}
//	            		Log.e("OLD VALUE", key);
//            		}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//            }
	    };
	}


	 @Override
	 protected void onStop() {
	 // TODO Auto-generated method stub
		 if(GeneralData.SEND_ANALYTICS){
			try {
				 GoogleAnalytics.getInstance(getApplicationContext()).reportActivityStop(this);	
			} catch (Exception e) {
				// TODO: handle exception
			}
		 }
		 super.onStop();
	 }
 	
	private void SEND_ANALYTICS() {
		if(GeneralData.SEND_ANALYTICS){
			switch (vars.selected) {
			case R.id.drawer_btnInicio:
				SendAnalytics(getResources().getString(R.string.screen_inicio));
				break;
			case R.id.drawer_btnEventos:
				SendAnalytics(getResources().getString(R.string.screen_eventos));
				break;
			case R.id.drawer_btnFundaciones:
				SendAnalytics(getResources().getString(R.string.screen_fundaciones));
				break;
			case R.id.drawer_btnMasEncontradas:
				SendAnalytics(getResources().getString(R.string.screen_encontradas));
				break;
			case R.id.drawer_btnMasPerdidas:
				SendAnalytics(getResources().getString(R.string.screen_perdidas));
				break;
			case R.id.drawer_btnAdopta:
				SendAnalytics(getResources().getString(R.string.screen_adoptar));
				break;
			case R.id.drawer_btnTips:
				SendAnalytics(getResources().getString(R.string.screen_tips));
				break;
			case R.id.drawer_btnTestimonios:
				SendAnalytics(getResources().getString(R.string.screen_testimonios));
				break;
			case R.id.drawer_btnMascotaEncontrada:
				SendAnalytics(getResources().getString(R.string.screen_encontradas));
				break;
			case R.id.drawer_btnEscuadron:
	    		if(!vars.fragTag.equals("fragSubirMascota")){
	    			if(GeneralData.IS_SEARCH_ACTIVE.equals("")){
	    				SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
	    				GeneralData.IS_SEARCH_ACTIVE=preferences.getString(GeneralData.PREF_ESCUADRON, "");
	    			}
	    			if(GeneralData.IS_SEARCH_ACTIVE.equals("1")){
	    				SendAnalytics(getResources().getString(R.string.screen_escuadronActivo));
	    			}else{
	    				SendAnalytics(getResources().getString(R.string.screen_escuadron));
	    			}
	    		}
				break;
			case R.id.drawer_btnSubirAdopcion:
				SendAnalytics(getResources().getString(R.string.screen_subirAdopcion));
				break;
			case R.id.drawer_btnSubirEvento:
				SendAnalytics(getResources().getString(R.string.screen_subirEvento));
				break;
			case R.id.drawer_btnSubirTestimonio:
				SendAnalytics(getResources().getString(R.string.screen_subirTestimonio));
				break;
			case R.id.drawer_btnSubirTip:
				SendAnalytics(getResources().getString(R.string.screen_subirTip));
				break;
			}
		}
	}
	 
	private void SendAnalytics(String screenName) {
		if(GeneralData.SEND_ANALYTICS){
			Tracker t = ((AnalyticsSampleApp) getApplication()).getTracker(
	                TrackerName.APP_TRACKER);
	        t.setScreenName(screenName);
	        t.send(new HitBuilders.AppViewBuilder().build());
		}
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		reciveArgs(intent);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		GeneralData.USU_ID="";
		GeneralData.IS_SEARCH_ACTIVE="";
		GeneralData.USU_CIUDAD="";
		GeneralData.LOGIN_TIPE=-1;
//		try{
//			//GARBAGE COLLECTOR
//			System.gc();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		super.onDestroy();
	}
	
    @Override
    public void onResume() {
        super.onResume();
    }
    
	@Override
	public void onPause() {
	    super.onPause();
	}
	
	private void animButtons(boolean visible) {
		if(vars.isShowButtons!=visible || visible){
			vars.isShowButtons=visible;
			if(visible){
				if(animInButtons==null){
					animInButtons=AnimationUtils.loadAnimation(this, R.anim.anim_in_buttons);
				}
				buttons.setVisibility(View.VISIBLE);
				buttons.clearAnimation();
				buttons.startAnimation(animInButtons);
			}else{
				if(animOutButtons==null){
					animOutButtons=AnimationUtils.loadAnimation(this, R.anim.anim_out_buttons);
					animOutButtons.setAnimationListener(out);
				}
				buttons.clearAnimation();
				buttons.startAnimation(animOutButtons);
			}
		}
	}
	
	AnimationListener out =new AnimationListener() {
		
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
			buttons.setVisibility(View.GONE);
		}
	};
	
	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			if(was.equals(GeneralData.WORK)){
				SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(GeneralData.PREF_DEVICETAG, "***");
				editor.commit();
			}
		}
		logOut();
	}
	
	private void logOut() {
		try {
			dialLoad.getDialog().cancel();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		GeneralData.IS_SEARCH_ACTIVE="";
		
//		try {
//			Looper.prepare();
//			Log.e("prepare", "prepare");
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("CATCH", "prepare");
//		}
		finish();
		startActivity(new Intent(this, LoginActivity.class));
//		try {
//			Looper.loop();
//		    Looper.myLooper().quit();
//			Log.e("quit looper", "quit looper");
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("CATCH", "quit looper");
//		}
		
//    	SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
//    	Toast.makeText(getApplicationContext(), "tag quedo"+preferences.getString(GeneralData.PREF_DEVICETAG, "***"), 0).show();
	}

	private void requestLogOut() {
		dialLoad.show(fragmentManager, "");
		try {
			if(vars.selected==R.id.drawer_btnInicio || vars.selected==R.id.drawer_btnTestimonios || vars.selected==R.id.drawer_btnAdopta || vars.selected==R.id.drawer_btnTips || vars.selected==R.id.drawer_btnMasPerdidas || vars.selected==R.id.drawer_btnEventos || vars.selected==R.id.drawer_btnFundaciones || vars.selected==R.id.drawer_btnMasEncontradas){
				fragmentManager.beginTransaction()
				.remove(fragHome).commit();
			}else{
				switch (vars.selected) {
				case R.id.drawer_btnMascotaEncontrada:
					fragmentManager.beginTransaction()
					.remove(fragsubirEncontrada).commit();
					break;
				case R.id.drawer_btnEscuadron:
					if(fragEscuadronActive!=null){
						fragmentManager.beginTransaction()
						.remove(fragEscuadronActive).commit();	
					}
					
					if(fragEscuadronInActive!=null){
						fragmentManager.beginTransaction()
						.remove(fragEscuadronInActive).commit();	
					}
					break;
				case R.id.drawer_btnSubirAdopcion:
					fragmentManager.beginTransaction()
					.remove(fragSubirAdoptar).commit();
					break;
				case R.id.drawer_btnSubirEvento:
					fragmentManager.beginTransaction()
					.remove(fragSubirEvento).commit();
					break;
				case R.id.drawer_btnSubirTestimonio:
					fragmentManager.beginTransaction()
					.remove(fragSubirTestimonio).commit();
					break;
				case R.id.drawer_btnSubirTip:
					fragmentManager.beginTransaction()
					.remove(fragSubirTip).commit();
					break;
				}	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		
		if(GeneralData.LOGIN_TIPE==-1){
			GeneralData.LOGIN_TIPE=preferences.getInt(GeneralData.PREF_LOGIN_TIPE, -1);	
		}
		
		if(GeneralData.LOGIN_TIPE==GeneralData.LOGIN_FACE){
			logOutFacebook();
		}else if(GeneralData.LOGIN_TIPE==GeneralData.LOGIN_PLUS){
			
		}
		
		
		usu.setUsu_id(preferences.getString(GeneralData.PREF_DEVICEID, "***"));
		usu.setEvtEndRequest(this);
		
		//CERRAMOS SESION INTERNAMENTE 
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(GeneralData.PREF_EMAIL, "@");
		editor.putString(GeneralData.PREF_DEVICEID, "***");
		//GUARDAMOS EN EL TAG POR SI HAY ALGUN ERROR CERRAMOS SESION LUEGO
		editor.putString(GeneralData.PREF_DEVICETAG, usu.getUsu_id());
		editor.putString(GeneralData.PREF_USUID, "***");
		editor.putString(GeneralData.PREF_USUCIUDAD, "0");
		editor.putString(GeneralData.PREF_ESCUADRON, ""+GeneralData.INACTIVE);
		editor.putInt(GeneralData.PREF_LOGIN_TIPE, -1);
		editor.putString(GeneralData.PREF_STATE, ""+GeneralData.DESCONNECTED);
		editor.commit();
		
		if(netWorkState.isOnLine(this)){
			usu.requestLogOut();
		}else{
			logOut();
		}
	}
	
	private void logOutFacebook() {
		try {
		    Session session = Session.getActiveSession();
		    if(session != null) {
		        if(!session.isClosed()){
		            session.closeAndClearTokenInformation();
		        }
		    }else{
		        session = new Session(this);
		        Session.setActiveSession(session);
		        session.closeAndClearTokenInformation();
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void OnCancelDialog(Bundle args, String id_extra) {
		// TODO Auto-generated method stub
		if(vars.selected==R.id.drawer_btnInicio || vars.selected==R.id.drawer_btnTestimonios|| vars.selected==R.id.drawer_btnEventos || vars.selected==R.id.drawer_btnFundaciones || vars.selected==R.id.drawer_btnMasEncontradas || vars.selected==R.id.drawer_btnMasPerdidas || vars.selected==R.id.drawer_btnAdopta || vars.selected==R.id.drawer_btnTips){
			fragHome.cancelDown();
			//BORRAMOS EL EXTRA 
			for (int i = 0; i < vars.extras.size(); i++) {
				if(vars.extras.get(i).dialTag.equals(vars.dialTag)){
					vars.extras.remove(i);
					i=vars.extras.size()+1;
				}
			}
			SEND_ANALYTICS();
		}else if(args!=null){
			try {
				switch (vars.selected) {
				case R.id.drawer_btnSubirEvento:
					fragSubirEvento.setMapValues(args);
					break;

				case R.id.drawer_btnMascotaEncontrada:
					fragsubirEncontrada.setMapValues(args);
					break;
					
				case R.id.drawer_btnEscuadron:
					fragSubirPerdida.setMapValues(args);
					break;
				}	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		vars.dialTag="";
	}
	
	
//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		if ((keyCode == KeyEvent.KEYCODE_BACK)){
//			if(vars.selected==R.id.drawer_btnInicio){
//				vars.isBack=false;
//				finish();
//			}else{
//				try {
//					if(fragSubirPerdida!=null){
//						if(vars.callInfo){
//							vars.callInfo=false;
//							onEvtRequestEscuadron(false, null);
//						}else{
//							vars.path="";
//							vars.isBack=true;
//							fragDrawer.setSelection(R.id.drawer_btnInicio, true);
//						}
//					}else{
//						vars.path="";
//						vars.isBack=true;
//						fragDrawer.setSelection(R.id.drawer_btnInicio, true);
//						supportInvalidateOptionsMenu();
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					vars.path="";
//					vars.isBack=true;
//					fragDrawer.setSelection(R.id.drawer_btnInicio, true);
//				}	
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(vars.selected==R.id.drawer_btnInicio){
			vars.isBack=false;
			finish();
		}else{
			try {
				if(fragSubirPerdida!=null){
//					fragSubirPerdida=null;
					if(vars.callInfo){
						vars.callInfo=false;
						onEvtRequestEscuadron(false, null);
					}else{
						vars.path="";
						vars.isBack=true;
						fragDrawer.setSelection(R.id.drawer_btnInicio, true);
					}
				}else{
					vars.path="";
					vars.isBack=true;
					fragDrawer.setSelection(R.id.drawer_btnInicio, true);
					supportInvalidateOptionsMenu();
				}
			} catch (Exception e) {
				// TODO: handle exception
				vars.path="";
				vars.isBack=true;
				fragDrawer.setSelection(R.id.drawer_btnInicio, true);
			}
			return;
		}
		super.onBackPressed();
	}

	@Override
	public void onEvtRequestEscuadron(boolean request, Bundle args) {
		// TODO Auto-generated method stub
		if(request){
			if(fragEscuadronActive==null){
				fragEscuadronActive=FragEscuadronActivar.newInstace(this);
			}
			fragEscuadronActive.setArguments(args);
	    	fragmentManager.beginTransaction()
	    	.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
	    	.replace(R.id.main_content, fragEscuadronActive).commit();
		}else{
			if(fragSubirPerdida==null){
				fragSubirPerdida=FragSubirPerdida.newInstace(vars, memoryCacheMultimedia, this, this, this, this, this, this);
			}else{
				FragSubirPerdida.restoreInstace(fragSubirPerdida, vars, memoryCacheMultimedia, this, this, this, this, this, this);
			}
			fragmentManager.beginTransaction()
	    	.setCustomAnimations(R.anim.anim_out2, R.anim.anim_in2)
	    	.replace(R.id.main_content, fragSubirPerdida).commit();
		}
	}

	@Override
	public void onEvtRequestSelection(int selection) {
		// TODO Auto-generated method stub
		if(fragDrawer==null){
			fragDrawer=(FragDrawer) fragmentManager.findFragmentByTag("fragDrawer");
			fragDrawer.setEvtDrawer(this);
			fragDrawer.setEvtDrawerUs(this);
			fragDrawer.setVars(vars);
			fragDrawer.setRetainInstance(true);
		}
		if(fragDrawer!=null){
			fragDrawer.setSelection(selection, true);	
		}
	}

	@Override
	public void onEvtEvtRequestTutorial(int tipe) {
		// TODO Auto-generated method stub
		if(dialTutorial==null){
			dialTutorial=new DialogTutorial();
			dialTutorial.setEvtDrawerSelection(this);
		}
		if(tipe==-1){
			dialTutorial.setArguments(null);
		}else{
			if(dialTutorial.getArguments()==null){
				Bundle b=new Bundle();
				b.putInt("tutorial", tipe);
				dialTutorial.setArguments(b);
			}else{
				dialTutorial.getArguments()
				.putInt("tutorial", tipe);
			}	
		}
		dialTutorial.show(fragmentManager, "");
	}

	@Override
	public void onEvtRequestOtro() {
		// TODO Auto-generated method stub
		fragSubirPerdida=null;
		if(fragSubirPerdida==null){
			fragSubirPerdida=FragSubirPerdida.newInstace(vars, memoryCacheMultimedia, this, this, this, this, this, this);
		}
		replaceAnim(fragSubirPerdida, "fragSubirMascota");
	}

	@Override
	public void onEvtRequestInactive() {
		// TODO Auto-generated method stub
		if(fragEscuadronInActive==null){
			fragEscuadronInActive=new FragEscuadronActivo();
			fragEscuadronInActive.setEvtRequestOtro(this);
		}
		replaceAnim(fragEscuadronInActive, "fragEscuadronInActive");
	}
	
	@Override
	public void onEvtRequestDialog(Extra extra, Bundle args) {
		// TODO Auto-generated method stub
		boolean concurrence=false;
    	switch (extra.tipe) {
		case GeneralData.EVENTO:
			SendAnalytics(getResources().getString(R.string.dialog_eventos));
			
			if(dialEvento==null){
				dialEvento=DialogEvento.newInstace(this, extra);
			}else{
				DialogEvento.restoreInstace(dialEvento, this, extra);
			}
			if(dialEvento.getDialog()==null){
				concurrence=false;
			}else if(dialEvento.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
				vars.dialTag="dialEvento";
				dialEvento.show(fragmentManager, vars.dialTag);
			}else{
				DialogEvento dial=DialogEvento.newInstace(this, extra);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.MAS_ADOPTAR:
			SendAnalytics(getResources().getString(R.string.dialog_adoptar));
			if(dialAdoptarMascota==null){
				dialAdoptarMascota=DialogMascotaAdoptar.newInstace(this, extra);
			}else{
				DialogMascotaAdoptar.restoreInstace(dialAdoptarMascota, this, extra);
			}
			if(dialAdoptarMascota.getDialog()==null){
				concurrence=false;
			}else if(dialAdoptarMascota.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
//				dialAdoptarMascota.setArguments(args);
				vars.dialTag="dialAdoptarMascota";
				dialAdoptarMascota.show(fragmentManager, vars.dialTag);	
			}else{
				DialogMascotaAdoptar dial=DialogMascotaAdoptar.newInstace(this, extra);
//				dial.setArguments(args);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.MAS_ENCONTRADA:
			SendAnalytics(getResources().getString(R.string.dialog_encontradas));
			if(dialogMascotaEncontrada==null){
				dialogMascotaEncontrada=DialogMascotaEncontrada.newInstace(this, extra);
			}else{
				DialogMascotaEncontrada.restoreInstace(dialogMascotaEncontrada, this, extra);
			}
			if(dialogMascotaEncontrada.getDialog()==null){
				concurrence=false;
			}else if(dialogMascotaEncontrada.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
				vars.dialTag="dialogMascotaEncontrada";
				dialogMascotaEncontrada.show(fragmentManager, vars.dialTag);	
			}else{
				DialogMascotaEncontrada dial=DialogMascotaEncontrada.newInstace(this, extra);
//				dial.setArguments(args);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.MAS_PERDIDA:
			SendAnalytics(getResources().getString(R.string.dialog_perdidas));
			if(dialMascotaPerdida==null){
				dialMascotaPerdida=DialogMascotaPerdida.newInstace(this, extra);
			}
			////////////////////////////////////////////////
			DialogMascotaPerdida.restoreInstace(dialMascotaPerdida, this, extra);
			
			ClsMascota mas=(ClsMascota) extra.objectSelected;
			Toast.makeText(getApplicationContext(), ""+mas.getMas_latitud()+"_"+mas.getMas_longitud(), 0).show();
			
			if(dialMascotaPerdida.getDialog()==null){
				concurrence=false;
			}else if(dialMascotaPerdida.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
				vars.dialTag="dialMascotaPerdida";
				dialMascotaPerdida.show(fragmentManager, vars.dialTag);
			}else{
				DialogMascotaPerdida dial=DialogMascotaPerdida.newInstace(this, extra);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.FUNDACION:
			SendAnalytics(getResources().getString(R.string.dialog_fundaciones));
			if(dialFundacion==null){
				dialFundacion=DialogFundacion.newInstace(this, extra);
			}else{
				DialogFundacion.restoreInstace(dialFundacion, this, extra);
			}
			if(dialFundacion.getDialog()==null){
				concurrence=false;
			}else if(dialFundacion.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
//				dialFundacion.setArguments(args);
				vars.dialTag="dialFundacion";
				dialFundacion.show(fragmentManager, vars.dialTag);
			}else{
				DialogFundacion dial=DialogFundacion.newInstace(this, extra);
//				dial.setArguments(args);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.TESTIMONIO:
			SendAnalytics(getResources().getString(R.string.dialog_testimonios));
			if(dialTestimonio==null){
				dialTestimonio=DialogTestimonio.newInstace(this, extra);
			}else{
				DialogTestimonio.restoreInstace(dialTestimonio, this, extra);
			}
			if(dialTestimonio.getDialog()==null){
				concurrence=false;
			}else if(dialTestimonio.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
//				dialTestimonio.setArguments(args);
				vars.dialTag="dialTestimonio";
				dialTestimonio.show(fragmentManager, vars.dialTag);
			}else{
				DialogTestimonio dial=DialogTestimonio.newInstace(this, extra);
//				dial.setArguments(args);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.TIP:
			SendAnalytics(getResources().getString(R.string.dialog_tips));
			if(dialTip==null){
				dialTip=DialogTip.newInstace(this, extra);
			}else{
				DialogTip.restoreInstace(dialTip, this, extra);
			}
			if(dialTip.getDialog()==null){
				concurrence=false;
			}else if(dialTip.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
//				dialTip.setArguments(args);
				vars.dialTag="dialTip";
				dialTip.show(fragmentManager, vars.dialTag);
			}else{
				DialogTip dial=DialogTip.newInstace(this, extra);
//				dial.setArguments(args);
				dial.show(fragmentManager, "");
			}
			break;
		case GeneralData.MAP:
//			SendAnalytics(getResources().getString(R.string.dialog_tips));
			if(dialMap==null){
				dialMap=new DialogMap();
				
			}
			dialMap.setEvtCancelDialog(this);
			
			if(dialMap.getDialog()==null){
				concurrence=false;
			}else if(dialMap.getDialog().isShowing()){
				concurrence=true;
			}
			if(!concurrence){
				dialMap.setArguments(args);
				dialMap.show(fragmentManager, "");
			}else{
				DialogMap dialMap=new DialogMap();
				dialMap.setEvtCancelDialog(this);
				dialMap.setArguments(args);
				dialMap.show(fragmentManager, "");
			}
			break;
		}
    	extra.dialTag=vars.dialTag;
    	vars.extras.add(extra);
	}
	
	private void reciveArgs(Intent i) {
		// TODO Auto-generated method stub
		try {
			String reg_id = i.getStringExtra("reg_id");
			if(reg_id!=null){
				showDilog_regId=reg_id;
				showDialog_tipe=i.getIntExtra("tipe", -1);
				
				Bundle b=new Bundle();
				b.putString("reg_id", showDilog_regId);
				onEvtRequestDialog(null, null);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_btnCamera:
			vars.path=MediaUtil.takeFromCamera(this);
			vars.picturefromThis=true;
			break;

		case R.id.main_btnEscuadron:
			fragDrawer.setSelection(R.id.drawer_btnEscuadron, true);
			getSupportActionBar().setTitle(vars.titleActionBar);
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
//			Toast.makeText(getApplicationContext(), "tag "+vars.fragTag, 0).show();
			if(vars.picturefromThis && vars.fragTag.equals("fragHome")){
				fragDrawer.setSelection(R.id.drawer_btnMascotaEncontrada, true);
				getSupportActionBar().setTitle(vars.titleActionBar);
			}else{
				if(vars.fragTag.equals("fragSubirEvento")){
					if(fragSubirEvento==null){
						fragSubirEvento=(FragSubirEvento) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirEvento.restoreInstace(fragSubirEvento, vars, memoryCacheMultimedia, this, this, this, this, this);
					}
					if(fragSubirEvento!=null){
    					fragSubirEvento.ActivityResult(requestCode, data);
					}
				}else if(vars.fragTag.equals("fragSubirTestimonio")){
					if(fragSubirTestimonio==null){
						fragSubirTestimonio=(FragSubirTestimonio) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirTestimonio.restoreInstace(fragSubirTestimonio, vars, memoryCacheMultimedia, this, this, this);
					}
					if(fragSubirTestimonio!=null){
						fragSubirTestimonio.ActivityResult(requestCode, data);
					}
				}else if(vars.fragTag.equals("fragSubirTip")){
					if(fragSubirTip==null){
						fragSubirTip=(FragSubirTip) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirTip.restoreInstace(fragSubirTip, vars, memoryCacheMultimedia, this, this, this);
					}
					if(fragSubirTip!=null){
						fragSubirTip.ActivityResult(requestCode, data);
					}
				}else if(vars.fragTag.equals("fragSubirAdoptar")){
					if(fragSubirAdoptar==null){
						fragSubirAdoptar=(FragSubirAdoptar) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirAdoptar.restoreInstace(fragSubirAdoptar, vars, memoryCacheMultimedia, this, this, this);
					}
					if(fragSubirAdoptar!=null){
						fragSubirAdoptar.ActivityResult(requestCode, data);
					}
				}else if(vars.fragTag.equals("fragSubirMascota")){
					if(fragSubirPerdida==null){
						fragSubirPerdida=(FragSubirPerdida) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirPerdida.restoreInstace(fragSubirPerdida, vars, memoryCacheMultimedia, this, this, this, this, this, this);
					}
					if(fragSubirPerdida!=null){
						fragSubirPerdida.ActivityResult(requestCode, data);
					}
				}else if(vars.fragTag.equals("fragMascotaEncontrada")){
					if(fragsubirEncontrada==null){
						fragsubirEncontrada=(FragSubirEncontrada) fragmentManager.findFragmentByTag(vars.fragTag);
						FragSubirEncontrada.restoreInstace(fragsubirEncontrada, vars, memoryCacheMultimedia, this, this, this, this);
					}
					if(fragsubirEncontrada!=null){
						fragsubirEncontrada.ActivityResult(requestCode, data);
					}
				}	
			}
		}else{
			vars.path="";
			vars.picturefromThis=false;
		}
	};
	
	@Override
	public void OnDrawerSelection(String title, int id) {
		// TODO Auto-generated method stub
		drawerLayout.closeDrawer(Gravity.START);
		if(vars.picturefromThis){
			vars.picturefromThis=false;
		}else{
			vars.path="";
		}
		vars.titleActionBar=title;
		
		vars.callInfo=true;
		if(id!=R.id.drawer_btnEscuadron){
			vars.callInfo=false;
		}
		
		vars.codeCategory=-1;
		switch (id) {
		case R.id.drawer_btnInicio:
			vars.codeCategory=GeneralData.ALL;
			break;
		case R.id.drawer_btnEventos:
			vars.codeCategory=GeneralData.EVENTO;
			break;
		case R.id.drawer_btnFundaciones:
			vars.codeCategory=GeneralData.FUNDACION;
			break;
		case R.id.drawer_btnMasEncontradas:
			vars.codeCategory=GeneralData.MAS_ENCONTRADA;
			break;
		case R.id.drawer_btnMasPerdidas:
			vars.codeCategory=GeneralData.MAS_PERDIDA;
			break;
		case R.id.drawer_btnAdopta:
			vars.codeCategory=GeneralData.MAS_ADOPTAR;
			break;
		case R.id.drawer_btnTips:
			vars.codeCategory=GeneralData.TIP;
			break;
		case R.id.drawer_btnTestimonios:
			vars.codeCategory=GeneralData.TESTIMONIO;
			break;
		case R.id.drawer_btnMascotaEncontrada:
    		if(!vars.fragTag.equals("fragMascotaEncontrada")){
    			if(fragsubirEncontrada==null){
    				fragsubirEncontrada=FragSubirEncontrada.newInstace(vars, memoryCacheMultimedia, this, this, this, this);
    			}
    			replaceAnim(fragsubirEncontrada, "fragMascotaEncontrada");
    		}
    		animButtons(false);
			break;
		case R.id.drawer_btnEscuadron:
			vars.path="";
			if(GeneralData.IS_SEARCH_ACTIVE.equals("")){
				SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
				GeneralData.IS_SEARCH_ACTIVE=preferences.getString(GeneralData.PREF_ESCUADRON, "");
			}
			if(GeneralData.IS_SEARCH_ACTIVE.equals("1")){
				vars.callInfo=false;
				if(!vars.fragTag.equals("fragEscuadronInActive")){
					if(fragEscuadronInActive==null){
						fragEscuadronInActive=FragEscuadronActivo.newInstance(this);
					}
					replaceAnim(fragEscuadronInActive, "fragEscuadronInActive");	
				}
			}else{
				if(!vars.fragTag.equals("fragSubirMascota")){
					if(fragSubirPerdida==null){
    					fragSubirPerdida=FragSubirPerdida.newInstace(vars, memoryCacheMultimedia, this, this, this, this, this, this);
    				}
    				replaceAnim(fragSubirPerdida, "fragSubirMascota");	
				}
			}
    		animButtons(false);
			break;
		case R.id.drawer_btnSubirAdopcion:
			vars.path="";
    		if(!vars.fragTag.equals("fragSubirAdoptar")){
    			if(fragSubirAdoptar==null){
    				fragSubirAdoptar=FragSubirAdoptar.newInstace(vars, memoryCacheMultimedia, this, this, this);
    			}
    			replaceAnim(fragSubirAdoptar, "fragSubirAdoptar");
    		}
    		animButtons(false);
			break;
		case R.id.drawer_btnSubirEvento:
			vars.path="";
    		if(!vars.fragTag.equals("fragSubirEvento")){
    			if(fragSubirEvento==null){
    				fragSubirEvento=FragSubirEvento.newInstace(vars, memoryCacheMultimedia, this, this, this, this, this);
    			}
    			replaceAnim(fragSubirEvento, "fragSubirEvento");
    		}
    		animButtons(false);
			break;
		case R.id.drawer_btnSubirTestimonio:
			vars.path="";
    		if(!vars.fragTag.equals("fragSubirTestimonio")){
    			if(fragSubirTestimonio==null){
    				fragSubirTestimonio=FragSubirTestimonio.newInstace(vars, memoryCacheMultimedia, this, this, this);
    			}
    			replaceAnim(fragSubirTestimonio, "fragSubirTestimonio");
    		}
    		animButtons(false);
			break;
		case R.id.drawer_btnSubirTip:
			vars.path="";
        	if(!vars.fragTag.equals("fragSubirTip")){
        		if(fragSubirTip==null){
        			fragSubirTip =FragSubirTip.newInstace(vars, memoryCacheMultimedia, this, this, this);
        		}
    			replaceAnim(fragSubirTip, "fragSubirTip");
    		}
    		animButtons(false);
			break;
		case R.id.drawer_btnCerrarSesion:
			requestLogOut();
			break;
		}
		
    	if(vars.codeCategory!=-1){
    		vars.path="";
    		animButtons(true);
    		if(vars.fragTag.equals("fragHome")){
    			vars.scrollTo=true;
    			fragHome.filtrateSource(vars.codeCategory, true);
    			if(vars.isBack){
    				vars.isBack=false;
    				getSupportActionBar().setTitle(vars.titleActionBar);
    			}
    		}else if(fragHome==null && vars.selected==-1){
    			vars.codeCategory=GeneralData.ALL;
    			vars.doDrop=true;
    			fragHome=FragHome.newInstance(MainActivity.this, MainActivity.this, MainActivity.this, MainActivity.this, vars);
    			fragHome.setSource(lstCardsOriginal, lstCardsAux, lstRegistros, lstMascotas, lstEventos, lstFundaciones, lstTips, lstTestimonios, lstFavoritos);
				vars.scrollTo=true;
				if(vars.isBack){
					vars.isBack=false;
					getSupportActionBar().setTitle(vars.titleActionBar);
					replaceAnimBack(fragHome, "fragHome");
				}else{
					replaceAnim(fragHome, "fragHome");	
				}
    		}else{
    			vars.doDrop=false;
    			fragHome=FragHome.newInstance(MainActivity.this, MainActivity.this, MainActivity.this, MainActivity.this, vars);
    			fragHome.setSource(lstCardsOriginal, lstCardsAux, lstRegistros, lstMascotas, lstEventos, lstFundaciones, lstTips, lstTestimonios, lstFavoritos);
				vars.scrollTo=true;
				if(vars.isBack){
					vars.isBack=false;
					getSupportActionBar().setTitle(vars.titleActionBar);
					replaceAnimBack(fragHome, "fragHome");
				}else{
					replaceAnim(fragHome, "fragHome");	
				}
    		}
    	}
    	
    	vars.selected=id;
    	SEND_ANALYTICS();
	}
	
	@Override
	public void OnDrawerUs() {
		// TODO Auto-generated method stub
		drawerLayout.closeDrawer(Gravity.START);
		startActivity(new Intent(this, UsActivity.class));
	}

	@Override
	public void onEvtRequestTitleBar(String title) {
		// TODO Auto-generated method stub
		vars.titleActionBar=title;
		getSupportActionBar().setTitle(vars.titleActionBar);
	}
	
	@Override
	public void onEvtActivityResult(int tipe) {
		// TODO Auto-generated method stub
		vars.path=MediaUtil.takeFromCamera(this);
	}
	
	private DialogSetTime dialTime;
	private DialogSetData dialData;
	
	@Override
	public void onEvtRequestDataTime() {
		// TODO Auto-generated method stub
		vars.dataTime="";
		
		dialData=new DialogSetData();
		dialData.setEvtSetFecha(this);
		dialData.show(fragmentManager, "");
	}

	@Override
	public void onEvtSetData(String fecha) {
		// TODO Auto-generated method stub
		vars.dataTime=fecha;
		dialTime=new DialogSetTime();
		dialTime.setEvtSetTime(this);
		dialTime.show(fragmentManager, "");	
	}
	
	@Override
	public void onEvtSetTime(String time, String finalTime) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date strDate=null;
		boolean isOk=true;
		try {
			strDate= sdf.parse(vars.dataTime+" "+time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(strDate!=null){
			if (strDate.before(new Date())) {
				isOk=false;
			}
		}
		
		if(isOk){
			if(fragSubirEvento==null){
				fragSubirEvento=(FragSubirEvento) fragmentManager.findFragmentByTag(vars.fragTag);
				FragSubirEvento.restoreInstace(fragSubirEvento, vars, memoryCacheMultimedia, this, this, this, this, this);
			}
			
			if(fragSubirEvento!=null){
				fragSubirEvento.setFecha(vars.dataTime+" "+finalTime);	
			}	
		}else{
			Toast.makeText(getApplicationContext(), getString(R.string.error_after_fecha), 0).show();
		}
	}

	@Override
	public void onEvtLoadCards() {
		// TODO Auto-generated method stub
		int s=lstRegistros.size(), s2=0;
		find=false;
		lstCardsOriginal.clear();
		for (int i = 0; i < s; i++) {
			ClsRegistro aux=lstRegistros.get(i);
				ClsCard card=null;
				switch (aux.getReg_tipe()) {
				case ClsRegistro.MASCOTA:
					card=getCard(aux.getReg_id(), ClsRegistro.MASCOTA);
					break;
				case ClsRegistro.EVENTO:
					card=getCard(aux.getReg_id(), ClsRegistro.EVENTO);
					break; 
				case ClsRegistro.TESTIMONIO:
					card=getCard(aux.getReg_id(), ClsRegistro.TESTIMONIO);
					break; 
				case ClsRegistro.USUARIO:
					card=getCard(aux.getReg_id(), ClsRegistro.USUARIO);
					break;
				case ClsRegistro.TIP:
					card=getCard(aux.getReg_id(), ClsRegistro.TIP);
					break; 
				}
				if(card!=null){
					int tipe=card.getTipe();
					//SI ES ALGUN TIPO DE MASCOTA (SUBCATEGORIA) DEBEMOS MANDAR 1 QUE ES US CATEGORIA
					//MAS_ADOPTAR=8, MAS_PERDIDA=9, MAS_ENCONTRADA=10, MAS_AGREGADA=11
					if(tipe==GeneralData.MAS_ADOPTAR || tipe==GeneralData.MAS_AGREGADA || tipe==GeneralData.MAS_ENCONTRADA || tipe==GeneralData.MAS_PERDIDA || tipe==GeneralData.MASCOTA){
						tipe=GeneralData.MASCOTA;
					}else if(tipe==GeneralData.FUNDACION){
						tipe=GeneralData.USUARIO;
					}
					s2=lstFavoritos.size();
					/////////////////////////////////////////////////////////AQUI REVISAR
					for (int j = 0; j < s2; j++) {
						favAux=lstFavoritos.get(j);
						if(favAux.getCard_id().equals(card.getId()) && favAux.getFav_tipe()==tipe){
							find=true;
							j=s2;
						}
					}
					
					if(find){
						card.setChecked(true);
					}
					
		            lstCardsOriginal.add(card);
				}
				find=false;	
		}
		vars.size=lstCardsAux.size();
	}
	
	private ClsCard getCardSelected(String reg_id) {
		int size;
		size=lstCardsOriginal.size();
		for (int i = 0; i < size; i++) {
			ClsCard card=lstCardsOriginal.get(i);
			if(card.getReg_id().equals(reg_id)){
				return card;
			}
		}
		return null;
	}
	
	private ClsCard getCard(String reg_id, int tipe) {
		int size=0;
		ClsCard card;
			switch (tipe) {
			case ClsRegistro.MASCOTA:
				size=lstMascotas.size();
				for (int i = 0; i < size; i++) {
					ClsMascota aux=lstMascotas.get(i);
					if(aux.getReg_id().equals(reg_id)){
						String title="";
						switch (aux.getMas_tipe()) {
						case ClsMascota.MAS_ADOPTAR:
							title=title_adopta;
							break;
							
						case ClsMascota.MAS_ENCONTRADA:
							title=title_encontrada;
							break;
							
						case ClsMascota.MAS_PERDIDA:
							title=title_perdida;	
							break;
						}
						card=new ClsCard(reg_id, aux.getMas_id(), aux.getReg_id()+"_"+aux.getMas_id()+".jpg", title, aux.getMas_nombre(), aux.getMas_tipe(), aux.getFav_count());
						return card;
					}
				} 
				break;
			case ClsRegistro.EVENTO:
				size=lstEventos.size();
				for (int i = 0; i < size; i++){
					ClsEvento aux=lstEventos.get(i);
					if(aux.getReg_id().equals(reg_id)){
						card=new ClsCard(reg_id, aux.getEvt_id(), aux.getReg_id()+"_"+aux.getEvt_id()+".jpg", title_evento, aux.getEvt_titulo(), GeneralData.EVENTO, aux.getFav_count());
						return card;
					}
				}
				break;
			case ClsRegistro.TESTIMONIO:
				size=lstTestimonios.size();
				for (int i = 0; i < size; i++) {
					ClsTestimonio aux=lstTestimonios.get(i);
					if(aux.getReg_id().equals(reg_id)){
						card=new ClsCard(reg_id, aux.getTes_id(), aux.getReg_id()+"_"+aux.getTes_id()+".jpg", title_testimonio, aux.getTes_titulo(), GeneralData.TESTIMONIO, aux.getFav_count());
						return card;
					}
				}
				break;
			case ClsRegistro.USUARIO:
				size=lstFundaciones.size();
				for (int i = 0; i < size; i++) {
					ClsFundacion aux=lstFundaciones.get(i);
					if(aux.getReg_id().equals(reg_id)){
						card=new ClsCard(reg_id, aux.getFun_id(), aux.getReg_id()+"_"+aux.getFun_id()+".jpg", title_fundacion, aux.getFun_nombre(), GeneralData.FUNDACION, aux.getFav_count());
						return card;
					}
				}
				break;
			case ClsRegistro.TIP:
				size=lstTips.size();
				for (int i = 0; i < size; i++) {
					ClsTip aux=lstTips.get(i);
					if(aux.getReg_id().equals(reg_id)){
						card=new ClsCard(reg_id, aux.getTip_id(), aux.getReg_id()+"_"+aux.getTip_id()+".jpg", title_tip, aux.getTip_titulo(), GeneralData.TIP, aux.getFav_count());
						return card;
					}
				}
				break;
			}
		return null;
	}
	
	@Override
	public Object getObjectSelected(String reg_id, int tipe) {
		// TODO Auto-generated method stub
		int size;
		switch (tipe) {
		case GeneralData.MASCOTA:
			size=lstMascotas.size();
			for (int i = 0; i < size; i++) {
				ClsMascota aux=lstMascotas.get(i);
				if(aux.getReg_id().equals(reg_id)){
					return aux;
				}
			} 
			break;
		case GeneralData.EVENTO:
			size=lstEventos.size();
			for (int i = 0; i < size; i++) {
				ClsEvento aux=lstEventos.get(i);
				if(aux.getReg_id().equals(reg_id)){
					Log.e("RETURN EVENT", ",...");
					return aux;
				}
			}
			break;
		case GeneralData.TESTIMONIO:
			size=lstTestimonios.size();
			for (int i = 0; i < size; i++) {
				ClsTestimonio aux=lstTestimonios.get(i);
				if(aux.getReg_id().equals(reg_id)){
					return aux;
				}
			}
			break;
		case GeneralData.FUNDACION:
			size=lstFundaciones.size();
			for (int i = 0; i < size; i++) {
				ClsFundacion aux=lstFundaciones.get(i);
				if(aux.getReg_id().equals(reg_id)){
					return aux;
				}
			}
			break;
		case GeneralData.TIP:
			size=lstTips.size();
			for (int i = 0; i < size; i++) {
				ClsTip aux=lstTips.get(i);
				if(aux.getReg_id().equals(reg_id)){
					return aux;
				}
			}
			break;
		}
		Log.e("RETURN null", ",...");
		return null;
	}
}
