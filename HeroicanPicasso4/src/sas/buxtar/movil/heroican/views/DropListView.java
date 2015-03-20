package sas.buxtar.movil.heroican.views;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.clases.VARS;
import sas.buxtar.movil.heroican.process.ImageFetcher;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class DropListView extends RecyclerView{

	private float rawY, posActual, posInicial, limiteDrop;
	private int scrolly=0, heightDetail, restore=TOP, porcentajePivot=-1;
	private static int BOTTOM=1, TOP=-1;
	private double PORCENTAJE_PASS=5;
	private ViewGroup container;
	private int TIMERESTORE=2000;
	private boolean drop=false, set=false;
	private TranslateAnimation animToTop, animToBottom;
	private Animation animPivotTop=null;
	public boolean wasDrop=false;
	private boolean enabledDrop;
	
//	private final GestureDetector detector = new GestureDetector(new MyGestureDetector());
	
	public boolean isEnabledDrop() {
		return enabledDrop;
	}

	public void setEnabledDrop(boolean enabled) {
		this.enabledDrop = enabled;
		if(!enabledDrop){
			wasDrop=true;
			heightDetail=0;
		}
	}

	public DropListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public DropListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public DropListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		enabledDrop=true;
		//LE DAMOS VIDA MIENTRAS LO INICIALIZAMOS
		vars=new VARS();
		setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(RecyclerView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState==RecyclerView.SCROLL_STATE_IDLE){
					if(scrolly==0 && container.getY()==-heightDetail){
						hilador();
					}
				}
			}
		});
	}
	
	public interface EvtMoveScroll{
		void onEvtMoveScroll();
	}
	
	public void setEvtMoveScroll(EvtMoveScroll evt) {
		this.evtMoveScroll=evt;
	}
	
	private EvtMoveScroll evtMoveScroll=null;
	
	private int saveHeight=-1;
	public void setParams(int heightDetail) {
		if(saveHeight==-1 && heightDetail!=0){
			this.saveHeight=heightDetail;	
		}
		if(enabledDrop){
			this.heightDetail=saveHeight;
		}
	}
	
	public void setContainer_(ViewGroup container) {
		this.container=container;
	}
	
	private boolean moved=false;
	
	
	private VARS vars;
	public void setVars(VARS vars) {
		this.vars=vars;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(vars.size>0){
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				moved=false;
				//INNECESARIO
				try {
					this.lastSize=vars.size;
					vars.h_item=getResources().getDimensionPixelSize(R.dimen.H_card);
			    	vars.h_items=vars.size*(vars.h_item);
					heightTotal = vars.h_items-getHeight();
				
				} catch (Exception e) {
					// TODO: handle exception
				}
				//INNECESARIO
				
				if(porcentajePivot==-1){
					if(container!=null){
						porcentajePivot=(container.getHeight()*4)/100;
						limiteDrop=(float) (container.getHeight()/PORCENTAJE_PASS);	
					}
				}
				posInicial=container.getY();
				rawY=ev.getRawY();
				break;
	
			case MotionEvent.ACTION_MOVE:
				
				if(evtMoveScroll!=null){
					evtMoveScroll.onEvtMoveScroll();
				}
				
				moved=true;
				posActual=posInicial+(ev.getRawY()-rawY);
				
				if(evtDetail!=null){
					if(container.getY()==-heightDetail){
						if(showDetail){
							showDetail=false;
							evtDetail.onEvtShowDetail(showDetail);
						}
					}else{
						if(!showDetail){
							showDetail=true;
							evtDetail.onEvtShowDetail(showDetail);
						}
					}	
				}
				
				if(scrolly==0 && (ev.getRawY()-rawY)>0){
					if(drop==false){
						posInicial=container.getY();
						rawY=ev.getRawY();
						posActual=posInicial+(ev.getRawY()-rawY);
						set=false;
					}
					container.setY(posActual);
					drop=true;
					restore=TOP;
					wasDrop=false;
					return true;
				}else if(scrolly==0 && container.getY()>-heightDetail && (ev.getRawY()-rawY)<0){
					updatePostion();
					drop=false;
					restore=BOTTOM;
					wasDrop=false;
					return true;
				}else if(scrolly==0 && !set){
					set=true;
					posActual=-heightDetail;
					updatePostion();
					rawY=ev.getRawY();
					posInicial=container.getY();
				}else {
					drop=false;
					posActual=container.getY();
				}
				wasDrop=true;
				break;
				
			case MotionEvent.ACTION_UP:
				posActual=container.getY();
				set=false;
				if(container.getY()>0){
					if(posActual>limiteDrop){
						update=true;
					}
					restoreTop();
				}else if(!moved){
					wasDrop=true;
				}
				drop=false;
				moved=false;
				break;
			}
		}
		return super.onTouchEvent(ev);
	}
	
	private boolean showDetail=false;
	
	private boolean update=false;
	
	private EvtDrop evtDrop;
	public interface EvtDrop{
		public void OnEvtDrop();
	}
	
	public void setEvtDrop(EvtDrop evt) {
		evtDrop=evt;
	}
	
	private Thread thread;
	private void hilador(){
		thread =new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(TIMERESTORE);
				} catch (Exception e) {
					// TODO: handle exception
				}
				handler.post(runnable);	
			}
		};
		thread.start();
	}
	
	final Handler handler =new Handler();
	final Runnable runnable= new Runnable() {
		public void run() {
			if(scrolly==0 && container.getY()==-heightDetail){
				restoreBottom();
			}
			thread=null;
		}
	};
	
	private void updatePostion() {
		container.setY(posActual);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if(vars.size>0){
			scrolly =  computeVerticalScrollOffset();
			try {
				if(posActual!=-heightDetail && scrolly>0){
					posActual=-heightDetail;
					updatePostion();
				}
				if(vars.size!=lastSize){
					this.lastSize=vars.size;
					vars.h_item=getResources().getDimensionPixelSize(R.dimen.H_card);
					vars.h_items=vars.size*(vars.h_item);
					heightTotal = vars.h_items-getHeight();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
	        ratio = (float) Math.min(Math.max(computeVerticalScrollOffset(), 0), heightTotal) / heightTotal;
	        newAlpha = (int) (ratio * 100);
	        
	    	if(newAlpha>=99 && evtEndListView!=null){
	    		if(!isTheEnd){
	    			if(evtEndListView!=null){
	    				isTheEnd=true;
	    				evtEndListView.onEvtEndListView(isTheEnd);	
	    			}
	    		}
	    	}else{
	    		if(isTheEnd){
	    			if(evtEndListView!=null){
	    				isTheEnd=false;
	    				evtEndListView.onEvtEndListView(isTheEnd);	
	    			}
	    		}
	    	}	
		}

	}
	
	private int newAlpha=-1, heightTotal=-1, lastSize=0;
	private float ratio;
	private boolean isTheEnd=false;
	
	public void restoreTop() {
		wasDrop=false;
		float to=posActual-posInicial+porcentajePivot;
		
		container.clearAnimation();	
		animPivotTop=AnimationUtils.loadAnimation(getContext(), R.anim.dial_pivotbottom);
		animToTop=new TranslateAnimation(0, 0, 0, -to);
		animToTop.setFillAfter(true);
		animToTop.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
		animToTop.setAnimationListener(listenerRestore);

		container.startAnimation(animToTop);
		posActual=0;
		
	}
	
	public void restoreBottom() {
		////////////////////////MODIFICATED
		if(scrolly==0){
			animToBottom = new TranslateAnimation(0, 0, -heightDetail, 0);
			animToBottom.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
		    container.clearAnimation();
		    container.startAnimation(animToBottom);
		    container.setY(0);
		    posActual=0;	
		}
	}

	private final AnimationListener listenerRestore = new AnimationListener() {
		
		@SuppressLint("NewApi")
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			container.setY(0);
			container.clearAnimation();
			if(restore==TOP){
				if(animPivotTop!=null){
					container.startAnimation(animPivotTop);	
				}else{
					Toast.makeText(getContext(), "NULLL", 0).show();
				}
			}
			if(update){
				evtDrop.OnEvtDrop();
				update=false;
			}
			wasDrop=true;
//			container.setY(0);
//			container.clearAnimation();
//			if(restore==TOP){
//				container.startAnimation(animPivotTop);	
//			}
//			if(update){
//				evtDrop.OnEvtDrop();
//				update=false;
//			}
//			wasDrop=true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	};

//	@SuppressLint("NewApi")
//    class MyGestureDetector extends SimpleOnGestureListener {
//		@Override
//		public boolean onDown(MotionEvent e) {
//			// TODO Auto-generated method stub
//			Toast.makeText(getContext(), "DOWN", 0).show();
//			return true;
//		}
////        @Override
////        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//////        	MainActivity.txt.setText("vel"+velocityY+"\n"+e1.getRawY());
////        	
////            return false;
////        }
////        
////        @Override
////        public boolean onScroll(MotionEvent ev1, MotionEvent ev,
////        		float distanceX, float distanceY) {
////        	// TODO Auto-generated method stub
//////        	MainActivity.txt.setText("DISTANCE"+distanceY);
//////    		stop=true;
//////        	long velocity = Math.abs((int) velocityY) / 10;
//////        	velocity = (long) Math.min(velocity, 200);
////        	return super.onScroll(ev1, ev, distanceX, distanceY);
////        }
//    }
	
    @Override
    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }
    
    public interface EvtEndListView{
		void onEvtEndListView(boolean is);
	}
    private EvtEndListView evtEndListView;
    
    public void setEvtEndListView(EvtEndListView evt) {
		this.evtEndListView=evt;
	}
    
    private EvtShowDetail evtDetail;
    public interface EvtShowDetail{
		void onEvtShowDetail(boolean is);
	}
    public void setEvtShowDetail(EvtShowDetail evt) {
		this.evtDetail=evt;
	}
}
