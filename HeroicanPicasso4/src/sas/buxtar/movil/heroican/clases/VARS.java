package sas.buxtar.movil.heroican.clases;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class VARS implements Parcelable{
    public int heightContLst=0, heightObject=0, selected=-1, codeCategory=-1;
	public boolean isBack=true, picturefromThis=false, doDrop=true, setDimens=true, isShowButtons, callInfo=false, refresh=true;
	public String path="", dataTime="", fragTag="", titleActionBar="", dialTag="";
	public int h_items=0, h_item=0, size=0, sizeExtra=0;
	public boolean scrollTo=true;
	public ArrayList<Extra> extras;
	
	public static class Extra{
		public int tipe=-1;
		public String regId="", dialTag="", regIdExternal;
		public ClsCard card;
		public Object objectSelected;
		
		public Extra() {
			// TODO Auto-generated constructor stub
			regIdExternal=null;
			regId=dialTag="";
			tipe=-1;
		}
	}
	
	public VARS() {
		// TODO Auto-generated constructor stub
		extras=new ArrayList<Extra>();
		this.doDrop=true;
		this.codeCategory=-1;
		this.isShowButtons=true;
		this.selected=-1;
		this.heightObject=heightContLst=0;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(codeCategory);
		dest.writeInt(heightContLst);
		dest.writeInt(heightObject);
		dest.writeInt(selected);
		dest.writeInt(doDrop ? 1 : 0);
		dest.writeString(dialTag);
	}
	
	public VARS(Parcel in) {
		// TODO Auto-generated constructor stub
		this.codeCategory=in.readInt();
		this.selected=in.readInt();
		this.heightContLst=in.readInt();
		this.heightObject=in.readInt();
		this.doDrop=(in.readInt()==0)?false:true;
		this.dialTag=in.readString();
	}
}