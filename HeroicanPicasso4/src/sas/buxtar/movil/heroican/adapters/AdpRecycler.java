package sas.buxtar.movil.heroican.adapters;

import java.util.List;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.adapters.AdpRecycler.Holder;
import sas.buxtar.movil.heroican.adapters.AdpRecycler.Holder.EvtRecyclerClic;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AdpRecycler extends RecyclerView.Adapter<Holder>{

	private Context context;
	private EvtRecyclerClic evt;
    private OnClickListener listener;
    private boolean animate;
    private List<ClsCard> items;
    private String card_encontrada="", card_perdida="", card_adoptar="";
	
	public AdpRecycler(Context context, OnClickListener listener, List<ClsCard> items, EvtRecyclerClic evt) {
		// TODO Auto-generated constructor stub
		this.items=items;
        this.context = context;
		this.listener = listener;
		this.animate=true;
		this.evt=evt;
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public void onBindViewHolder(Holder holder,int position) {
		// TODO Auto-generated method stub
		
		//SET INFORMATION
		String concad="";
        ClsCard card = null;
        try {
        	card=items.get(position);	
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        if(card!=null){
        	 switch (card.getTipe()) {
     		case GeneralData.MAS_ADOPTAR:
     			concad=card_adoptar;
     			holder.bar.setBackgroundResource(R.color.morado);
     			holder.icono.setImageResource(R.drawable.ic_huesoblanco);
     			break;
     		case GeneralData.MAS_ENCONTRADA:
     			concad=card_encontrada;
     			holder.bar.setBackgroundResource(R.color.verdemanzana);
     			holder.icono.setImageResource(R.drawable.ic_buscadorblanco);
     			break;
     		case GeneralData.MAS_PERDIDA:
     			concad=card_perdida;
     			holder.bar.setBackgroundResource(R.color.rojo);
     			holder.icono.setImageResource(R.drawable.ic_huellablanca);
     			break;
     		case GeneralData.EVENTO:
     			holder.bar.setBackgroundResource(R.color.amarillo);
     			holder.icono.setImageResource(R.drawable.ic_regaloblanco);
     			break;
     		case GeneralData.TESTIMONIO:
     			holder.bar.setBackgroundResource(R.color.rosado);
     			holder.icono.setImageResource(R.drawable.ic_hablandoblanco);
     			break;
     		case GeneralData.TIP:
     			holder.bar.setBackgroundResource(R.color.naranja);
     			holder.icono.setImageResource(R.drawable.ic_tijerasblanco);
     			break;
     		case GeneralData.FUNDACION:
     			holder.bar.setBackgroundResource(R.color.azulclaro);
     			holder.icono.setImageResource(R.drawable.ic_estrellablanca);
     			break;
     		}
             
             holder.txtTitulo.setText(card.getTitle());
             holder.txtDescripcion.setText(card.getDescripcion()+" "+concad);
             holder.txtFavorito.setText(""+card.getFavCount());
             holder.btnFavorito.setEnabled(true);
             
     		if(card.isChecked()){
     			holder.btnFavorito.setImageResource(R.drawable.selector_favorito2);
     		}else{
     			holder.btnFavorito.setImageResource(R.drawable.selector_favorito1);
     		}
     		
            holder.btnFavorito.setOnClickListener(listener);
            holder.btnCompartir.setOnClickListener(listener);
            holder.btnCompartir.setTag(card);
            holder.btnFavorito.setTag(card);
             
            if(animate){
            	try {
                	holder.container.clearAnimation();
                	Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_animation);
                	holder.container.setTag(animation);
                	holder.container.startAnimation(animation);	
				} catch (Exception e){
					// TODO: handle exception
				}
            }
            
            Picasso.with(context)
    		.load(GeneralData.SERVER_IMAGES+card.getImageId())
    		.into(holder.img);
        }
			
        holder.btnCompartir.setTag(card);
        holder.btnFavorito.setTag(card);
        holder.btnAux.setTag(position);
		
	}
	
//	@Override
//	public void onViewRecycled(Holder holder) {
//		// TODO Auto-generated method stub
//		String key=null;
//		boolean fromNet=(Boolean)holder.txtTitulo.getTag();
//		if(fromNet){
//			BitmapWorkerTask task=(BitmapWorkerTask) holder.bar.getTag();
//			if(task!=null){
//				if(!task.isCancelled()){
//					try {
//						task.cancel(true);
//						Log.e("CANCEL TASK RECYCLED:", ""+memoryCache.size());
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}
//			ClsCard card=(ClsCard) holder.btnFavorito.getTag();
//			key=card.getImageId();
//			holder.img.setImageBitmap(null);
//		}
//		if(key!=null){
//			try {
//				if(memoryCache.get(key)!=null){
//					Bitmap bit=memoryCache.get(key);
//					memoryCache.remove(key);
//					bit.recycle();
//					bit=null;
//					Log.e("RECYCLED RECYCLED", "BY PRIORITY");
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//		super.onViewRecycled(holder);
//	}
	
//	@Override
//	public void onViewAttachedToWindow(Holder holder) {
//		// TODO Auto-generated method stub
//		super.onViewAttachedToWindow(holder);
//		try {
//			BitmapWorkerTask task=(BitmapWorkerTask) holder.bar.getTag();
//			boolean fromNet=false;
//			ClsCard card=(ClsCard) holder.btnCompartir.getTag();
//			if(task!=null){
//				if(task.isCancelled() && card!=null){
//					if(processImage==null){
//			        	processImage=new ProcessImageV2(context, memoryCache, true);
//			        }
//		        	if(cacheUtils==null){
//		        		cacheUtils=new CacheUtils(context);	
//		        	}
//		        	if(!cacheUtils.isCacheSource(card.getImageId())){
//		        		fromNet=true;
//		        	}
//		        	holder.txtTitulo.setTag(fromNet);
//			        try {
//			        	task=processImage.loadBitmap(card.getImageId(), holder.img);
//			        	holder.bar.setTag(task);
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}else if(card!=null){
//				if(processImage==null){
//		        	processImage=new ProcessImageV2(context, memoryCache, true);
//		        }
//	        	if(cacheUtils==null){
//	        		cacheUtils=new CacheUtils(context);	
//	        	}
//	        	if(!cacheUtils.isCacheSource(card.getImageId())){
//	        		fromNet=true;
//	        	}
//	        	holder.txtTitulo.setTag(fromNet);
//		        try {
//		        	task=processImage.loadBitmap(card.getImageId(), holder.img);
//		        	holder.bar.setTag(task);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
//	@Override
//	public void onViewAttachedToWindow(Holder holder) {
//		// TODO Auto-generated method stub
//		super.onViewAttachedToWindow(holder);
//		try {
//			BitmapWorkerTask task=(BitmapWorkerTask) holder.bar.getTag();
//			boolean fromNet=false;
//			ClsCard card=(ClsCard) holder.btnCompartir.getTag();
//			if(task!=null){
//				if(task.isCancelled() && card!=null){
//					if(processImage==null){
//			        	processImage=new ProcessImageV2(context, memoryCache, true);
//			        }
//		        	if(cacheUtils==null){
//		        		cacheUtils=new CacheUtils(context);	
//		        	}
//		        	if(!cacheUtils.isCacheSource(card.getImageId())){
//		        		fromNet=true;
//		        	}
//		        	holder.txtTitulo.setTag(fromNet);
//			        try {
//			        	task=processImage.loadBitmap(card.getImageId(), holder.img);
//			        	holder.bar.setTag(task);
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}else if(card!=null){
//				if(processImage==null){
//		        	processImage=new ProcessImageV2(context, memoryCache, true);
//		        }
//	        	if(cacheUtils==null){
//	        		cacheUtils=new CacheUtils(context);	
//	        	}
//	        	if(!cacheUtils.isCacheSource(card.getImageId())){
//	        		fromNet=true;
//	        	}
//	        	holder.txtTitulo.setTag(fromNet);
//		        try {
//		        	task=processImage.loadBitmap(card.getImageId(), holder.img);
//		        	holder.bar.setTag(task);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}

	@Override
	public void onViewDetachedFromWindow(Holder holder) {
		// TODO Auto-generated method stub
		try {
			Animation anim=(Animation) holder.container.getTag();
			anim.cancel();
			holder.container.clearAnimation();	
			anim=null;
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onViewDetachedFromWindow(holder);
	}
	
//	@Override
//	public void onViewDetachedFromWindow(Holder holder) {
//		// TODO Auto-generated method stub
//		try {
//			Animation anim=(Animation) holder.container.getTag();
//			anim.cancel();
//			holder.container.clearAnimation();	
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		String key=null;
//		boolean fromNet=(Boolean)holder.txtTitulo.getTag();
//		if(memoryCache.size()+(memoryCache.maxSize()/2)>memoryCache.maxSize() && fromNet){
//			BitmapWorkerTask task=(BitmapWorkerTask) holder.bar.getTag();
//			if(task!=null){
//				if(!task.isCancelled()){
//					try {
//						task.cancel(true);
//						Log.e("CANCEL TASK DETACHED:", ""+memoryCache.size());
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}
//			ClsCard card=(ClsCard) holder.btnFavorito.getTag();
//			key=card.getImageId();
//			holder.img.setImageBitmap(null);
//		}else if(memoryCache.size()+(memoryCache.maxSize()/4)>memoryCache.maxSize() && !fromNet){
//			BitmapWorkerTask task=(BitmapWorkerTask) holder.bar.getTag();
//			if(task!=null){
//				if(!task.isCancelled()){
//					try {
//						task.cancel(true);
//						Log.e("CANCEL TASK DETACHED:", ""+memoryCache.size());
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			}
//			ClsCard card=(ClsCard) holder.btnFavorito.getTag();
//			key=card.getImageId();
//			holder.img.setImageBitmap(null);
//		}
//		super.onViewDetachedFromWindow(holder);
//		if(key!=null){
//			try {
//				if(memoryCache.get(key)!=null){
//					Bitmap bit=memoryCache.get(key);
//					memoryCache.remove(key);
//					bit.recycle();
//					bit=null;
//					Log.e("RECYCLED DETACHED", "BY PRIORITY");
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//		Log.e("MEMORY:", memoryCache.size()+"  MAX SIZE:"+memoryCache.maxSize());
//	}
	
    public boolean isAnimate() {
		return animate;
	}


	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
		LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_card, viewGroup, false);
		return new Holder(view, evt);
	}
	
	//SI EXISTE EL PARAMETRO LO ELIMINA Y LUEGO LO AGREGA AL FINAL
	//SINO SIMPLEMENTE LO AGREGA
    public void addOrUpdateItem(ClsCard contend) {
        int pos = items.indexOf(contend);
        if (pos >= 0) {
            updateItem(contend, pos);
        } else {
            addItem(contend);
        }
    }
    
    public void delItem(int position){
    	if(position<items.size()){
    		items.remove(position);
            notifyItemRemoved(position);	
    	}
    }
    
    private void updateItem(ClsCard contend, int position) {
    	delItem(position);
        addItem(contend);
    }

    private void addItem(ClsCard contend) {
    	items.add(contend);
        notifyItemInserted(items.size()-1);
    }
	
	public static class Holder extends ViewHolder implements OnClickListener{

        protected ImageView img;
        protected TextView txtTitulo, txtDescripcion, txtFavorito;
        protected LinearLayout container, bar;
        protected Button btnAux;
        protected ImageView btnCompartir, icono, btnFavorito;
	    private EvtRecyclerClic evtRecyclerClic;

	    public Holder(View convertView, EvtRecyclerClic evtRecyclerClic) {
	        super(convertView);
	        this.evtRecyclerClic=evtRecyclerClic;
	        container = (LinearLayout) convertView.findViewById(R.id.itemCard_container);
            img = (ImageView)convertView.findViewById(R.id.itemCard_img);
            txtFavorito = (TextView)convertView.findViewById(R.id.itemCard_txtFavorito);
            txtTitulo = (TextView)convertView.findViewById(R.id.itemCard_txtTitulo);
            txtDescripcion = (TextView)convertView.findViewById(R.id.itemCard_txtDescripcion);
            btnFavorito = (ImageView) convertView.findViewById(R.id.itemCard_btnFavorito);
            btnCompartir = (ImageView) convertView.findViewById(R.id.itemCard_btnCompartir);
            icono=(ImageView) convertView.findViewById(R.id.itemCard_icono);
            bar=(LinearLayout) convertView.findViewById(R.id.itemCard_bar);
            btnAux=(Button) convertView.findViewById(R.id.itemCard_btnAux);
            btnAux.setOnClickListener(this);
	    }

		@Override
		public void onClick(View v) {
			if(evtRecyclerClic!=null){
				evtRecyclerClic.onEvtRecyclerClic((View) v.getParent().getParent(), (Integer)v.getTag());	
			}
		}
		
	    public interface EvtRecyclerClic {
	        public void onEvtRecyclerClic(View recyclerItem, int position);
	    }
	}
}
