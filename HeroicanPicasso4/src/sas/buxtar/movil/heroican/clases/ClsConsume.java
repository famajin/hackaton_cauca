package sas.buxtar.movil.heroican.clases;

import sas.buxtar.movil.heroican.interfaces.EvtEndInsert;
import sas.buxtar.movil.heroican.util.GeneralData;
import sas.buxtar.movil.heroican.util.httpHandlerJSON;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.EvtResultDML;
import sas.buxtar.movil.heroican.util.httpHandlerJSON.Send;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class ClsConsume implements EvtResultDML{
	
	private Send[] arraySend=null;
	private httpHandlerJSON handler=null;
	private String urlMethod="Consume";
	private BillingProcessor bp;
	private boolean readyConsume;

	public void consume() {
		if(arraySend==null){
			arraySend=new Send[1];	
		}
		for (int i = 0; i < 1; i++) {
			arraySend[i]=new Send();
		}
		arraySend[0].name="usu_id";
		arraySend[0].value=""+GeneralData.USU_ID;

		handler =new httpHandlerJSON(GeneralData.SERVERCONTROLLER+urlMethod, httpHandlerJSON.DML);
		handler.setEvtResult(this);
		handler.execute(arraySend);
	}

	@Override
	public void OnResultDML(String canDo, Object tag) {
		// TODO Auto-generated method stub
		handler=null;
		if(evtEndInsert!=null){
			if(canDo!=null){
				evtEndInsert.OnEndInsert(canDo);
			}else{
				evtEndInsert.OnEndInsert(null);
			}	
		}
	}

	@Override
	public void OnResultDDL(String jsons, Object sourceJson) {
		// TODO Auto-generated method stub
		
	}
	
	private EvtEndInsert evtEndInsert;
	
	public void setEvtEndInsert(EvtEndInsert evt) {
		this.evtEndInsert=evt;
	}
	
	public void pay(Activity context) {
		if(bp!=null){
			bp.purchase(context, GeneralData.ESCUADRON_PRODUCT_ID);	
		}
	}
	
	public void initProcessorBilling(Context context) {
		readyConsume=false;
        bp = new BillingProcessor(context, GeneralData.MERCHANT_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
//            	Toast.makeText(context, "COMPRADO", 0).show();
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
//            	Toast.makeText(getApplicationContext(), "numer error:"+errorCode+"\nerror"+error.toString(), 0).show();
            }
            @Override
            public void onBillingInitialized() {
                readyConsume=true;
            }
            @Override
            public void onPurchaseHistoryRestored() {
                
                for(String sku : bp.listOwnedProducts())
                    Log.d("", "Owned Managed Product: " + sku);
                for(String sku : bp.listOwnedSubscriptions())
                    Log.d("", "Owned Subscription: " + sku);
                
            }
        });
	}
	
	public boolean isReadyConsume() {
		return readyConsume;
	}

	public void setReadyConsume(boolean readyConsume) {
		this.readyConsume = readyConsume;
	}
	
	public void relase() {
		// TODO Auto-generated method stub
        if (bp != null){
            bp.release();
        }
	}

}
