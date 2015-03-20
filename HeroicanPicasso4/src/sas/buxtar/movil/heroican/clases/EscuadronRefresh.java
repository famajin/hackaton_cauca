package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;

public class EscuadronRefresh implements EvtResultDML{
	
	private EvtEscuadronRefresh evt;
	private final String urlMethod="RefreshEscuadron";
	
	public void refresh(String usu_id, EvtEscuadronRefresh evt) {
		this.evt=evt;
		Send[] arrayConsult=new Send[1];
		arrayConsult[0]=new Send();
		arrayConsult[0].name="usu_id";
		arrayConsult[0].value=usu_id;
		httpHandlerJSON handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethod, httpHandlerJSON.DML, false);
		handler.setEvtResult(this);
		handler.execute(arrayConsult);	
	}
	
	@Override
	public void OnResultDML(String result, Object tag) {
		// TODO Auto-generated method stub
		if(result!=null){
			if(!result.contains("error")){
				evt.onEvtEscuadronRefresh(result);	
			}
		}
	}

	@Override
	public void OnResultDDL(String jsons, Object tag) {
		// TODO Auto-generated method stub
		
	}
	
	public interface EvtEscuadronRefresh{
		public void onEvtEscuadronRefresh(String escuadronActive);
	}

}
