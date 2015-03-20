package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.os.Parcel;
import android.os.Parcelable;


public class ClsTestimonio implements EvtResultDML, Parcelable{
	
    private String tes_id, usu_id, tes_titulo, tes_descripcion, tes_pathimage, reg_id;
	private final String urlMethodInsert="InsertTestimonio";
	private final int sizeSend=4;
	private Send[] arraySend=null;
    private httpHandlerJSON handler=null;
    private Integer fav_count;
    
	public ClsTestimonio() {
		// TODO Auto-generated constructor stub
		tes_id = usu_id=tes_titulo=tes_descripcion=tes_pathimage=reg_id="";
		fav_count=0;
	}
	
    public Integer getFav_count() {
		return fav_count;
	}

	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}
	
	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public ClsTestimonio(String tes_id, String usu_id, String tes_titulo,
			String tes_descripcion, String tes_pathimage, String reg_id) {
		this.tes_id = tes_id;
		this.usu_id = usu_id;
		this.tes_titulo = tes_titulo;
		this.tes_descripcion = tes_descripcion;
		this.tes_pathimage = tes_pathimage;
		this.reg_id = reg_id;
		this.fav_count=0;
	}
	
	public void init(String tes_id, String usu_id, String tes_titulo,
			String tes_descripcion, String tes_pathimage, String reg_id) {
		this.tes_id = tes_id;
		this.usu_id = usu_id;
		this.tes_titulo = tes_titulo;
		this.tes_descripcion = tes_descripcion;
		this.tes_pathimage = tes_pathimage;
		this.reg_id = reg_id;
		this.fav_count=0;
	}

	public String getTes_titulo() {
		return tes_titulo;
	}

	public void setTes_titulo(String tes_titulo) {
		this.tes_titulo = tes_titulo;
	}

	public String getTes_descripcion() {
		return tes_descripcion;
	}

	public void setTes_descripcion(String tes_descripcion) {
		this.tes_descripcion = tes_descripcion;
	}

	public String getTes_pathimage() {
		return tes_pathimage;
	}

	public void setTes_pathimage(String tes_pathimage) {
		this.tes_pathimage = tes_pathimage;
	}

	public String getTes_id() {
		return tes_id;
	}

	public void setTes_id(String tes_id) {
		this.tes_id = tes_id;
	}

	public String getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}

	public void insert() {	
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[0].name="tes_titulo";
		arraySend[0].value=""+tes_titulo;
		
		arraySend[1].name="tes_descripcion";
		arraySend[1].value=""+tes_descripcion;
		
		arraySend[2].name="image";
		arraySend[2].value=""+tes_pathimage;
		
		arraySend[3].name="usu_id";
		arraySend[3].value=""+usu_id;
		
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
	
	public String getSQL(int order) {
		return "INSERT INTO tbl_testimonio VALUES('"+tes_id+"', '"+usu_id+"', '"+tes_titulo+"', '"+tes_descripcion+"', '"+reg_id+"', "+order+", "+fav_count+");";
	}

	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}
	
	private EvtEndInsert evtEndInsert;
	
	public void setEvtEndInsert(EvtEndInsert evt) {
		this.evtEndInsert=evt;
	}
	
	public ClsTestimonio(Parcel in){
    	this.tes_id=in.readString();
        this.usu_id =in.readString();
        this.tes_titulo =in.readString();
        this.tes_descripcion =in.readString();
        this.tes_pathimage=in.readString();
        this.reg_id=in.readString();
        this.fav_count=in.readInt();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(tes_id);
		dest.writeString(usu_id);
		dest.writeString(tes_titulo);
		dest.writeString(tes_descripcion);
		dest.writeString(tes_pathimage);
		dest.writeString(reg_id);
		dest.writeInt(fav_count);
	}
	
    public static final Parcelable.Creator<ClsTestimonio> CREATOR = new Parcelable.Creator<ClsTestimonio>() {

        public ClsTestimonio createFromParcel(Parcel in) {
            return new ClsTestimonio(in);
        }

        public ClsTestimonio[] newArray(int size) {
            return new ClsTestimonio[size];
        }
	};
}
