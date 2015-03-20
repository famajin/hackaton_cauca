package com.example.focusgroupheroican.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.views.ViewChart;

public class FragChartBool extends Fragment{
	
	public static final String TAG="FragChartBool";
	private TextView txtYes, txtNot;
	private ViewChart chartNot, chartYes;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return View.inflate(getActivity(), R.layout.frag_chart_bool, null);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		chartYes=(ViewChart) view.findViewById(R.id.fragChart_yes);
		txtYes=(TextView) view.findViewById(R.id.fragChart_txtYes);
		chartNot=(ViewChart) view.findViewById(R.id.fragChart_not);
		txtNot=(TextView) view.findViewById(R.id.fragChart_txtNot);
	}
	
	
	public void reload(Double yes_to, Double not_to, String yes_txt, String not_txt, int orientation) {
		Double aum=0.0;
		
		if(yes_to>not_to){
			aum=100.0-yes_to;
		}else{
			aum=100.0-not_to;
		}
		
		if(yes_to>0.0){
			yes_to+=aum;	
		}
		if(not_to>0.0){
			not_to+=aum;	
		}
		
		Double yes=yes_to/100.0;
		Double not=not_to/100.0;
		
		chartYes.reload(Float.parseFloat(""+yes), orientation);
		chartNot.reload(Float.parseFloat(""+not), orientation);
		
		txtYes.setText(yes_txt);
		txtNot.setText(not_txt);
	}
	
	public static FragChartBool newInstace() {
		FragChartBool frag=new FragChartBool();
		frag.setRetainInstance(true);
		return frag;
	}
	
}
