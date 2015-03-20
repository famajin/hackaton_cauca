package sas.buxtar.movil.heroican.redes;

import sas.buxtar.movil.heroican.R;
import android.app.Activity;
import android.content.Intent;

public class ShareUtil {

	public static void share(Activity activity, ShareContend shareContend){
	   Intent intentCompartir = new Intent(Intent.ACTION_SEND);
	   intentCompartir.setType("text/plain");
	   intentCompartir.putExtra(Intent.EXTRA_SUBJECT, shareContend.getOthersName());
	   intentCompartir.putExtra(Intent.EXTRA_TEXT, shareContend.getOthersDescription());
	   activity.startActivity(Intent.createChooser(intentCompartir , activity.getResources().getString(R.string.share)));
	}
}
