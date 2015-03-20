package sas.buxtar.movil.heroican.vivelabs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

public class CacheUtils {

	public static String PATH_CACHE="";
	private static final String FOLDER_CACHE="CACHE_PROCESSS";
	public static final int MAX_CACHE=100;
	private int size=0;
	private Context context;
	private static boolean isClearCacheAditional=true;
	
	public CacheUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void updatePathCacheSource(){
		if(PATH_CACHE.equals("")){
			//PATH PUBLICO
//			PATH_CACHE=""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+FOLDER_CACHE+"/";
			
			//PATH PRIVADO
			PATH_CACHE=""+context.getDir(CacheUtils.FOLDER_CACHE, Context.MODE_WORLD_WRITEABLE).getPath()+"/";
		}
	}
	
    public boolean isCacheSource(String name) {
    	File file=new File(CacheUtils.PATH_CACHE+name);
		if(file.exists()){
			file=null;
			return true;
		}else{
			file=null;
			return false;
		}
	}
    
	public boolean writeCacheSource(Bitmap bit, String name) {
//		//FOLDER PUBLICO
//		File storageDir = new File(CacheUtils.FOLDER_CACHE);
//		storageDir.mkdirs();
//		File fileImagen= new File(storageDir,name);
		
		//FOLDER PRIVADO
		File fileImagen= new File(CacheUtils.PATH_CACHE+name);
//		
        try {
            FileOutputStream out = new FileOutputStream(fileImagen);
            bit.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
//            storageDir=null;
            out=null;
            fileImagen=null;
            out=null;
            
            //BORRAMOS LOS SOBRANTES
            clearCacheSourceAditional();
//            
//            //NO LO RECICLAMOS EL BITMAP PORQUE VIENE DE LA MEMORIA CACHE
////          bit.recycle();
////          bit=null;
            return true;
        } catch (Exception e) {
        	return false;
        }
	}
	
	private void clearCacheSourceAditional() {
		if(isClearCacheAditional){
			new TaskProcess().execute(new String[]{});	
		}
	}
	
	//ORDENAR EL ARRGLO DE FILES POR FECHA
	private void setOrdeByDate(File[] files, boolean TopMayor) {
		if(TopMayor){
			Arrays.sort( files, new Comparator<File>()
			{
			    public int compare(File o1, File o2) {
			        if (o1.lastModified() > o2.lastModified()) {
			            return -1;
			        } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
			            return +1;
			        } else {
			            return 0;
			        }
			    }
			});
		}else{
			Arrays.sort( files, new Comparator<File>()
			{
			    public int compare(File o1, File o2) {
			        if (o1.lastModified() < o2.lastModified()) {
			            return -1;
			        } else if (((File)o1).lastModified() > ((File)o2).lastModified()) {
			            return +1;
			        } else {
			            return 0;
			        }
			    }
			});
		}
	}
	
	private class TaskProcess extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			int sizeFiles=0;
			isClearCacheAditional=false;
			File directory=new File(CacheUtils.PATH_CACHE);
			if(directory.exists()){
				File[] files = directory.listFiles();
				setOrdeByDate(files, false);
				sizeFiles=files.length;
				if(sizeFiles>MAX_CACHE){
					int countRemove=sizeFiles-MAX_CACHE;
					for (int i = 0; i < countRemove; i++) {
						if(files[i].exists()){
							files[i].delete();
						}
					}
				}
				files=null;	
			}
			directory=null;
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			isClearCacheAditional=true;
		}
		
	}
	
	public ArrayList<String> getFiles(){
		File directory=new File(CacheUtils.PATH_CACHE);
		
		ArrayList<String> lstFiles=new ArrayList<String>();
		File[] files = directory.listFiles();
		//ORDENAMOS DE MENOR A MAYOR
		setOrdeByDate(files, false);
		
		for(int i=0; i < files.length; i++){
			File file = files[i];
			//FECHA DE CREACION
			Date d=new Date(files[i].lastModified());
			String f=""+android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", d);
			lstFiles.add(file.getPath()+"\n"+f);
//			lstFiles.add(file.getName());
	    }
		files=null;
		directory=null;
		return lstFiles;
	}
	
	public ArrayList<String> getAllFiles(String nameDirectory){
		File directory=new File(nameDirectory);
		ArrayList<String> lstFiles=new ArrayList<String>();
		File[] files = directory.listFiles();
		size=files.length;
		for(int i=0; i < size; i++){
			File file = files[i];
			lstFiles.add(file.getPath());
//			lstFiles.add(file.getName());
	    }
		files=null;
		directory=null;
		return lstFiles;
	}
	
	public void clearCacheSource() {
		isClearCacheAditional=true;
		File directory=new File(CacheUtils.PATH_CACHE);
		if(directory.exists()){
			File[] files = directory.listFiles();
			size=files.length;
			for(int i=0; i < size; i++){
				File file = files[i];
				file.delete();
		    }
			files=null;	
		}
		directory=null;
	}
	
	public void clearCacheMemory(ArrayList<String> bitmapCacheNames, LruCache<String,Bitmap> memoryCache) {
		isClearCacheAditional=true;
		
		String isNotInCacheMemory="";
		
		int size=bitmapCacheNames.size();
		for (int i = 0; i < size; i++) {
			if(memoryCache!=null){
				String key=bitmapCacheNames.get(i);
				if(memoryCache.get(key)!=null){
					Bitmap bit=memoryCache.get(key);
					memoryCache.remove(key);
					bit.recycle();
					bit=null;
				}else{
					isNotInCacheMemory+=key+"\n";
				}
			}
		}
		//LIMPIAMOS EL NETWORK_ERROR
		if(memoryCache!=null){
			if(memoryCache.get(ProcessImageV2.networkError)!=null){
				Bitmap bit=memoryCache.get(ProcessImageV2.networkError);
				memoryCache.remove(ProcessImageV2.networkError);
				bit.recycle();
				bit=null;
			}
		}
		
//		Toast.makeText(RegistroFundacionActivity.con, "was Not In Cache Memory!!!\n"+isNotInCacheMemory, 0).show();
//		Toast.makeText(RegistroFundacionActivity.con, "Clear Memory", 0).show();
	}
}



