package sas.buxtar.movil.heroican.fragments;

import sas.buxtar.movil.heroican.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class DialogLoad extends DialogFragment{

//	private ImageView img;
//	private AnimationDrawable animationDrawable=null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dialog_load, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initDialog();
//		img=(ImageView) view.findViewById(R.id.load_img);
//		try {
//			img.setImageResource(R.anim.anim_load);
//			animationDrawable = (AnimationDrawable) img.getDrawable();
//			animationDrawable.start();	
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
	
	private void initDialog() {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setCancelable(false);
	}
	
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		try {
//			animationDrawable.stop();
//			img.setImageBitmap(null);
//			animationDrawable=null;	
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		super.onDestroy();
//	}
}
