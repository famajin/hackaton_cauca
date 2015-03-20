package com.example.focusgroupheroican.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.focusgroupheroican.R;

public class ViewChartRang extends LinearLayout{
	
	private ViewChart chart;
	private TextView txtDatos;
	
	public ViewChartRang(Context context, int color, String text, int count) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, color, text, count);
	}
	
	private void init(Context context, int color, String text, int count) {
		inflate(context, R.layout.view_chart_rang, this);
		chart=(ViewChart) findViewById(R.id.viewRangChart_chart);
		if(color!=-1){
			chart.setBackgroundResource(color);	
		}
		txtDatos=(TextView)findViewById(R.id.viewRangChart_txt);
	}
	
	public void reload(Double porcent, Double aum, String text) {
		if(porcent>0.0){
			porcent+=aum;
		}
		Double to=(porcent)/100.0;
		chart.reload(Float.parseFloat(""+to), LinearLayout.HORIZONTAL);
		txtDatos.setText(text);
	}

}
