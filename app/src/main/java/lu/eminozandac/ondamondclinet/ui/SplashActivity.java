package lu.eminozandac.ondamondclinet.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.service.LocationService;
import lu.eminozandac.ondamondclinet.utils.CommonUtil;

public class SplashActivity extends BaseActivity {
	public static SplashActivity instance = null;
	private static final int REQUEST_PERMISSION = 1;
	private static String[] PERMISSIONS = {
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.CAMERA,
			Manifest.permission.CALL_PHONE
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		verifyStoragePermissions(this);
	}

	public void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission0 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int permission1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
		int permission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
		int permission3 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
		if (permission0 != PackageManager.PERMISSION_GRANTED
				|| permission1 != PackageManager.PERMISSION_GRANTED
				|| permission2 != PackageManager.PERMISSION_GRANTED
				|| permission3 != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(
						activity,
						PERMISSIONS,
						REQUEST_PERMISSION
				);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					gotoNextPage();
				}
			}, 1000);
		}
	}

	private void gotoNextPage() {
		if (CommonUtil.verifyStoragePermissions(CommonUtil.TYPE_LOCATION_PERMISSION, instance) && !LocationService.isRunning()) {
			Intent service = new Intent(instance, LocationService.class);
			startService(service);
		}
		startActivity(new Intent(instance, LoginActivity.class));
		finish();
	}
}