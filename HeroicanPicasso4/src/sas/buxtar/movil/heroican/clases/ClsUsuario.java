package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;

public class ClsUsuario implements EvtResultDML{
	
	public static final int FUNDACION=6;
	public static final int PERSONA=7;
	
	private String usu_id, usu_password, usu_email, usu_ciudad, usu_nombre, usu_pathImage, reg_id, gcm, usu_escuadron;
	
	private int usu_tipe;
	
	private String urlMethodInsert="InsertUsuario";
	private String urlMethodLoginApp="LogIn_App";
	private String urlMethodLoginRedes="LogIn_Redes";
	private String urlMethodLogout="LogOutUsuario";
	
	private int sizeSend=7;
	private Send[] arraySend=null, arrayLoginApp=null, arrayLoginRedes=null, arrayLogout=null;
	
    private httpHandlerJSON handler=null;
    
    public ClsUsuario() {
		// TODO Auto-generated constructor stub
	}

	public ClsUsuario(String usu_id, String usu_password, String usu_email,
			String usu_ciudad, String usu_nombre, String usu_pathImage,
			String reg_id, int usu_tipe) {
		this.usu_id = usu_id;
		this.usu_password = usu_password;
		this.usu_email = usu_email;
		this.usu_ciudad = usu_ciudad;
		this.usu_nombre = usu_nombre;
		this.usu_pathImage = usu_pathImage;
		this.reg_id = reg_id;
		this.usu_tipe = usu_tipe;
	}
	
	public void reset() {
		usu_id = usu_password=usu_email=usu_ciudad=usu_nombre=usu_pathImage=reg_id="";
	}

	public String getReg_id() {
		return reg_id;
	}
	
	public String getUsu_pathImage() {
		return usu_pathImage;
	}

	public void setUsu_pathImage(String usu_pathImage) {
		this.usu_pathImage = usu_pathImage;
	}

	public String getUsu_nombre() {
		return usu_nombre;
	}

	public void setUsu_nombre(String usu_nombre) {
		this.usu_nombre = usu_nombre;
	}

	public String getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}

	public String getUsu_password() {
		return usu_password;
	}

	public void setUsu_password(String usu_password) {
		this.usu_password = usu_password;
	}

	public String getUsu_email() {
		return usu_email;
	}

	public void setUsu_email(String usu_email) {
		this.usu_email = usu_email;
	}

	public String getUsu_ciudad() {
		return usu_ciudad;
	}

	public void setUsu_ciudad(String usu_ciudad) {
		this.usu_ciudad = usu_ciudad;
	}

	public int getUsu_tipe() {
		return usu_tipe;
	}

	public void setUsu_tipe(int usu_tipe) {
		this.usu_tipe = usu_tipe;
	}
	
	public String getUsu_escuadron() {
		return usu_escuadron;
	}

	public void setUsu_escuadron(String usu_escuadron) {
		this.usu_escuadron = usu_escuadron;
	}
	
	public void insert() {
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[0].name="usu_id";
		arraySend[0].value=""+usu_id;
		
		arraySend[1].name="usu_email";
		arraySend[1].value=""+usu_email;
		
		arraySend[2].name="usu_ciudad";
		arraySend[2].value=""+usu_ciudad;	
		
		arraySend[3].name="usu_password";
		arraySend[3].value=""+usu_password;	
		
		arraySend[4].name="usu_tipe";
		arraySend[4].value=""+usu_tipe;
		
		arraySend[5].name="usu_nombre";
		arraySend[5].value=""+usu_nombre;
		
		arraySend[6].name="image";
		arraySend[6].value=""+usu_pathImage;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodInsert, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arraySend);
	}

	@Override
	public void OnResultDML(String UsuId_SearchActive, Object tag) {
		// TODO Auto-generated method stub
		handler=null;
		if(evtEndValidate!=null){
			if(UsuId_SearchActive!=null){
				if(!UsuId_SearchActive.equals("error")){
					evtEndValidate.OnEndInsert(UsuId_SearchActive);	
				}else{
					evtEndValidate.OnEndInsert("error");
				}	
			}else{
				evtEndValidate.OnEndInsert(null);
			}
		}
	}
	
	private EvtEndInsert evtEndValidate;
	
	public void setEvtEndRequest(EvtEndInsert evt) {
		this.evtEndValidate=evt;
	}

	public void requestLoginApp() {
		if(arrayLoginApp==null){
			arrayLoginApp=new Send[3];	
		}
		for (int i = 0; i < 3; i++) {
			arrayLoginApp[i]=new Send();
		}
		arrayLoginApp[0].name="usu_password";
		arrayLoginApp[0].value=""+usu_password;
		
		arrayLoginApp[1].name="usu_email";
		arrayLoginApp[1].value=""+usu_email;
		
		arrayLoginApp[2].name="gcm";
		arrayLoginApp[2].value=""+gcm;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodLoginApp, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arrayLoginApp);
	}
	
	public void requestLoginRedes() {
		if(arrayLoginRedes==null){
			arrayLoginRedes=new Send[2];	
		}
		for (int i = 0; i < 2; i++) {
			arrayLoginRedes[i]=new Send();
		}
		
		arrayLoginRedes[0].name="usu_email";
		arrayLoginRedes[0].value=""+usu_email;
		
		arrayLoginRedes[1].name="gcm";
		arrayLoginRedes[1].value=""+gcm;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodLoginRedes, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arrayLoginRedes);
	}
	
	public void requestLogOut() {
		if(arrayLogout==null){
			arrayLogout=new Send[1];	
		}
		for (int i = 0; i < 1; i++) {
			arrayLogout[i]=new Send();
		}
		arrayLogout[0].name="dev_id";
		arrayLogout[0].value=""+usu_id;
		
		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodLogout, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arrayLogout);
	}
	

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}

}
