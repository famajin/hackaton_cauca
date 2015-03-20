package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.os.Parcel;
import android.os.Parcelable;


public class ClsFundacion implements Parcelable, EvtResultDML{
	
    private String fun_id, fun_nombre,fun_email ,fun_descripcion, fun_pathimage, fun_razonsocial, fun_direccion, fun_ciudad, fun_telefono, fun_representante, fun_cedularepresentante, reg_id, fun_password, fun_nit, usu_escuadron, gcm;

	private final String urlMethodInsert="InsertFundacion";  
	private final int sizeSend=13;
	private Send[] arraySend=null;
    private httpHandlerJSON handler=null;
    
    private Integer fav_count;
        
	public ClsFundacion() {
		// TODO Auto-generated constructor stub
		fun_id = fun_nit=fun_nombre=fun_password=fun_telefono=fun_cedularepresentante=fun_representante=fun_ciudad=fun_direccion=fun_razonsocial=fun_email=fun_descripcion=fun_pathimage=reg_id="";
		fav_count=0;
	}
	
	public ClsFundacion(String fun_id, String fun_nit, String fun_nombre, String fun_email,
			String fun_descripcion, String fun_pathimage,
			String fun_razonsocial, String fun_direccion, String fun_ciudad,
			String fun_telefono, String fun_representante,
			String fun_cedularepresentante, String reg_id, String password) {
		this.fun_id = fun_id;
		this.fun_nit=fun_nit;
		this.fun_nombre = fun_nombre;
		this.fun_email = fun_email;
		this.fun_descripcion = fun_descripcion;
		this.fun_pathimage = fun_pathimage;
		this.fun_razonsocial = fun_razonsocial;
		this.fun_direccion = fun_direccion;
		this.fun_ciudad = fun_ciudad;
		this.fun_telefono = fun_telefono;
		this.fun_representante = fun_representante;
		this.fun_cedularepresentante = fun_cedularepresentante;
		this.reg_id = reg_id;
		this.fun_password=password;
		this.fav_count=0;
	}
	
	public void init(String fun_id, String fun_nit, String fun_nombre, String fun_email,
			String fun_descripcion, String fun_pathimage,
			String fun_razonsocial, String fun_direccion, String fun_ciudad,
			String fun_telefono, String fun_representante,
			String fun_cedularepresentante, String reg_id, String password) {
		this.fun_id = fun_id;
		this.fun_nit=fun_nit;
		this.fun_nombre = fun_nombre;
		this.fun_email = fun_email;
		this.fun_descripcion = fun_descripcion;
		this.fun_pathimage = fun_pathimage;
		this.fun_razonsocial = fun_razonsocial;
		this.fun_direccion = fun_direccion;
		this.fun_ciudad = fun_ciudad;
		this.fun_telefono = fun_telefono;
		this.fun_representante = fun_representante;
		this.fun_cedularepresentante = fun_cedularepresentante;
		this.reg_id = reg_id;
		this.fun_password=password;
		this.fav_count=0;
	}
	
    public Integer getFav_count() {
		return fav_count;
	}
    
	public String getSQL(int order) {
		return "INSERT INTO tbl_fundacion VALUES('"+fun_id+"', '"+fun_nit+"', '"+fun_representante+"', '"+fun_cedularepresentante+"', '"+fun_direccion+"', '"+fun_descripcion+"', '"+fun_telefono+"', '"+fun_razonsocial+"', '"+fun_nombre+"', '"+fun_email+"', '"+fun_ciudad+"', "+order+", "+fav_count+", '"+reg_id+"');";
	}

	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}
	
	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getFun_nit() {
		return fun_nit;
	}

	public void setFun_nit(String fun_nit) {
		this.fun_nit = fun_nit;
	}

	public String getFun_password() {
		return fun_password;
	}

	public void setFun_password(String fun_password) {
		this.fun_password = fun_password;
	}

	public String getReg_id() {
		return reg_id;
	}
	
	public String getFun_id() {
		return fun_id;
	}

	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}

	public String getFun_nombre() {
		return fun_nombre;
	}

	public void setFun_nombre(String fun_nombre) {
		this.fun_nombre = fun_nombre;
	}

	public String getFun_email() {
		return fun_email;
	}

	public void setFun_email(String fun_email) {
		this.fun_email = fun_email;
	}

	public String getFun_descripcion() {
		return fun_descripcion;
	}

	public void setFun_descripcion(String fun_descripcion) {
		this.fun_descripcion = fun_descripcion;
	}

	public String getFun_pathimage() {
		return fun_pathimage;
	}

	public void setFun_pathimage(String fun_pathimage) {
		this.fun_pathimage = fun_pathimage;
	}

	public String getFun_razonsocial() {
		return fun_razonsocial;
	}

	public void setFun_razonsocial(String fun_razonsocial) {
		this.fun_razonsocial = fun_razonsocial;
	}

	public String getFun_direccion() {
		return fun_direccion;
	}

	public void setFun_direccion(String fun_direccion) {
		this.fun_direccion = fun_direccion;
	}

	public String getFun_ciudad() {
		return fun_ciudad;
	}

	public void setFun_ciudad(String fun_ciudad) {
		this.fun_ciudad = fun_ciudad;
	}

	public String getFun_telefono() {
		return fun_telefono;
	}

	public void setFun_telefono(String fun_telefono) {
		this.fun_telefono = fun_telefono;
	}

	public String getFun_representante() {
		return fun_representante;
	}

	public void setFun_representante(String fun_representante) {
		this.fun_representante = fun_representante;
	}

	public String getFun_cedularepresentante() {
		return fun_cedularepresentante;
	}

	public void setFun_cedularepresentante(String fun_cedularepresentante) {
		this.fun_cedularepresentante = fun_cedularepresentante;
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
		
		arraySend[0].name="fun_cedularepresentante";
		arraySend[0].value=""+fun_cedularepresentante;
		
		arraySend[1].name="fun_descripcion";
		arraySend[1].value=""+fun_descripcion;	
		
		arraySend[2].name="fun_direccion";
		arraySend[2].value=""+fun_direccion;
		
		arraySend[3].name="image";
		arraySend[3].value=""+fun_pathimage;
		
		arraySend[4].name="fun_nit";
		arraySend[4].value=""+fun_nit;
		
		arraySend[5].name="fun_razonsocial";
		arraySend[5].value=""+fun_razonsocial;
		
		arraySend[6].name="fun_representante";
		arraySend[6].value=""+fun_representante;
		
		arraySend[7].name="fun_telefono";
		arraySend[7].value=""+fun_telefono;
		
		arraySend[8].name="usu_nombre";
		arraySend[8].value=""+fun_nombre;
		
		arraySend[9].name="usu_email";
		arraySend[9].value=""+fun_email;
		
		arraySend[10].name="usu_password";
		arraySend[10].value=""+fun_password;
		
		arraySend[11].name="usu_ciudad";
		arraySend[11].value=""+fun_ciudad;
		
		arraySend[12].name="gcm";
		arraySend[12].value=""+gcm;
		
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
	public ClsFundacion(Parcel in){
		this.fun_id = in.readString();
		this.fun_nit=in.readString();
		this.fun_nombre = in.readString();
		this.fun_email = in.readString();
		this.fun_descripcion = in.readString();
		this.fun_pathimage = in.readString();
		this.fun_razonsocial = in.readString();
		this.fun_direccion = in.readString();
		this.fun_ciudad = in.readString();
		this.fun_telefono = in.readString();
		this.fun_representante = in.readString();
		this.fun_cedularepresentante = in.readString();
		this.reg_id = in.readString();
		this.fun_password=in.readString();
		this.fav_count=in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(fun_id);
		dest.writeString(fun_nit);
		dest.writeString(fun_nombre);
		dest.writeString(fun_email);
		dest.writeString(fun_descripcion);
		dest.writeString(fun_pathimage);
		dest.writeString(fun_razonsocial);
		dest.writeString(fun_direccion);
		dest.writeString(fun_ciudad);
		dest.writeString(fun_telefono);
		dest.writeString(fun_representante);
		dest.writeString(fun_cedularepresentante);
		dest.writeString(fun_password);
		dest.writeString(reg_id);
		dest.writeInt(fav_count);
	}
	
    public static final Parcelable.Creator<ClsFundacion> CREATOR = new Parcelable.Creator<ClsFundacion>() {

        public ClsFundacion createFromParcel(Parcel in) {
            return new ClsFundacion(in);
        }

        public ClsFundacion[] newArray(int size) {
            return new ClsFundacion[size];
        }
	};
}
