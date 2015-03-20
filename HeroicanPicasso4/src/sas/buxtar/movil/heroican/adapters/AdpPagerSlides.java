package sas.buxtar.movil.heroican.adapters;

import sas.buxtar.movil.heroican.fragments.FragSlide1;
import sas.buxtar.movil.heroican.fragments.FragSlide2;
import sas.buxtar.movil.heroican.fragments.FragSlide3;
import sas.buxtar.movil.heroican.fragments.FragSlide3.EvtClicEscuadron;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdpPagerSlides extends FragmentPagerAdapter {
	
	private EvtClicEscuadron evt;
	private FragSlide3 slide3;
	private int visiblePay, visibleEscuadron;
	
    public AdpPagerSlides(FragmentManager fm, EvtClicEscuadron evt, FragSlide3 slide3, int visiblePay, int visibleEscuadron) {
        super(fm);
        this.slide3=slide3;
        this.evt=evt;
        this.visibleEscuadron=visibleEscuadron;
        this.visiblePay=visiblePay;
    }
 
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int arg0) {
    	switch (arg0) {
		case 0:
			
			return new FragSlide1();
		case 1:
			
			return new FragSlide2();
		case 2:
			if(slide3==null){
				slide3=FragSlide3.newInstace(evt, visiblePay, visibleEscuadron);
			}else{
				FragSlide3.restoreInstace(slide3, evt);
			}
			return slide3;
		}
    	
    	return null;
    }
 
}
