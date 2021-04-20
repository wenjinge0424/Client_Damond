package lu.eminozandac.ondamondclinet.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseGeoPoint;
import lu.eminozandac.ondamondclinet.OnDamondClient;
import lu.eminozandac.ondamondclinet.service.LocationService;

public class LocationUtil implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static LocationUtil getInstance() {
        return LocationService.utilInstance;
    }

    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    public GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;
    public Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;

    public void onCreate(Bundle savedInstanceState) {
        mRequestingLocationUpdates = false;
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient(OnDamondClient.getContext());
        mRequestingLocationUpdates = true;
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
        }
    }

    public synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        long interval = 60 * 1000;
        mLocationRequest.setInterval(interval * 2);
        mLocationRequest.setFastestInterval(interval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void onStart() {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mCurrentLocation != null && mCurrentLocation != location) {
            mCurrentLocation = location;
//            UserModel.UpdateLocation(new ParseGeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {}
}
