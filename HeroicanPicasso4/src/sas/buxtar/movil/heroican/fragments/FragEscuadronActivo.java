package sas.buxtar.movil.heroican.fragments;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.VARS;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragEscuadronActivo extends Fragment implements OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_escuadron_inactive, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		((Button)view.findViewById(R.id.fragEsc_btnOtro)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(evt!=null){
			evt.onEvtRequestOtro();
		}
	}
	
	public static FragEscuadronActivo newInstance(EvtRequestOtro ero) {
		FragEscuadronActivo fragEscuadronActivo=new FragEscuadronActivo();
		fragEscuadronActivo.setEvtRequestOtro(ero);
		return fragEscuadronActivo;
	}
	
	public static FragEscuadronActivo restoreInstance(FragEscuadronActivo fragEscuadronActivo, EvtRequestOtro ero) {
		fragEscuadronActivo.setEvtRequestOtro(ero);
		return fragEscuadronActivo;
	}
	
	private VARS vars;
	public void setVars(VARS vars) {
		this.vars=vars;
	}
	
	public void setEvtRequestOtro(EvtRequestOtro evt) {
		this.evt=evt;
	}
	private EvtRequestOtro evt;
	public interface EvtRequestOtro{
		public void onEvtRequestOtro();
	}
}
