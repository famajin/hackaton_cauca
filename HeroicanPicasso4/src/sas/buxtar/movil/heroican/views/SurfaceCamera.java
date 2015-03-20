package sas.buxtar.movil.heroican.views;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class SurfaceCamera extends SurfaceView implements SurfaceHolder.Callback, AutoFocusCallback{
	
	private SurfaceHolder mySurfaceHolder;
	private Camera myCamera;
	private byte[] tempdata;
	private int tipeCamera=Camera.CameraInfo.CAMERA_FACING_BACK, rotationFoto;
	private boolean isFlashMode=false, myPreviewRunning;
	
	public SurfaceCamera(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SurfaceCamera(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	public SurfaceCamera(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		mySurfaceHolder = getHolder();
		mySurfaceHolder.addCallback(this);
		mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void takePicture(int rotation) {
		this.rotationFoto=rotation;
		try {
			Camera.Parameters p = myCamera.getParameters();
			if(isFlashMode){
				if(p.getFlashMode()!=null){
					p.setFlashMode(Parameters.FLASH_MODE_ON);
					myCamera.setParameters(p);
				}
			}
			//ENFOCA PRIMERO
			myCamera.autoFocus(this);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getContext(), "CATH", 0).show();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stud
		if(myPreviewRunning){
			myCamera.stopPreview();
			myPreviewRunning = false;
		}
		Camera.Parameters p = myCamera.getParameters();
		p.setPreviewSize(width,height);
		myCamera.setParameters(p);
		try {
			myCamera.setPreviewDisplay(holder);
			myCamera.startPreview();
		} catch (Exception e) {
			// TODO: handle exception
		}
		myPreviewRunning = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		initCamera();
	}
	
	public void setFlash(boolean isFlashMode) {
		this.isFlashMode=isFlashMode;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		destroyCamera();
	}
	
	private void initCamera() {
		myCamera=Camera.open(tipeCamera);
		setCameraDisplayOrientation(tipeCamera, myCamera);
		
		Camera.Parameters params = myCamera.getParameters();
		
		List<String> focusModes = params.getSupportedFocusModes();
		if (focusModes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
		}
		if(params.getFlashMode()!=null){
			params.setFlashMode(Parameters.FLASH_MODE_OFF);	
		}
		myCamera.setParameters(params);
	}
	
	private void destroyCamera() {
		myCamera.stopPreview();
		myPreviewRunning = false;
		myCamera.release();
		myCamera = null;
	}
	
	@SuppressLint("NewApi")
	private void setCameraDisplayOrientation(
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);

	     int degrees = 0;
	     switch (0) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	 }
	
	ShutterCallback myShutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {}
	};
	
	PictureCallback onTaken = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera myCamera) {
			// TODO Auto-generated method stub
			if(data != null){
				tempdata = data;
//				recivePicture();
				hilador();
			}
		}
	};
	PictureCallback myPictureCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera myCamera) {
			// TODO Auto-generated method stub
		}
	};
	
	private LruCache<String, Bitmap> memoryCache;
	
	private void initMemory() {
	     // OBTENEMOS EL MAXIMO DE MEMORIA DISPONIBLE PARA LA APLICACION
       final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
       // USAMOS SOLO 1/8 PARA EL CACHE DE BITMAPS
       final int cacheSize = maxMemory / 8;
       memoryCache = new LruCache<String, Bitmap>(cacheSize) {
           @Override
           protected int sizeOf(String key, Bitmap bitmap) {
               //METODO QUE RETORNA EL TAMAÏ¿½O DE CADA BITMAP ALMACENADO EN MEMORIA CACHE
               // EL TAMANO SE EXPRESA EN KILOBYTES
               return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
           }
       };
	}
	
	private void recivePicture(){
		initMemory();
//		final BitmapFactory.Options options = new BitmapFactory.Options();
        //Asignamos true en el atributo inJustDecodeBounds para obtener las caracteristicas del grafico
    	// sin cargar el bitmap
//    	options.inJustDecodeBounds = true;
//		WeakReference<Bitmap> weak=new WeakReference<Bitmap>(BitmapFactory.decodeByteArray(tempdata, 0, tempdata.length, options));
		Bitmap bitmap=BitmapFactory.decodeByteArray(tempdata, 0, tempdata.length);
		if(rotationFoto!=180){
			if(rotationFoto==0){
				rotationFoto=180;
			}
			
			//ROTAMOS LA IMAGEN
			bitmap=rotateBitmap(bitmap, rotationFoto);
		}
		
		String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File storageDir = new File(""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/HEROICAN/");
		storageDir.mkdirs();
		File fileImagen= new File(storageDir,fileName+".jpg");
        //SI YA EXISTE LO BORRAMOS
        try {
            FileOutputStream out = new FileOutputStream(fileImagen);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (Exception e) {}
//        weak.clear();
        evtFoto.OnFinishFoto(storageDir.getAbsolutePath()+"/"+fileName+".jpg");
	}
	
	private Bitmap rotateBitmap(Bitmap source, int angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
//		WeakReference<Bitmap> weak=new WeakReference<Bitmap>(Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true ));
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true );
	}
	
	private void hilador(){
		Thread thread =new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.post(runnable);
			}
		};
		thread.start();
	}
	final Handler handler =new Handler();
	final Runnable runnable= new Runnable() {
		public void run() {
			Thread thread =new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							recivePicture();
						}
					});
				}
			};
			thread.start();
		}
	};

	@Override
	public void onAutoFocus(boolean arg0, Camera arg1) {
		// TODO Auto-generated method stub
		evtFoto.OnProcessFoto();
		myCamera.takePicture(myShutterCallback, myPictureCallback, onTaken);
	}
	
	private EvtFoto evtFoto;
    public interface EvtFoto{
		public void OnFinishFoto(String ruta);
		public void OnProcessFoto();
	}
    
    public void setEvtFinishFoto(EvtFoto evt) {
		this.evtFoto=evt;
	}
    //xmpp
    //asterisk
}
