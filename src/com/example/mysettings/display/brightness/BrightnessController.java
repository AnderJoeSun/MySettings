/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zhengnian.mysettings.display.brightness;

import java.util.ArrayList;

import me.zhengnian.mysettings.R;

import android.content.Context;
import android.os.AsyncTask;
import android.os.IPowerManager;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.widget.TextView;

public class BrightnessController implements BrightnessSlider.Listener {
    private static final String TAG = "MySettings.BrightnessController";

    private final int mMinimumBacklight;
    private final int mBottomValueOffset = 30;
    private final int mMaximumBacklight;

    private final Context mContext;
    private final boolean mAutomaticAvailable;
    private final IPowerManager mPower;
    private final CurrentUserTracker mUserTracker;
    
    private TextView mValue;

    private ArrayList<BrightnessStateChangeCallback> mChangeCallbacks =
            new ArrayList<BrightnessStateChangeCallback>();

    public interface BrightnessStateChangeCallback {
        public void onBrightnessLevelChanged();
    }

    public BrightnessController(Context context, BrightnessSlider brightnessSlider) {
        mContext = context;
        mUserTracker = new CurrentUserTracker(mContext);

        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mMinimumBacklight = pm.getMinimumScreenBrightnessSetting() - mBottomValueOffset;
        mMaximumBacklight = pm.getMaximumScreenBrightnessSetting();

//        mAutomaticAvailable = context.getResources().getBoolean(
//                com.android.internal.R.bool.config_automatic_brightness_available);
        mAutomaticAvailable = false;
        mPower = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));

        brightnessSlider.setOnChangedListener(this);
    }

    public void addStateChangedCallback(BrightnessStateChangeCallback cb) {
        mChangeCallbacks.add(cb);
    }

    @Override
    public void onInit(BrightnessSlider control) {
        if (mAutomaticAvailable) {
            int automatic;
            try {
                automatic = Settings.System.getIntForUser(mContext.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        mUserTracker.getCurrentUserId());
            } catch (SettingNotFoundException snfe) {
                automatic = 0;
            }
        }
        
        int value;
        try {
            value = Settings.System.getIntForUser(mContext.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    mUserTracker.getCurrentUserId());
        } catch (SettingNotFoundException ex) {
            value = mMaximumBacklight;
        }

        control.setMax(mMaximumBacklight - mMinimumBacklight);
        control.setValue(value - mMinimumBacklight);
    }

    @Override
    public void onChanged(BrightnessSlider view, boolean tracking, int value) {
		setMode(Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

		final int val = value + mMinimumBacklight;
		setBrightness(val);
		if (!tracking) {
			AsyncTask.execute(new Runnable() {
				public void run() {
					Settings.System.putIntForUser(
							mContext.getContentResolver(),
							Settings.System.SCREEN_BRIGHTNESS, val,
							mUserTracker.getCurrentUserId());
				}
			});
		}
		if (mValue == null) {
			mValue = (TextView) view.findViewById(R.id.value_tv);
		}
		mValue.setText("" + val);
		

		for (BrightnessStateChangeCallback cb : mChangeCallbacks) {
			cb.onBrightnessLevelChanged();
        }
    }

    private void setMode(int mode) {
        Settings.System.putIntForUser(mContext.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE, mode,
                mUserTracker.getCurrentUserId());
    }
    
    private void setBrightness(int brightness) {
        try {
            mPower.setTemporaryScreenBrightnessSettingOverride(brightness);
        } catch (RemoteException ex) {
        }        
    }

    
}
