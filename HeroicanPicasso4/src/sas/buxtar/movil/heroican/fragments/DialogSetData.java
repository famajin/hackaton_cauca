package sas.buxtar.movil.heroican.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;


public class DialogSetData extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	int a=0;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
				
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		a=0;
		return new DatePickerDialog(getActivity(),this, year, month, day);
	}
	
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		if(evtSetData!=null){
			if(a==0){
				month= month+1;
				String date=""+year+"-"+month+"-"+day;
				evtSetData.onEvtSetData(date);	
			}
		}
		a++;
		getDialog().cancel();
	}
	
	private EvtSetData evtSetData;
	public void setEvtSetFecha(EvtSetData evt) {
		this.evtSetData=evt;
	}
	public interface EvtSetData{
		public void onEvtSetData(String fecha);
	}
    	
}
