package com.example.focusgroupheroican.clases;

import org.json.JSONObject;

import com.example.focusgroupheroican.util.EvtEndInsert;
import com.example.focusgroupheroican.util.GeneralData;
import com.example.focusgroupheroican.util.httpHandlerJSON;
import com.example.focusgroupheroican.util.httpHandlerJSON.EvtResultDML;
import com.example.focusgroupheroican.util.httpHandlerJSON.Send;


public class ClsUsuario implements EvtResultDML{
	
	public String usu_nombre, usu_email;
	public int edad_id=-1, usu_sex=-1, carg_id=-1;
	private String url="ExistsUsuario";
	
	private EvtEndInsert evtEndInsert;
	
	//usu_email VARCHAR(70), carg_id INTEGER, edad_id INTEGER, usu_nombre VARCHAR(100), usu_sex INTEGER
	public String getSQL() {
		return "INSERT INTO tbl_usuario VALUES('"+usu_email+"', "+carg_id+", "+edad_id+", '"+usu_nombre+"', "+usu_sex+");";
	}
	
	public String getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("usu_email", ""+usu_email);
			json.put("usu_nombre", ""+usu_nombre);
			json.put("edad_id", ""+edad_id);
			json.put("usu_sex", ""+usu_sex);
			json.put("carg_id", ""+carg_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return json.toString();
	}
	
	public void exists(EvtEndInsert evtEndInsert) {
		this.evtEndInsert=evtEndInsert;
		httpHandlerJSON handler=new httpHandlerJSON(GeneralData.SERVERCONTROLLER+url, httpHandlerJSON.DML);
		Send send=new Send();
		send.name="usu_email";
		send.value=usu_email;
		handler.setEvtResult(this);
		handler.execute(new Send[]{send});
	}
	
	public void insert(EvtEndInsert evtEndInsert) {
		this.evtEndInsert=evtEndInsert;
		httpHandlerJSON handler=new httpHandlerJSON(GeneralData.SERVERCONTROLLER+"InsertUsuario", httpHandlerJSON.DML);
		Send[] array=new Send[5];
		
		for (int i = 0; i < 5; i++) {
			array[i]=new Send();
		}
		
		array[0].name="usu_email";
		array[0].value=usu_email;
		
		array[1].name="usu_sex";
		array[1].value=""+usu_sex;
		
		array[2].name="usu_nombre";
		array[2].value=usu_nombre;
		
		array[3].name="carg_id";
		array[3].value=""+carg_id;
		
		array[4].name="edad_id";
		array[4].value=""+edad_id;
		
		handler.setEvtResult(this);
		handler.execute(array);
	}

	@Override
	public void OnResultDML(String canDo, Object tag) {
		// TODO Auto-generated method stub
		if(canDo!=null){
			if(!canDo.contains(GeneralData.ERROR_CONEXION)){
				evtEndInsert.OnEndInsert(canDo);
			}else{
				evtEndInsert.OnEndInsert(null);
			}
		}else{
			evtEndInsert.OnEndInsert(null);
		}
	}

	@Override
	public void OnResultDDL(String jsons, Object tag) {
		// TODO Auto-generated method stub
		
	}

}
