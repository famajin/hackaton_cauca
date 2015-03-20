package sas.buxtar.movil.heroican.util;

import java.util.ArrayList;

import sas.buxtar.movil.heroican.clases.ClsEvento;
import sas.buxtar.movil.heroican.clases.ClsFavorito;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsRegistro;
import sas.buxtar.movil.heroican.clases.ClsTestimonio;
import sas.buxtar.movil.heroican.clases.ClsTip;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Data extends SQLiteOpenHelper{
	
	private final String tbl_tip="CREATE TABLE IF NOT EXISTS `tbl_tip` (`tip_id` varchar(30) NOT NULL DEFAULT '',`usu_id` varchar(30) DEFAULT NULL,`tip_titulo` varchar(114) DEFAULT NULL,`tip_descripcion` varchar(400) DEFAULT NULL,`reg_id` varchar(30) DEFAULT NULL, `ord` INTEGER, `fav_count` INTEGER DEFAULT 0, PRIMARY KEY (`tip_id`));";
	private final String tbl_testimonio="CREATE TABLE IF NOT EXISTS `tbl_testimonio` (`tes_id` varchar(30) NOT NULL DEFAULT '',`usu_id` varchar(30) DEFAULT NULL,`tes_titulo` varchar(114) DEFAULT NULL,`tes_descripcion` varchar(400) DEFAULT NULL,`reg_id` varchar(30) DEFAULT NULL, `ord` INTEGER, `fav_count` INTEGER DEFAULT 0, PRIMARY KEY (`tes_id`));";
	private final String tbl_mascota="CREATE TABLE IF NOT EXISTS `tbl_mascota` (`mas_id` varchar(30) NOT NULL DEFAULT '',`usu_id` varchar(35) DEFAULT NULL,`mas_nombre` varchar(40) DEFAULT NULL,`mas_edad` varchar(2) DEFAULT NULL,`mas_sexo` varchar(1) DEFAULT NULL,`mas_raza` varchar(2) DEFAULT NULL,`mas_tamano` varchar(2) DEFAULT NULL,`mas_descripcion` varchar(400) DEFAULT NULL,`mas_contacto` varchar(114) DEFAULT NULL,`mas_color` varchar(3) DEFAULT NULL,`mas_longitud` varchar(35) DEFAULT NULL,`mas_latitud` varchar(35) DEFAULT NULL,`mas_tipe` varchar(3) DEFAULT NULL,`reg_id` varchar(30) DEFAULT NULL , `ord` INTEGER, `fav_count` INTEGER DEFAULT 0, PRIMARY KEY (`mas_id`));";
	private final String tbl_fundacion="CREATE TABLE IF NOT EXISTS `tbl_fundacion` (`fun_id` varchar(30) NOT NULL DEFAULT '',`fun_nit` varchar(35) DEFAULT '',`fun_representante` varchar(60) DEFAULT NULL,`fun_cedularepresentante` varchar(35) DEFAULT NULL,`fun_direccion` varchar(114) DEFAULT NULL,`fun_descripcion` varchar(400) DEFAULT NULL,`fun_telefono` varchar(60) DEFAULT NULL,`fun_razonsocial` varchar(114) DEFAULT NULL, `fun_nombre` varchar(60) DEFAULT NULL, `fun_email` varchar(60) DEFAULT NULL, `fun_ciudad` varchar(5) DEFAULT NULL, `ord` INTEGER, `fav_count` INTEGER DEFAULT 0,`reg_id` varchar(35) DEFAULT '', PRIMARY KEY (`fun_id`));";
	private final String tbl_evento="CREATE TABLE IF NOT EXISTS `tbl_evento` (`evt_id` varchar(30) NOT NULL DEFAULT '',`usu_id` varchar(30) DEFAULT NULL,`evt_titulo` varchar(114) DEFAULT NULL,`evt_descripcion` varchar(400) DEFAULT NULL,`evt_lugar` varchar(114) DEFAULT NULL,`evt_contacto` varchar(114) DEFAULT NULL,`evt_fecha` varchar(35) DEFAULT NULL,`evt_latitud` varchar(35) DEFAULT NULL,`evt_longitud` varchar(35) DEFAULT NULL,`reg_id` varchar(30) DEFAULT NULL, `ord` INTEGER, `fav_count` INTEGER DEFAULT 0, PRIMARY KEY (`evt_id`));";
	private final String tbl_favorito="CREATE TABLE IF NOT EXISTS `tbl_favorito` (`card_id` varchar(30) DEFAULT '',`fav_tipe` varchar(3) DEFAULT '', `ord` INTEGER);";
	private final String tbl_registro="CREATE TABLE IF NOT EXISTS `tbl_registro` (`reg_id` varchar(30) NOT NULL DEFAULT '',`reg_registrado` varchar(30) DEFAULT NULL,`reg_tipe` varchar(3) DEFAULT NULL,`reg_fecha` datetime DEFAULT NULL, `ord` INTEGER, PRIMARY KEY (`reg_id`));";
	
	private final String CREATE_TABLES=tbl_registro+tbl_evento+tbl_favorito+tbl_fundacion+tbl_mascota+tbl_testimonio+tbl_tip;
	private final String DROP_TABLES="DROP TABLE IF EXISTS tbl_favorito;DROP TABLE IF EXISTS tbl_evento; DROP TABLE IF EXISTS tbl_tip;DROP TABLE IF EXISTS tbl_testimonio;DROP TABLE IF EXISTS tbl_mascota;DROP TABLE IF EXISTS tbl_fundacion;DROP TABLE IF EXISTS tbl_registro;";
	private final String DELETE_FROM_TABLES="DELETE FROM tbl_favorito;DELETE FROM tbl_evento;DELETE FROM tbl_tip;DELETE FROM tbl_testimonio;DELETE FROM tbl_mascota;DELETE FROM tbl_fundacion;DELETE FROM tbl_registro;";
	
	public Data(Context context) {
		super(context, "bd_heroican", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String[] tables = CREATE_TABLES.split(";");
		for(String SQL : tables){
		   db.execSQL(SQL);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String[] queries = DROP_TABLES.split(";");
		for(String query : queries){
		    db.execSQL(query);
		}
		
		String[] drops = CREATE_TABLES.split(";");
		for(String drop : drops){
		   db.execSQL(drop);
		}
	}
	
	public boolean clear() {
		return exeScript(DELETE_FROM_TABLES);
	}
	
	public boolean exeScript(String script) {
		SQLiteDatabase db =getWritableDatabase();
		try {
			String[] queries = script.split(";");
			 for(String query : queries){
				 try {
					 db.execSQL(query);	
				} catch (Exception e) {
					// TODO: handle exception
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
			Log.e("EXCEPTION EXE DML", ""+e.getMessage().toString());
			return false;
		}
	}

	public String test(String tbl) {
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT ord FROM "+tbl+" ORDER BY ord ASC", null);
		
		String a="";
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			a+=c.getString(0)+"-";
		}
		c.close();
		db.close();
		return a;
	}
	
	public String getRegIds() {
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT reg_id FROM tbl_registro", null);
		
		String reg_ids="";
		int size=c.getCount();
		if(size>0){
			for (int i = 0; i < size; i++) {
				c.moveToPosition(i);
				reg_ids+="'"+c.getString(0)+"',";
			}	
		}else{
			reg_ids="'-2',";
		}
		c.close();
		db.close();
		return reg_ids;
	}
	
	public Integer getMax(String tbl) {
		SQLiteDatabase db=getReadableDatabase();
		Cursor c=null;
		Integer max=0;
		try {
			c =db.rawQuery("SELECT MAX(ord) FROM "+tbl, null);
			if(c.getCount()>0){
				c.moveToPosition(0);
				max=c.getInt(0);	
			}
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				if(c!=null){
					c.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			Log.e("EXCEPTION MAX", e.getMessage().toString());
		}
		db.close();
		return max;
	}

	//1 VEZ
	//CARGA DE INTERNET LA BASE DE DATOS->DE LA BASE DE DATOS CARGAMOS LOS MODELOS
	//LUEGO ACTUALIZAMOS DE INTERNET LOS NUEVOS Y ACTUALIZAMOS EL ORDEN DE LOS DE ACA
	
	//ACTUALIZAMOS EL ORDEN DE LOS DE ACA (SOLO EN EL DROP)
	
	public void loadSource(ArrayList<ClsRegistro> lstRegistros, ArrayList<ClsMascota> lstMascotas, ArrayList<ClsEvento> lstEventos, ArrayList<ClsFundacion> lstFundaciones, ArrayList<ClsTip> lstTips, ArrayList<ClsTestimonio> lstTestimonios, ArrayList<ClsFavorito> lstFavoritos) {
		loadRegistros(lstRegistros);
		loadMascotas(lstMascotas);
		loadEventos(lstEventos);
		loadFundaciones(lstFundaciones);
		loadTips(lstTips);
		loadTestimonios(lstTestimonios);
		loadFavoritos(lstFavoritos);
	}
	
	public void loadRegistros(ArrayList<ClsRegistro> lstRegistros){
		lstRegistros.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_registro ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			lstRegistros.add(new ClsRegistro(c.getString(0), c.getString(1), c.getInt(2), c.getString(3)));
		}
		c.close();
		db.close();
	}
	
	public void loadMascotas(ArrayList<ClsMascota> lstMascotas) {
		lstMascotas.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_mascota ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			ClsMascota aux=new ClsMascota(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), "", c.getString(9), c.getDouble(11), c.getDouble(10), c.getInt(12), c.getString(13));
			aux.setFav_count(c.getInt(15));
			lstMascotas.add(aux);
		}
		c.close();
		db.close();
	}
	
	public void loadEventos(ArrayList<ClsEvento> lstEventos) {
		lstEventos.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_evento ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			ClsEvento aux=new ClsEvento(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), "", c.getDouble(7), c.getDouble(8), c.getString(9));
			aux.setFav_count(c.getInt(11));
			lstEventos.add(aux);
		}
		c.close();
		db.close();
	}
	
	public void loadFundaciones(ArrayList<ClsFundacion> lstFundaciones) {
		lstFundaciones.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_fundacion ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			ClsFundacion aux=new ClsFundacion(c.getString(0), c.getString(1), c.getString(8), c.getString(9), c.getString(5), "", c.getString(7), c.getString(4), c.getString(10), c.getString(6), c.getString(2), c.getString(3), c.getString(13), "");
			aux.setFav_count(c.getInt(12));
			lstFundaciones.add(aux);
		}
		c.close();
		db.close();
	}

	public void loadTips(ArrayList<ClsTip> lstTips) {
		lstTips.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_tip ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			ClsTip aux=new ClsTip(c.getString(0), c.getString(1), c.getString(3), c.getString(2), "", c.getString(4));
			aux.setFav_count(c.getInt(6));
			lstTips.add(aux);
		}
		c.close();
		db.close();
	}
	
	public void loadTestimonios(ArrayList<ClsTestimonio> lstTestimonios) {
		lstTestimonios.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_testimonio ORDER BY ord ASC", null);
		
		int size=c.getCount();
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			ClsTestimonio aux=new ClsTestimonio(c.getString(0), c.getString(1), c.getString(2), c.getString(3), "", c.getString(4));
			aux.setFav_count(c.getInt(6));
			lstTestimonios.add(aux);
		}
		c.close();
		db.close();
	}
	
	public void loadFavoritos(ArrayList<ClsFavorito> lstFavoritos) {
		lstFavoritos.clear();
		SQLiteDatabase db=getReadableDatabase();
		Cursor c =db.rawQuery("SELECT* FROM tbl_favorito ORDER BY ord ASC", null);
		
		int size=c.getCount();
		
		for (int i = 0; i < size; i++) {
			c.moveToPosition(i);
			lstFavoritos.add(new ClsFavorito(c.getString(0), c.getInt(1)));
		}
		c.close();
		db.close();
		
		Log.e("CONSULTADOS", ""+lstFavoritos.size());
	}
	
}
