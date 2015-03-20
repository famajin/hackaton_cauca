package sas.buxtar.movil.heroican.vivelabs;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class AsyncDrawable extends BitmapDrawable {
    //Declaramos un WeakReference para relacionarlo con unBitmapWorkerTask
	private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
	
	//Constructor del AsynDrawable
    public AsyncDrawable(Resources res, Bitmap bitmap,
            BitmapWorkerTask bitmapWorkerTask) {
        super(res, bitmap);
        //Inicializamos el WeakReference realizando una referencia al BitmapWorkerTask
        // que ingresa en el constructor
        bitmapWorkerTaskReference =
            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
    }
    
    //Retorna el BitmapWorkerTask asociado al AsyncDrawable
    public BitmapWorkerTask getBitmapWorkerTask() {
    	return bitmapWorkerTaskReference.get();
    }
}