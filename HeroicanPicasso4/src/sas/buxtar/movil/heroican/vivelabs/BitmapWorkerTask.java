package sas.buxtar.movil.heroican.vivelabs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.NetWorkState;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//AsyncTask para recuperar el Bitmap de la fuente
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    //Declaramos un WeakReference para relacionarlo con un ImageView 
	private final WeakReference<ImageView> imageViewReference;
	//Variable String para almacenar el nombre del grafico
    private String data = "";
    private Context context;
    private int widthReq=0;
    private int heightReq=0;
    //Objeto de LruCache para reservar espacio para los bitmap en memoria
  	//se almacenan objetos de tipo Bitmap bajo la llave String
    private LruCache<String,Bitmap> memoryCache;
    
    public static final int NET=0, PATH=1, ASSETS=2;
    private int tipe;
    private boolean createCache;
    //Contructor
    public BitmapWorkerTask(Context context, ImageView imageView, LruCache<String,Bitmap> memoryCache, int tipe, boolean createCache) {
    	this.tipe=tipe;
    	this.createCache=createCache;
        // inicializamos el imageViewReferences con el ImageView de ingreso en el constructor
    	imageViewReference = new WeakReference<ImageView>(imageView);
        //Asignamos el contexto y memoryCache de ingreso en el constructor a las variables correspondientes
    	this.context=context;
        this.memoryCache=memoryCache;
        //Obtenemos las metricas de la pantalla (ancho, alto, ...)
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //Calculamos el ancho requerido de la imagen dividiendo el ancho de la pantalla entre 2 y restando es espacio entre los items en el GridView
        //Se divide entre dos debido a que se muestran dos columnas en el GridView
        widthReq=(metrics.widthPixels/4)-2*context.getResources().getDimensionPixelSize(R.dimen.grid_wine_space);
        //Calculamos el alto requerido del archivo dimens en los recursos (values/dimens.xml)
        heightReq=context.getResources().getDimensionPixelSize(R.dimen.item_h);

    }

    public String getData() {
		return data;
	}
    
    //Proceso que se ejecuta en un Thread paralelo
    @Override
    protected Bitmap doInBackground(String... params) {
        //Obtenemos el nombre de la imagen
    	data = params[0];
    	Bitmap bitmap=null;
    	if(tipe==NET){
    		NetWorkState NetWorkState=new NetWorkState();
    		if(NetWorkState.isOnLine(context) && !isCancelled()){
    			bitmap = decodeFromNet(data, widthReq, heightReq);
            	if(createCache){
            		if(bitmap!=null){
            			if(cacheUtils==null){
            	    		cacheUtils=new CacheUtils(context);	
            	    	}
                    	if(!cacheUtils.isCacheSource(data)){
                    		//CREAMOS EL CACHE
                    		cacheUtils.writeCacheSource(bitmap, data);
                    	}		
            		}
            	}
    		}else{
    			bitmap=decodeBitmapFromAssets(context.getAssets(), ProcessImageV2.networkError, widthReq, heightReq);
    			data=ProcessImageV2.networkError;
    		}
    	}else if(tipe==PATH){
    		bitmap = decodeFromExternalStorage(data, widthReq, heightReq, createCache);
    		if(bitmap==null && !isCancelled()){
    			bitmap = decodeFromNet(data, widthReq, heightReq);
    		}
    	}
    	
    	if(bitmap==null && !isCancelled()){
    		bitmap=decodeBitmapFromAssets(context.getAssets(), ProcessImageV2.imageError, widthReq, heightReq);
    		data=ProcessImageV2.imageError;
    	}
        //Agregamos el bitmap en la memoria bajo el nombre de la imagen como  llave
        addBitmapToMemoryCache(data, bitmap);
        //Retornamos el bitmap
		try {
			//GARBAGE COLLECTOR
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
        return bitmap;
    }
    private CacheUtils cacheUtils;
    //Proceso que se ejecuta en el UI Thread
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //Si el AsyncTask ah sido cancelado retornamos null
    	if (isCancelled()) {
            bitmap = null;
        }
    	//Evaluamos si el ImageViewReferences y el bitmap son diferentes de nulos
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask =
                    getBitmapWorkerTask(imageView);
            //Evaluamos si el bitmapWorkerTaks del ImageView corresponde al actual
            // y si el imageView es diferente de nulo
            if (this == bitmapWorkerTask && imageView != null) {
            	//Agregamos el bitmap al ImageView
            	Animation animation = AnimationUtils.loadAnimation(context, R.anim.imageview_animation);
                imageView.startAnimation(animation);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    
    //Funcion para obtener el BitmapWorkerTask asociado a un ImageView 
    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            //Preguntamos si el drawable del imageView es una instancia de AsyncDrawable
            if (drawable instanceof AsyncDrawable) {
                //Realizamos un cast de Drawable a AsyncDrawable
            	final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            	//Retornamos el BitmapWorkerTask al asyncDrawable
                return asyncDrawable.getBitmapWorkerTask();
            }
         }
        
         return null;
     }
    
    public Bitmap decodeFromNet(String name,int reqWidth, int reqHeight) {
    	final DefaultHttpClient client = new DefaultHttpClient();

        //forming a HttoGet request
        final HttpGet getRequest = new HttpGet(GeneralData.SERVER_IMAGES+name);
        try {

            HttpResponse response = client.execute(getRequest);

            //check 200 OK for success
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                return null;

            }
            Bitmap bit=null;
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream
                    inputStream = entity.getContent();
                    if(!isCancelled()){
                    	// decoding stream data back into image Bitmap that android understands
                        bit = BitmapFactory.decodeStream(inputStream);	
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
            return bit;
        } catch (Exception e) {
            // You Could provide a more explicit error message for IOException
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + name + e.toString());
            return null;
        }
	}
    
//    //Funcion  para cargar el bitmap de los assets con el nombre del archivo y las dimensiones requeridas
//    public Bitmap decodeFromNet(String name,int reqWidth, int reqHeight) {
//		Bitmap bitmap=null;
//    	//Declaramos e instanciamos el objeto options.
//    	final BitmapFactory.Options options = new BitmapFactory.Options();
//        //Asignamos true en el atributo inJustDecodeBounds para obtener las caracteristicas del grafico
//    	// sin cargar el bitmap
//    	options.inJustDecodeBounds = true;
//        //Declaramos un objeto de InputStream
//    	InputStream iS;
//		try{
//			java.net.URL ul=new java.net.URL(GeneralData.SERVER_IMAGES+name);
//			//Obtenemos el InputStream iS de los assets con el nombre del grafico 
//			iS=ul.openStream();
//			BitmapFactory.decodeStream(iS, null,options);
//			iS.close();
//			//Obtenemos el indice inSampleSize para escalar la imagen
//			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//			//Asignamos false en el atributo inJustDecodeBounds para cargar el bitmap escanlando el grafico
//			options.inJustDecodeBounds = false;
//			iS=ul.openStream();
//			bitmap=BitmapFactory.decodeStream(iS,null,options);
//			iS.close();
//			iS=null;
//			return bitmap;	
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
    
//    //Funcion  para cargar el bitmap de los assets con el nombre del archivo y las dimensiones requeridas
//    public Bitmap decodeFromNet(String name,
//            int reqWidth, int reqHeight) {
//
//    	Bitmap bitmap=null;
//    	//Declaramos e instanciamos el objeto options.
//    	final BitmapFactory.Options options = new BitmapFactory.Options();
//        //Asignamos true en el atributo inJustDecodeBounds para obtener las caracteristicas del grafico
//    	// sin cargar el bitmap
//    	options.inJustDecodeBounds = true;
//        //Declaramos un objeto de InputStream
//    	InputStream iS;
//		try{
//			java.net.URL ul=new java.net.URL(GeneralData.SERVER_IMAGES+name);
//			//Obtenemos el InputStream iS de los assets con el nombre del grafico 
//			iS=ul.openStream();
//			if(!isCancelled()){
//				Bitmap b=BitmapFactory.decodeStream(iS, null,options);
//				iS.close();
//				try {
//					b.recycle();
//					b=null;
//					Log.e("RECYCLED:", ".....................");
//				} catch (Exception e) {
//					// TODO: handle exception
//					Log.e("catch:", ".....................");
//				}
//			}else{
//				iS.close();
//				return null;
//			}
//			//Obtenemos el indice inSampleSize para escalar la imagen
//			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
////			Log.e("SAMPLE SIZE NET:", ""+options.inSampleSize);
//			//Asignamos false en el atributo inJustDecodeBounds para cargar el bitmap escanlando el grafico
//			options.inJustDecodeBounds = false;
//			iS=ul.openStream();
//			if(!isCancelled()){
//				bitmap=BitmapFactory.decodeStream(iS,null,options);
//				iS.close();
//				iS=null;
//			}else{
//				iS.close();
//				iS=null;
//				return null;
//			}
//			return bitmap;	
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
    
    
////  //Funcion  para cargar el bitmap de los assets con el nombre del archivo y las dimensiones requeridas
//  public static Bitmap decodeFromNet(AssetManager asset, String name,
//          int reqWidth, int reqHeight) {
//
//  	Bitmap bitmap=null;
//  	//Declaramos e instanciamos el objeto options.
//  	final BitmapFactory.Options options = new BitmapFactory.Options();
//      //Asignamos true en el atributo inJustDecodeBounds para obtener las caracteristicas del grafico
//  	// sin cargar el bitmap
//  	options.inJustDecodeBounds = true;
//      //Declaramos un objeto de InputStream
//  	InputStream iS;
//		try{
//			java.net.URL ul=new java.net.URL(GeneralData.SERVER_IMAGES+name);
//			//Obtenemos el InputStream iS de los assets con el nombre del grafico 
//			iS = new BufferedInputStream(ul.openStream());
//			iS.mark(iS.available());
//			BitmapFactory.decodeStream(iS, null,options);
////			//Obtenemos el indice inSampleSize para escalar la imagen
//			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//			iS.reset();
//			//Asignamos false en el atributo inJustDecodeBounds para cargar el bitmap escanlando el grafico
//			options.inJustDecodeBounds = false;
//			
//			bitmap=BitmapFactory.decodeStream(iS,null,options);
//			
//			return bitmap;	
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//  }
    
	public static Bitmap decodeFromExternalStorage(
			String pathName, int reqWidth, int reqHeight, boolean createCache) {
    	String path;
    	if(createCache){
    		path=CacheUtils.PATH_CACHE+pathName;
    	}else{
    		path=pathName;
    	}
    	try {
    		File f=new File(path);
    		if(f.exists()){
    			Bitmap bitmap = null;
    			final BitmapFactory.Options options = new BitmapFactory.Options();
    			options.inJustDecodeBounds = true;
    			BitmapFactory.decodeFile(path, options);
    			options.inSampleSize = calculateInSampleSize(options, reqWidth,
    					reqHeight);
    			options.inJustDecodeBounds = false;
    			bitmap = BitmapFactory.decodeFile(path, options);
    			ExifInterface exif = new ExifInterface(pathName);
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
    			bitmap= Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
    					bitmap.getHeight(), matrix, true);
    			return bitmap;	
    		}else{
    			return null;
    		}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
    public static Bitmap decodeBitmapFromAssets(AssetManager asset, String name,
            int reqWidth, int reqHeight) {

    	Bitmap bitmap=null;
    	//Declaramos e instanciamos el objeto options.
    	final BitmapFactory.Options options = new BitmapFactory.Options();
        //Asignamos true en el atributo inJustDecodeBounds para obtener las caracteristicas del grafico
    	// sin cargar el bitmap
    	options.inJustDecodeBounds = true;
        //Declaramos un objeto de InputStream
    	InputStream iS;
		try {
			//Obtenemos el InputStream iS de los assets con el nombre del grafico 
			iS = asset.open(name);
			BitmapFactory.decodeStream(iS, null,options);
			//Obtenemos el indice inSampleSize para escalar la imagen
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			//Asignamos false en el atributo inJustDecodeBounds para cargar el bitmap escanlando el grafico
			options.inJustDecodeBounds = false;
			bitmap=BitmapFactory.decodeStream(iS,null,options);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        return bitmap;
    }
    
//    public static Bitmap decodeSampledBitmapFromAssets(AssetManager asset, String name,
//            int reqWidth, int reqHeight) {
//    	Bitmap bitmapImage;
//    	URL imageUrl = null;
//    	try {
//    	imageUrl = new URL(GeneralData.SERVER_IMAGES+name);
//
//    	HttpGet httpRequest = null;
//    	try {
//    		httpRequest = new HttpGet(imageUrl.toURI());	
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//    	HttpClient httpclient = new DefaultHttpClient();
//    	HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
//    	final HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            InputStream inputStream = null;
//            try {
//                inputStream = entity.getContent();
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                //options.inSampleSize = 2;
//                final Bitmap bitmap = BitmapFactory
//                        .decodeStream(inputStream, null, options);
//                return bitmap;
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                entity.consumeContent();
//            }
//        }
//        
//    	}catch(IOException e){
//    		e.printStackTrace();
//    	}
//    	
//    	return null;
//    }
    
    //Funcion para calculora el indice inSampleSize
    public static int calculateInSampleSize(
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
    //Funcion para agregar un Bitmap a la memoria
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        //Agrega el bitmap solo si no ha sido agregado antes
    	try {
        	if (memoryCache.get(key) == null) {
        		if(bitmap!=null){
        			memoryCache.put(key, bitmap);	
        		}
            }	
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
}
