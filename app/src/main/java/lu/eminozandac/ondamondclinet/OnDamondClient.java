package lu.eminozandac.ondamondclinet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.parse.Parse;
import com.parse.ParseACL;

public class OnDamondClient extends MultiDexApplication {
	public static Context mContext;

	@Override
	protected void attachBaseContext(Context context) {
		super.attachBaseContext(context);
		MultiDex.install(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();

		// initialize
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
//		AppPreference.initialize(pref);

		/*
		 * initialize parse
		 */
		// Initialize Crash Reporting.
//		ParseCrashReporting.enable(this);
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		// Add your initialization code here
		Parse.initialize(new Parse.Configuration.Builder(this)
				.applicationId("maquetee_app_01")
				.clientKey("master_maquetee_0123")
				.server("http://3.137.103.177:1337/parse")
				.build());
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
		initImageLoader(mContext);
//		new MyImageLoader();
//		MyImageLoader.init();
	}

	public static Context getContext() {
		return mContext;
	}

	private void initImageLoader(Context context) {
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//				.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//				.diskCacheExtraOptions(480, 800, null)
//				.threadPoolSize(3) // default
//				.threadPriority(Thread.NORM_PRIORITY - 2) // default
//				.tasksProcessingOrder(QueueProcessingType.FIFO) // default
//				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new LruMemoryCache(10 * 1024 * 1024))
//				.memoryCacheSize(10 * 1024 * 1024)
//				.memoryCacheSizePercentage(13) // default
//				.diskCacheSize(50 * 1024 * 1024)
//				.diskCacheFileCount(100)
//				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//				.imageDownloader(new BaseImageDownloader(context)) // default
//				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//				.writeDebugLogs()
//				.build();
//		ImageLoader.getInstance().init(config);
	}
}