package sas.buxtar.movil.heroican.vivelabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class ProcessImageV2 {

    private CacheUtils cacheUtils;
    private Context context;
    private LruCache<String,Bitmap> memoryCache;
    private boolean createCache;
	public static String networkError="images_errors/network.jpg";
	public static String imageError="images_errors/process.jpg";
    
	public ProcessImageV2(Context context, LruCache<String, Bitmap> memoryCache, boolean createCache) {
		this.createCache=createCache;
		this.context = context;
		this.memoryCache = memoryCache;
	}
	public void loadImage(String fileName, ImageView imageView) {
		//Obtenemos el Bitmap en memoria que corresponde al fileName
		//El metodo getBitmapFromMemCache esta implementado al final de esta clase
		final Bitmap bitmap = getBitmapFromMemCache(fileName);
		//Si es diferente de nulo lo agregamos directamente al ImageView
	    if (bitmap != null) {
	        imageView.setImageBitmap(bitmap);
	    } 
	    //Si es igual a nulo, significa que no esta en memoria y que debemos obtenerlo 
	    else {
	    	//Evaluamos si el procso para obtener el bitmap mediante la clase BitmapWoerkerTask esta
	    	// en ejecucion(retorna false) o no (retorna un true) mediante la funcion 
	    	// cancelPtencialWork, que esta implementada inmediatamente debajo de esta funcion
	    	if (cancelPotentialWork(fileName, imageView)) {
	    		//La clase BitmapWorkerTask es un AsynkTask que realiza proceso de carga del Bitmap en un Thread paralelo
	    		//Ademas hace una referencia debil al ImageView
	    		//La clase esta implementada en el paquete com.vivelabs.android.androidmultimedia.util
	    		int tipe;
	    		if(createCache){
	    			if(cacheUtils==null){
			    		cacheUtils=new CacheUtils(context);	
			    	}
		        	if(cacheUtils.isCacheSource(fileName)){
		        		tipe=BitmapWorkerTask.PATH;
		        	}else{
		        		tipe=BitmapWorkerTask.NET;
		        	}	
	    		}else{
	    			tipe=BitmapWorkerTask.PATH;
	    		}
		    	
	            final BitmapWorkerTask task = new BitmapWorkerTask(context,imageView,memoryCache, tipe, createCache);
	            //AsyncDrawable hereda de BitmapDrawable y hace una referencia debil al BitmapWorkerTask
	            // La referencia debil (WeakReference) permite que el objeto pueda ser destruido por el recolector de basura
	            final AsyncDrawable asyncDrawable =new AsyncDrawable(context.getResources(), null, task);
	            //Colocamos el AsyncDrawable en el ImageView
	            imageView.setImageDrawable(asyncDrawable);
	            //Ejecutamos el BitmapWorkerTask
	            task.execute(fileName);
	        }
        }
    }
	//Funcion para evaluar si el AsincTask BitmapWorkerTask esta en ejecucion
	public static boolean cancelPotentialWork(String data, ImageView imageView) {
        //Obtenemos el BitmapWorkerTask del ImageView 
		final BitmapWorkerTask bitmapWorkerTask = BitmapWorkerTask.getBitmapWorkerTask(imageView);
		
        if (bitmapWorkerTask != null) {
        	//Obtenemos el nombre de la imagen
            final String bitmapData = bitmapWorkerTask.getData();
            //Si el nombre de la imagen es diferente a la actual cancela el anterior proceso
            if (!bitmapData.equals(data)) {
               bitmapWorkerTask.cancel(true);
            } else {
               //El nombre de la imagene es el mismo que el solicitado se retorna false
               //para no generar otro Task
                return false;
            }
        }
        //El ImageView no tienen ningun BitmapWorkerTask asociado o ha sido cancelado
        return true;
    }

	//Retorna el Bitmap en memoria para la llave key, en caso de no existir retorna un nulo
	public Bitmap getBitmapFromMemCache(String key) {
	    return memoryCache.get(key);
	}	
}
