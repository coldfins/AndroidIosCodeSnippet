package com.post.wall.wallpostapp.utility;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class UILApplication extends Application {

	public static final String TAG = UILApplication.class.getSimpleName();
	private RequestQueue mRequestQueue;
	LruBitmapCache mLruBitmapCache;
	private ImageLoader mImageLoader;

//	private RequestQueue mRequestQueue;
//	private ImageLoader mImageLoader;
//	LruBitmapCache mLruBitmapCache;

	private static UILApplication mInstance;

//	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		try {
//			MultiDex.install(this);
//
			mInstance = this;
//
//			if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//				StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
//				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
//			}

			super.onCreate();

			// create database and its table

//			HomeDatabaseManager.createHomeTableApplication(mInstance);// home feeds
//			CommentDatabaseManager.createHomeTableApplication(mInstance);// home feed's comment
////			NewEventsDatabaseManager.createNewEventTableApplication(mInstance); //new Event lists
////			NewMembersDatabaseManager.createNewMemberTableApplication(mInstance); // new Member
//			LoginBackgroundDatabaseManager.createNewLoginBackTableApplication(mInstance);
//			NewAnnouncementDatabaseManager.createNewAnnouncementTableApplication(mInstance); // Announcement

			initImageLoader(getApplicationContext());

//			AnalyticsTrackers.initialize(this);
//			AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
//
//			Utility.setFonts(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void initImageLoader(Context context) {
		try {
			// This configuration tuning is custom. You can tune every option, you
			// may tune some of them,
			// or you can create default configuration by method.
			ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
				config.threadPriority(Thread.NORM_PRIORITY - 2);
				config	.denyCacheImageMultipleSizesInMemory();
				config	.discCacheFileNameGenerator(new Md5FileNameGenerator());
				config	.diskCacheSize(50 * 1024 * 1024); // 50 MiB
				config	.tasksProcessingOrder(QueueProcessingType.LIFO);
				config	.writeDebugLogs(); // Remove for release app
					
			// Initialize ImageLoader with configuration.
			com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static class Config {
//		public static final boolean DEVELOPER_MODE = false;
//	}

	// @Override
	// public void onCreate() {
	// super.onCreate();
	// mInstance = this;
	// }

	public static synchronized UILApplication getInstance() {
		return mInstance;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			getLruBitmapCache();
			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
		}

		return this.mImageLoader;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public LruBitmapCache getLruBitmapCache() {
		if (mLruBitmapCache == null)
			mLruBitmapCache = new LruBitmapCache();
		return this.mLruBitmapCache;
	}

//	public RequestQueue getRequestQueue() {
//		if (mRequestQueue == null) {
//			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//		}
//
//		return mRequestQueue;
//	}
//
//	public ImageLoader getImageLoader() {
//		getRequestQueue();
//		if (mImageLoader == null) {
//			getLruBitmapCache();
//			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
//		}
//
//		return this.mImageLoader;
//	}
//
//	public LruBitmapCache getLruBitmapCache() {
//		if (mLruBitmapCache == null)
//			mLruBitmapCache = new LruBitmapCache();
//		return this.mLruBitmapCache;
//	}
//
//	public <T> void addToRequestQueue(Request<T> req, String tag) {
//		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//		getRequestQueue().add(req);
//	}
//
//	public <T> void addToRequestQueue(Request<T> req) {
//		req.setTag(TAG);
//		getRequestQueue().add(req);
//	}
//
//	public void cancelPendingRequests(Object tag) {
//		if (mRequestQueue != null) {
//			mRequestQueue.cancelAll(tag);
//		}
//	}
//
//	public void onLowmemory() {
//		Runtime.getRuntime().gc();
//	}
//	@Override
//	protected void attachBaseContext(Context base) {
//		super.attachBaseContext(base);
//		MultiDex.install(this);
//	}
//
//
//
//
//
//	public synchronized Tracker getGoogleAnalyticsTracker() {
//		AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//		return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//	}
//
//	/***
//	 * Tracking screen view
//	 *
//	 * @param screenName screen name to be displayed on GA dashboard
//	 */
//	public void trackScreenView(String screenName) {
//		Tracker t = getGoogleAnalyticsTracker();
//
//		// Set screen name.
//		t.setScreenName(screenName);
//
//		// Send a screen view.
//		t.send(new HitBuilders.ScreenViewBuilder().build());
//
//		GoogleAnalytics.getInstance(this).dispatchLocalHits();
//	}
//
//	/***
//	 * Tracking exception
//	 *
//	 * @param e exception to be tracked
//	 */
//	public void trackException(Exception e) {
//		if (e != null) {
//			Tracker t = getGoogleAnalyticsTracker();
//
//			t.send(new HitBuilders.ExceptionBuilder()
//							.setDescription(
//									new StandardExceptionParser(this, null)
//											.getDescription(Thread.currentThread().getName(), e))
//							.setFatal(false)
//							.build()
//			);
//		}
//	}
//
//	/***
//	 * Tracking event
//	 *
//	 * @param category event category
//	 * @param action   action of the event
//	 * @param label    label
//	 */
//	public void trackEvent(String category, String action, String label) {
//		Tracker t = getGoogleAnalyticsTracker();
//
//		// Build and send an Event.
//		t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
//	}

}