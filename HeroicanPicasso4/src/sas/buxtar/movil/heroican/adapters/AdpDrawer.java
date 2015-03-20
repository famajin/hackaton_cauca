package sas.buxtar.movil.heroican.adapters;
//package org.cite.heroican.adapters;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
//import java.util.ArrayList;
//
//import org.cite.heroican.R;
//import org.cite.heroican.fragments.FragDrawer.Sel;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class AdpDrawer extends ArrayAdapter{
//
//	private Container aux;
//	private ArrayList<Sel> fuente;
//	
//	public AdpDrawer(Context context, ArrayList<Sel> fuente) {
//		super(context, R.layout.item_drawer, fuente);
//		// TODO Auto-generated constructor stub
//		this.fuente=fuente;
//	}
//	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		if(convertView==null){
//			convertView=View.inflate(getContext(), R.layout.item_drawer, null);
//			aux=new Container();
//			aux.icono=(ImageView) convertView.findViewById(R.id.itemDrawer_icono);
//			aux.txt=(TextView) convertView.findViewById(R.id.itemDrawer_txt);
////			aux.container=(ViewGroup) convertView.findViewById(R.id.itemDrawer_container);
//			convertView.setTag(aux);
//		}else{
//			aux=(Container) convertView.getTag();
//		}
//		aux.txt.setText(fuente.get(position).text);
//		if(fuente.get(position).isSel){
//			aux.container.setBackgroundResource(R.color.pressDrawer);
//			aux.txt.setTextColor(Color.parseColor("#FFFFFF"));
//			aux.icono.setImageResource(fuente.get(position).press);
//		}else{
//			aux.icono.setImageResource(fuente.get(position).normal);
//			aux.txt.setTextColor(Color.parseColor("#000000"));
//			if(position%2==0){
//				aux.container.setBackgroundResource(R.color.blanco);
//			}else{
//				aux.container.setBackgroundResource(R.color.gris);
//			}
//		}
//		
//		
//		
//		return convertView;
//	}
//	
//	static class Container{
//		ImageView icono;
//		TextView txt;
//		ViewGroup container;
//	}
//
//}
