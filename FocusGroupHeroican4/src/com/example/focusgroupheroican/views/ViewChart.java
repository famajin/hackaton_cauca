package com.example.focusgroupheroican.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewChart extends TextView{
	
	public ViewChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public ViewChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ViewChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public void reload(float to, int orientation) {
		ScaleAnimation scale;
		if(orientation==LinearLayout.VERTICAL){
			scale=new ScaleAnimation(1, 1, 0, to, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
		}else{
			scale=new ScaleAnimation(0, to, 1, 1);	
		}
		scale.setFillAfter(true);
		scale.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
		startAnimation(scale);
	}

}
