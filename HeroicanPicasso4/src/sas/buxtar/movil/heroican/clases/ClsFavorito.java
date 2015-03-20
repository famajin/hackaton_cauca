package sas.buxtar.movil.heroican.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class ClsFavorito implements Parcelable{
	private String card_id;
	private int fav_tipe;
	public ClsFavorito(String card_id, int fav_tipe) {
		this.card_id = card_id;
		this.fav_tipe = fav_tipe;
	}
	public ClsFavorito() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(String card_id, int fav_tipe) {
		this.card_id = card_id;
		this.fav_tipe = fav_tipe;
	}
	public String getCard_id() {
		return card_id;
	}
	public int getFav_tipe() {
		return fav_tipe;
	}
	
	public String getSQL(int order) {
		return "INSERT INTO tbl_favorito VALUES('"+card_id+"', '"+fav_tipe+"', "+order+");";
	}
	
	public String getSQLDel() {
		return "DELETE FROM tbl_favorito WHERE card_id='"+card_id+"' AND  fav_tipe='"+fav_tipe+"';";
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(card_id);
		dest.writeInt(fav_tipe);
	}
	
	public ClsFavorito(Parcel in) {
		// TODO Auto-generated constructor stub
		card_id=in.readString();
		fav_tipe=in.readInt();
	}
	
    public static final Parcelable.Creator<ClsFavorito> CREATOR = new Parcelable.Creator<ClsFavorito>() {

        public ClsFavorito createFromParcel(Parcel in) {
            return new ClsFavorito(in);
        }

        public ClsFavorito[] newArray(int size) {
            return new ClsFavorito[size];
        }
	};
}
