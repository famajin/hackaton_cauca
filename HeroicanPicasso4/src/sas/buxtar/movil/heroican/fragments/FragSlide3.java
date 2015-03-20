package sas.buxtar.movil.heroican.fragments;


import sas.buxtar.movil.heroican.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragSlide3 extends Fragment implements OnClickListener{
	
	private EvtClicEscuadron evt;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.slide_escuadron3, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
//		if(getArguments()!=null){
//			((Button)view.findViewById(R.id.escuadron_btn)).setVisibility(getArguments().getInt("visibleEscuadron"));
//			((Button)view.findViewById(R.id.escuadron_btnpay)).setVisibility(getArguments().getInt("visiblePay"));	
//		}
		
//		((Button)view.findViewById(R.id.escuadron_btn)).setOnClickListener(this);
//		((Button)view.findViewById(R.id.escuadron_btnpay)).setOnClickListener(this);
	}
	
	public static FragSlide3 newInstace(EvtClicEscuadron evt, int visiblePay, int visibleEscuadron) {
		FragSlide3 frag=new FragSlide3();
		Bundle b=new Bundle();
		b.putInt("visiblePay", visiblePay);
		b.putInt("visibleEscuadron", visibleEscuadron);
		frag.setArguments(b);
		frag.setEvtClicEscuadron(evt);
		return frag;
	}
	
	public static void restoreInstace(FragSlide3 frag, EvtClicEscuadron evt) {
		frag.setEvtClicEscuadron(evt);
	}
	
//	public void setVisibilitys(int visiblePay, int visibleEscuadron) {
//		((Button)getView().findViewById(R.id.escuadron_btn)).setVisibility(visibleEscuadron);
//		((Button)getView().findViewById(R.id.escuadron_btnpay)).setVisibility(visiblePay);
//	}
	
	public interface EvtClicEscuadron{
		public void onEvtClicEscuadron(int id);
	}
	public void setEvtClicEscuadron(EvtClicEscuadron evt) {
		this.evt=evt;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(evt!=null){
			evt.onEvtClicEscuadron(v.getId());	
		}
	}
	
}