package com.example.focusgroupheroican.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.R.id;
import com.example.focusgroupheroican.R.layout;
import com.example.focusgroupheroican.R.string;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.clases.ClsRango;

public class ViewAdminRang extends LinearLayout{
	
	public ViewAdminRang(Context context, String text, int count) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, text, count);
	}
	
	private void init(Context context, String text, int count) {
		inflate(context, R.layout.view_rang_admin, this);
		
		TextView txtDescription=(TextView)findViewById(R.id.viewRang_txt);
		txtDescription.setText(text);
		
		TextView txtCount=(TextView)findViewById(R.id.viewRang_txtCount);
		txtCount.setText("...");
	}

}
