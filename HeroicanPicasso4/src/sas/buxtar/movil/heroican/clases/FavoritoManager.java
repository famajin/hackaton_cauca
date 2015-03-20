package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndFavorito;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;

public class FavoritoManager implements EvtResultDML{

	private int sizeSend=4;
	private Send[] arraySend=null;
	private httpHandlerJSON handler=null;
	private String urlMethod="Favorito", usu_id, card_id, fav_tipe;

	private int fav_increment;
	
	public FavoritoManager() {
		// TODO Auto-generated constructor stub
	}
	
	public String getFav_tipe() {
		return fav_tipe;
	}

	public void setFav_tipe(String fav_tipe) {
		this.fav_tipe = fav_tipe;
	}
	
	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	public String getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}
	
	public int getFavIncrement() {
		return fav_increment;
	}

	public void setFavIncrement(int increment) {
		this.fav_increment = increment;
	}

	public void insert(Object tag) {
		if(arraySend==null){
			arraySend=new Send[sizeSend];
			
			for (int i = 0; i < sizeSend; i++) {
				arraySend[i]=new Send();
			}
		}
		
		arraySend[0].name="usu_id";
		arraySend[0].value=""+usu_id;
		
		arraySend[1].name="fav_increment";
		arraySend[1].value=""+fav_increment;
		
		arraySend[2].name="card_id";
		arraySend[2].value=""+card_id;
		
		arraySend[3].name="fav_tipe";
		arraySend[3].value=""+fav_tipe;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethod, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.setTag(tag);
		handler.execute(arraySend);
	}

	@Override
	public void OnResultDML(String canDo, Object tag) {
		// TODO Auto-generated method stub
		handler=null;
		if(canDo!=null){
			if(evtEndFavorito!=null){
				evtEndFavorito.OnEndFavorito(true, tag);
			}else{
				evtEndFavorito.OnEndFavorito(false, tag);
			}
		}
	}
	
	public void setEvtEndFavorito(EvtEndFavorito evt) {
		this.evtEndFavorito=evt;
	}
	private EvtEndFavorito evtEndFavorito;

	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}
	
}
