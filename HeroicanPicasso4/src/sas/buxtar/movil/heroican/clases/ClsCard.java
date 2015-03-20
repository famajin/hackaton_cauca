package sas.buxtar.movil.heroican.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class ClsCard implements Parcelable{
	
	private int fav_increment;
	private String pathimage, title, id, reg_id, descripcion, categoria;
	
    private int tipe, fav_count;
    private boolean isChecked;

    public ClsCard(String reg_id, String id, String imageId, String title, String desc, int cod_Cat, int count) {
    	this.id=id;
        this.pathimage = imageId;
        this.title = title;
        this.descripcion = desc;
        this.tipe = cod_Cat;
        this.isChecked=false;
        this.fav_increment=0;
        this.fav_count=count;
        this.reg_id=reg_id;
    }
    
    public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getImageId() {
        return pathimage;
    }
    public void setImageId(String imageId) {
        this.pathimage = imageId;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
   
	public int getTipe() {
		return tipe;
	}
	public void setTipe(int cod_categoria) {
		this.tipe = cod_categoria;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

    public String toString() {
        return title + "\n" + descripcion;
    }

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		if(isChecked){
			fav_increment=1;
		}else{
			fav_increment=0;
		}
		this.isChecked = isChecked;
	}
	
    public int getFavCount() {
		return fav_count;
	}

	public void setFavCount(int count) {
		this.fav_count = count;
		if(count<0){
			fav_count=0;
		}
	}
	
	public ClsCard(Parcel in){
    	this.id=in.readString();
    	this.categoria=in.readString();
        this.pathimage =in.readString();
        this.title =in.readString();
        this.descripcion =in.readString();
        this.tipe = in.readInt();
        this.fav_increment=in.readInt();
        this.fav_count=in.readInt();
        this.isChecked=(in.readInt()==0)?false:true;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(isChecked ? 1 : 0);
		dest.writeInt(fav_increment);
		dest.writeInt(fav_count);
		dest.writeInt(tipe);
		dest.writeString(descripcion);
		dest.writeString(title);
		dest.writeString(pathimage);
		dest.writeString(id);
		dest.writeString(categoria);
	}
	
    public static final Parcelable.Creator<ClsCard> CREATOR = new Parcelable.Creator<ClsCard>() {

        public ClsCard createFromParcel(Parcel in) {
            return new ClsCard(in);
        }

        public ClsCard[] newArray(int size) {
            return new ClsCard[size];
        }
	};
}
