package sas.buxtar.movil.heroican.fragments;


import sas.buxtar.movil.heroican.AnalyticsSampleApp;
import sas.buxtar.movil.heroican.AnalyticsSampleApp.TrackerName;
import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.adapters.AdpPagerSlides;
import sas.buxtar.movil.heroican.clases.ClsConsume;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.fragments.FragSlide3.EvtClicEscuadron;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class FragEscuadronActivar extends Fragment implements EvtEndInsert, EvtClicEscuadron, OnClickListener, OnPageChangeListener{
	private ClsMascota mas;
	private DialogLoad dialLoad;
	private ClsConsume consume=null;
	private FragSlide3 fragSlide;
	private ViewPager pager;

	private int visiblePay=View.GONE, visibleEscuadron=View.VISIBLE;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_escuadron_active, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(getArguments()!=null){
			if(getArguments().containsKey("extra")){
				mas=getArguments().getParcelable("extra");
				if(mas!=null){
					mas.setEvtEndInsert(this);
					dialLoad=new DialogLoad();
					consume=new ClsConsume();
					consume.initProcessorBilling(getActivity());
				}
			}
		}
		
		((Button)view.findViewById(R.id.escuadron_btn)).setOnClickListener(this);
		fragSlide=FragSlide3.newInstace(this, visiblePay, visibleEscuadron);
		pager=(ViewPager) view.findViewById(R.id.escuadron_pager);
		pager.setOnPageChangeListener(this);
		pager.setAdapter(new AdpPagerSlides(getChildFragmentManager(), this, fragSlide, visiblePay, visibleEscuadron));
	}
	
	public static FragEscuadronActivar newInstace(EvtRequestSelection evt) {
		FragEscuadronActivar frag=new FragEscuadronActivar();
		frag.setEvtRequestSelection(evt);
		return frag;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		try {
			if(consume!=null){
				consume.relase();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onDestroy();
	}

	private NetWorkState netWorkState=null;
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.escuadron_btnpay:
////			if(consume==null){
////				consume.initProcessorBilling(getActivity());
////			}
////			if(consume.isReadyConsume()){
////				consume.pay(getActivity());
////			}
//			mas.insert();
//			break;
//
//		case R.id.escuadron_btn:
//			if(netWorkState==null){
//				netWorkState=new NetWorkState();
//			}
//			if(netWorkState.isOnLine(getActivity())){
//				dialLoad.show(getFragmentManager(), "dialLoad");
//	        	if(GeneralData.USU_ID.equals("")){
//	        		SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
//	        		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
//	        	}
//	        	consume.setEvtEndInsert(endConsume);
//	        	consume.consume();
//			}else{
//				Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
//			}
//			break;
//		}
//	}

	private void SendEvent() {
		if(GeneralData.SEND_ANALYTICS){
			Tracker t = ((AnalyticsSampleApp) getActivity().getApplication()).getTracker(TrackerName.APP_TRACKER);
	        t.send(new HitBuilders.EventBuilder()
	        .setCategory("Escuadron de busqueda")
	        .setAction(mas.getMas_id())
	        .setLabel(mas.getUsu_id())
	        .build());
		}
	}
	
	EvtEndInsert endConsume=new EvtEndInsert() {
		
		@Override
		public void OnEndInsert(String was) {
			// TODO Auto-generated method stub
			if(was!=null){
				if(was.contains("error")){
					try {
						dialLoad.getDialog().cancel();	
					} catch (Exception e) {
						// TODO: handle exception
					}
					Toast.makeText(getActivity(), getString(R.string.conexion_error), 0).show();
				}else{
					//HABILITAR PAGO
					if(was.equals("1")){
						try {
							dialLoad.getDialog().cancel();	
						} catch (Exception e) {
							// TODO: handle exception
						}
//						fragSlide.setVisibilitys(View.VISIBLE, View.GONE);
						Toast.makeText(getActivity(), getString(R.string.debes_pagar), 0).show();
					}else if(was.equals("0")){
						mas.insert();
					}else{
						try {
							dialLoad.getDialog().cancel();	
						} catch (Exception e) {
							// TODO: handle exception
						}
						Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
					}
				}
			}
		}
	};
	
	@Override
	public void OnEndInsert(String was) {
		// TODO Auto-generated method stub
		if(was!=null){
			SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(GeneralData.PREF_ESCUADRON, "1");
			editor.commit();
			SendEvent();
			mas=null;
			if(evtRequestSelection!=null){
				GeneralData.IS_SEARCH_ACTIVE="1";
				evtRequestSelection.onEvtRequestSelection(R.id.drawer_btnEscuadron);
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
    
    public interface EvtRequestInActive{
    	public void onEvtRequestInactive();
    }
    
	private EvtRequestSelection evtRequestSelection;
	private void setEvtRequestSelection(EvtRequestSelection evt) {
		this.evtRequestSelection=evt;
	}

	@Override
	public void onEvtClicEscuadron(int id) {
		// TODO Auto-generated method stub
		switch (id) {
//		case R.id.escuadron_btnpay:
////			if(consume==null){
////				consume.initProcessorBilling(getActivity());
////			}
////			if(consume.isReadyConsume()){
////				consume.pay(getActivity());
////			}
//			mas.insert();
//			break;

		case R.id.escuadron_btn:
			if(netWorkState==null){
				netWorkState=new NetWorkState();
			}
			if(netWorkState.isOnLine(getActivity())){
				dialLoad.show(getFragmentManager(), "dialLoad");
	        	if(GeneralData.USU_ID.equals("")){
	        		SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
	        		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
	        	}
//	        	consume.setEvtEndInsert(endConsume);
//	        	consume.consume();
	        	
	        	mas.insert();
	        	
			}else{
				Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
			}
			break;
		}
	}
	
	int pos=0;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (pos) {
		case 0:
			pager.setCurrentItem(1);
			break;

		case 1:
			pager.setCurrentItem(2);
			break;
			
		case 2:
//			if(netWorkState==null){
//				netWorkState=new NetWorkState();
//			}
//			if(netWorkState.isOnLine(getActivity())){
//				dialLoad.show(getFragmentManager(), "dialLoad");
//	        	if(GeneralData.USU_ID.equals("")){
//	        		SharedPreferences preferences = getActivity().getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
//	        		GeneralData.USU_ID=preferences.getString(GeneralData.PREF_USUID, "");
//	        	}
////	        	consume.setEvtEndInsert(endConsume);
////	        	consume.consume();
//	        	mas.insert();
//	        	
//			}else{
//				Toast.makeText(getActivity(), getResources().getString(R.string.conexion_error), 0).show();
//			}
			Toast.makeText(getActivity(), "SEND", 0).show();
			break;
		}
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
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		pos=arg0;
		if(pos<2){
			((Button)getView().findViewById(R.id.escuadron_btn)).setText(getResources().getString(R.string.reg_siguiente));
		}else{
			((Button)getView().findViewById(R.id.escuadron_btn)).setText(getResources().getString(R.string.activar));
		}
	}

}