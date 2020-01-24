/*
 * This file is added by zhengnian.me for: if this is the first time this Android tablet run by users, then we should get development settings disabled. 2013.06.19
 */

package me.zhengnian.mysettings;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.os.storage.ExternalStorageFormatter;
import com.android.internal.widget.LockPatternUtils;


public class MyReceiver extends BroadcastReceiver {
//	private static final String ZYGOTE_SOCKET = "zygote";

    @Override
    public void onReceive(final Context context, Intent intent) {
    
    	String action = intent.getAction();
        if(action.equals("android.intent.action.BOOT_COMPLETED") || action.equals("me.zhengnian.action.enable_MySettings")  ) {
        	neverSleep(context);
        	disableKeyGuard(context);
        	if(action.equals("android.intent.action.BOOT_COMPLETED")){
        		Settings.System.putInt(context.getContentResolver(),
        				"my_settings_enabled", 1);
        	}
//        	final ContentResolver cr = context.getContentResolver();
//        	boolean mLastEnabledState = Settings.Global.getInt(cr,
//        			Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
//        	if(mLastEnabledState && (new File("/data/data/cloned_system")).exists()){ // if this is the first time this Android tablet run by users, then we should get development settings disabled.  
//        		Settings.Global.putInt(cr,
//            			Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
//        		if(Settings.Global.getInt(context.getContentResolver(),
//                        Settings.Global.ADB_ENABLED, 0) == 1){
//            		Settings.Global.putInt(context.getContentResolver(),
//                            Settings.Global.ADB_ENABLED, 0);
//            	}
//            }
        	
//        	editLatinConfigXML();
        	
		} else if (action.equals("me.zhengnian.action.MySettings_started") && Settings.System.getInt(context.getContentResolver(),
				"my_settings_enabled", 0) != 0){
			neverSleep(context);
        	disableKeyGuard(context);
		} else if (action.equals("me.zhengnian.action.disable_MySettings")) {
			sleepAfter30Minutes(context);
			enableKeyGuard(context);
		} else if(action.equals("my.android.intent.action.RESET_NOT_ERASE_SD_CARD")) {
        	Intent alarmintent = new Intent();
			alarmintent.setAction("android.need.alarm.reset");
			context.sendBroadcast(alarmintent);
			context.sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
		} else if (action
				.equals("my.android.intent.action.RESET_ERASE_SD_CARD")) {
			Intent alarmintent = new Intent();
			alarmintent.setAction("android.need.alarm.reset");
			context.sendBroadcast(alarmintent);
			Intent eraseIntent = new Intent(
					ExternalStorageFormatter.FORMAT_AND_FACTORY_RESET);
			eraseIntent.setComponent(ExternalStorageFormatter.COMPONENT_NAME);
			context.startService(eraseIntent);
		} else if(action.equals("my.android.intent.action.chinese_s")) {
			
			IActivityManager am = ActivityManagerNative.getDefault();
            Configuration config;
			try {
				config = am.getConfiguration();
//				config.setLocale(new Locale("zh"));
				config.setLocale(new Locale("zh","CN"));
	            am.updateConfiguration(config);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Will set userSetLocale to indicate this isn't some passing default - the user
            // wants this remembered
            
            // Trigger the dirty bit for the Settings Provider.
            BackupManager.dataChanged("com.android.providers.settings");
//			String languageToLoad  = "zh";
//		    Locale locale = new Locale(languageToLoad);
//		    Locale.setDefault(locale);
//		    Configuration config = context.getResources().getConfiguration();
//		    config.locale = Locale.SIMPLIFIED_CHINESE;
	    
		} else if(action.equals("my.android.intent.action.chinese_f")) {
			
			
			
		} else if(action.equals("my.android.intent.action.english_us")) {
//			IActivityManager am = ActivityManagerNative.getDefault();
//            Configuration config;
//			try {
//				config = am.getConfiguration();
////				config.setLocale(new Locale("zh"));
//				config.setLocale(new Locale("en","US"));
//	            am.updateConfiguration(config);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//
//            BackupManager.dataChanged("com.android.providers.settings");
			try {
				new File("/data/fj.fj").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(action.equals("my.android.intent.action.shutdown")) {
			shutdown();
		} else if(action.equals("my.android.intent.action.disable_adb")) {
			Settings.Global.putInt(context.getContentResolver(),
					Settings.Global.ADB_ENABLED, 0);
		} else if(action.equals("tdb")) {
			
		}
    }
    
	private void shutdown() {
		
	}

	private void neverSleep(Context context) {

		Settings.System.putInt(context.getContentResolver(),
				SCREEN_OFF_TIMEOUT, Integer.MAX_VALUE);
		Toast.makeText(context, "休眠超时时间已被MySettings更改为23天。", Toast.LENGTH_SHORT)
				.show();

	}
	
	private void sleepAfter30Minutes(Context context) {

		Settings.System.putInt(context.getContentResolver(),
				SCREEN_OFF_TIMEOUT, 30*60*1000);
		Toast.makeText(context, "休眠超时时间已被MySettings更改为30分钟。", Toast.LENGTH_SHORT)
				.show();

	}
	
	private void disableKeyGuard(Context context) {
		LockPatternUtils mLockPatternUtils = new LockPatternUtils(context);
		mLockPatternUtils.clearLock(false);
		mLockPatternUtils.setLockScreenDisabled(true);
		
		Toast.makeText(context, "屏保已被MySettings更改为“无”。", Toast.LENGTH_SHORT)
		.show();

	}
	
	private void enableKeyGuard(Context context) {
		LockPatternUtils mLockPatternUtils = new LockPatternUtils(context);
		mLockPatternUtils.clearLock(false);
		mLockPatternUtils.setLockScreenDisabled(false);
		
		
		
		Toast.makeText(context, "屏保已被MySettings更改为“滑动”。", Toast.LENGTH_SHORT)
		.show();

	}
    
//    private void editLatinConfigXML(){
//
//            try {
//            	LocalSocket zygoteSocket = new LocalSocket();
//
//                zygoteSocket.connect(new LocalSocketAddress(ZYGOTE_SOCKET, LocalSocketAddress.Namespace.RESERVED));
//
//                BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( zygoteSocket.getOutputStream()), 256);
//                bw.write("example-addDefaultContacts");
//                bw.newLine();
//                bw.flush();
//                bw.close();
//                zygoteSocket.close();
//                Log.e("wefwfew",
//						"wwwwwwwwwwwwwwwwwwwwwwww ms wq editLatinConfigXML sent");
//            } catch(Exception e){
//            	e.printStackTrace();
//            }
//    
//    }

}
