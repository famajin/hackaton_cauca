package com.example.focusgroupheroican.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.clases.ClsViewQuestions;
import com.example.focusgroupheroican.util.Data;
import com.example.focusgroupheroican.views.ViewQuestionBool;
import com.example.focusgroupheroican.views.ViewQuestionRang;

public class FragGenerales extends Fragment{

	private ArrayList<ClsQuestion> questions;
	private ViewGroup lin;
	public static String TAG="FragGenerales";
	private ArrayList<ClsViewQuestions> lstViewQuestions;
	private Data db;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return View.inflate(getActivity(), R.layout.frag_generales, null);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		lin=(ViewGroup) view.findViewById(R.id.gen_lin);
		
		lstViewQuestions=new ArrayList<ClsViewQuestions>();
		
		db=new Data(getActivity());
		
		questions=db.getQuestions("'0', '1', '2', '3', '4', '5'");
		
		for (ClsQuestion question :questions) {
			switch (question.ques_tipe) {
			case ClsQuestion.BOOLEAN:
				ViewQuestionBool qb=new ViewQuestionBool(getActivity(), question);
				lin.addView(qb);
				lstViewQuestions.add(new ClsViewQuestions(qb, question.ques_tipe));
				break;
				
			case ClsQuestion.RANG:
				ViewQuestionRang qr=new ViewQuestionRang(getActivity(), question);
				lin.addView(qr);
				lstViewQuestions.add(new ClsViewQuestions(qr, question.ques_tipe));
				break;
			}
		}
	}
	
	public boolean validate() {
		for (ClsViewQuestions question :lstViewQuestions) {
		switch (question.tipe) {
			case ClsQuestion.BOOLEAN:
				((ViewQuestionBool)question.v).saveComment();
				break;
				
			case ClsQuestion.RANG:
				((ViewQuestionRang)question.v).saveComment();
				break;
			}
		}
		for (ClsQuestion ques:questions) {
			if(ques.answer_id==-1){
				return false;
			}
		}
		return true;
	}
	
	public void insertChoice_comments(String usu_email, int fun_id) {
		String script="";
		Integer cho_id=db.getMaxId("cho_id", "tbl_choice");
		for (ClsQuestion ques:questions){
			cho_id++;
			script+="INSERT INTO tbl_choice VALUES("+cho_id+", '"+usu_email+"', "+fun_id+", "+ques.ques_id+", "+ques.answer_id+");";
			if(ques.comment.length()>0){
				script+="INSERT INTO tbl_comment VALUES(null, "+cho_id+", '"+ques.comment+"');";
			}
		}
		db.exeScript(script);
	}
	
	public String getChoice_comments(String usu_email, int fun_id) {
		Integer cho_id=0;
		String json_tree="{";
		for (ClsQuestion ques:questions){
			cho_id++;
			try {
				JSONObject json = new JSONObject();
				json.put("usu_email", ""+usu_email);
				json.put("fun_id", ""+fun_id);
				json.put("ques_id", ""+ques.ques_id);
				json.put("answer_id", ""+ques.answer_id);
				json.put("comment", ""+ques.comment);
				json_tree+="\""+cho_id+"\":"+json.toString()+",";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		json_tree=json_tree.substring(0, json_tree.length()-1);
		json_tree+="}";
		return json_tree;
	}
	
	public static FragGenerales newInstace() {
		FragGenerales frag=new FragGenerales();
		return frag;
	}
	
}
