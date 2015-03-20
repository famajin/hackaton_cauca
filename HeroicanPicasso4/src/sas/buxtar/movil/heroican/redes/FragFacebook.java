package sas.buxtar.movil.heroican.redes;

import java.util.Arrays;

import sas.buxtar.movil.heroican.R;
import sas.buxtar.movil.heroican.redes.FacebookManager.EvtLoginFaceBook;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class FragFacebook extends Fragment{

	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private FacebookManager manager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager=new FacebookManager(getActivity());
		manager.setEvtLoginFaceBook(evtLoginFaceBook);
		manager.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);

		authButton.setReadPermissions(Arrays.asList("email", "user_location", "user_birthday", "user_likes", "user_about_me"));
		return view;
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    manager.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    manager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    manager.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    manager.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    manager.onSaveInstanceState(outState);
	}
	
	private EvtLoginFaceBook evtLoginFaceBook;
	public void setEvtLoginFaceBook(EvtLoginFaceBook evt) {
		this.evtLoginFaceBook=evt;
	}
}
