package sas.buxtar.movil.heroican.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkState {
	
	public boolean isOnLine(Context con) {
	    ConnectivityManager cm =(ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
}
