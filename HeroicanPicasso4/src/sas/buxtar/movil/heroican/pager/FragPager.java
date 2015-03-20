package sas.buxtar.movil.heroican.pager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.fragments.FragHome.InfoPagina;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTutorial;
import sas.buxtar.movil.heroican.process.ImageFetcher;
import sas.buxtar.movil.heroican.views.DropListView.EvtShowDetail;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class FragPager extends Fragment implements OnPageChangeListener, OnTouchListener, EvtShowDetail{

	private ViewPager pager;
	private int TIME_NEXT=10000, size, next, actual=0;
	private boolean toRight, touch=false, first=true, isShow=true;
	private CountDownTimer timer;
	private long infiteTime=1000000000;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_pager, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		ArrayList<InfoPagina> lstInfo=new ArrayList<InfoPagina>();
		lstInfo.add(new InfoPagina(0, InfoPagina.NORMAL, "pager_encontre_uno.png", getResources().getString(R.string.encontreuno), R.color.verdemanzana, R.id.drawer_btnMascotaEncontrada));
		lstInfo.add(new InfoPagina(1, InfoPagina.NORMAL, "pager_escuadron.png", getResources().getString(R.string.escuadronbusqueda), R.color.rojo, R.id.drawer_btnEscuadron));
		lstInfo.add(new InfoPagina(3, InfoPagina.NORMAL, "pager_adoptar.png", getResources().getString(R.string.adopta), R.color.morado, R.id.drawer_btnAdopta));
		lstInfo.add(new InfoPagina(4, InfoPagina.SPECIAL, "", getResources().getString(R.string.estoyperdido), R.color.rojo, -1));
		
//		lstInfo.add(new InfoPagina(2, InfoPagina.NORMAL, R.drawable.ic_tijeras, "Tips", R.color.naranja));
		
		pager=(ViewPager) view.findViewById(R.id.pager);
		pager.setOnPageChangeListener(this);
		
		TransformerPager transformer= new TransformerPager();
		pager.setPageTransformer(true, transformer);
		
//		AdpPager2 adpPager = new AdpPager2(getChildFragmentManager(), lstInfo);
//        pager.setAdapter(adpPager);
		
		ArrayList<FragItemPager> items=new ArrayList<FragItemPager>();
		for (int i = 0; i < lstInfo.size(); i++) {
			items.add(null);
		}
		AdpPager adp=new AdpPager(getChildFragmentManager(), lstInfo,items,evtRequestSelection, evtRequestDialog, evtRequestTutorial, this);
		pager.setAdapter(adp);
        SlowScroller scroller=new SlowScroller(getActivity());
        try {
        	Field mScroller = ViewPager.class.getDeclaredField("mScroller");   
        	mScroller.setAccessible(true);
        	mScroller.set(pager, scroller); 
		} catch (Exception e) {
			// TODO: handle exception
		}
        pager.setOnTouchListener(this);
        
        size=lstInfo.size();
        
        toRight= true;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		stopTimer();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isShow){
			initTimer();	
		}		
	}
	
	private int getNext() {
		next=actual;
		if(actual==0){
			toRight=true;
		}else if(actual>=size-1){
			toRight=false;
		}
		
		if(toRight){
			next++;
		}else{
			next--;
		}
		return next;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int sel) {
		// TODO Auto-generated method stub
		if(sel!=0 && sel<size){
			if(sel>actual){
				toRight=true;
			}else{
				toRight=false;
			}
		}
		actual=sel;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getAction()==MotionEvent.ACTION_DOWN || evt.getAction()==MotionEvent.ACTION_MOVE){
			touch=true;
			initTimer();
		}else if (evt.getAction()==MotionEvent.ACTION_UP || evt.getAction()==MotionEvent.ACTION_CANCEL) {
			touch=false;
		}
		return false;
	}
	
	private void stopTimer() {
		// TODO Auto-generated method stub
		if(timer!=null){
			first=true;
			timer.cancel();
			timer=null;
		}
	}

	private void initTimer() {
		if(timer==null){
			first=true;
			touch=false;
			timer=new CountDownTimer(infiteTime, TIME_NEXT) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					if(!first){
						if(!touch){
							pager.setCurrentItem(getNext());	
						}	
					}else{
						first=false;
					}
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					initTimer();
				}
			}.start();	
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

	@Override
	public void onEvtShowDetail(boolean is) {
		// TODO Auto-generated method stub
		isShow=is;
		if(is){
			initTimer();
		}else{
			stopTimer();
		}
	}
}



