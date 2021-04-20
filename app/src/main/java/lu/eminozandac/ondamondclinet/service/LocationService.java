package lu.eminozandac.ondamondclinet.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

import lu.eminozandac.ondamondclinet.utils.LocationUtil;

public class LocationService extends Service {
	public static LocationService instance;
	private static boolean isRunning = false;
	public static LocationUtil utilInstance;
	public static Bundle mBundle = null;
	public LocationService() {}

	public static boolean isRunning() {
		return isRunning;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		isRunning = true;
		utilInstance.onStart();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		utilInstance = new LocationUtil();
		if (mBundle == null)
			mBundle = new Bundle();
		utilInstance.onCreate(mBundle);
	}

	@Override
	public void onDestroy() {
		isRunning = false;
		super.onDestroy();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		// TODO Auto-generated method stub
		Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
		restartServiceIntent.setPackage(getPackageName());

		PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(),
				1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
		AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000,
				restartServicePendingIntent);
		
		super.onTaskRemoved(rootIntent);
	}
}

