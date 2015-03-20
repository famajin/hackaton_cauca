package sas.buxtar.movil.heroican.util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationConexion implements LocationListener{

	private EvtStateGps evtStateGps;
	private EvtChangeLocation evtChangeLocation;
	private boolean isActivated=false;
	private Context context;
	
	public LocationConexion(Context con) {
		this.context=con;
		LocationManager mlocManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
	}
	
	public boolean isActivated() {
		return isActivated;
	}
		
	@Override
	public void onLocationChanged(Location loc) {
		// Este método se ejecuta cada vez que el GPS recibe nuevas coordenadas
		// debido a la detección de un cambio de ubicacion
		if(evtChangeLocation!=null){
			String direccion ="";
			if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
				try {
					Geocoder geocoder = new Geocoder(context, Locale.getDefault());
					List<Address> list = geocoder.getFromLocation(
							loc.getLatitude(), loc.getLongitude(), 1);
					if (!list.isEmpty()) {
						Address address = list.get(0);
						direccion =""+address.getAddressLine(0);
					}else{
						direccion="null";
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			evtChangeLocation.OnChangeLocation(loc.getLatitude(), loc.getLongitude(), direccion);	
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// Este método se ejecuta cuando el GPS es desactivado
		if(evtStateGps!=null){
			isActivated=false;
			evtStateGps.OnStateGps(false);	
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		// Este método se ejecuta cuando el GPS es activado
		if(evtStateGps!=null){
			isActivated=true;
			evtStateGps.OnStateGps(true);
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Este método se ejecuta cada vez que se detecta un cambio en el
		// status del proveedor de localización (GPS)
		// Los diferentes Status son:
		// OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
		// TEMPORARILY_UNAVAILABLE -> Tempòralmente no disponible pero se
		// espera que este disponible en breve
		// AVAILABLE -> Disponible
	}

	public interface EvtChangeLocation{
		public void OnChangeLocation(double latitud, double longitud, String direccion);
	}
	
	public interface EvtStateGps{
		public void OnStateGps(boolean isActivated);
	}
	
	public void setEvtChangeLocation(EvtChangeLocation evt) {
		this.evtChangeLocation=evt;
	}
	
	public void setEvtStateGps(EvtStateGps evt) {
		this.evtStateGps=evt;
	}
}
