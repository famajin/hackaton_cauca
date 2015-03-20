package sas.buxtar.movil.heroican.fragments;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;


public class DialogSetTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
				
		final Calendar c = Calendar.getInstance();
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(), this, hourOfDay, minute, false);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		if(evtSetTime!=null){
			String h="", m="", horario="";
			int hd=hourOfDay;
			
			if(hd>12){
				horario="pm";
				hd=hd-12;
			}else{
				horario="am";
			}
			if(hd<10){
				h="0"+hd;
			}else{
				h=""+hd;
			}
			if(minute<10){
				m="0"+minute;
			}else{
				m=""+minute;
			}
			
			evtSetTime.onEvtSetTime(""+hourOfDay+":"+minute, ""+h+":"+m+" "+horario);
			getDialog().cancel();
		}
	}
	
	
	private EvtSetTime evtSetTime;
	public void setEvtSetTime(EvtSetTime evt) {
		this.evtSetTime=evt;
	}
	public interface EvtSetTime{
		public void onEvtSetTime(String time, String finalTime);
	}
    	
}
