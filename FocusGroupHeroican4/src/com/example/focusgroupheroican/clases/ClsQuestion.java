package com.example.focusgroupheroican.clases;

import java.util.ArrayList;

public class ClsQuestion {

	public static final int BOOLEAN=0, RANG=1;
	
	public String ques_description, comment="", ques_hint;
	public int ques_tipe, ques_id, rang_id=-1, hint_id, answer_id=-1;
	public ArrayList<ClsRango> rangs;
	
	//FOR CHART
	public Double porcent_yes=0.0, porcent_not=0.0;
	public int personas_yes=0, personas_not=0;
	
	public ClsQuestion(int ques_id, int ques_tipe, String ques_description, String ques_hint) {
		this.ques_id=ques_id;
		this.ques_hint=ques_hint;
		this.ques_description = ques_description;
		this.ques_tipe = ques_tipe;
	}
	
	public void setRangs(ArrayList<ClsRango> rangs) {
		this.rangs=rangs;
	}
	
	public String getSQL() {
		return "INSERT INTO tbl_question VALUES("+ques_id+", "+hint_id+", "+ques_tipe+", '"+ques_description+"', "+rang_id+");";
	}
	
}
