package sas.buxtar.movil.heroican.pager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


public class SlowScroller extends Scroller{

		private int TIME = 1100;

		public SlowScroller(Context context) {
		    super(context);
		}
		
		public SlowScroller(Context context, Interpolator interpolator) {
		    super(context, interpolator);
		}
		
		@Override      
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {      
		    // Ignore received duration, use fixed one instead           
		    super.startScroll(startX, startY, dx, dy, TIME);       
		    }        
		@Override       
		public void startScroll(int startX, int startY, int dx, int dy) {          
		// Ignore received duration, use fixed one instead          
			super.startScroll(startX, startY, dx, dy, TIME);      
		}  
	
}
