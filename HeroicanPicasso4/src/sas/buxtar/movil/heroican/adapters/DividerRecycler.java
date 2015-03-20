package sas.buxtar.movil.heroican.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class DividerRecycler extends RecyclerView.ItemDecoration {
	
    private int space;
    
    public DividerRecycler(Context context, int spaceDps) {
    	//CONVERT PIXELS IN DP
    	this.space = (int) TypedValue.applyDimension(
    			TypedValue.COMPLEX_UNIT_DIP, spaceDps, 
    	        context.getResources().getDisplayMetrics()
    	);
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
		outRect.bottom = space;
        outRect.top = space;
    }
}
