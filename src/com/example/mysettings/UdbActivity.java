package me.zhengnian.mysettings;



import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class UdbActivity extends Activity {
	
	private static final String TAG = "MySettings-UdbActivity"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int state = Settings.Global.getInt(getContentResolver(),
				Settings.Global.ADB_ENABLED, -1);
		if(state==1){
			Settings.Global.putInt(getContentResolver(),
					Settings.Global.ADB_ENABLED, 0);
			Log.d(TAG, "Settings.Global.ADB_ENABLED set to 0");
		} else if(state==0){
			Settings.Global.putInt(getContentResolver(),
					Settings.Global.ADB_ENABLED, 1);
			Log.d(TAG, "Settings.Global.ADB_ENABLED set to 1");
		} else{
			Log.e(TAG, "Settings.Global.getInt Settings.Global.ADB_ENABLED bad value: -1");
		}
		
		finish();
	}
	
}
