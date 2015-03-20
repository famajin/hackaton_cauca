package sas.buxtar.movil.heroican.adapters;

import java.util.List;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.ClsCard;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AdpCard extends ArrayAdapter{

	private final Context mContext;
    private boolean animate;
    private OnClickListener listener;
    private List<ClsCard> items;
    private String card_encontrada="", card_perdida="", card_adoptar="";

    public AdpCard(Context context, OnClickListener listener, List<ClsCard> items) {
    	super(context, R.layout.item_card, items);   
        this.items=items;
        this.mContext = context;
        this.listener = listener;
        this.card_adoptar=mContext.getResources().getString(R.string.card_adoptar);
        this.card_encontrada=mContext.getResources().getString(R.string.card_encontrada);
        this.card_perdida=mContext.getResources().getString(R.string.card_perdida);
    }

    @Override
    public int getCount() {
        // Size + number of columns for top empty row
        return items.size();
    }
    
    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null){
        	LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_card, null);
            holder = new ViewHolder();
            holder.container = (LinearLayout) convertView.findViewById(R.id.itemCard_container);
            holder.img = (ImageView)convertView.findViewById(R.id.itemCard_img);
            holder.txtFavorito = (TextView)convertView.findViewById(R.id.itemCard_txtFavorito);
            holder.txtTitulo = (TextView)convertView.findViewById(R.id.itemCard_txtTitulo);
            holder.txtDescripcion = (TextView)convertView.findViewById(R.id.itemCard_txtDescripcion);
            holder.btnFavorito = (ImageView) convertView.findViewById(R.id.itemCard_btnFavorito);
            holder.btnCompartir = (ImageView) convertView.findViewById(R.id.itemCard_btnCompartir);
            holder.icono=(ImageView) convertView.findViewById(R.id.itemCard_icono);
            holder.bar=(LinearLayout) convertView.findViewById(R.id.itemCard_bar);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        ClsCard card = null;
        try {
        	card=items.get(position);	
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        if(card!=null){
        	String concad="";
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
                	Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.card_animation);
                	holder.container.startAnimation(animation);	
				} catch (Exception e){
					// TODO: handle exception
				}
            }
            
            Picasso.with(mContext)
    		.load(GeneralData.SERVER_IMAGES+card.getImageId())
    		.into(holder.img);
        }
        return convertView;
    }
    
    public class ViewHolder{
        ImageView img;
        TextView txtTitulo, txtDescripcion, txtFavorito;
        LinearLayout container, bar;
        ImageView btnCompartir, icono;
        ImageView btnFavorito;
    }
    
    public boolean isAnimate() {
		return animate;
	}


	public void setAnimate(boolean animate) {
		this.animate = animate;
	}
}
