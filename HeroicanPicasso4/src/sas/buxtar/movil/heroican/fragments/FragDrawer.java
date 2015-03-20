package sas.buxtar.movil.heroican.fragments;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.VARS;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragDrawer extends Fragment implements OnClickListener{

	private String selected;
	private VARS vars;
	
	public static class Sel{
		public int press, normal;
 	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_drawer, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		((Button)view.findViewById(R.id.drawer_btnAdopta)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnCerrarSesion)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnMascotaEncontrada)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnEscuadron)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnEventos)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnFundaciones)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnInicio)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnMasEncontradas)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnMasPerdidas)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnSubirAdopcion)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnSubirEvento)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnSubirTestimonio)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnSubirTip)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnTestimonios)).setOnClickListener(this);
		((Button)view.findViewById(R.id.drawer_btnTips)).setOnClickListener(this);
		
		((Button)view.findViewById(R.id.drawer_btnUs)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(evtDrawerUs!=null){
					evtDrawerUs.OnDrawerUs();
				}
			}
		});
		
		if(vars!=null){
			if(vars.selected!=-1){
				setSelection(vars.selected, false);	
			}	
		}
	}
	
	public void setVars(VARS vars) {
		this.vars=vars;
	}
	
	public interface EvtDrawer{
		public void OnDrawerSelection(String title, int pos);
	}
	public void setEvtDrawer(EvtDrawer evt) {
		this.evtDrawer=evt;
	}
	private EvtDrawer evtDrawer;
	
	public interface EvtDrawerUs{
		public void OnDrawerUs();
	}
	public void setEvtDrawerUs(EvtDrawerUs evt) {
		this.evtDrawerUs=evt;
	}
	private EvtDrawerUs evtDrawerUs;

	private Button btnAux=null;
	private ImageView imgAux=null;
	private TextView txtAux=null;
	private Sel selAux=null;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		selection(v, true);
	}
	
	public void setSelection(int id, boolean call) {
		selection(findView(id), call);	
	}
	
	private View findView(int id) {
		return (getView().findViewById(id)); 
	}
	
	private void selection(View v, boolean call) {
		if(btnAux!=v){
			if(btnAux!=null){
				btnAux.setBackgroundResource(R.drawable.seldrawer_default);
			}
			
			switch (v.getId()) {
			case R.id.drawer_btnAdopta:
				
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
//					btnAux.setBackgroundResource(R.drawable.seldrawer_adopta);
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;

				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Adopta);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icAdopta);
				selAux.normal=R.drawable.ic_hueso;
				selAux.press=R.drawable.ic_huesoblanco;
				
				btnAux.setBackgroundResource(R.color.morado);
				break;
			case R.id.drawer_btnMascotaEncontrada:				
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
//					btnAux.setBackgroundResource(R.drawable.seldrawer_encontradas);
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_EncontreUno);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icEncontreUno);
				selAux.normal=R.drawable.ic_camamarilla;
				selAux.press=R.drawable.ic_camarablanca;
				
				btnAux.setBackgroundResource(R.color.verdemanzana);
				
				break;
			case R.id.drawer_btnEscuadron:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Escuadron);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icEscuadron);
				selAux.normal=R.drawable.ic_lupaa;
				selAux.press=R.drawable.ic_lupablanca;
				
				btnAux.setBackgroundResource(R.color.rojo);

				break;
			case R.id.drawer_btnEventos:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Eventos);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icEventos);
				selAux.normal=R.drawable.ic_regalo;
				selAux.press=R.drawable.ic_regaloblanco;
				
				btnAux.setBackgroundResource(R.color.amarillo);

				break;
			case R.id.drawer_btnFundaciones:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Fundaciones);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icFundaciones);
				selAux.normal=R.drawable.ic_estrella;
				selAux.press=R.drawable.ic_estrellablanca;
				
				btnAux.setBackgroundResource(R.color.azulclaro);
				break;
			case R.id.drawer_btnInicio:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Inicio);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icInicio);
				selAux.normal=R.drawable.ic_casa;
				selAux.press=R.drawable.ic_casablanca;
				
				btnAux.setBackgroundResource(R.color.pressDrawer);
				break;
			case R.id.drawer_btnMasEncontradas:				
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_masEncontradas);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icMasEncontradas);
				selAux.normal=R.drawable.ic_buscador;
				selAux.press=R.drawable.ic_buscadorblanco;
				
				btnAux.setBackgroundResource(R.color.verdemanzana);

				break;
			case R.id.drawer_btnMasPerdidas:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_masPerdidas);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icMasPerdidas);
				selAux.normal=R.drawable.ic_huella;
				selAux.press=R.drawable.ic_huellablanca;
				
				btnAux.setBackgroundResource(R.color.rojo);

				break;
			case R.id.drawer_btnSubirAdopcion:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_SubirAdopcion);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icSubirAdopcion);
				selAux.normal=R.drawable.ic_publicaradopcion;
				selAux.press=R.drawable.ic_subircolor;
				
				btnAux.setBackgroundResource(R.color.morado);
				
				break;
			case R.id.drawer_btnSubirEvento:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;

				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_SubirEvento);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icSubirEvento);
				selAux.normal=R.drawable.ic_publicarevento;
				selAux.press=R.drawable.ic_subircolor;
				
				btnAux.setBackgroundResource(R.color.amarillo);
				
				break;
			case R.id.drawer_btnSubirTestimonio:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;

				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_SubirTestimonio);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icSubirTestimonio);
				selAux.normal=R.drawable.ic_publicartestimonio;
				selAux.press=R.drawable.ic_subircolor;
				
				btnAux.setBackgroundResource(R.color.rosado);
				
				break;
			case R.id.drawer_btnSubirTip:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;

				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_SubirTip);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icSubirTip);
				selAux.normal=R.drawable.ic_publicartip;
				selAux.press=R.drawable.ic_subircolor;
				
				btnAux.setBackgroundResource(R.color.naranja);
				
				break;
			case R.id.drawer_btnTips:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;

				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Tips);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icTips);
				selAux.normal=R.drawable.ic_tijeras;
				selAux.press=R.drawable.ic_tijerasblanco;
				
				btnAux.setBackgroundResource(R.color.naranja);
				
				break;
			case R.id.drawer_btnTestimonios:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_Testimonios);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icTestimonios);
				selAux.normal=R.drawable.ic_hablando;
				selAux.press=R.drawable.ic_hablandoblanco;
				
				btnAux.setBackgroundResource(R.color.rosado);

				break;
			case R.id.drawer_btnCerrarSesion:
				if(selAux!=null){
					imgAux.setImageResource(selAux.normal);
					txtAux.setTextColor(Color.parseColor("#000000"));
				}else{
					selAux=new Sel();
				}
				
				btnAux=(Button) v;
				
				txtAux=(TextView) getView().findViewById(R.id.itemDrawer_CerrarSesion);
				imgAux=(ImageView) getView().findViewById(R.id.itemDrawer_icCerrarSesion);
				selAux.normal=R.drawable.ic_camamarilla;
				selAux.press=R.drawable.ic_camarablanca;
				
				btnAux.setBackgroundResource(R.color.pressDrawer);
				break;
			}
			
			imgAux.setImageResource(selAux.press);
			txtAux.setTextColor(Color.parseColor("#FFFFFF"));
			selected=txtAux.getText().toString();
		}
		if(call){
			evtDrawer.OnDrawerSelection(selected, v.getId());	
		}
	}
}
