package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;

public class ClsPersona implements EvtResultDML{

	private String per_id, per_nombre, per_email, per_password, per_genero, per_ciudad, per_fecha, per_pathImage, gcm, usu_escuadron;
	private String urlMethodInsert="InsertPersona";
	private int sizeSend=8;
	private Send[] arraySend=null;
   
    private httpHandlerJSON handler=null;
	
	public String getPer_id() {
		return per_id;
	}

	public ClsPersona(String per_id, String per_nombre, String per_email,
			String per_password, String per_genero, String per_ciudad,
			String per_fecha, String path) {
		this.per_id = per_id;
		this.per_nombre = per_nombre;
		this.per_email = per_email;
		this.per_password = per_password;
		this.per_genero = per_genero;
		this.per_ciudad = per_ciudad;
		this.per_fecha = per_fecha;
		this.per_pathImage=path;
	}

	public void setPer_id(String per_id) {
		this.per_id = per_id;
	}

	public String getPer_nombre() {
		return per_nombre;
	}

	public void setPer_nombre(String per_nombre) {
		this.per_nombre = per_nombre;
	}

	public String getPer_email() {
		return per_email;
	}

	public void setPer_email(String per_email) {
		this.per_email = per_email;
	}

	public String getPer_password() {
		return per_password;
	}

	public void setPer_password(String per_password) {
		this.per_password = per_password;
	}

	public String getPer_genero() {
		return per_genero;
	}

	public void setPer_genero(String per_genero) {
		this.per_genero = per_genero;
	}

	public String getPer_ciudad() {
		return per_ciudad;
	}

	public void setPer_ciudad(String per_ciudad) {
		this.per_ciudad = per_ciudad;
	}

	public String getPer_fecha() {
		return per_fecha;
	}

	public void setPer_fecha(String per_fecha) {
		this.per_fecha = per_fecha;
	}
	
	public String getUsu_escuadron() {
		return usu_escuadron;
	}

	public void setUsu_escuadron(String usu_escuadron) {
		this.usu_escuadron = usu_escuadron;
	}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getPer_pathImage() {
		return per_pathImage;
	}

	public void setPer_pathImage(String per_pathImage) {
		this.per_pathImage = per_pathImage;
	}
	
	public void insert() {
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[0].name="per_id";
		arraySend[0].value=""+per_id;
		
		arraySend[1].name="per_genero";
		arraySend[1].value=""+per_genero;
		
		arraySend[2].name="per_fecha";
		arraySend[2].value=""+per_fecha;
		
		arraySend[3].name="usu_nombre";
		arraySend[3].value=""+per_nombre;
		
		arraySend[4].name="usu_email";
		arraySend[4].value=""+per_email;
		
		arraySend[5].name="usu_password";
		arraySend[5].value=""+per_password;
		
		arraySend[6].name="usu_ciudad";
		arraySend[6].value=""+per_ciudad;
		
		arraySend[7].name="gcm";
		arraySend[7].value=""+gcm;
		
//		arraySend[7].name="image";
//		arraySend[7].value=""+per_pathImage;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodInsert, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arraySend);
	}

	@Override
	public void OnResultDML(String canDo, Object tag) {
		// TODO Auto-generated method stub
		handler=null;
		if(canDo!=null){
			evtEndInsert.OnEndInsert(canDo);
		}else{
			evtEndInsert.OnEndInsert(null);
		}
	}


	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}
	
	private EvtEndInsert evtEndInsert;
	
	public void setEvtEndInsert(EvtEndInsert evt) {
		this.evtEndInsert=evt;
	}
}