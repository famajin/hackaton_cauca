package sas.buxtar.movil.heroican.redes;

import sas.buxtar.movil.heroican.clases.ClsEvento;
import sas.buxtar.movil.heroican.clases.ClsFundacion;
import sas.buxtar.movil.heroican.clases.ClsMascota;
import sas.buxtar.movil.heroican.clases.ClsTestimonio;
import sas.buxtar.movil.heroican.clases.ClsTip;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.os.Parcel;
import android.os.Parcelable;

public class ShareContend implements Parcelable{

	private String name, description, caption, link, message;
	private String othersName, othersDescription;

	public ShareContend(String name, String description, String caption,
			String link, String message) {
		this.name = name;
		this.description = description;
		this.caption = caption;
		this.link = link;
		this.message = message;
	}
	
	public ShareContend() {
		name=description=caption=link=message=othersDescription=othersName="";
	}
	
	public void setClass(Object classs, int classTipe){
		switch (classTipe) {
		case GeneralData.MASCOTA:
			ClsMascota mas=(ClsMascota) classs;
			
			this.name=mas.getMas_nombre();
			this.description=mas.getMas_nombre();
			this.caption=mas.getMas_contacto()+"----caption";
			this.link=GeneralData.SERVER_IMAGES+mas.getReg_id()+"_"+mas.getMas_id()+".jpg";
			
			switch (mas.getMas_tipe()) {
			case GeneralData.MAS_ADOPTAR:
				this.description+="\n'nADOPTALO";
				break;

			case GeneralData.MAS_ENCONTRADA:
				this.description+="\n'nFUE VISTO";
				break;
				
			case GeneralData.MAS_PERDIDA:
				this.description+="\n'nESTA PERDIDO";
				break;
			}
			break;
		case GeneralData.EVENTO:
			ClsEvento evt=(ClsEvento) classs;
			this.name=evt.getEvt_titulo()+" texto a compartir";
			this.link=GeneralData.SERVER_IMAGES+evt.getReg_id()+"_"+evt.getEvt_id()+".jpg";
			break;
		case GeneralData.TESTIMONIO:
			ClsTestimonio tes=(ClsTestimonio) classs;
			this.name=tes.getTes_titulo()+" texto a compartir";
			this.link=GeneralData.SERVER_IMAGES+tes.getReg_id()+"_"+tes.getTes_id()+".jpg";
			break;
		case GeneralData.FUNDACION:
			ClsFundacion fun=(ClsFundacion) classs;
			this.name=fun.getFun_nombre();
			this.description=fun.getFun_email()+"\n"+fun.getFun_ciudad()+"\n"+fun.getFun_razonsocial();
			this.link=GeneralData.SERVER_IMAGES+fun.getReg_id()+"_"+fun.getFun_id()+".jpg";
			break;
		case GeneralData.TIP:
			ClsTip tip=(ClsTip) classs;
			this.name=tip.getTip_titulo();
			this.description=tip.getTip_descripcion();
			this.link=GeneralData.SERVER_IMAGES+tip.getReg_id()+"_"+tip.getTip_id()+".jpg";
			break;
		}
	}
	
	public void prepareShareOthers() {
		this.othersName=name;
		this.othersDescription=description+"\n\n"+link;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getOthersName() {
		return othersName;
	}

	public String getOthersDescription() {
		return othersDescription;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ShareContend(Parcel in) {
		// TODO Auto-generated constructor stub
		this.name=in.readString();
		this.caption=in.readString();
		this.link=in.readString();
		this.message=in.readString();
		this.description=in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(caption);
		dest.writeString(link);
		dest.writeString(message);
		dest.writeString(description);
	}

}
