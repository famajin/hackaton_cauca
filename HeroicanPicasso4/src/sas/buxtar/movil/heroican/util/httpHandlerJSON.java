package sas.buxtar.movil.heroican.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;


public class httpHandlerJSON extends AsyncTask<Send, Void, String>{

	private String url;
	private int mode, auxSize;
	public static final int DDL=0, DML=1;
	private Object tag=null;
	private boolean stop=false;
	private long TIME_STOP=1500;
	
	private Bitmap sendBitmap=null;
	private static final int MINSIZE=570;
	
	public httpHandlerJSON(String URL, int mode) {
		this.url=URL;
		this.mode=mode;
	}
	
	public httpHandlerJSON(String URL, int mode, boolean stop) {
		this.url=URL;
		this.mode=mode;
		this.stop=stop;
	}
	
	@Override
	protected String doInBackground(Send... params) {
		// TODO Auto-generated method stub
		
		if(stop){
			try {
				Thread.sleep(TIME_STOP);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		String result=null;
		if(!isCancelled()){
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				
				//MANDAMOS UN JSON CON LOS DATOS
				JSONObject json = new JSONObject();
				
				//AGREGAMOS EN UN ARREGLO LOS PARAMETROS
				ArrayList<NameValuePair> lstParams = new ArrayList<NameValuePair>();

				for (int i = 0; i < params.length; i++) {
					if(params[i].name.equals("image")){
						//TRANFORMAMOS, ESCALAMOS Y CONVERTIMOS A STRING A LA IMAGEN CON EL PATH 
						params[i].value=imageToString(params[i].value);
						lstParams.add(new BasicNameValuePair(params[i].name, params[i].value));
					}else if(params[i].name.equals("reg_ids")){
						lstParams.add(new BasicNameValuePair(params[i].name, params[i].value));
					}else{
						json.put(params[i].name, params[i].value);
					}
				}
//				Log.e("URL", url);
				Log.e("SEND", json.toString());
				lstParams.add(new BasicNameValuePair("jsonData", json.toString()));
				if(!isCancelled()){
					if(mode==DML){
						//AGREGAMOS LOS PARAMETROS AL httpPost INSERTAMOS CON UTF-8
						httppost.setEntity(new UrlEncodedFormEntity(lstParams, "UTF-8"));	
					}else{
						//AGREGAMOS LOS PARAMETROS AL httpPost
						httppost.setEntity(new UrlEncodedFormEntity(lstParams));
					}
					
					HttpResponse response = httpclient.execute(httppost);
					if(mode==DDL){
						result=inputStreamToString(response.getEntity().getContent()).toString();
					}else{
						result=EntityUtils.toString(response.getEntity());
					}
//					Log.e("RESULT", result);
					try {
						httpclient.getConnectionManager().closeExpiredConnections();
						httpclient=null;
						httppost=null;
						response=null;
						try {
							System.gc();
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("CATCH CLOSE EXPIRED", e.getMessage().toString());
					}
					return result;	
				}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}	
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(!isCancelled()){
			if(result!=null){
				if(mode==DDL){
					evtResult.OnResultDDL(result, tag);
				}else {
					Log.e("***", result);
					evtResult.OnResultDML(result, tag);
				}
			}else{
				if(mode==DDL){ 
					evtResult.OnResultDDL(null, tag);
				}else {
					evtResult.OnResultDML(null, tag);
				}
			}	
		}
	}

	public void setTag(Object sourceJson) {
		this.tag=sourceJson;
	}
	
	private StringBuilder inputStreamToString(InputStream content) {
		// TODO Auto-generated method stub
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(content));
		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return answer;
	}
	


	private String imageToString(String path) {
		File imgFile = new  File(path);
		if(imgFile.exists()){
	    	//ESCALAMOS EL BITMAP A UNA RESOLUCION MAS PEQUEÑA
	    	sendBitmap=getFromPath(path, MINSIZE, MINSIZE);
	    	
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] byteArray = stream.toByteArray();
	    	
	    	//RECICLAMOS EL BITMAP SEND
	    	sendBitmap.recycle();
	    	sendBitmap=null;
			
	    	return Base64.encodeToString(byteArray, Base64.DEFAULT);
		}else{
	    	return "null";
		}
	}
	
	private Bitmap getFromPath(String path, int reqWidth, int reqHeight){
		try {
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path,options);
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		    options.inJustDecodeBounds = false;
		    Bitmap bitmap=BitmapFactory.decodeFile(path,options);
				ExifInterface exif = new ExifInterface(path);
				Matrix matrix = new Matrix();
				int exifOrientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);
				if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
					matrix.postRotate(-90);
				else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
					matrix.postRotate(-180);
				else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
					matrix.postRotate(-270);
				
				
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				
				if(width>height){
					auxSize=MINSIZE*height/width;
					reqWidth=MINSIZE;
					reqHeight=auxSize;
				}else{
					auxSize=MINSIZE*width/height;
					reqWidth=auxSize;
					reqHeight=MINSIZE;
				}
				
				float scaleWidth = ((float) reqWidth) / width;
				float scaleHeight = ((float) reqHeight) / height;
				matrix.postScale(scaleWidth, scaleHeight);
				
				Bitmap bit=Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
				bitmap.recycle();
				bitmap=null;
				return bit;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
    //Funcion para calcular el indice inSampleSize
   private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
    }

	public interface EvtResultDML{
		void OnResultDML(String canDo, Object tag);
		void OnResultDDL(String jsons, Object tag);
	}
	public void setEvtResult(EvtResultDML evt) {
		this.evtResult=evt;
	}
	private EvtResultDML evtResult;
	
	
	public static class Send{
		public Send(String name, String value) {
			// TODO Auto-generated constructor stub
			this.name=name;
			this.value=value;
		}
		public Send() {
			// TODO Auto-generated constructor stub
		}
		public String name, value;
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}
	
}
