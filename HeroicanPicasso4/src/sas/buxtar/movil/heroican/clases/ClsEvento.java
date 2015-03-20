package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndConsult;
import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.os.Parcel;
import android.os.Parcelable;


public class ClsEvento implements Parcelable, EvtResultDML{
	
    private String evt_id, usu_id, evt_titulo, evt_descripcion, evt_lugar, evt_contacto, evt_fecha,evt_pathimage, reg_id;
    private double evt_latitud=0, evt_longitud=0;

	private final String urlMethodInsert="InsertEvento";
	private final int sizeSend=9;
	private Send[] arraySend=null;
	
    private httpHandlerJSON handler=null;
    
    private Integer fav_count;
    
    public Integer getFav_count() {
		return fav_count;
	}


	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}
    
	public ClsEvento() {
		// TODO Auto-generated constructor stub
		evt_id = evt_fecha=evt_pathimage=usu_id=usu_id=evt_titulo=evt_descripcion=evt_contacto=evt_lugar=reg_id="";
		fav_count=0;
		evt_latitud=evt_longitud;
	}


	public ClsEvento(String evt_id, String usu_id, String evt_titulo,
			String evt_descripcion, String evt_lugar, String evt_contacto,
			String evt_fecha, String evt_pathimage,
			double evt_latitud, double evt_longitud, String reg_id) {
		this.evt_id = evt_id;
		this.usu_id = usu_id;
		this.evt_titulo = evt_titulo;
		this.evt_descripcion = evt_descripcion;
		this.evt_lugar = evt_lugar;
		this.evt_contacto = evt_contacto;
		this.evt_fecha = evt_fecha;
		this.evt_pathimage = evt_pathimage;
		this.reg_id = reg_id;
		this.evt_latitud = evt_latitud;
		this.evt_longitud = evt_longitud;
		this.fav_count=0;
	}
	
	public void init(String evt_id, String usu_id, String evt_titulo,
			String evt_descripcion, String evt_lugar, String evt_contacto,
			String evt_fecha, String evt_pathimage,
			double evt_latitud, double evt_longitud, String reg_id) {
		this.evt_id = evt_id;
		this.usu_id = usu_id;
		this.evt_titulo = evt_titulo;
		this.evt_descripcion = evt_descripcion;
		this.evt_lugar = evt_lugar;
		this.evt_contacto = evt_contacto;
		this.evt_fecha = evt_fecha;
		this.evt_pathimage = evt_pathimage;
		this.reg_id = reg_id;
		this.evt_latitud = evt_latitud;
		this.evt_longitud = evt_longitud;
		this.fav_count=0;
	}
	
	public String getSQL(int order) {
		return "INSERT INTO tbl_evento VALUES('"+evt_id+"', '"+usu_id+"', '"+evt_titulo+"', '"+evt_descripcion+"', '"+evt_lugar+"', '"+evt_contacto+"', '"+evt_fecha+"', '"+evt_latitud+"', '"+evt_longitud+"', '"+reg_id+"', "+order+", "+fav_count+");";
	}

	public String getReg_id() {
		return reg_id;
	}

	public String getEvt_id() {
		return evt_id;
	}


	public void setEvt_id(String evt_id) {
		this.evt_id = evt_id;
	}


	public String getUsu_id() {
		return usu_id;
	}


	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}


	public String getEvt_titulo() {
		return evt_titulo;
	}


	public void setEvt_titulo(String evt_titulo) {
		this.evt_titulo = evt_titulo;
	}


	public String getEvt_descripcion() {
		return evt_descripcion;
	}


	public void setEvt_descripcion(String evt_descripcion) {
		this.evt_descripcion = evt_descripcion;
	}


	public String getEvt_lugar() {
		return evt_lugar;
	}


	public void setEvt_lugar(String evt_lugar) {
		this.evt_lugar = evt_lugar;
	}


	public String getEvt_contacto() {
		return evt_contacto;
	}


	public void setEvt_contacto(String evt_contacto) {
		this.evt_contacto = evt_contacto;
	}


	public String getEvt_pathimage() {
		return evt_pathimage;
	}


	public void setEvt_pathimage(String evt_pathimage) {
		this.evt_pathimage = evt_pathimage;
	}


	public String getEvt_fecha() {
		return evt_fecha;
	}


	public void setEvt_fecha(String evt_fecha) {
		this.evt_fecha = evt_fecha;
	}


	public double getEvt_latitud() {
		return evt_latitud;
	}


	public void setEvt_latitud(double evt_latitud) {
		this.evt_latitud = evt_latitud;
	}


	public double getEvt_longitud() {
		return evt_longitud;
	}


	public void setEvt_longitud(double evt_longitud) {
		this.evt_longitud = evt_longitud;
	}


	public void insert() {
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[0].name="evt_titulo";
		arraySend[0].value=""+evt_titulo;
		
		arraySend[1].name="evt_descripcion";
		arraySend[1].value=""+evt_descripcion;
		
		arraySend[2].name="evt_fecha";
		arraySend[2].value=""+evt_fecha;
		
		arraySend[3].name="evt_lugar";
		arraySend[3].value=""+evt_lugar;
		
		arraySend[4].name="evt_contacto";
		arraySend[4].value=""+evt_contacto;
		
		arraySend[5].name="evt_latitud";
		arraySend[5].value=""+evt_latitud;
		
		arraySend[6].name="evt_longitud";
		arraySend[6].value=""+evt_longitud;
		
		arraySend[7].name="image";
		arraySend[7].value=""+evt_pathimage;
		
		arraySend[8].name="usu_id";
		arraySend[8].value=""+usu_id;
		
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

	
	private EvtEndConsult evt=null;
	
	public void setEvtEndConsult(EvtEndConsult evt) {
		this.evt=evt;
	}
	
	private EvtEndInsert evtEndInsert;
	
	public void setEvtEndInsert(EvtEndInsert evt) {
		this.evtEndInsert=evt;
	}


	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}
	
	public ClsEvento(Parcel in){
    	this.evt_id=in.readString();
        this.usu_id =in.readString();
        this.evt_titulo =in.readString();
        this.evt_descripcion =in.readString();
        this.evt_lugar=in.readString();
        this.evt_fecha=in.readString();
        this.evt_pathimage=in.readString();
        this.reg_id=in.readString();
        this.evt_latitud=in.readDouble();
        this.evt_longitud=in.readDouble();
        this.fav_count=in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		
		dest.writeString(evt_id);
		dest.writeString(usu_id);
		dest.writeString(evt_descripcion);
		dest.writeString(evt_titulo);
		dest.writeString(evt_lugar);
		dest.writeString(evt_fecha);
		dest.writeString(evt_pathimage);
		dest.writeDouble(evt_longitud);
		dest.writeDouble(evt_latitud);
		dest.writeString(reg_id);
		dest.writeInt(fav_count);
	}
	
    public static final Parcelable.Creator<ClsEvento> CREATOR = new Parcelable.Creator<ClsEvento>() {

        public ClsEvento createFromParcel(Parcel in) {
            return new ClsEvento(in);
        }

        public ClsEvento[] newArray(int size) {
            return new ClsEvento[size];
        }
	};
}