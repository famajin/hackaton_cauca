package sas.buxtar.movil.heroican.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
public class MediaUtil {

	public static final int CAMERA=11, GALLERY=22;
	private static Intent intentGallery=null, intentCamera=null;
	
	public static String takeFromCamera(Activity activity){
		if(intentCamera==null){
			intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		}
		String ruta=""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/HEROICAN/";
		String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File storageDir = new File(ruta);
		storageDir.mkdirs();
		File fileImagen= new File(storageDir, fileName+".jpg");
		Uri fileUri=Uri.fromFile(fileImagen);
		try {
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			activity.startActivityForResult(intentCamera, CAMERA);
		} catch (Exception e) {
//			//TRATAMOS DE NUEVO
			intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			activity.startActivityForResult(intentCamera, CAMERA);
		}
		
		
		return fileImagen.getAbsolutePath();
	}
	
	public static File takeFromCamera(ActionBarActivity activity, int a){
		if(intentCamera==null){
			intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		}
		String ruta=""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/HEROICAN/";
		String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File storageDir = new File(ruta);
		storageDir.mkdirs();
		File fileImagen= new File(storageDir, fileName+".jpg");
		Uri fileUri=Uri.fromFile(fileImagen);
		try {
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			activity.startActivityForResult(intentCamera, CAMERA);
		} catch (Exception e) {
//			//TRATAMOS DE NUEVO
//			intentCamera=new Intent();
//			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//			activity.startActivityForResult(intentCamera, CAMERA);
		}
		
		
		return fileImagen;
	}
	
	public static void takeFromGallery(Activity activity){
		if(intentGallery==null){
			intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);	
		}
		try {
			activity.startActivityForResult(intentGallery, GALLERY);	
		} catch (Exception e) {
			//TRATAMOS DE NUEVO
			intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			activity.startActivityForResult(intentGallery, GALLERY);
		}
	}
	
	private static final String[] proj = { MediaStore.Images.Media.DATA };
	private static CursorLoader loader=null;
	public static String getPathFromURI(Context context, Uri contentUri) {
		loader=null;
	    loader = new CursorLoader(context, contentUri, proj, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    String path=cursor.getString(column_index);
	    cursor.close();
	    cursor=null;
	    return path;
	}
	
	
	public static void relase() {
		loader=null;
		intentCamera=intentGallery=null;
	}

}
