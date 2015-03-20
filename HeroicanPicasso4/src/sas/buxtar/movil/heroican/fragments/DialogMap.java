package sas.buxtar.movil.heroican.fragments;


import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.interfaces.EvtCancelDialog;
import sas.buxtar.movil.heroican.location.Geocoding;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.views.ScrollFace;
import sas.buxtar.movil.heroican.views.ScrollFace.EvtCloseDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DialogMap extends DialogFragment implements OnKeyListener, OnShowListener, EvtCloseDialog, AnimationListener, OnCameraChangeListener, OnClickListener{
	
	private ViewGroup container;
	private Animation animIn, animOutBottom, animOutTop, animPivot;
	private boolean isShow=false;
	private double lat=0, lon=0;
	private TextView txtDireccion;
	private int color=0, icono=0;
	private Button btnAceptar, btnCancelar;
	private boolean cancel=true;
	
	public DialogMap() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dialog_map, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		cancel=true;
		map=null;
		map=new SupportMapFragment();
		initArgs();
		initDialog();
		initAnimations();
		initViews(view);
		btnAceptar.setEnabled(false);
		btnCancelar.setEnabled(false);
	}
	
	private void initArgs() {
		if(getArguments()!=null){
			lat=getArguments().getDouble("lat");
			lon=getArguments().getDouble("lon");	
			color=getArguments().getInt("color");
			icono=getArguments().getInt("icono");
		}
	}
	
	private SupportMapFragment map;
	private void setMap() {
		getChildFragmentManager().beginTransaction()
		.add(R.id.dialMap_frame, map, "map").commit();
		hilador();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stu
		super.onDestroy();
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	
	private void initViews(View view) {
    	//CONVERT PIXELS IN DP
//    	Resources r = mContext.getResources();
//    	int px = (int) TypedValue.applyDimension(
//    	        TypedValue.COMPLEX_UNIT_DIP,
//    	        yourdpmeasure, 
//    	        r.getDisplayMetrics()
//    	);
		container=(ViewGroup) view.findViewById(R.id.dialMap_container);
		btnAceptar=(Button) view.findViewById(R.id.dialMap_btnAceptar);
		btnCancelar=(Button) view.findViewById(R.id.dialMap_btnCancelar);
		btnAceptar.setOnClickListener(this);
		btnCancelar.setOnClickListener(this);
		txtDireccion=(TextView) view.findViewById(R.id.dialMap_txtTitulo);
		if(color!=0){
			((ViewGroup)view.findViewById(R.id.dialMap_bar)).setBackgroundResource(color);
			((ViewGroup)view.findViewById(R.id.selback)).setBackgroundResource(color);
		}
		if(icono!=0){
			((ImageView)view.findViewById(R.id.dialMap_icono)).setBackgroundResource(color);
		}
        if(getResources().getBoolean(R.bool.isTablet)){
            if(GeneralData.marginX==0){
            	int w_screen=getResources().getDisplayMetrics().widthPixels;
            	int h_screen=getResources().getDisplayMetrics().heightPixels;
            	GeneralData.marginX=(w_screen*GeneralData.marginX_tablet)/100;
            	GeneralData.marginY=(h_screen*GeneralData.marginY_tablet)/100;
            }
            ViewGroup margin=(ViewGroup) view.findViewById(R.id.dialMap_margin);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.setMargins(GeneralData.marginX, GeneralData.marginY, GeneralData.marginX, GeneralData.marginY);
            margin.setLayoutParams(params);	
        }
	}
	
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && isShow && KeyEvent.ACTION_UP ==event.getAction()){
			cancel=true;
			OnCloseDialog(ScrollFace.TOP);
		}
		return false;
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
			if(!cancel){
//				Toast.makeText(getActivity(), ""+lat+"\n"+lon, 0).show();
				Bundle bundle=new Bundle();
				bundle.putString("direccion", direccion);
				bundle.putDouble("lat", lat);
				bundle.putDouble("lon", lon);
				evtCancelDialog.OnCancelDialog(bundle, "");
			}else{
				evtCancelDialog.OnCancelDialog(null, "");
			}
		}
	}
	
	private EvtCancelDialog evtCancelDialog=null;
	public void setEvtCancelDialog(EvtCancelDialog evt) {
		evtCancelDialog=evt;
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		setMap();
		isShow=true;
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
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
				hand.post(runnable);
			}
		};
		thread.start();
	}
	final Handler hand =new Handler();
	final Runnable runnable= new Runnable() {
		public void run() {
			try {
				((ViewGroup)getView().findViewById(R.id.dialMap_progress)).setVisibility(View.GONE);
				btnAceptar.setEnabled(true);
				btnCancelar.setEnabled(true);
				initMap();	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	
	private void initMap() {
		int zoom=15;
		map.getMap().setOnCameraChangeListener(this);
		map.getMap().setMyLocationEnabled(true);

		if(lat==0 && lon==0){
			try {
		        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
		        Criteria criteria = new Criteria();
		        String provider = locationManager.getBestProvider(criteria, true);
		        Location location = locationManager.getLastKnownLocation(provider);
		        if(location!=null){
		            lat = location.getLatitude();
		            lon = location.getLongitude();
		        }else{
					lat=GeneralData.COL_LATITUD;
					lon=GeneralData.COL_LONGITUD;
					zoom=5;
		        }
			} catch (Exception e) {
				// TODO: handle exception
				lat=GeneralData.COL_LATITUD;
				lon=GeneralData.COL_LONGITUD;
			}
		}
		
		LatLng point=new LatLng(lat, lon);
		
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(point)
	    .zoom(zoom)                   // Sets the zoom
	    //.bearing(90)                // Sets the orientation of the camera to east
	    .tilt(45)                   // Sets the tilt of the camera to 30 degrees
	    .build();                   // Creates a CameraPosition from the builder
		map.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	private LatLng point;
	private String direccion="";
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Geocoding.DIRECCION_OBTENIDA:
				if(txtDireccion!=null){
					try {
						direccion=msg.obj.toString();
						txtDireccion.setText(direccion);	
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				break;
			
			}	
		}
	};
	
	@Override
	public void onCameraChange(CameraPosition position) {
		map.getMap().clear();
		point = position.target;
		map.getMap().addMarker(new MarkerOptions().position(point));
//		mMap.addMarker(new MarkerOptions().position(point).icon( BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_myplaces)));
		Geocoding.getInstancia(getActivity()).getAddress(point, handler);
		
		lat=position.target.latitude;
		lon=position.target.longitude;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.dialMap_btnAceptar){
			cancel=false;
			OnCloseDialog(ScrollFace.BOTTOM);
		}else{
			cancel=true;
			OnCloseDialog(ScrollFace.BOTTOM);
		}
	}
	
}
