package sas.buxtar.movil.heroican.redes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;

public class FacebookManager {
	
	private UiLifecycleHelper uiHelper;
	
	public FacebookManager(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		uiHelper = new UiLifecycleHelper(activity, callback);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		uiHelper.onCreate(savedInstanceState);
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if(evtSessionFaceBook!=null){
			evtSessionFaceBook.onEvtSessionFaceBook(session.isOpened());	
		}
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	        if(evtSessionFaceBook!=null){
	        	evtSessionFaceBook.onEvtSessionFaceBook(session.isOpened());	
	        }
	        
	        if (state.isOpened()) {
	        	
	        	 Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	
	        	        @Override
	        	        public void onCompleted(GraphUser user, Response response) {
	        	            if (user != null && evtLoginFaceBook!=null) {
	        	            	evtLoginFaceBook.onEvtLoginFaceBook(user);
	        	            }
	        	       }
	        	 });
	        }
	    }
	};
	
	public void onResume() {
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    
	    uiHelper.onResume();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	public void onPause() {
	    uiHelper.onPause();
	}
	
	public void onDestroy() {
	    uiHelper.onDestroy();
	}
	
	public void onSaveInstanceState(Bundle outState) {
	    uiHelper.onSaveInstanceState(outState);
	}

	public interface EvtLoginFaceBook{
		public void onEvtLoginFaceBook(GraphUser user);
	}
	private EvtLoginFaceBook evtLoginFaceBook;
	public void setEvtLoginFaceBook(EvtLoginFaceBook evt) {
		this.evtLoginFaceBook=evt;
	}
	
	public interface EvtSessionFaceBook{
		public void onEvtSessionFaceBook(boolean isOpened);
	}
	private EvtSessionFaceBook evtSessionFaceBook;
	public void setEvtSessionFaceBook(EvtSessionFaceBook evt) {
		this.evtSessionFaceBook=evt;
	}
	
	
	/////////////PUBLISH
	
	List<String> permissions;
	
	Session.StatusCallback statusCallback = new SessionStatusCallback();
	
	private void checkSessionAndPost (){
		 
		Session session = Session.getActiveSession();
		session.addCallback(statusCallback);
		Log.d("FbShare", "Session Permissions Are - " + session.getPermissions());
			
		if(session.getPermissions().contains("publish_actions")) {
			publishAction(session);
		} else {
			session.requestNewPublishPermissions(new Session.NewPermissionsRequest(activity, permissions));
		} 
	}
	private Activity activity;
	public void initPublish(Bundle savedInstanceState) {
		permissions = new ArrayList<String>();
		permissions.add("publish_actions");
		Session session = Session.getActiveSession();
		if(session == null) {
			if(savedInstanceState != null) {
				session = Session.restoreSession(activity, null, statusCallback, savedInstanceState);
			}
			if(session == null) {
				session = new Session(activity);
			}
			session.addCallback(statusCallback);
			Session.setActiveSession(session);
		}
	}
	
	public void publish() {
		Session session = Session.getActiveSession();
		if(session != null && session.getState().isOpened()) {
				checkSessionAndPost();
		} else {
			
			Log.d("FbShare", "Session is null");
			session = new Session(activity);
			Session.setActiveSession(session);
			session.addCallback(statusCallback);
			
			Log.d("FbShare", "Session info - " + session);
			try {
				Log.d("FbShare", "Opening session for read");
				session.openForRead(new Session.OpenRequest(activity));
			} catch(UnsupportedOperationException exception) {
				exception.printStackTrace();
				Log.d("FbShare", "Exception Caught");
				Toast.makeText(activity, "Unable to post your score on facebook", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	private void publishAction(Session session) {
		 
		Log.d("FbShare", "Inside publishAction()");
		Bundle postParams = new Bundle();
		postParams.putString("name", "POR HEROICAN");
		postParams.putString("caption", "ESTE ES EL CAPTION");
		postParams.putString("description", "ESTA ES LA DESCRIPCION");
		postParams.putString("link", "http://heroican.com/HeroiCan/images/102_41.jpg");
		postParams.putString("message", "ESTE ES EL MENSAJE");
 
		Request.Callback callback = new Request.Callback() {
 
			@Override
			public void onCompleted(Response response) {
				FacebookRequestError error = response.getError();
				if(error != null) {
					Log.d("FbShare", "Facebook error - " + error.getErrorMessage());
					Log.d("FbShare", "Error code - " + error.getErrorCode());
					Log.d("FbShare", "JSON Response - " + error.getRequestResult());
					Log.d("FbShare", "Error Category - " + error.getCategory());
					Toast.makeText(activity, "Failed to share the post.Please try again", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(activity, "Successfully shared the post", Toast.LENGTH_SHORT).show();
				}
			}
		};
 
		Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, callback);
 
		RequestAsyncTask asyncTask = new RequestAsyncTask(request);
		asyncTask.execute();
	}
	
	private class SessionStatusCallback implements Session.StatusCallback {
 
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			//Check if Session is Opened or not, if open & clicked on share button publish the story
			if(session != null && state.isOpened()) {
				Log.d("FbShare", "Session is opened");
				if(session.getPermissions().contains("publish_actions")) {
					Log.d("FbShare", "Starting share");
					publishAction(session);
				} else {
					Log.d("FbShare", "Session dont have permissions");
					publish();
				}
			} else {
				Log.d("FbShare", "Invalid fb Session");
			}
		}
	}

	public void onStart() {
		Session.getActiveSession().addCallback(statusCallback);
	}
	
	public void onStop() {
		Session.getActiveSession().removeCallback(statusCallback);
	}
	
	public void onSaveStateShare(Bundle outState) {
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}
	
	public void onActivityResultShare(int requestCode, int resultCode, Intent data) {
		Session.getActiveSession().addCallback(statusCallback);
		Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
	}
	
	/////////////////////////SHARE
	public void share(ShareContend shareContend) {
		FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(activity)
        .setLink(shareContend.getLink())
        .setCaption(shareContend.getCaption())
        .setDescription(shareContend.getDescription())
        .build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	
	
}
