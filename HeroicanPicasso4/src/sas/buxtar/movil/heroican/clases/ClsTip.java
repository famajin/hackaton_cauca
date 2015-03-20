package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.os.Parcel;
import android.os.Parcelable;


public class ClsTip implements Parcelable, EvtResultDML{
	
	private String tip_id ,usu_id , tip_descripcion, tip_titulo, tip_pathImage, reg_id;
	private final String urlMethodInsert="InsertTip";  
	private final int sizeSend=4;
	private Send[] arraySend=null;
    private httpHandlerJSON handler=null;
    
    private Integer fav_count;
    
    public Integer getFav_count() {
		return fav_count;
	}


	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}


	public ClsTip() {
		// TODO Auto-generated constructor stub
		tip_id = usu_id=tip_descripcion=tip_titulo=tip_pathImage=reg_id="";
		fav_count=0;
	}	

	public ClsTip(String tip_id, String usu_id, String tip_descripcion,
			String tip_titulo, String tip_pathImage, String reg_id) {
		this.tip_id = tip_id;
		this.usu_id = usu_id;
		this.tip_descripcion = tip_descripcion;
		this.tip_titulo = tip_titulo;
		this.tip_pathImage = tip_pathImage;
		this.reg_id = reg_id;
		this.fav_count=0;
	}
	
	public void init(String tip_id, String usu_id, String tip_descripcion,
			String tip_titulo, String tip_pathImage, String reg_id) {
		this.tip_id = tip_id;
		this.usu_id = usu_id;
		this.tip_descripcion = tip_descripcion;
		this.tip_titulo = tip_titulo;
		this.tip_pathImage = tip_pathImage;
		this.reg_id = reg_id;
		this.fav_count=0;
	}
	
	public String getSQL(int order) {
		return "INSERT INTO tbl_tip VALUES('"+tip_id+"', '"+usu_id+"', '"+tip_titulo+"', '"+tip_descripcion+"', '"+reg_id+"', "+order+", "+fav_count+");";
	}

	public String getReg_id() {
		return reg_id;
	}

	public String getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(String usu_id) {
		this.usu_id = usu_id;
	}
	
	public String getTip_path() {
		return tip_pathImage;
	}


	public void setTip_path(String tip_path) {
		this.tip_pathImage = tip_path;
	}

	public String getTip_id() {
		return tip_id;
	}

	public void setTip_id(String tip_id) {
		this.tip_id = tip_id;
	}

	public String getTip_descripcion() {
		return tip_descripcion;
	}

	public void setTip_descripcion(String tip_descripcion) {
		this.tip_descripcion = tip_descripcion;
	}

	public String getTip_titulo() {
		return tip_titulo;
	}

	public void setTip_titulo(String tip_titulo) {
		this.tip_titulo = tip_titulo;
	}
    
	public void insert() {
		if(arraySend==null){
			arraySend=new Send[sizeSend];	
		}
		for (int i = 0; i < sizeSend; i++) {
			arraySend[i]=new Send();
		}
		
		arraySend[1].name="tip_titulo";
		arraySend[1].value=""+tip_titulo;
		
		arraySend[2].name="tip_descripcion";
		arraySend[2].value=""+tip_descripcion;
		
		arraySend[3].name="usu_id";
		arraySend[3].value=""+usu_id;
		
		arraySend[0].name="image";
		arraySend[0].value=""+tip_pathImage;
		
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


	public void pauseHandler() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
	}
	
	private EvtEndInsert evtEndInsert;
	
	public void setEvtEndInsert(EvtEndInsert evt) {
		this.evtEndInsert=evt;
	}
	
	public ClsTip(Parcel in){
    	this.tip_id=in.readString();
        this.usu_id =in.readString();
        this.tip_descripcion =in.readString();
        this.tip_titulo =in.readString();
        this.tip_pathImage=in.readString();
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
		dest.writeString(tip_id);
		dest.writeString(usu_id);
		dest.writeString(tip_descripcion);
		dest.writeString(tip_titulo);
		dest.writeString(tip_pathImage);
		dest.writeString(reg_id);
		dest.writeInt(fav_count);
	}
	
    public static final Parcelable.Creator<ClsTip> CREATOR = new Parcelable.Creator<ClsTip>() {

        public ClsTip createFromParcel(Parcel in) {
            return new ClsTip(in);
        }

        public ClsTip[] newArray(int size) {
            return new ClsTip[size];
        }
	};
}





