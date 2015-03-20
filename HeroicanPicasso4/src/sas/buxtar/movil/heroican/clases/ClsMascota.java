package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.os.Parcel;
import android.os.Parcelable;


public class ClsMascota implements EvtResultDML, Parcelable{

	public static final int MAS_ADOPTAR=8, MAS_PERDIDA=9, MAS_ENCONTRADA=10, MAS_AGREGADA=11;
	
	private String mas_id, usu_id, mas_nombre, mas_edad, mas_sexo, mas_raza, mas_tamano, mas_descripcion, mas_contacto, mas_pathimage, mas_color, reg_id;

	private double mas_longitud=0, mas_latitud=0;
    private int mas_tipe;
    private Integer fav_count;
    
    //POR LOS DIALOGOS
    public String isCheck="0";

	private final String urlMethodInsert="InsertMascota";
	private final int sizeSend=13;
	private Send[] arraySend=null;
	
    private httpHandlerJSON handler=null;

	public ClsMascota(String mas_id, String usu_id, String mas_nombre,
			String mas_edad, String mas_sexo, String mas_raza,
			String mas_tamano, String mas_descripcion, String mas_contacto,
			String mas_pathimage, String mas_color,
			double mas_latitud, double mas_longitud, int mas_tipe, String reg_id) {
		this.mas_id = mas_id;
		this.usu_id = usu_id;
		this.mas_nombre = mas_nombre;
		this.mas_edad = mas_edad;
		this.mas_sexo = mas_sexo;
		this.mas_raza = mas_raza;
		this.mas_tamano = mas_tamano;
		this.mas_descripcion = mas_descripcion;
		this.mas_contacto = mas_contacto;
		this.mas_pathimage = mas_pathimage;
		this.mas_color = mas_color;
		this.reg_id = reg_id;
		this.mas_longitud = mas_longitud;
		this.mas_latitud = mas_latitud;
		this.mas_tipe = mas_tipe;
		this.fav_count=0;
	}
	
	public void init(String mas_id, String usu_id, String mas_nombre,
			String mas_edad, String mas_sexo, String mas_raza,
			String mas_tamano, String mas_descripcion, String mas_contacto,
			String mas_pathimage, String mas_color,
			double mas_latitud, double mas_longitud, int mas_tipe, String reg_id) {
		this.mas_id = mas_id;
		this.usu_id = usu_id;
		this.mas_nombre = mas_nombre;
		this.mas_edad = mas_edad;
		this.mas_sexo = mas_sexo;
		this.mas_raza = mas_raza;
		this.mas_tamano = mas_tamano;
		this.mas_descripcion = mas_descripcion;
		this.mas_contacto = mas_contacto;
		this.mas_pathimage = mas_pathimage;
		this.mas_color = mas_color;
		this.reg_id = reg_id;
		this.mas_longitud = mas_longitud;
		this.mas_latitud = mas_latitud;
		this.mas_tipe = mas_tipe;
		this.fav_count=0;
	}
	
	public void reset(String mas_id, String usu_id, String mas_nombre,
			String mas_edad, String mas_sexo, String mas_raza,
			String mas_tamano, String mas_descripcion, String mas_contacto,
			String mas_pathimage, String mas_color, String mas_direccion,
			double mas_latitud, double mas_longitud, int mas_tipe, String reg_id) {
		this.mas_id = mas_id;
		this.usu_id = usu_id;
		this.mas_nombre = mas_nombre;
		this.mas_edad = mas_edad;
		this.mas_sexo = mas_sexo;
		this.mas_raza = mas_raza;
		this.mas_tamano = mas_tamano;
		this.mas_descripcion = mas_descripcion;
		this.mas_contacto = mas_contacto;
		this.mas_pathimage = mas_pathimage;
		this.mas_color = mas_color;
		this.reg_id = reg_id;
		this.mas_longitud = mas_longitud;
		this.mas_latitud = mas_latitud;
		this.mas_tipe = mas_tipe;
		this.fav_count=0;
	}
    public Integer getFav_count() {
		return fav_count;
	}
    
	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}

	public int getMas_tipe() {
		return mas_tipe;
	}
	
	public void setMas_tipe(int mas_tipe) {
		this.mas_tipe = mas_tipe;
	}
	
	public String getSQL(int order) {
		return "INSERT INTO tbl_mascota VALUES('"+mas_id+"', '"+usu_id+"', '"+mas_nombre+"', '"+mas_edad+"', '"+mas_sexo+"', '"+mas_raza+"', '"+mas_tamano+"', '"+mas_descripcion+"', '"+mas_contacto+"', '"+mas_color+"', '"+mas_longitud+"', '"+mas_latitud+"', '"+mas_tipe+"', '"+reg_id+"', "+order+", "+fav_count+");";
	}
    
	public ClsMascota() {
		// TODO Auto-generated constructor stub
		mas_id = usu_id=mas_nombre=mas_edad=mas_sexo=mas_raza=mas_tamano=mas_descripcion=mas_contacto=mas_pathimage=mas_color=reg_id="";
		mas_longitud=mas_latitud=0;
		fav_count=mas_tipe=0;
	}
	
    public String getReg_id() {
		return reg_id;
	}
    
    public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}


	public String getMas_id() {
		return mas_id;
	}

	public void setMas_id(String mas_id) {
		this.mas_id = mas_id;
	}

	public String getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}

	public String getMas_nombre() {
		return mas_nombre;
	}

	public void setMas_nombre(String mas_nombre) {
		this.mas_nombre = mas_nombre;
	}

	public String getMas_edad() {
		return mas_edad;
	}

	public void setMas_edad(String mas_edad) {
		this.mas_edad = mas_edad;
	}

	public String getMas_sexo() {
		return mas_sexo;
	}

	public void setMas_sexo(String mas_sexo) {
		this.mas_sexo = mas_sexo;
	}

	public String getMas_raza() {
		return mas_raza;
	}

	public void setMas_raza(String mas_raza) {
		this.mas_raza = mas_raza;
	}

	public String getMas_tamano() {
		return mas_tamano;
	}

	public void setMas_tamano(String mas_tamano) {
		this.mas_tamano = mas_tamano;
	}

	public String getMas_descripcion() {
		return mas_descripcion;
	}

	public void setMas_descripcion(String mas_descripcion) {
		this.mas_descripcion = mas_descripcion;
	}

	public String getMas_contacto() {
		return mas_contacto;
	}

	public void setMas_contacto(String mas_contacto) {
		this.mas_contacto = mas_contacto;
	}

	public String getMas_pathimage() {
		return mas_pathimage;
	}

	public void setMas_pathimage(String mas_pathimage) {
		this.mas_pathimage = mas_pathimage;
	}

	public String getMas_color() {
		return mas_color;
	}

	public void setMas_color(String mas_color) {
		this.mas_color = mas_color;
	}

	public double getMas_longitud() {
		return mas_longitud;
	}

	public void setMas_longitud(double mas_longitud) {
		this.mas_longitud = mas_longitud;
	}

	public double getMas_latitud() {
		return mas_latitud;
	}

	public void setMas_latitud(double mas_latitud) {
		this.mas_latitud = mas_latitud;
	}

	public httpHandlerJSON getHandler() {
		return handler;
	}

	public void setHandler(httpHandlerJSON handler) {
		this.handler = handler;
	}
	
	public void insert() {	
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[0].name="mas_color";
		arraySend[0].value=""+mas_color;
		
		arraySend[1].name="mas_contacto";
		arraySend[1].value=""+mas_contacto;
		
		arraySend[2].name="mas_descripcion";
		arraySend[2].value=""+mas_descripcion;		
		
		arraySend[3].name="mas_edad";
		arraySend[3].value=""+mas_edad;
		
		arraySend[4].name="mas_latitud";
		arraySend[4].value=""+mas_latitud;
		
		arraySend[5].name="mas_longitud";
		arraySend[5].value=""+mas_longitud;
		
		arraySend[6].name="mas_nombre";
		arraySend[6].value=""+mas_nombre;
		
		arraySend[7].name="mas_raza";
		arraySend[7].value=""+mas_raza;
		
		arraySend[8].name="mas_sexo";
		arraySend[8].value=""+mas_sexo;
		
		arraySend[9].name="mas_tamano";
		arraySend[9].value=""+mas_tamano;
		
		arraySend[10].name="usu_id";
		arraySend[10].value=""+usu_id;
		
		arraySend[11].name="mas_tipe";
		arraySend[11].value=""+mas_tipe;
		
		arraySend[12].name="image";
		arraySend[12].value=""+mas_pathimage;
		
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
	
	public ClsMascota(Parcel in){
		this.mas_id = in.readString();
		this.usu_id = in.readString();
		this.mas_nombre = in.readString();
		this.mas_edad = in.readString();
		this.mas_sexo = in.readString();
		this.mas_raza = in.readString();
		this.mas_tamano = in.readString();
		this.mas_descripcion = in.readString();
		this.mas_contacto = in.readString();
		this.mas_pathimage = in.readString();
		this.mas_color = in.readString();
		this.reg_id = in.readString();
		this.mas_longitud = in.readDouble();
		this.mas_latitud = in.readDouble();
		this.mas_tipe = in.readInt();
		this.isCheck=in.readString();
		this.fav_count=in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeDouble(mas_longitud);
		dest.writeDouble(mas_latitud);
		dest.writeInt(mas_tipe);
		dest.writeString(mas_id);
		dest.writeString(reg_id);
		dest.writeString(mas_color);
		dest.writeString(mas_pathimage);
		dest.writeString(mas_contacto);
		dest.writeString(mas_descripcion);
		dest.writeString(mas_tamano);
		dest.writeString(mas_raza);
		dest.writeString(mas_sexo);
		dest.writeString(mas_edad);
		dest.writeString(mas_nombre);
		dest.writeString(usu_id);
		dest.writeString(isCheck);
		dest.writeInt(fav_count);
	}
	
    public static final Parcelable.Creator<ClsMascota> CREATOR = new Parcelable.Creator<ClsMascota>() {

        public ClsMascota createFromParcel(Parcel in) {
            return new ClsMascota(in);
        }

        public ClsMascota[] newArray(int size) {
            return new ClsMascota[size];
        }
	};
}
