package sas.buxtar.movil.heroican.util;

import sas.buxtar.movil.heroican.util.LocationConexion.EvtChangeLocation;
import sas.buxtar.movil.heroican.util.LocationConexion.EvtStateGps;
import android.content.Context;


public class LocationUtil {

	private static LocationConexion locationConexion;
	
	public static void setListeners(Context con, EvtChangeLocation evtLocation, EvtStateGps evtStateGps){
		if(locationConexion==null){
			locationConexion=new LocationConexion(con);
		}
		locationConexion.setEvtChangeLocation(evtLocation);
		locationConexion.setEvtStateGps(evtStateGps);
	}
	
	public static void relase(){
		locationConexion.setEvtChangeLocation(null);
		locationConexion.setEvtStateGps(null);
		locationConexion=null;
	}
	
}
