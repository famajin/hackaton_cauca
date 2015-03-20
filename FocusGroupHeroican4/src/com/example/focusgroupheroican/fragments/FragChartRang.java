package com.example.focusgroupheroican.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.clases.ClsRango;
import com.example.focusgroupheroican.views.ViewChartRang;

public class FragChartRang extends Fragment{
	
	public static final String TAG="FragChartBool";
	private ClsQuestion ques;
	private ArrayList<ViewChartRang> lstCharts;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return View.inflate(getActivity(), R.layout.frag_chart_rang, null);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ViewGroup lin=(ViewGroup)view.findViewById(R.id.fragChart_lin);
		lstCharts=new ArrayList<ViewChartRang>();
		int i=0;
		for (ClsRango rang : ques.rangs) {
			int color=-1;
			if(ques.rangs.size()>=5){
				switch (i) {
				case 0: 
					color=R.color.col1;
					break;
				case 1:
					color=R.color.col2;
					break;
				case 2:
					color=R.color.col3;
					break;
				case 3:
					color=R.color.col4;
					break;
				case 4:
					color=R.color.col5;
					break;
				}	
			}else if(ques.rangs.size()<=4){
				switch (i) {
				case 0: color=R.color.col1;
					break;
				case 1:
					color=R.color.col3;
					break;
				case 2:
					color=R.color.col4;
					break;
				case 3:
					color=R.color.col5;
					break;
				}
			}
			ViewChartRang chart=new ViewChartRang(getActivity(), color, rang.rang_description+"\nDATOS\nDATOS\nDATOS", 23);
			lstCharts.add(chart);
			lin.addView(chart);
			i++;
		}
	}
	
	public void setQuestion(ClsQuestion ques) {
		this.ques=ques;
	}
	
	public void reload() {
		Double max=0.0;
		for (int i = 0; i < lstCharts.size(); i++) {
			ClsRango rang=ques.rangs.get(i);
			if(rang.porcent>max){
				max=rang.porcent;
			}
		}
		
		Double aum=0.0;
		aum=100.0-max;
		for (int i = 0; i < lstCharts.size(); i++) {
			ClsRango rang=ques.rangs.get(i);
			lstCharts.get(i).reload(rang.porcent, aum, rang.rang_description+"\n"+rang.porcent+"%"+"\n"+rang.personas+" Personas");
		}
	}
	
	public static FragChartRang newInstace(ClsQuestion ques) {
		FragChartRang frag=new FragChartRang();
		frag.setQuestion(ques);
		frag.setRetainInstance(true);
		return frag;
	}
	
}
