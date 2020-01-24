/*
 * This file is added by zhengnian.me for: reset the system to factory-sets. 2013.09.13
 */

package me.zhengnian.mysettings.scheduler;

import com.android.internal.os.storage.ExternalStorageFormatter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Scheduler extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
    
    	String action = intent.getAction();
        if(action.equals("my.android.intent.action.RESET_NOT_ERASE_SD_CARD")) {
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
		} 
    }
}
