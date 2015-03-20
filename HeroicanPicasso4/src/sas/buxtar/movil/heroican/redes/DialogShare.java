package sas.buxtar.movil.heroican.redes;

import sas.buxtar.movil.heroican.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class DialogShare extends DialogFragment implements OnClickListener{

	private FacebookManager facebookManager;
	private ShareContend shareContend;
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.dialog_share, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onActivityCreated(arg0);
		facebookManager=new FacebookManager(getActivity());
		facebookManager.onCreate(arg0);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initDialog();
		if(getArguments()!=null){
			if(getArguments().containsKey("shareContend")){
				shareContend=(ShareContend) getArguments().getParcelable("shareContend");
			}
		}
		if(shareContend==null){
			shareContend=new ShareContend("", "", "", "", "");
		}
		((Button)view.findViewById(R.id.dialShare_btnFacebook)).setOnClickListener(this);
		((Button)view.findViewById(R.id.dialShare_btnOthers)).setOnClickListener(this);
	}
	
	
	private void initDialog() {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		facebookManager.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		facebookManager.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		facebookManager.onPause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		facebookManager.onDestroy();
		super.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle arg0) {
		// TODO Auto-generated method stub
		facebookManager.onSaveInstanceState(arg0);
		super.onSaveInstanceState(arg0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.dialShare_btnFacebook){
			facebookManager.share(shareContend);
		}else{
			shareContend.prepareShareOthers();
			ShareUtil.share(getActivity(), shareContend);
		}
		try {
			getDialog().cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
