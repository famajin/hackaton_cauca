package sas.buxtar.movil.heroican.tutorial;


import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

public class DialogTutorial extends DialogFragment implements OnKeyListener, OnClickListener{
	
	private int tutorial=0;
	private FragTutorialCamara fragCamara;
	private FragTutorialEscuadron fragEscuadron;
	private FragTutorialDrawer fragDrawer;
	private ImageView imgCamara, imgEscuadron;
	public static final int TUT_CAMARA=1, TUT_ESCUADRON=2, TUT_DRAWER=0;
	private animInListener listenerIn;
	private animOutListener listenerOut;
	private ViewGroup container;
	private boolean COMPLETE;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dial_tutorial, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initDialog();
		animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.tutorial_in);
		container=(ViewGroup)view.findViewById(R.id.dialTutorial_container);
		container.startAnimation(animIn);
		
		listenerIn=new animInListener();
		listenerOut=new animOutListener();
		imgCamara=(ImageView) view.findViewById(R.id.dialTutorial_imgCamara);
		imgEscuadron=(ImageView) view.findViewById(R.id.dialTutorial_imgEscuadron);
		
		((Button)view.findViewById(R.id.dialTutorial_btnEntendido)).setOnClickListener(this);
		
		if(getArguments()!=null){
			COMPLETE=false;
			tutorial=getArguments().getInt("tutorial");
			((Button)view.findViewById(R.id.dialTutorial_btnOmitir)).setVisibility(View.GONE);
		}else{
			COMPLETE=true;
			tutorial=TUT_DRAWER;
			((Button)view.findViewById(R.id.dialTutorial_btnOmitir)).setOnClickListener(this);
		}
		
		next(tutorial);
	}
	
	private void closeDialog() {
		container.clearAnimation();
		animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.tutorial_out);
		animOut.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				container.setVisibility(View.GONE);
				getDialog().cancel();
				if(!COMPLETE){
					if(evtSelection!=null){
						switch (tutorial) {
							case TUT_CAMARA:
								evtSelection.onEvtRequestSelection(R.id.drawer_btnMascotaEncontrada);
								break;
							case TUT_ESCUADRON:
								evtSelection.onEvtRequestSelection(R.id.drawer_btnEscuadron);
								break;
						}
					}
				}
			}
		});
		container.startAnimation(animOut);
	}
	
	private EvtRequestSelection evtSelection;
	public void setEvtDrawerSelection(EvtRequestSelection evtSelection) {
		this.evtSelection=evtSelection;
	}
	
	private Animation animIn=null, animOut=null;
	private int idOut, idIn;
	private void next(int tut) {
		switch (tut) {
		case TUT_CAMARA:
			if(fragCamara==null){
				fragCamara=new FragTutorialCamara();
			}
			setAnims(R.id.dialTutorial_imgCamara, R.id.dialTutorial_imgEscuadron);
			replaceAnim(fragCamara);
			break;

		case TUT_ESCUADRON:
			if(fragEscuadron==null){
				fragEscuadron=new FragTutorialEscuadron();
			}
			setAnims(R.id.dialTutorial_imgEscuadron, R.id.dialTutorial_imgCamara);
			replaceAnim(fragEscuadron);
			break;
			
		case TUT_DRAWER:
			if(fragDrawer==null){
				fragDrawer=new FragTutorialDrawer();
			}
			setAnims(-1, -1);
			replaceAnim(fragDrawer);
			break;
		default:
			closeDialog();
			break;
		}
	}
	
	private void setAnims(int in, int out) {
		this.idIn=in;
		this.idOut=out;
		imgEscuadron.clearAnimation();
		imgCamara.clearAnimation();
		animIn=null;
		animOut=null;
		animIn = AnimationUtils.loadAnimation(getActivity(), R.anim.tutorial_in);
		animIn.setAnimationListener(listenerIn);
		animOut = AnimationUtils.loadAnimation(getActivity(), R.anim.tutorial_out);
		animOut.setAnimationListener(listenerOut);
		
		if(out==-1){
			if(imgEscuadron.getVisibility()==View.VISIBLE){
				imgEscuadron.startAnimation(animOut);	
			}
			if(imgCamara.getVisibility()==View.VISIBLE){
				imgCamara.startAnimation(animOut);	
			}
		}else{ 
			if(idIn==R.id.dialTutorial_imgCamara && imgCamara.getVisibility()==View.INVISIBLE){
				imgCamara.startAnimation(animIn);	
			}else if(imgEscuadron.getVisibility()==View.INVISIBLE){
				imgEscuadron.startAnimation(animIn);
			}
			if(idOut==R.id.dialTutorial_imgCamara && imgCamara.getVisibility()==View.VISIBLE){
				imgCamara.startAnimation(animOut);
			}else if(imgEscuadron.getVisibility()==View.VISIBLE){
				imgEscuadron.startAnimation(animOut);
			}
		}
	}
	
	private void replaceAnim(Fragment frag) {
		getChildFragmentManager().beginTransaction()
		.setCustomAnimations(R.anim.tutorial_xin, R.anim.tutorial_xout)
		.replace(R.id.dialTutorial_frame, frag)
		.commit();
	}
	
	private void initDialog() {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getDialog().setOnKeyListener(this);
		setCancelable(false);
	}
	
	private class animInListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if(idIn==R.id.dialTutorial_imgCamara){
				imgCamara.setVisibility(View.VISIBLE);
			}else{
				imgEscuadron.setVisibility(View.VISIBLE);
			}
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
			if(idOut==-1){
				imgEscuadron.setVisibility(View.INVISIBLE);
				imgCamara.setVisibility(View.INVISIBLE);
			}else if(idOut==R.id.dialTutorial_imgCamara){
				imgCamara.setVisibility(View.INVISIBLE);
			}else{
				imgEscuadron.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	}
	
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && KeyEvent.ACTION_UP ==event.getAction()){
			closeDialog();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialTutorial_btnEntendido:
			if(COMPLETE){
				tutorial++;
				next(tutorial);	
			}else{
				closeDialog();
			}
			break;

		case R.id.dialTutorial_btnOmitir:
			closeDialog();
			break;
		}
	}
}
