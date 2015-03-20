package sas.buxtar.movil.heroican.clases;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import sas.buxtar.movil.heroican.interfaces.EvtEndConsult;
import sas.buxtar.movil.heroican.util.Data;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.SimpleCursorTreeAdapter;


public class ClsRegistro implements EvtResultDML, Parcelable{
	
	private String urlMethodConsult="ConsultRegistros";
    private httpHandlerJSON handler=null, handlerSearch=null;
    
    private Data db;
    private String reg_id, reg_registrado, reg_fecha;

	public static final int USUARIO=0;
	public static final int MASCOTA=1;
	public static final int EVENTO=2;
	public static final int TIP=3;
	public static final int TESTIMONIO=4;
	public static final int SEARCH=5;
	
	private JSONObject jsonResponse;
	private JSONObject jsonChildNode;
	private JSONArray array;
	private ArrayList<Send> jsons;
	private int posJson=0;
    
	public ClsRegistro(String reg_id, String reg_registrado,
			int reg_tipe, String reg_fecha) {
		this.reg_id = reg_id;
		this.reg_registrado = reg_registrado;
		this.reg_fecha = reg_fecha;
		this.reg_tipe = reg_tipe;
	}
	
	public void init(String reg_id, String reg_registrado,
			int reg_tipe, String reg_fecha) {
		this.reg_id = reg_id;
		this.reg_registrado = reg_registrado;
		this.reg_fecha = reg_fecha;
		this.reg_tipe = reg_tipe;
	}

	public String getReg_fecha() {
		return reg_fecha;
	}

	private int reg_tipe, sizeConsult=6;
	private Send[] arrayConsult=null, arrayConsultSearch=null;
    
    
    public String getReg_id() {
		return reg_id;
	}

	public String getReg_registrado() {
		return reg_registrado;
	}

	public int getReg_tipe() {
		return reg_tipe;
	}
    
	public String getSQL(int i) {
		return "INSERT INTO tbl_registro VALUES('"+reg_id+"', '"+reg_registrado+"', '"+reg_tipe+"', '"+reg_fecha+"', "+i+");";
	}
	
	public ClsRegistro() {
		// TODO Auto-generated constructor stub
	}
	
	public ClsRegistro(Context con) {
		// TODO Auto-generated constructor stub
		db=new Data(con);
	}
	
	private boolean refresh;
	private int code_category=-1;
	
	public void consult(ArrayList<Object> lstConsulta, String usu_ciudad, int code ,boolean refresh, String dev_id) {
		if(handler==null){
			this.code_category=code;
			this.refresh=refresh;
			if(arrayConsult==null){
				arrayConsult=new Send[sizeConsult];
				
				for (int i = 0; i < sizeConsult; i++) {
					arrayConsult[i]=new Send();
				}
			}
			String reg_ids="'-2',";
			if(!refresh){
				reg_ids=db.getRegIds();
			}
			
			Log.e("REG_IDS", reg_ids);
			
			arrayConsult[0].name="usu_id";
			arrayConsult[0].value=GeneralData.USU_ID;
			
			arrayConsult[1].name="usu_ciudad";
			arrayConsult[1].value=usu_ciudad;
			
			arrayConsult[2].name="reg_ids";
			arrayConsult[2].value=reg_ids;
			
			arrayConsult[3].name="code";
			arrayConsult[3].value=""+code;
			
			arrayConsult[4].name="dev_id";
			arrayConsult[4].value=dev_id;
			
			arrayConsult[5].name="refresh";
			if(refresh){
				arrayConsult[5].value="1";	
			}else{
				arrayConsult[5].value="0";
			}
			boolean pause;
			if(refresh){
				pause=false;
			}else{
				pause=true;
			}
			handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethodConsult, httpHandlerJSON.DDL, pause);
			handler.setEvtResult(this);
			handler.setTag(lstConsulta);
			handler.execute(arrayConsult);	
		}
	}
	
	public void cancelConsult() {
		if(handler!=null){
			handler.cancel(true);	
		}
	}
	
	EvtEndConsult evtEndConsultSearch;
	
	public void consultRegistro(String usu_id, String reg_id, Object[] params, EvtEndConsult evtEndConsultSearch){
		this.evtEndConsultSearch=evtEndConsultSearch;
		if(arrayConsultSearch==null){
			arrayConsultSearch=new Send[3];
			for (int i = 0; i < 3; i++) {
				arrayConsultSearch[i]=new Send();
			}
		}
		
		arrayConsultSearch[0].name="reg_id";
		arrayConsultSearch[0].value=reg_id;
		
		arrayConsultSearch[1].name="tipe";
		arrayConsultSearch[1].value=""+params[0];
		
		arrayConsultSearch[2].name="usu_id";
		arrayConsultSearch[2].value=usu_id;
		
		handlerSearch =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+"SearchRegistro", httpHandlerJSON.DDL);
		handlerSearch.setEvtResult(evtResultReg);
		handlerSearch.setTag(params);
		handlerSearch.execute(arrayConsultSearch);
	}
	
	public void cancelConsultRegistro() {
		if(handlerSearch!=null){
			handlerSearch.cancel(true);	
		}
	}
	
	//CUANDO HACEMOS UNA CONSULTA POR REG_ID
	EvtResultDML evtResultReg=new EvtResultDML() {
		
		@Override
		public void OnResultDML(String request, Object tag) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void OnResultDDL(String jsons, Object sourceJson) {
			// TODO Auto-generated method stub
			if(jsons!=null){
				try {
					Object[] params=(Object[]) sourceJson;
					JSONObject jsonObject=null;
					try {
						jsonObject = new JSONObject(jsons);	
					} catch (Exception e) {}
					
					int tipe=(Integer) params[0];
					
					if(tipe==GeneralData.MAS_ENCONTRADA || tipe==GeneralData.MAS_PERDIDA || tipe==GeneralData.MAS_ADOPTAR){
						tipe=GeneralData.MASCOTA;
					}
					
					switch (tipe) {
					case GeneralData.MASCOTA:
						ClsMascota masEnc=(ClsMascota) params[1];
						masEnc.setMas_id(jsonObject.optString("mas_id"));
						masEnc.setUsu_id(jsonObject.optString("usu_id"));
						masEnc.setMas_nombre(jsonObject.optString("mas_nombre"));
						masEnc.setMas_edad(jsonObject.optString("mas_edad"));
						masEnc.setMas_sexo(jsonObject.optString("mas_sexo"));
						masEnc.setMas_raza(jsonObject.optString("mas_raza"));
						masEnc.setMas_tamano(jsonObject.optString("mas_tamano"));
						masEnc.setMas_descripcion(jsonObject.optString("mas_descripcion"));
						masEnc.setMas_contacto(jsonObject.optString("mas_contacto"));
						masEnc.setMas_color(jsonObject.optString("mas_color"));
						masEnc.setFav_count(jsonObject.optInt("fav_count"));
						//INCREMENT
						masEnc.isCheck=jsonObject.optString("isCheck");
						try {
							masEnc.setMas_latitud(Double.parseDouble(""+jsonChildNode.getString("mas_latitud")));
							masEnc.setMas_longitud(Double.parseDouble(""+jsonChildNode.getString("mas_longitud")));
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					}
					evtEndConsultSearch.OnEndConsult(true);
				} catch (Exception e) {
					// TODO: handle exception
					evtEndConsultSearch.OnEndConsult(false);
				}
			}else{
				evtEndConsultSearch.OnEndConsult(false);
			}
		}
	};
	
	@Override
	public void OnResultDDL(String result, Object sourceJson) {
		// TODO Auto-generated method stub
		if(result!=null){
			new TaskProcess().execute(new Object[]{result, sourceJson});	
		}else{
			evtEndConsult.OnEndConsult(false);
		}
	}
	
	public class TaskProcess extends AsyncTask<Object, Object, Object>{

		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			//REINICIAMOS
			try {
				posJson=0;
				loadJsons((String) params[0]);
				
				ArrayList<Object> source=(ArrayList<Object>) params[1];
				
				if(refresh){
					int code=code_category;
					if(code_category==GeneralData.MAS_ADOPTAR || code_category==GeneralData.MAS_AGREGADA || code_category==GeneralData.MAS_ENCONTRADA || code_category==GeneralData.MAS_PERDIDA){
						code=GeneralData.MASCOTA;
					}else if(code_category==GeneralData.FUNDACION){
						code=GeneralData.USUARIO;
					}
					
					if(code==GeneralData.MASCOTA){
						db.exeDML("DELETE FROM tbl_registro WHERE reg_id IN (SELECT reg_id FROM tbl_mascota WHERE mas_tipe='"+code_category+"')");
						db.exeDML("DELETE FROM tbl_mascota WHERE mas_tipe='"+code_category+"'");
					}else if(code!=GeneralData.ALL){
						db.exeDML("DELETE FROM tbl_registro WHERE reg_tipe='"+code+"'");
					}
					
					Log.e("......", "CODE:"+code_category+"---REFRESH:"+refresh);
					
					switch (code) {
					case GeneralData.ALL:
						db.exeDML("DELETE FROM tbl_registro");
						db.clear();
						Log.e("CLEARED ALL", "scakmclc");
						break;

					case GeneralData.USUARIO:
						db.exeDML("DELETE FROM tbl_fundacion");
						break;
						
					case GeneralData.TESTIMONIO:
						db.exeDML("DELETE FROM tbl_testimonio");
						break;
						
					case GeneralData.TIP:
						db.exeDML("DELETE FROM tbl_tip");
						Log.e("CLEARED TIPSS", "WAS CLEAR");
						break;
						
					case GeneralData.EVENTO:
						db.exeDML("DELETE FROM tbl_evento");
						break;
					}
				}
				
				//REINICIAMOS
				posJson=0;
				loadRegistros(source.get(posJson));
				posJson++;
				loadTestimonios(source.get(posJson));
				posJson++;
				loadTips(source.get(posJson));
				posJson++;
				loadMascotas(source.get(posJson));
				posJson++;
				loadFundaciones(source.get(posJson));
				posJson++;
				loadEventos(source.get(posJson));
				posJson++;
				loadFavoritos(source.get(posJson));
				
				return "";
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handler=null;
			if(result!=null){
				evtEndConsult.OnEndConsult(true);	
			}else{
				evtEndConsult.OnEndConsult(false);
			}
		}
		
	}
	
	private void loadJsons(String request) {
		if(jsons==null){
			jsons=new ArrayList<Send>();
			jsons.add(new Send("jsonRegistros", ""));
			jsons.add(new Send("jsonTestimonios", ""));
			jsons.add(new Send("jsonTips", ""));
			jsons.add(new Send("jsonMascotas", ""));
			jsons.add(new Send("jsonFundaciones", ""));
			jsons.add(new Send("jsonEventos", ""));
			jsons.add(new Send("jsonFavoritos", ""));	
		}
		
		//DESCONCATENAR LOS JSON
		String caracter;
		int size=request.length();
		
		for (int i = 0; i < size; i++) {
			caracter=""+request.charAt(i);
			if(caracter.equals("¬")){
				posJson++;
				caracter="";
			}
			jsons.get(posJson).value+=caracter;
		}
//		Log.e(jsons.get(2).name, jsons.get(2).value);
	}
	
	private void reset() {
		jsonResponse=null;
		jsonChildNode=null;
		array=null;
		try {
			jsonResponse = new JSONObject(jsons.get(posJson).value);
			array = jsonResponse.optJSONArray(jsons.get(posJson).name);
		} catch (Exception e) {}
	}
	
	private void loadRegistros(Object sourceJson) {
		reset();
		String SCRIPT="";
		ClsRegistro aux=new ClsRegistro();
		int max=db.getMax("tbl_registro");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("reg_id"), jsonChildNode.optString("reg_registrado"), jsonChildNode.optInt("reg_tipe"), jsonChildNode.optString("reg_fecha"));
				SCRIPT+=aux.getSQL(max);
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			ArrayList<ClsRegistro> lstRegistros=(ArrayList<ClsRegistro>) sourceJson;
			db.loadRegistros(lstRegistros);
		}
	}
	
	private void loadTestimonios(Object sourceJson) {
		reset();
		String SCRIPT="";
		ClsTestimonio aux=new ClsTestimonio();
		int max=db.getMax("tbl_testimonio");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("tes_id"), jsonChildNode.optString("usu_id"), jsonChildNode.optString("tes_titulo"), jsonChildNode.optString("tes_descripcion"), "", jsonChildNode.optString("reg_id"));
				aux.setFav_count(jsonChildNode.optInt("fav_count"));
				SCRIPT+=aux.getSQL(max);	
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			ArrayList<ClsTestimonio> lstTestimonios=(ArrayList<ClsTestimonio>) sourceJson;
			db.loadTestimonios(lstTestimonios);
		}
	}
	
	private void loadTips(Object sourceJson){
		reset();
		String SCRIPT="";
		ClsTip aux=new ClsTip();
		int max=db.getMax("tbl_tip");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("tip_id"), jsonChildNode.optString("usu_id"), jsonChildNode.optString("tip_descripcion"), jsonChildNode.optString("tip_titulo"), "", jsonChildNode.optString("reg_id"));
				aux.setFav_count(jsonChildNode.optInt("fav_count"));
				SCRIPT+=aux.getSQL(max);	
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			ArrayList<ClsTip> lstTips=(ArrayList<ClsTip>) sourceJson;
			db.loadTips(lstTips);
		}
	}
	
	private void loadMascotas(Object sourceJson) {
		reset();
		ClsMascota aux=new ClsMascota();
		String SCRIPT="";
		int max=db.getMax("tbl_mascota");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("mas_id"), jsonChildNode.optString("usu_id"), jsonChildNode.optString("mas_nombre"), jsonChildNode.optString("mas_edad"), jsonChildNode.optString("mas_sexo"), jsonChildNode.optString("mas_raza"), jsonChildNode.optString("mas_tamano"), jsonChildNode.optString("mas_descripcion"), jsonChildNode.optString("mas_contacto"), "", jsonChildNode.optString("mas_color"),  Double.parseDouble(""+jsonChildNode.optString("mas_latitud")), Double.parseDouble(""+jsonChildNode.optString("mas_longitud")), jsonChildNode.optInt("mas_tipe"), jsonChildNode.optString("reg_id"));
				aux.setFav_count(jsonChildNode.optInt("fav_count"));
//				Log.e("LAT", jsonChildNode.optString("mas_longitud")+"...."+jsonChildNode.optString("mas_descripcion"));
				SCRIPT+=aux.getSQL(max);
				Log.e("SQL: ", aux.getSQL(max));
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			ArrayList<ClsMascota> lstMascotas=(ArrayList<ClsMascota>) sourceJson;
			db.loadMascotas(lstMascotas);
		}
	}
	
	private void loadFundaciones(Object sourceJson) {
		reset();
		String SCRIPT="";
		ClsFundacion aux=new ClsFundacion();
		int max=db.getMax("tbl_fundacion");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("fun_id"), jsonChildNode.optString("fun_nit"), jsonChildNode.optString("usu_nombre"), jsonChildNode.optString("usu_email"), jsonChildNode.optString("fun_descripcion"), "", jsonChildNode.optString("fun_razonsocial"), jsonChildNode.optString("fun_direccion"), jsonChildNode.optString("usu_ciudad"), jsonChildNode.optString("fun_telefono"), jsonChildNode.optString("fun_representante"), jsonChildNode.optString("fun_cedularepresentante"), jsonChildNode.optString("reg_id"), "");
				aux.setFav_count(jsonChildNode.optInt("fav_count"));
				SCRIPT+=aux.getSQL(max);	
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			ArrayList<ClsFundacion> lst=(ArrayList<ClsFundacion>) sourceJson;
			db.loadFundaciones(lst);
		}
	}
	
	private void loadEventos(Object sourceJson) {
		reset();
		String SCRIPT="";
		ClsEvento aux=new ClsEvento();
		int max=db.getMax("tbl_evento");
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				max++;
				aux.init(jsonChildNode.optString("evt_id"), jsonChildNode.optString("usu_id"), jsonChildNode.optString("evt_titulo"), jsonChildNode.optString("evt_descripcion"), jsonChildNode.optString("evt_lugar"), jsonChildNode.optString("evt_contacto"), jsonChildNode.optString("evt_fecha"), "", Double.parseDouble(""+jsonChildNode.optString("evt_latitud")), Double.parseDouble(""+jsonChildNode.optString("evt_longitud")), jsonChildNode.optString("reg_id"));
				aux.setFav_count(jsonChildNode.optInt("fav_count"));
				SCRIPT+=aux.getSQL(max);	
			}
		}	
		if(db!=null && SCRIPT.length()>0){
			Log.e("SQL", SCRIPT);
			db.exeScript(SCRIPT);
			ArrayList<ClsEvento> lstEventos=(ArrayList<ClsEvento>) sourceJson;
			db.loadEventos(lstEventos);
		}
	}
	
	//LOS FAVORITOS LOCALES DEBEMOS BORRARLOS PARA NO ACTUALIZARLOS CADA VEZ
	private void loadFavoritos(Object sourceJson) {
		reset();
		db.exeDML("DELETE FROM tbl_favorito");
		String SCRIPT="";
		ClsFavorito aux=new ClsFavorito();
		for (int i = 0; i < array.length(); i++) {
			try {
				jsonChildNode = array.getJSONObject(i);	
			} catch (Exception e) {
				jsonChildNode=null;
			}
			if(jsonChildNode!=null){
				aux.init(jsonChildNode.optString("card_id"), jsonChildNode.optInt("fav_tipe"));
				SCRIPT+=aux.getSQL(i);	
			}
		}
		if(db!=null && SCRIPT.length()>0){
			db.exeScript(SCRIPT);
			Log.e("FAVORITOS", SCRIPT);
			ArrayList<ClsFavorito> lstFavoritos=(ArrayList<ClsFavorito>) sourceJson;
			db.loadFavoritos(lstFavoritos);
		}
	}
	

	@Override
	public void OnResultDML(String canDo, Object tag) {
		// TODO Auto-generated method stub
		
	}

	private EvtEndConsult evtEndConsult=null;
	
	public void setEvtEndConsult(EvtEndConsult evt) {
		this.evtEndConsult=evt;
	}

	
	
	///////////////////////////RANDOM
	
	private httpHandlerJSON handlerRandom=null;
	private EvtEndConsult evtEndConsultRandom;
	private Send[] arrayConsultRandom=null;
	public void consultRandom(Object params, EvtEndConsult evt) {
		this.evtEndConsultRandom=evt;
		if(arrayConsultRandom==null){
			arrayConsultRandom=new Send[0];
		}
		
		handlerRandom =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+"SearchRandom", httpHandlerJSON.DDL);
		handlerRandom.setEvtResult(evtResultRandom);
		handlerRandom.setTag(params);
		handlerRandom.execute(arrayConsultRandom);
	}
	
	public void cancelRandom() {
		if(handlerRandom!=null){
			try {
				handlerRandom.cancel(true);	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	//CUANDO HACEMOS UNA CONSULTA POR REG_ID
	EvtResultDML evtResultRandom=new EvtResultDML() {
		
		@Override
		public void OnResultDML(String request, Object tag) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void OnResultDDL(String jsons, Object sourceJson) {
			// TODO Auto-generated method stub
			if(jsons!=null){
				try {
					JSONObject jsonObject=null;
					try {
						jsonObject = new JSONObject(jsons);	
					} catch (Exception e) {}
					
					ClsMascota masEnc=(ClsMascota)sourceJson;
					masEnc.setMas_id(jsonObject.optString("mas_id"));
					masEnc.setReg_id(jsonObject.optString("reg_id"));
					
					evtEndConsultRandom.OnEndConsult(true);
				} catch (Exception e) {
					// TODO: handle exception
					evtEndConsultRandom.OnEndConsult(false);
				}
			}else{
				evtEndConsultRandom.OnEndConsult(false);
			}
		}
	};

	public ClsRegistro(Parcel in){
		this.reg_id = in.readString();
		this.reg_registrado = in.readString();
		this.reg_fecha = in.readString();
		this.reg_tipe = in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(reg_id);
		dest.writeString(reg_registrado);
		dest.writeString(reg_fecha);
		dest.writeInt(reg_tipe);
	}
	
    public static final Parcelable.Creator<ClsRegistro> CREATOR = new Parcelable.Creator<ClsRegistro>() {

        public ClsRegistro createFromParcel(Parcel in) {
            return new ClsRegistro(in);
        }

        public ClsRegistro[] newArray(int size) {
            return new ClsRegistro[size];
        }
	};
}
