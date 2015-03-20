package com.example.focusgroupheroican.clases;

import java.util.ArrayList;

import com.example.focusgroupheroican.util.EvtEndInsert;
import com.example.focusgroupheroican.util.GeneralData;
import com.example.focusgroupheroican.util.httpHandlerJSON;
import com.example.focusgroupheroican.util.httpHandlerJSON.EvtResultDML;
import com.example.focusgroupheroican.util.httpHandlerJSON2;

public class Encuesta implements EvtResultDML{
	
	public ArrayList<String> send;
	private EvtEndInsert evtEndInsert;
	private String url="InsertEncuesta";
	
	public Encuesta() {
		// TODO Auto-generated constructor stub
		send=new ArrayList<String>();
	}
	
	public void insert(EvtEndInsert evtEndInsert) {
		this.evtEndInsert=evtEndInsert;
		httpHandlerJSON2 handler=new httpHandlerJSON2(GeneralData.SERVERCONTROLLER+url, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(""+0, send.get(0));
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
