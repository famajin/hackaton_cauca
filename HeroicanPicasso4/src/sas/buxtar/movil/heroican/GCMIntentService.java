package sas.buxtar.movil.heroican;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import sas.buxtar.movil.heroican.util.GeneralData;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	public static final String SENDER_ID = "617354728016";
	
	
	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {

		Log.i("", "onRegistered: registrationId=" + registrationId);
		if(evtResultGcm!=null){
			evtResultGcm.OnEvtGCM(registrationId);	
		}
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i("", "onUnregistered: registrationId=" + registrationId);
		if(evtResultGcm!=null){
			evtResultGcm.OnEvtGCM(registrationId);	
		}
	}

	@Override
	protected void onMessage(Context context, Intent data) {
		SharedPreferences preferences = getSharedPreferences(GeneralData.PREFERENCES_NAME,Context.MODE_PRIVATE);
		String usu_state=preferences.getString(GeneralData.PREF_STATE, "null");
		if(usu_state.equals(""+GeneralData.CONNECTED)){
			String request = data.getStringExtra("message");
			TaskProcess process=new TaskProcess(context);
			process.execute(new String[]{request});			
		}
	}
	
	private class TaskProcess extends AsyncTask<String, Notifire, Notifire>{

		private Context context;
		public TaskProcess(Context context) {
			// TODO Auto-generated constructor stub
			this.context=context;
		}
		
		@Override
		protected Notifire doInBackground(String... params) {
			// TODO Auto-generated method stu
			JSONObject jsonObject=null;
			String json=params[0];
			try {
				jsonObject = new JSONObject(json);	
			} catch (Exception e) {}
			
			if(jsonObject!=null){
				Notifire notifire=new Notifire();
				
				notifire.tipe=Integer.parseInt(jsonObject.optString("tipe"));
				notifire.reg_id=jsonObject.optString("reg_id");
				
				switch (notifire.tipe) {
				case GeneralData.MAS_ENCONTRADA:
					notifire.title=context.getResources().getString(R.string.notifire_encontrado_t);
					notifire.description=context.getResources().getString(R.string.notifire_encontrado_d);
					break;
					
				case GeneralData.MAS_PERDIDA:
					//OBTENEMOS EL NOMBRE
					notifire.title=jsonObject.optString("title");
					notifire.title+=" "+context.getResources().getString(R.string.notifire_perdido_t);
					notifire.description=context.getResources().getString(R.string.notifire_perdido_d);
					break;
				}
				return notifire;
			}else{
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(Notifire result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
					notJellyBean(result, context);
				}else{
					notKitkat(result, context);
				}
			}
		}
		
	}
	
	private void notJellyBean(Notifire notifire, Context context) {
        int idNotification=-1;
        try {
			idNotification=Integer.parseInt(notifire.reg_id);
		} catch (Exception e) {
			// TODO: handle exception
			idNotification=(int)System.currentTimeMillis();
		}
        if(idNotification==-1){
        	idNotification=(int)System.currentTimeMillis();
        }
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("reg_id", notifire.reg_id);
		intent.putExtra("tipe", notifire.tipe);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		PendingIntent pIntent = PendingIntent.getActivity(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// Create the notification with a notification builder
		Notification notification = new Notification.Builder(context)
		.setSmallIcon(R.drawable.ic_launcher_app)
		.setWhen(System.currentTimeMillis())
		.setContentTitle(notifire.title)
		.setContentText(notifire.description)
		.setContentIntent(pIntent)
		.getNotification();
		// Remove the notification on click
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(idNotification, notification);

		{
			// Wake Android Device when notification received
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			mWakelock.acquire();
 
			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}	
	}
	
	private void notKitkat(Notifire notifire,Context context) {
        int idNotification=-1;
        
        try {
			idNotification=Integer.parseInt(notifire.reg_id);
		} catch (Exception e) {
			// TODO: handle exception
			idNotification=(int)System.currentTimeMillis();
		}
        if(idNotification==-1){
        	idNotification=(int)System.currentTimeMillis();
        }
//		Toast.makeText(getApplicationContext(), "VICTORIA EN EL NOMBRE DE NUESTRO SEÑOR Y SALVADOR JESUS!!!!!"+notifire.reg_id, 0).show();
	    NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(context)
	                    .setSmallIcon(R.drawable.ic_launcher_app)
	                    .setContentTitle(notifire.title)
	                    .setContentText(notifire.description);
	
	//    Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + audioToneName);
	//    mBuilder.setSound(sound);
	    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	    mBuilder.setSound(alarmSound);
	    
	    mBuilder.setAutoCancel(true);
	    
	    Intent resultIntent = new Intent(this, MainActivity.class);
	    resultIntent.setAction(Intent.ACTION_MAIN);
	    resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	    resultIntent.putExtra("reg_id", notifire.reg_id);
	    resultIntent.putExtra("tipe", notifire.tipe);
//	    Bundle b=new Bundle();
//	    b.putInt("tipe", notifire.tipe);
//	    b.putString("reg_id", notifire.reg_id);
//	    resultIntent.putExtras(b);
//	    resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    
//	    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//	    // Adds the back stack for the Intent (but not the Intent itself)
//	    stackBuilder.addParentStack(MainActivity.class);
//	    // Adds the Intent that starts the Activity to the top of the stack
//	    stackBuilder.addNextIntent(resultIntent);
//	    PendingIntent contentIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), idNotification, resultIntent, 0);
	    
	    mBuilder.setContentIntent(contentIntent);
	    NotificationManager mNotificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    // mId allows you to update the notification later on.
	    mNotificationManager.notify(idNotification, mBuilder.build());
	//    mNotificationManager.notify(Integer.parseInt(notifire.reg_id), mBuilder.build());
	}
	
	private class Notifire{
		public String title="", description="", reg_id="";
		public int tipe=-1;
	}
	
	
	   @Override
	protected void onError(Context arg0, String errorId) {
		Log.e(TAG, "onError: errorId=" + errorId);
	}
	
	public interface EvtResultGCM{
		void OnEvtGCM(String gcm);
	}
	private static EvtResultGCM evtResultGcm;
	public static void setEvtResultGCM(EvtResultGCM evt2) {
		evtResultGcm=evt2;
	}

}