package lu.eminozandac.ondamondclinet.ui.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.model.ParseConstants;
import lu.eminozandac.ondamondclinet.utils.MyMath;

public class MapSearchFragment extends Fragment implements OnMapReadyCallback {
    public static MapSearchFragment instance;
    MainActivity mActivity;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;

    public static MapSearchFragment newInstance() {
        MapSearchFragment fragment = new MapSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        mActivity = MainActivity.instance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_search, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        MapsInitializer.initialize(mActivity);
        googleMap.setTrafficEnabled(false);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        showMap();
    }

    private void showMap() {
        if (googleMap != null) {
            googleMap.clear();
            ParseGeoPoint point = ParseUser.getCurrentUser().getParseGeoPoint(ParseConstants.user_currentLocation);
            LatLng mCurrentLatLng = new LatLng(point.getLatitude(), point.getLongitude());
            MarkerOptions pointOption = new MarkerOptions().position(mCurrentLatLng).title(getString(R.string.me));
            pointOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.police_marker));
            googleMap.addMarker(pointOption);
            setZoomToMarkers();
        }
    }

    private void setZoomToMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        ParseGeoPoint point = ParseUser.getCurrentUser().getParseGeoPoint(ParseConstants.user_currentLocation);
        if (point != null) {
            builder.include(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        LatLngBounds bounds = builder.build();
        double range = (Math.sqrt(2.0d) * 2000.0d) / 2.0d;
        LatLng center = bounds.getCenter();
        LatLng northEast = MyMath.move(center, range, range);
        LatLng southWest = MyMath.move(center, -range, -range);
        builder.include(northEast);
        builder.include(southWest);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
    }
}
