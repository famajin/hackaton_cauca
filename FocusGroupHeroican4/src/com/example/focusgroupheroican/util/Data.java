package com.example.focusgroupheroican.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.focusgroupheroican.R;
import com.example.focusgroupheroican.clases.ClsComment;
import com.example.focusgroupheroican.clases.ClsConfig;
import com.example.focusgroupheroican.clases.ClsQuestion;
import com.example.focusgroupheroican.clases.ClsRango;

public class Data extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME= "db_poll_";
	
	private Context context;
	
	private final String tbl_usuario="CREATE TABLE tbl_usuario (usu_email VARCHAR(70), carg_id INTEGER, edad_id INTEGER, usu_nombre VARCHAR(100), usu_sex INTEGER, PRIMARY KEY(usu_email));";
	private final String tbl_cargo="CREATE TABLE tbl_cargo (carg_id INTEGER, carg_description VARCHAR(100), PRIMARY KEY(carg_id));";
	private final String tbl_edad="CREATE TABLE tbl_edad (edad_id INTEGER, edad_description VARCHAR(100), PRIMARY KEY(edad_id));";
	private final String tbl_question="CREATE TABLE tbl_question (ques_id INTEGER, hint_id INTEGER, ques_tipe INTEGER, ques_description VARCHAR(700), rang_id INTEGER, PRIMARY KEY(ques_id));";
	private final String tbl_hint="CREATE TABLE tbl_hint (hint_id INTEGER, hint_description VARCHAR(100), PRIMARY KEY(hint_id));";
	private final String tbl_answer="CREATE TABLE tbl_answer (ans_id INTEGER, ans_tipe INTEGER, ans_description VARCHAR(700), PRIMARY KEY(ans_id));";
	private final String tbl_choice="CREATE TABLE tbl_choice (cho_id INTEGER, usu_email VARCHAR(70), fun_id INTEGER, ques_id INTEGER, ans_id INTEGER, PRIMARY KEY(cho_id));";
	private final String tbl_comment="CREATE TABLE tbl_comment (com_id INTEGER PRIMARY KEY AUTOINCREMENT, cho_id INTEGER, com_description VARCHAR(1000));";
	private final String tbl_rang="CREATE TABLE tbl_rang (rang_id INTEGER, rang_pos INTEGER, ans_id INTEGER);";
	
	private final String script_tables=tbl_edad+tbl_cargo+tbl_usuario+tbl_question+tbl_answer+tbl_choice+tbl_comment+tbl_hint+tbl_rang;
	
	public Data(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void initQuestions(SQLiteDatabase db) {
		ClsQuestion q=null;
		
		ArrayList<ClsQuestion> questions=new ArrayList<ClsQuestion>();
		
		q=new ClsQuestion(0, ClsQuestion.RANG, context.getResources().getString(R.string.gen_pre1), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=1;
//		q.setRangs(rangs_a);
		questions.add(q);
		
		q=new ClsQuestion(1, ClsQuestion.RANG, context.getResources().getString(R.string.gen_pre2), context.getResources().getString(R.string.hint_porque));
//		q.setRangs(rangs_b);
		q.hint_id=1;
		q.rang_id=2;
		questions.add(q);
		
		q=new ClsQuestion(2, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre3), context.getResources().getString(R.string.hint_cambiarias));
		q.hint_id=2;
		questions.add(q);
		
		q=new ClsQuestion(3, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre4), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(4, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre5), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(5, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre6), context.getResources().getString(R.string.hint_cual));
		q.hint_id=3;
		questions.add(q);
		
		/////////////////////////////////////////////////////
		
		q=new ClsQuestion(6, ClsQuestion.RANG, context.getResources().getString(R.string.fun_pre1), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=1;
//		q.setRangs(rangs_a);
		questions.add(q);
		
		q=new ClsQuestion(7, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre2), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(8, ClsQuestion.RANG, context.getResources().getString(R.string.fun_pre3), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=3;
//		q.setRangs(rangs_c);
		questions.add(q);
		
		q=new ClsQuestion(9, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre4), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(10, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre5), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);

		q=new ClsQuestion(11, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre6), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);

		q=new ClsQuestion(12, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre7), context.getResources().getString(R.string.hint_porque));
		q.hint_id=3;
		questions.add(q);
		
		///////////////////////////////////////////////////////
		
		String script="";
		for (ClsQuestion ques :questions) {
			script+=ques.getSQL();
		}
		
		script+="INSERT INTO tbl_hint VALUES(1, '¿Por que?');";
		script+="INSERT INTO tbl_hint VALUES(2, '¿Cambiarias alguno?');";
		script+="INSERT INTO tbl_hint VALUES(3, '¿Cual? ¿el error fue solucionado?');";
		
		script+="INSERT INTO tbl_answer VALUES(1, "+ClsQuestion.BOOLEAN+",'si');";
		script+="INSERT INTO tbl_answer VALUES(2, "+ClsQuestion.BOOLEAN+",'no');";
		
		script+="INSERT INTO tbl_answer VALUES(3, "+ClsQuestion.RANG+",'Muy malo');";
		script+="INSERT INTO tbl_answer VALUES(4, "+ClsQuestion.RANG+",'Malo');";
		script+="INSERT INTO tbl_answer VALUES(5, "+ClsQuestion.RANG+",'Regular');";
		script+="INSERT INTO tbl_answer VALUES(6, "+ClsQuestion.RANG+",'Bueno');";
		script+="INSERT INTO tbl_answer VALUES(7, "+ClsQuestion.RANG+",'Muy bueno');";
		
		script+="INSERT INTO tbl_answer VALUES(8, "+ClsQuestion.RANG+",'Indiferente');";
		script+="INSERT INTO tbl_answer VALUES(9, "+ClsQuestion.RANG+",'Poco interesante');";
		script+="INSERT INTO tbl_answer VALUES(10, "+ClsQuestion.RANG+",'Interesante');";
		script+="INSERT INTO tbl_answer VALUES(11, "+ClsQuestion.RANG+",'Muy interesante');";
		
		script+="INSERT INTO tbl_answer VALUES(12, "+ClsQuestion.RANG+",'Muy dificil');";
		script+="INSERT INTO tbl_answer VALUES(13, "+ClsQuestion.RANG+",'Dificil');";
		script+="INSERT INTO tbl_answer VALUES(14, "+ClsQuestion.RANG+",'Facil');";
		script+="INSERT INTO tbl_answer VALUES(15, "+ClsQuestion.RANG+",'Muy facil');";
		
		/////////////////////////////////////
		
		script+="INSERT INTO tbl_edad VALUES(1, 'menor de 13');";
		script+="INSERT INTO tbl_edad VALUES(2, '13 a 15');";
		script+="INSERT INTO tbl_edad VALUES(3, '16 a 18');";
		script+="INSERT INTO tbl_edad VALUES(4, '19 a 22');";
		script+="INSERT INTO tbl_edad VALUES(5, '23 a 25');";
		script+="INSERT INTO tbl_edad VALUES(6, '26 a 29');";
		script+="INSERT INTO tbl_edad VALUES(7, '30 a 35');";
		script+="INSERT INTO tbl_edad VALUES(8, '36 a 40');";
		script+="INSERT INTO tbl_edad VALUES(9, 'mayor de 40');";
		
		//////////////////////////////////////
		
		script+="INSERT INTO tbl_cargo VALUES(1, 'Estudiante de colegio');";
		script+="INSERT INTO tbl_cargo VALUES(2, 'Estudiante universitario');";
		script+="INSERT INTO tbl_cargo VALUES(3, 'Abogado');";
		script+="INSERT INTO tbl_cargo VALUES(4, 'Economista');";
		script+="INSERT INTO tbl_cargo VALUES(5, 'Contador');";
		script+="INSERT INTO tbl_cargo VALUES(6, 'Profesor');";
		script+="INSERT INTO tbl_cargo VALUES(7, 'Diseñador');";
		script+="INSERT INTO tbl_cargo VALUES(8, 'Desarrollador de software');";
		script+="INSERT INTO tbl_cargo VALUES(9, 'Publicista');";
		script+="INSERT INTO tbl_cargo VALUES(10, 'Independiente');";
		
		//////////////////////////////////////////
		
		script+="INSERT INTO tbl_rang VALUES(1, 1, 3);";
		script+="INSERT INTO tbl_rang VALUES(1, 2, 4);";
		script+="INSERT INTO tbl_rang VALUES(1, 3, 5);";
		script+="INSERT INTO tbl_rang VALUES(1, 4, 6);";
		script+="INSERT INTO tbl_rang VALUES(1, 5, 7);";
		
		script+="INSERT INTO tbl_rang VALUES(2, 1, 8);";
		script+="INSERT INTO tbl_rang VALUES(2, 2, 9);";
		script+="INSERT INTO tbl_rang VALUES(2, 3, 10);";
		script+="INSERT INTO tbl_rang VALUES(2, 4, 11);";
		
		script+="INSERT INTO tbl_rang VALUES(3, 1, 12);";
		script+="INSERT INTO tbl_rang VALUES(3, 2, 13);";
		script+="INSERT INTO tbl_rang VALUES(3, 3, 14);";
		script+="INSERT INTO tbl_rang VALUES(3, 4, 15);";
		
		/////////////////////////////////////
		
		String[] queries = script.split(";");
		 for(String query : queries){
			 try {
				 db.execSQL(query);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("INIT INSERT:", query);
				Log.e("EXECPTION SCRIPT1", e.getMessage().toString());
			}
		 }
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		//MANUALLY
		try {
			String[] queries = script_tables.split(";");
			 for(String query : queries){
				 try {
					 db.execSQL(query);	
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("oNCREATE EXECPTION SCRIPT1", e.getMessage().toString());
				}
			 }
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("oNCREATE EXECPTION EXE SCRIPT2", e.getMessage().toString());
		}
		
		initQuestions(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	public Cursor exeConsulta(String cad) {
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery(cad, null);
		return c;
	}
	
	public String test(String cad) {
		SQLiteDatabase db=getReadableDatabase();
		String s="";
		try {
			Cursor c =db.rawQuery(cad, null);
			if (c.moveToFirst()) {
			     do{
			    	s= c.getString(0);
			     }while(c.moveToNext());
			}else{
				s="N";
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuess", e.getMessage().toString());
		}
		return s;
	}
	
	public ArrayList<String> getArray(String cad) {
		SQLiteDatabase db=getReadableDatabase();
		 ArrayList<String> array=new ArrayList<String>();
		try {
			Cursor c =db.rawQuery(cad, null);
			if (c.moveToFirst()) {
			     do{
			    	 array.add(c.getString(0));
			     }while(c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getArray", e.getMessage().toString());
		}
		return array;
	}
	
	public Integer getMaxId(String column, String table) {
		SQLiteDatabase db=getReadableDatabase();
		Integer s=0;
		try {
			Cursor c =db.rawQuery("SELECT IFNULL(MAX("+column+"), 0) FROM "+table, null);
			if (c.moveToFirst()) {
			     do{
			    	s= c.getInt(0);
			     }while(c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuess", e.getMessage().toString());
		}
		return s;
	}
	
	public boolean existsUsuario(String email) {
		SQLiteDatabase db=getReadableDatabase();
		boolean exists;
		try {
			Cursor c =db.rawQuery("SELECT * FROM tbl_usuario WHERE usu_email='"+email+"'", null);
			if (c.moveToFirst()) {
			     exists= true;
			}else{
				exists=false;
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			exists=true;
			Log.e("EXCEPTION getQuess", e.getMessage().toString());
		}
		return exists;
	}
	
	public boolean exeScript(String script) {
		SQLiteDatabase db =getWritableDatabase();
		try {
			String[] queries = script.split(";");
			 for(String query : queries){
				 try {
//					 query=query.replace('¬', ' ');
					 db.execSQL(query);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("QUERY:", query);
					Log.e("EXECPTION SCRIPT1", e.getMessage().toString());
				}
			 }
			 db.close();
			 return true;
		} catch (Exception e) {
			// TODO: handle exception
			try {
				db.close();	
			} catch (Exception e2) {
				// TODO: handle exception
			}
			Log.e("EXECPTION EXE SCRIPT2", e.getMessage().toString());
			return false;
		}
	}
	
	public boolean exeDML(String cad) {
		SQLiteDatabase db =getWritableDatabase();
		try {
			db.execSQL(cad);
			db.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean clear() {
		String script="DELETE FROM tbl_choice;";
		script+="DELETE FROM tbl_usuario;";
		script+="DELETE FROM tbl_comment;";
		return exeScript(script);
	}
	
	public void T() {
		ClsQuestion q=null;
		
		ArrayList<ClsQuestion> questions=new ArrayList<ClsQuestion>();
		
		q=new ClsQuestion(0, ClsQuestion.RANG, context.getResources().getString(R.string.gen_pre1), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=1;
//		q.setRangs(rangs_a);
		questions.add(q);
		
		q=new ClsQuestion(1, ClsQuestion.RANG, context.getResources().getString(R.string.gen_pre2), context.getResources().getString(R.string.hint_porque));
//		q.setRangs(rangs_b);
		q.hint_id=1;
		q.rang_id=2;
		questions.add(q);
		
		q=new ClsQuestion(2, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre3), context.getResources().getString(R.string.hint_cambiarias));
		q.hint_id=2;
		questions.add(q);
		
		q=new ClsQuestion(3, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre4), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(4, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre5), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(5, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.gen_pre6), context.getResources().getString(R.string.hint_cual));
		q.hint_id=3;
		questions.add(q);
		
		/////////////////////////////////////////////////////
		
		q=new ClsQuestion(6, ClsQuestion.RANG, context.getResources().getString(R.string.fun_pre1), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=1;
//		q.setRangs(rangs_a);
		questions.add(q);
		
		q=new ClsQuestion(7, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre2), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(8, ClsQuestion.RANG, context.getResources().getString(R.string.fun_pre3), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		q.rang_id=3;
//		q.setRangs(rangs_c);
		questions.add(q);
		
		q=new ClsQuestion(9, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre4), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);
		
		q=new ClsQuestion(10, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre5), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);

		q=new ClsQuestion(11, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre6), context.getResources().getString(R.string.hint_porque));
		q.hint_id=1;
		questions.add(q);

		q=new ClsQuestion(12, ClsQuestion.BOOLEAN, context.getResources().getString(R.string.fun_pre7), context.getResources().getString(R.string.hint_porque));
		q.hint_id=3;
		questions.add(q);
		
		///////////////////////////////////////////////////////
		
		String script="";
		for (ClsQuestion ques :questions) {
			script+=ques.getSQL();
		}
		
		Log.e("SQL", script);
		
		script="";
		
		script+="INSERT INTO tbl_hint VALUES(1, '¿Por que?');";
		script+="INSERT INTO tbl_hint VALUES(2, '¿Cambiarias alguno?');";
		script+="INSERT INTO tbl_hint VALUES(3, '¿Cual? ¿el error fue solucionado?');";
		
		script+="INSERT INTO tbl_answer VALUES(1, "+ClsQuestion.BOOLEAN+",'si');";
		script+="INSERT INTO tbl_answer VALUES(2, "+ClsQuestion.BOOLEAN+",'no');";
		
		script+="INSERT INTO tbl_answer VALUES(3, "+ClsQuestion.RANG+",'Muy malo');";
		script+="INSERT INTO tbl_answer VALUES(4, "+ClsQuestion.RANG+",'Malo');";
		script+="INSERT INTO tbl_answer VALUES(5, "+ClsQuestion.RANG+",'Regular');";
		script+="INSERT INTO tbl_answer VALUES(6, "+ClsQuestion.RANG+",'Bueno');";
		script+="INSERT INTO tbl_answer VALUES(7, "+ClsQuestion.RANG+",'Muy bueno');";
		
		script+="INSERT INTO tbl_answer VALUES(8, "+ClsQuestion.RANG+",'Indiferente');";
		script+="INSERT INTO tbl_answer VALUES(9, "+ClsQuestion.RANG+",'Poco interesante');";
		script+="INSERT INTO tbl_answer VALUES(10, "+ClsQuestion.RANG+",'Interesante');";
		script+="INSERT INTO tbl_answer VALUES(11, "+ClsQuestion.RANG+",'Muy interesante');";
		
		script+="INSERT INTO tbl_answer VALUES(12, "+ClsQuestion.RANG+",'Muy dificil');";
		script+="INSERT INTO tbl_answer VALUES(13, "+ClsQuestion.RANG+",'Dificil');";
		script+="INSERT INTO tbl_answer VALUES(14, "+ClsQuestion.RANG+",'Facil');";
		script+="INSERT INTO tbl_answer VALUES(15, "+ClsQuestion.RANG+",'Muy facil');";
		
		/////////////////////////////////////
		
		script+="INSERT INTO tbl_edad VALUES(1, 'menor de 13');";
		script+="INSERT INTO tbl_edad VALUES(2, '13 a 15');";
		script+="INSERT INTO tbl_edad VALUES(3, '16 a 18');";
		script+="INSERT INTO tbl_edad VALUES(4, '19 a 22');";
		script+="INSERT INTO tbl_edad VALUES(5, '23 a 25');";
		script+="INSERT INTO tbl_edad VALUES(6, '26 a 29');";
		script+="INSERT INTO tbl_edad VALUES(7, '30 a 35');";
		script+="INSERT INTO tbl_edad VALUES(8, '36 a 40');";
		script+="INSERT INTO tbl_edad VALUES(9, 'mayor de 40');";
		
		//////////////////////////////////////
		
		script+="INSERT INTO tbl_cargo VALUES(1, 'Estudiante de colegio');";
		script+="INSERT INTO tbl_cargo VALUES(2, 'Estudiante universitario');";
		script+="INSERT INTO tbl_cargo VALUES(3, 'Abogado');";
		script+="INSERT INTO tbl_cargo VALUES(4, 'Economista');";
		script+="INSERT INTO tbl_cargo VALUES(5, 'Contador');";
		script+="INSERT INTO tbl_cargo VALUES(6, 'Profesor');";
		script+="INSERT INTO tbl_cargo VALUES(7, 'Diseñador');";
		script+="INSERT INTO tbl_cargo VALUES(8, 'Desarrollador de software');";
		script+="INSERT INTO tbl_cargo VALUES(9, 'Publicista');";
		script+="INSERT INTO tbl_cargo VALUES(10, 'Independiente');";
		
		//////////////////////////////////////////
		
		script+="INSERT INTO tbl_rang VALUES(1, 1, 3);";
		script+="INSERT INTO tbl_rang VALUES(1, 2, 4);";
		script+="INSERT INTO tbl_rang VALUES(1, 3, 5);";
		script+="INSERT INTO tbl_rang VALUES(1, 4, 6);";
		script+="INSERT INTO tbl_rang VALUES(1, 5, 7);";
		
		script+="INSERT INTO tbl_rang VALUES(2, 1, 8);";
		script+="INSERT INTO tbl_rang VALUES(2, 2, 9);";
		script+="INSERT INTO tbl_rang VALUES(2, 3, 10);";
		script+="INSERT INTO tbl_rang VALUES(2, 4, 11);";
		
		script+="INSERT INTO tbl_rang VALUES(3, 1, 12);";
		script+="INSERT INTO tbl_rang VALUES(3, 2, 13);";
		script+="INSERT INTO tbl_rang VALUES(3, 3, 14);";
		script+="INSERT INTO tbl_rang VALUES(3, 4, 15);";
		
		Log.e("SQL2", script);
	}
	
	public ArrayList<ClsQuestion> getQuestions(String question_ids) {
		SQLiteDatabase db=getReadableDatabase();
		ArrayList<ClsQuestion> arrayList=new ArrayList<ClsQuestion>();
		try {
			Cursor c =db.rawQuery("SELECT q.ques_id, q.ques_tipe, q.ques_description, h.hint_description, q.rang_id FROM tbl_question q JOIN tbl_hint h ON(q.hint_id=h.hint_id) WHERE q.ques_id IN ("+question_ids+")", null);
			if (c.moveToFirst()) {
			     do{
			    	 ClsQuestion q=new ClsQuestion(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3));
			    	 q.rang_id=c.getInt(4);
			    	 if(q.ques_tipe==ClsQuestion.RANG){
			    		 //rang_id INTEGER, rang_pos INTEGER, ans_id
			    		 ArrayList<ClsRango> rangs=new ArrayList<ClsRango>();
			    		 Cursor c2 =db.rawQuery("SELECT r.rang_id, a.ans_description, a.ans_id FROM tbl_rang r JOIN tbl_answer a ON (a.ans_id=r.ans_id) WHERE r.rang_id="+q.rang_id, null);
			    		 if (c2.moveToFirst()) {
			    			 do{
			    				 ClsRango r=new ClsRango(c2.getInt(0), c2.getString(1));
			    				 r.ans_id=c2.getInt(2);
			    				 rangs.add(r);
//			    				 Log.e("", ""+c2.getInt(0));
			    			 }while(c2.moveToNext());
			    		 }
			    		 c2.close();
			    		 q.setRangs(rangs);
			    	 }
			    	 
			    	 arrayList.add(q);
			     }while(c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuess", e.getMessage().toString());
		}
		try {
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuess close", e.getMessage().toString());
		}
		return arrayList;
	}
	
	public ClsQuestion getQuestion(String question_id, int fun_id, ClsConfig config) {
		SQLiteDatabase db=getReadableDatabase();
		ClsQuestion q=null;
		try {
			String andFun=" AND fun_id="+fun_id;
			if(fun_id==-1){
				andFun="";
			}
			Cursor c =db.rawQuery("SELECT q.ques_id, q.ques_tipe, q.ques_description, h.hint_description, q.rang_id FROM tbl_question q JOIN tbl_hint h ON(q.hint_id=h.hint_id) WHERE q.ques_id ="+question_id, null);
			if (c.moveToFirst()) {
			     do{
			    	 q=new ClsQuestion(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3));
			    	 q.rang_id=c.getInt(4);
			    	 
			    	 String where=" WHERE ";
			    	 
			    	 if(config.carg_id==0 && config.edad_id==0 && config.gender_id==0){
			    		 where="";
			    	 }else{
			    		 String conditions="";
			    		 if(config.edad_id!=0){
			    			 conditions+="edad_id="+config.edad_id;
				    	 }
			    		 if(config.gender_id!=0){
			    			 if(conditions.length()>0){
			    				 conditions+=" AND ";
			    			 }
			    			 conditions+="usu_sex="+config.gender_id;
			    		 }
			    		 if(config.carg_id!=0){
			    			 if(conditions.length()>0){
			    				 conditions+=" AND ";
			    			 }
			    			 conditions+="carg_id="+config.carg_id;
			    		 }
			    		 where+=conditions;
			    	 }
			    	 
//			    	 Log.e("....", where);
			    	 
			    	 if(q.ques_tipe==ClsQuestion.RANG){
			    		 ArrayList<ClsRango> rangs=new ArrayList<ClsRango>();
			    		 Cursor c2 =db.rawQuery("SELECT r.rang_id, a.ans_description, a.ans_id FROM tbl_rang r JOIN tbl_answer a ON (a.ans_id=r.ans_id) WHERE r.rang_id="+q.rang_id, null);
			    		 if (c2.moveToFirst()) {
			    			 do{
			    				 ClsRango rang=new ClsRango(c2.getInt(0), c2.getString(1));
			    				 rang.ans_id=c2.getInt(2);
			    				 ///PORCENTAJES
			    				 String cas="SELECT (COUNT(*)*100/(SELECT COUNT(*) FROM tbl_usuario"+where+")), COUNT(*) FROM tbl_choice WHERE ans_id="+rang.ans_id+" AND ques_id="+q.ques_id+" AND usu_email IN (SELECT usu_email FROM tbl_usuario"+where+")"+andFun;
			    				 Log.e("CAS", cas);
			    				 Cursor c3 =db.rawQuery("SELECT (COUNT(*)*100/(SELECT COUNT(*) FROM tbl_usuario"+where+")), COUNT(*) FROM tbl_choice WHERE ans_id="+rang.ans_id+" AND ques_id="+q.ques_id+" AND usu_email IN (SELECT usu_email FROM tbl_usuario"+where+")"+andFun, null);
			    				 if (c3.moveToFirst()) {
			    					 rang.porcent=c3.getDouble(0);
			    					 rang.personas=c3.getInt(1);
			    					 Log.e("count", "rang_id"+rang.ans_id+"___"+rang.porcent+"____persons:"+rang.personas);
			    				 }
			    				 rangs.add(rang);
			    			 }while(c2.moveToNext());
			    		 }
			    		 c2.close();
			    		 q.setRangs(rangs);
			    	 }else{
			    		 //YES
			    		 Cursor c2 =db.rawQuery("SELECT (COUNT(*)*100/(SELECT COUNT(*) FROM tbl_usuario"+where+")), COUNT(*) FROM tbl_choice WHERE ans_id=1 AND ques_id="+q.ques_id+" AND usu_email IN (SELECT usu_email FROM tbl_usuario"+where+")"+andFun, null);
			    		 if (c2.moveToFirst()) {
					    	 q.porcent_yes=c2.getDouble(0);
					    	 q.personas_yes=c2.getInt(1);
			    		 }
			    		 //NOT
			    		 Cursor c3 =db.rawQuery("SELECT (COUNT(*)*100/(SELECT COUNT(*) FROM tbl_usuario"+where+")), COUNT(*) FROM tbl_choice WHERE ans_id=2 AND ques_id="+q.ques_id+" AND usu_email IN (SELECT usu_email FROM tbl_usuario"+where+")"+andFun, null);
			    		 if (c3.moveToFirst()) {
					    	 q.porcent_not=c3.getDouble(0);
					    	 q.personas_not=c3.getInt(1);
			    		 }
			    		 
			    		 c2.close();
			    		 c3.close();
			    	 }
			     }while(c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuestion", e.getMessage().toString());
		}
		try {
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getQuestion close", e.getMessage().toString());
		}
		return q;
	}
	
	public ArrayList<ClsComment> getComments(String ques_id, int fun_id) {
		String fun="";
		if(fun_id!=-1){
			fun=" AND ch.fun_id="+fun_id;
		}
		
		SQLiteDatabase db=getReadableDatabase();
		ArrayList<ClsComment> array=new ArrayList<ClsComment>();
		try {
			Cursor c =db.rawQuery("SELECT u.usu_email, u.usu_nombre, e.edad_description, carg.carg_description, u.usu_sex, a.ans_description, com.com_description FROM tbl_choice ch JOIN tbl_usuario u ON (u.usu_email=ch.usu_email) JOIN tbl_answer a ON(a.ans_id=ch.ans_id) JOIN tbl_edad e ON (e.edad_id=u.edad_id) JOIN tbl_cargo carg ON(carg.carg_id=u.carg_id) JOIN tbl_comment com ON(com.cho_id=ch.cho_id) WHERE ch.ques_id="+ques_id+fun, null);
			if (c.moveToFirst()) {
			     do{
			    	 array.add(new ClsComment(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
			     }while(c.moveToNext());
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION getArray", e.getMessage().toString());
		}
		
		return array;
	}
	
}
