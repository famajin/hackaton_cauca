package com.example.focusgroupheroican.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.R.layout;
import com.example.focusgroupheroican.clases.ClsQuestion;

public class FragFinal extends Fragment{
	
	public static final String TAG="FragFinal";
	private ArrayList<ClsQuestion> questions;
	private ViewGroup lin;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return View.inflate(getActivity(), R.layout.frag_finales, null);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	}
	
	public static FragFinal newInstace() {
		FragFinal frag=new FragFinal();
		frag.setRetainInstance(true);
		return frag;
	}
	
}
