package com.example.focusgroupheroican.fragments;


import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsConfig;
import com.example.focusgroupheroican.util.Data;


public class DialogConfig extends DialogFragment implements OnKeyListener, OnShowListener, AnimationListener{
	
	public static int TOP=0, BOTTOM=1;
	
	private ViewGroup container;
	private Animation animIn, animOutBottom, animOutTop, animPivot;
	private boolean isShow=false;
	private boolean cancel=true;
	
	private Data db;
	
	private ClsConfig config;
	
	public DialogConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dialog_config, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		cancel=true;
		db=new Data(getActivity());
		config=new ClsConfig(0, 0, 0);
		if(getArguments()!=null){
			Toast.makeText(getActivity(), "recive args", 0).show();
			config.carg_id=getArguments().getInt("carg");
			config.edad_id=getArguments().getInt("edad");
			config.gender_id=getArguments().getInt("sex");
		}
		initDialog();
		initViews(view);
		initAnimations();
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
		container=(ViewGroup) view.findViewById(R.id.dialConfig_container);
		
		initEdades(view);
		initGeneros(view);
		initCargos(view);
	}
	
	private void initEdades(View view) {
		ArrayList<String> edades=db.getArray("SELECT edad_description FROM tbl_edad");
		RadioGroup groupEdad=(RadioGroup) view.findViewById(R.id.dialConfig_groupEdad);
		
		RadioButton radioButton;
		
		radioButton=new RadioButton(getActivity());
		radioButton.setId(0);
		radioButton.setText(getString(R.string.todos));
		groupEdad.addView(radioButton);
		
		for (int i = 0; i < edades.size(); i++) {
			radioButton=new RadioButton(getActivity());
			radioButton.setId(i+1);
			radioButton.setText(edades.get(i)+" años");
			groupEdad.addView(radioButton);	
		}
		
		((RadioButton)groupEdad.getChildAt(config.edad_id)).setChecked(true);
		groupEdad.setOnCheckedChangeListener(onCheckEdad);
	}
	
	private void initCargos(View view) {
		ArrayList<String> cargos=db.getArray("SELECT carg_description FROM tbl_cargo");
		RadioGroup groupCargos=(RadioGroup) view.findViewById(R.id.dialConfig_groupCargos);
		
		RadioButton radioButton;
		
		radioButton=new RadioButton(getActivity());
		radioButton.setId(0);
		radioButton.setText(getString(R.string.todos));
		groupCargos.addView(radioButton);
		
		for (int i = 0; i < cargos.size(); i++) {
			radioButton=new RadioButton(getActivity());
			radioButton.setId(i+1);
			radioButton.setText(cargos.get(i));
			groupCargos.addView(radioButton);	
		}
		
		((RadioButton)groupCargos.getChildAt(config.carg_id)).setChecked(true);
		groupCargos.setOnCheckedChangeListener(onCheckCargo);
	}
	
	private void initGeneros(View view) {
		ArrayList<String> generos=new ArrayList<String>();
		generos.add(getString(R.string.todos));
		generos.add(getString(R.string.masculino));
		generos.add(getString(R.string.femenino));
		
		RadioGroup groupGeneros=(RadioGroup) view.findViewById(R.id.dialConfig_groupGenero);
		
		RadioButton radioButton;
		
		for (int i = 0; i < generos.size(); i++) {
			radioButton=new RadioButton(getActivity());
			radioButton.setId(i);
			radioButton.setText(generos.get(i));
			groupGeneros.addView(radioButton);	
		}
		
		((RadioButton)groupGeneros.getChildAt(config.gender_id)).setChecked(true);
		groupGeneros.setOnCheckedChangeListener(onCheckGenero);
	}
	
	OnCheckedChangeListener onCheckEdad=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			config.edad_id=checkedId;
		}
	};
	
	OnCheckedChangeListener onCheckCargo=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			config.carg_id=checkedId;
		}
	};
	
	OnCheckedChangeListener onCheckGenero=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			config.gender_id=checkedId;
		}
	};
	
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && isShow && KeyEvent.ACTION_UP ==event.getAction()){
			cancel=true;
			OnCloseDialog(TOP);
		}
		return false;
	}
	
	@Override
	public void onShow(DialogInterface dialog) {
		// TODO Auto-generated method stub
		container.clearAnimation();
		container.startAnimation(animIn);
	}
	
	public void OnCloseDialog(int restoreTo) {
		// TODO Auto-generated method stub
		if(restoreTo==TOP){
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
				evtCancelDialog.OnEvtCloseDialog(config, true);
			}else{
				evtCancelDialog.OnEvtCloseDialog(config, false);
			}
		}
	}
	
	public interface EvtCloseDialog {
		public void OnEvtCloseDialog(ClsConfig config, Boolean set);
	}
	
	private EvtCloseDialog evtCancelDialog=null;
	public void setEvtCloseDialog(EvtCloseDialog evt) {
		evtCancelDialog=evt;
	}
	
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
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
	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if(v.getId()==R.id.dialMap_btnAceptar){
//			cancel=false;
//			OnCloseDialog(ScrollFace.BOTTOM);
//		}else{
//			cancel=true;
//			OnCloseDialog(ScrollFace.BOTTOM);
//		}
//	}
	
}
