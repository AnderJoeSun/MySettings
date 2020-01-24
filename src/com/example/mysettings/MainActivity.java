package me.zhengnian.mysettings;


import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.Toast;


import me.zhengnian.mysettings.developement.DevelopmentSettings;

public class MainActivity extends PreferenceActivity implements
		CompoundButton.OnCheckedChangeListener {
	private Switch mEnabledSwitch;
	private boolean mLastEnabledState;

	private SharedPreferences mDevelopmentPreferences;
	private SharedPreferences.OnSharedPreferenceChangeListener mDevelopmentPreferencesListener;
	public static int displayDensityDpi = 0;
	public static float displayDensity = 0.0f;

	// 物理像素值
	public static int displayWidthPixel = 0;
	public static int displayHeightPixel = 0;

	// 屏幕分辨率
	public static int displayWidthWithoutNavigationBar = 0;
	public static int displayHeightWithoutNavigationBar = 0;

	public static int statusBarHeight = -1;
	public static int navigationBarHeight = -1;

	/**
	 * Populate the activity with the top-level headers.
	 */
	@Override
	public void onBuildHeaders(List<Header> headers) { 
		loadHeadersFromResource(R.xml.settings_headers, headers);
	}

	/**
	 * fj: if com.android.internal.R.bool.preferences_prefer_dual_pane if true, then we get two layer of Activity: Settings(container of only the top-level headers) and SubSettings(container of Fragments). (注：在settings_headers.xml指定了intent的header是不会触发onBuildStartFragmentIntent的)
	 */
	@Override
    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args,
            int titleRes, int shortTitleRes) { // 本方法在preferences_prefer_dual_pane为false(tablet UI)中不会调用！而在preferences_prefer_dual_pane为true(phone UI)中会调用以显示二层activity.
        Intent intent = super.onBuildStartFragmentIntent(fragmentName, args,
                titleRes, shortTitleRes);
        intent.setClass(this, MySubSettings.class);
        return intent;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		this.sendBroadcast((new Intent(this, MyReceiver.class))
				.setAction("me.zhengnian.action.MySettings_started"));

		super.onCreate(savedInstanceState);

		mEnabledSwitch = new Switch(this);

		// final int padding = 16; // 16dip
		// mEnabledSwitch.setPadding(0, 0, padding, 0);
		mEnabledSwitch.setOnCheckedChangeListener(this);

		DisplayMetrics metric = new DisplayMetrics();
		Display display = getWindowManager().getDefaultDisplay();
		// displayWidthPixel = display.getRawWidth();
		// displayHeightPixel = display.getRawHeight();

		displayWidthPixel = display.getWidth();
		displayHeightPixel = display.getHeight();

		display.getMetrics(metric);
		displayDensityDpi = metric.densityDpi;
		displayDensity = metric.density;
		displayWidthWithoutNavigationBar = metric.widthPixels;
		displayHeightWithoutNavigationBar = metric.heightPixels;

		statusBarHeight = getStatusBarHeight();

		Log.e("ms", "metric.densityDpi: " + metric.densityDpi
				+ " , metric.density: " + metric.density
				+ ", metric.widthPixels: " + metric.widthPixels
				+ ", metric.heightPixels: " + metric.heightPixels
				+ ", statusBarHeight: " + statusBarHeight);

		navigationBarHeight = displayHeightPixel
				- displayHeightWithoutNavigationBar;
		 mDevelopmentPreferences = getSharedPreferences(DevelopmentSettings.PREF_FILE,Context.MODE_PRIVATE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(
				mEnabledSwitch,
				new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
						ActionBar.LayoutParams.WRAP_CONTENT,
						Gravity.CENTER_VERTICAL | Gravity.END));
	}

	@Override
	public void onResume() {
		super.onResume();
		mLastEnabledState = Settings.System.getInt(getContentResolver(),
				"my_settings_enabled", 0) != 0;
		mEnabledSwitch.setChecked(mLastEnabledState);

		// hide nav bar ..start
//		Toast.makeText(this, "hide the sysstem bar ! ", Toast.LENGTH_LONG)
//				.show();
//		sendBroadcast(new Intent("com.android.systemui.statusbar.TabletStatusBar.HideNavigationBar"));
//		disableSystemBar();
		// hide nav bar ..end
		
		// pdf ..start
//		Intent i = new Intent(Intent.ACTION_VIEW);  
//	    i.addCategory(Intent.CATEGORY_DEFAULT);  
//	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );  
////	    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/readme2.pdf"));  
//	    Uri uri = Uri.fromFile(new File("/system/media/readme.pdf")); 
//	    i.setDataAndType(uri, "application/pdf");  
////	    i.setComponent(new ComponentName("com.adobe.reader","com.adobe.reader.ARViewer"));
//	    startActivity(i);
		// pdf ..end

	    Log.d("ms-", "eeeeeeeeeeeee");
		mDevelopmentPreferencesListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			@Override
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				invalidateHeaders();
			}
		};
		mDevelopmentPreferences
		.registerOnSharedPreferenceChangeListener(mDevelopmentPreferencesListener);

		invalidateHeaders();

	}

	@Override
	public void onPause() {
		super.onPause();
		mDevelopmentPreferences
				.unregisterOnSharedPreferenceChangeListener(mDevelopmentPreferencesListener);
		mDevelopmentPreferencesListener = null;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	protected void onStop() {
		super.onStop();
		getActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(null);
	}

	private int getStatusBarHeight() {

		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0;

		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			return this.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return -1;

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == mEnabledSwitch) {
			if (isChecked != mLastEnabledState) {
				if (isChecked) {
					this.sendBroadcast((new Intent(this,
							MyReceiver.class))
							.setAction("me.zhengnian.action.enable_MySettings"));
					Settings.System.putInt(getContentResolver(),
							"my_settings_enabled", 1);
					mLastEnabledState = true;
				} else {
					this.sendBroadcast((new Intent(this,
							MyReceiver.class))
							.setAction("me.zhengnian.action.disable_MySettings"));
					Settings.System.putInt(getContentResolver(),
							"my_settings_enabled", 0);
					mLastEnabledState = false;
				}
			}
		}

	}

	private void disableSystemBar() {
//		int flags = StatusBarManager.DISABLE_NONE; // 从这里开始！
//		flags |= StatusBarManager.DISABLE_RECENT | StatusBarManager.DISABLE_BACK | StatusBarManager.DISABLE_HOME;
//		flags |= StatusBarManager.DISABLE_EXPAND;
//		flags |= StatusBarManager.DISABLE_NOTIFICATION_TICKER;
//		flags |= StatusBarManager.DISABLE_SEARCH;
//
//		StatusBarManager mStatusBarManager = (StatusBarManager) this
//				.getSystemService(Context.STATUS_BAR_SERVICE);
//		mStatusBarManager.disable(flags); // 这里
	}
}
