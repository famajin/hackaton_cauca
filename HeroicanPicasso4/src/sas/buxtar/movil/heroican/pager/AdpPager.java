package sas.buxtar.movil.heroican.pager;

import java.util.ArrayList;
import java.util.List;

import sas.buxtar.movil.heroican.fragments.FragHome.InfoPagina;
import sas.buxtar.movil.heroican.interfaces.EvtRequestDialog;
import sas.buxtar.movil.heroican.interfaces.EvtRequestSelection;
import sas.buxtar.movil.heroican.interfaces.EvtRequestTutorial;
import sas.buxtar.movil.heroican.process.ImageFetcher;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View.OnTouchListener;


public class AdpPager extends FragmentPagerAdapter {
	
	private List<FragItemPager> fragments;
	private EvtRequestSelection evtRequestSelection;
	private EvtRequestTutorial evtRequestTutorial;
	private EvtRequestDialog evtRequestDialog;
	private OnTouchListener touch;
	
    public AdpPager(FragmentManager fm, ArrayList<InfoPagina> info, List<FragItemPager> fragments, EvtRequestSelection evtRequestSelection, EvtRequestDialog evtRequestDialog, EvtRequestTutorial evtRequestTutorial, OnTouchListener touch) {
        super(fm);
        this.fragments=fragments;
        this.info=info;
        this.touch=touch;
        this.evtRequestDialog=evtRequestDialog;
        this.evtRequestSelection=evtRequestSelection;
        this.evtRequestTutorial=evtRequestTutorial;
    }
    
	private ArrayList<InfoPagina> info;
 
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Fragment getItem(int arg0) {
//    	if(fragments.get(arg0)==null){
    	fragments.set(arg0, FragItemPager.newInstance(info.get(arg0), this.evtRequestSelection, this.evtRequestDialog, this.evtRequestTutorial, touch));
//    	}else{
//    		FragItemPager.restoreInstance(fragments.get(arg0),this.evtRequestSelection, this.evtRequestDialog, this.evtRequestTutorial, touch, mImageFetcher);
//    	}
        return this.fragments.get(arg0);
    }
 
}
