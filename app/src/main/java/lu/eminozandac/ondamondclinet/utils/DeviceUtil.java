package lu.eminozandac.ondamondclinet.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lu.eminozandac.ondamondclinet.R;

public class DeviceUtil {
	/*
	 * network connection
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean isConnected = false;
		try{
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
		} catch(Exception e) {
			isConnected = false;
		}
		if (context != null && !isConnected)
			MessageUtil.showError(context, R.string.msg_error_network);
		return isConnected;
	}
	/*
	 * Location service
	 */
	public static boolean isLocationServiceAvailable(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// getting GPS status
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		//return isGPSEnabled || isNetworkEnabled;
		return isGPSEnabled;
	}
}
