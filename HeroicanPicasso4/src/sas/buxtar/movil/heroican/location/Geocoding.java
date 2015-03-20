package sas.buxtar.movil.heroican.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import sas.buxtar.movil.heroican.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

public class Geocoding {

	private static Geocoding instancia = null;
	public static final int DIRECCION_OBTENIDA = 5001;
	private static Context contexto = null;
	Handler handlerGui;
	
	public Geocoding(Context contexto) {
		// TODO Auto-generated constructor stub
		this.contexto = contexto;
		
	}

	public static Geocoding getInstancia(Context context) {
		if (instancia == null) {
			instancia = new Geocoding(context);
		}
		return instancia;
	}
	
	/**
     * Invoked by the "Get Address" button.
     * Get the address of the current location, using reverse geocoding. This only works if
     * a geocoding service is available.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    // For Eclipse with ADT, suppress warnings about Geocoder.isPresent()
    @SuppressLint("NewApi")
    public void getAddress(LatLng location, final Handler handlerGui) {
    	this.handlerGui = handlerGui;
        // In Gingerbread and later, use Geocoder.isPresent() to see if a geocoder is available.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && !Geocoder.isPresent()) {
            // No geocoder is present. Issue an error message
            //Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
            return;
        }


	if (servicesConnected()) {

            
            // Turn the indefinite activity indicator on
            //mActivityIndicator.setVisibility(View.VISIBLE);

            // Start the background task
            (new GetAddressTask(contexto)).execute(location);
        }
    }
    
    /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    public static boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(contexto);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            //Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            /*Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, contexto, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
            }*/
            return false;
        }
    }

    
    /**
     * An AsyncTask that calls getFromLocation() in the background.
     * The class uses the following generic types:
     * Location - A {@link android.location.Location} object containing the current location,
     *            passed as the input parameter to doInBackground()
     * Void     - indicates that progress units are not used by this subclass
     * String   - An address passed to onPostExecute()
     */
    protected class GetAddressTask extends AsyncTask<LatLng, Void, String> {

        // Store the context passed to the AsyncTask when the system instantiates it.
        Context localContext;

        // Constructor called by the system to instantiate the task
        public GetAddressTask(Context context) {

            // Required by the semantics of AsyncTask
            super();

            // Set a Context for the background task
            localContext = context;
        }

        /**
         * Get a geocoding service instance, pass latitude and longitude to it, format the returned
         * address, and return the address to the UI thread.
         */
        @Override
        protected String doInBackground(LatLng... params) {
            /*
             * Get a new geocoding service instance, set for localized addresses. This example uses
             * android.location.Geocoder, but other geocoders that conform to address standards
             * can also be used.
             */
            Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());

            // Get the current location from the input parameter list
            LatLng location = params[0];

            // Create a list to contain the result address
            List <Address> addresses = null;

            // Try to get an address for the current location. Catch IO or network problems.
            try {

                /*
                 * Call the synchronous getFromLocation() method with the latitude and
                 * longitude of the current location. Return at most 1 address.
                 */
                addresses = geocoder.getFromLocation(location.latitude,
                    location.longitude, 1
                );

                // Catch network or other I/O problems.
                } catch (IOException exception1) {

                    // Log an error and return an error message
        //            Log.e(LocationUtils.APPTAG, getString(R.string.IO_Exception_getFromLocation));

                    // print the stack trace
                    exception1.printStackTrace();

                    // Return an error message
                    return (contexto.getString(R.string.IO_Exception_getFromLocation));
 
                // Catch incorrect latitude or longitude values
                } catch (IllegalArgumentException exception2) {

                    // Construct a message containing the invalid arguments
                    String errorString = contexto.getString(
                            R.string.illegal_argument_exception,
                            location.latitude,
                            location.longitude
                    );
                    // Log the error and print the stack trace
                    Log.e(LocationUtils.APPTAG, errorString);
                    exception2.printStackTrace();

                    //
                    return errorString;
                }
                // If the reverse geocode returned an address
                if (addresses != null && addresses.size() > 0) {

                    // Get the first address
                    Address address = addresses.get(0);

                    // Format the first line of address
                    String addressText = contexto.getString(R.string.address_output_string,

                            // If there's a street address, add it
                            address.getMaxAddressLineIndex() > 0 ?
                                    address.getAddressLine(0) : "",

                            // Locality is usually a city
                            address.getLocality(),

                            // The country of the address
                            address.getCountryName()
                    );
                    

                    // Return the text
                    return addressText;
//                    return address.getLocality()

                // If there aren't any addresses, post a message
                } else {
                  return contexto.getString(R.string.no_address_found);
                }
        }

        /**
         * A method that's called once doInBackground() completes. Set the text of the
         * UI element that displays the address. This method runs on the UI thread.
         */
        @Override
        protected void onPostExecute(String address) {

            // Turn off the progress bar
           // mActivityIndicator.setVisibility(View.GONE);
        	// Display the results of the lookup.
        	Message msg = new Message();
			msg.what = DIRECCION_OBTENIDA;
			msg.obj = address;
			
			handlerGui.sendMessage(msg);
          }
    }


}
