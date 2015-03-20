package sas.buxtar.movil.heroican.views;


import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.util.GeneralData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.Toast;

public class ScrollFace extends ScrollView{

	private float rawY, posActual, posInicial, limiteToBottom;
	private ViewGroup container, contend;
	private TranslateAnimation animToTop, animToBottom;
	private Animation animPivotTop=null, animPivotBottom=null;
	private boolean drop;
	private int porcentajePivot;
	private double PORCENTAJE_PASS=4;
	public static int TOP=0, BOTTOM=1;
	int restore=TOP;
    boolean dropp;
    
    int scrolly=0, heightTotal=0;
    float ratio;
	
	public ScrollFace(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ScrollFace(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ScrollFace(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setContainers(ViewGroup container, ViewGroup contenido) {
		this.container=container;
		this.contend=contenido;
		hacia_abajo=false;
		hacia_arriba=false;
		manual=false;
		drop=false;
		dropp=false;
		porcentajePivot=-1;
	}
	
	boolean manual;
	private boolean hacia_abajo, hacia_arriba;
	
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(isEnabled()){
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(porcentajePivot==-1){
					if(GeneralData.marginY==0){
						if(getResources().getBoolean(R.bool.isTablet)){
							int h_screen=getResources().getDisplayMetrics().heightPixels;
							GeneralData.marginY=(h_screen*16)/100;
						}else{
							GeneralData.marginY=getResources().getDimensionPixelSize(R.dimen.margin_dialog_top);
						}
					}
					int h_ner=container.getHeight()-(2*GeneralData.marginY);
					int h_do=contend.getHeight();
					
					animPivotTop=AnimationUtils.loadAnimation(getContext(), R.anim.dial_pivotbottom);
					animPivotBottom=AnimationUtils.loadAnimation(getContext(), R.anim.dial_pivottop);
					porcentajePivot=(container.getHeight()*4)/100;
					limiteToBottom=(float) (h_ner/PORCENTAJE_PASS);
					heightTotal = (h_do - h_ner)+getContext().getResources().getDimensionPixelSize(R.dimen.padding_dialog);
					
					if(h_ner>h_do){
						manual=true;
					}else{
						manual=false;
					}
				}
				posInicial=container.getY();
				rawY=ev.getRawY();
				
				if(scrolly==0){
					hacia_abajo=true;
				}else{
					hacia_abajo=false;
				}
				if(scrolly==255){
					hacia_arriba=true;
				}else{
					hacia_arriba=false;
				}
				break;

			case MotionEvent.ACTION_MOVE:
				posActual=posInicial+(ev.getRawY()-rawY);
				if(manual){
					if((ev.getRawY()-rawY)>0){
						if(drop==false){
							posInicial=container.getY();
							rawY=ev.getRawY();
							posActual=posInicial+(ev.getRawY()-rawY);
						}
						container.setY(posActual);
						drop=true;
						restore=TOP;
						return true;
					}else{
						if(drop==false){
							posInicial=container.getY();
							rawY=ev.getRawY();
							posActual=posInicial+(ev.getRawY()-rawY);
						}
						container.setY(posActual);
						drop=true;
						restore=BOTTOM;
						return true;
					}
				}else{
					if(scrolly!=0){
						hacia_abajo=false;
					}
					if(scrolly!=255){
						hacia_arriba=false;
					}
					if(hacia_abajo && (ev.getRawY()-rawY)>0){
						if(drop==false){
							posInicial=container.getY();
							rawY=ev.getRawY();
							posActual=posInicial+(ev.getRawY()-rawY);
						}
						container.setY(posActual);
						drop=true;
						restore=TOP;
						return true;
					}else if(hacia_arriba && (ev.getRawY()-rawY)<0){
						if(drop==false){
							posInicial=container.getY();
							rawY=ev.getRawY();
							posActual=posInicial+(ev.getRawY()-rawY);
						}
						container.setY(posActual);
						drop=true;
						restore=BOTTOM;
						return true;
					}else {
						drop=false;
						posActual=container.getY();
					}		
				}
				break;
				
			case MotionEvent.ACTION_UP:
				if(drop || container.getY()!=0){
					//PARA QUE TOME LOS 2 LIMITES
					if(posActual<0){
						posActual*=-1;
					}
					if(posActual>limiteToBottom){
//							isInCancel=true;
						setEnabled(false);
						evtCloseDialog.OnCloseDialog(restore);
					}else{		
						if(restore==TOP){
							restoreTop();
						}else {
							restoreBottom();
						}
					}
				}
				drop=false;
				hacia_abajo=false;
				hacia_arriba=false;
				break;
			}
			return super.onTouchEvent(ev);
		}else{
			posActual=0;
			posInicial=0;
			scrolly=0;
			hacia_abajo=true;
			return true;
		}
	}
	
	private void restoreTop() {
		float to=posActual-posInicial+porcentajePivot;
		
		container.clearAnimation();					
		animToTop=new TranslateAnimation(0, 0, 0, -to);
		animToTop.setFillAfter(true);
		animToTop.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
		animToTop.setAnimationListener(listenerRestore);
		
		container.startAnimation(animToTop);
	}
	
	private void restoreBottom() {
		float to=posInicial-posActual-porcentajePivot;
		container.clearAnimation();					
		animToBottom=new TranslateAnimation(0, 0, 0, -to);
		animToBottom.setFillAfter(true);
		animToBottom.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
		animToBottom.setAnimationListener(listenerRestore);
		
		container.startAnimation(animToBottom);
	}

	AnimationListener listenerRestore = new AnimationListener() {
		
		@SuppressLint("NewApi")
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			
			container.setY(0);
			container.clearAnimation();
			if(restore==TOP){
				container.startAnimation(animPivotTop);	
			}else{
				container.startAnimation(animPivotBottom);
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	};
	
	public interface EvtCloseDialog{
		public void OnCloseDialog(int restoreTo);
	}
	
	public void setEvtCloseDialog(EvtCloseDialog evt) {
		this.evtCloseDialog=evt;
	}
	private EvtCloseDialog evtCloseDialog;
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(isEnabled()){
        	ratio = (float) Math.min(Math.max(t, 0), heightTotal) / heightTotal;
            scrolly = (int) (ratio * 255);
            if(scrolly==0){
            	restore=TOP;
            }else if(scrolly==255){
            	restore=BOTTOM;
    		}	
        }
    }
}
