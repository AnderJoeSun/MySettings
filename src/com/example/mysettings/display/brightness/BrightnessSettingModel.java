/*
 * Copyright (C) 2012 The Android Open Source Project
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

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

import me.zhengnian.mysettings.display.brightness.BrightnessController.BrightnessStateChangeCallback;

class BrightnessSettingModel implements BrightnessStateChangeCallback {
	/** Represents the state of a given attribute. */
//	static class State {
//		int iconId;
//		String label;
//		boolean enabled = false;
//	}
//
//	static class UserState extends State {
//		Drawable avatar;
//	}
//
//	static class BrightnessState extends State {
//		boolean autoBrightness;
//	}
//
//	/** The callback to update a given tile. */
//	interface RefreshCallback {
//		public void refreshView(BrightnessSettingTileView view, State state);
//	}

	/** ContentObserver to watch brightness **/
	private class BrightnessObserver extends ContentObserver {
		public BrightnessObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			onBrightnessLevelChanged();
		}

		public void startObserving() {
			final ContentResolver cr = mContext.getContentResolver();
			cr.unregisterContentObserver(this);
			cr.registerContentObserver(Settings.System
					.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE), false,
					this, mUserTracker.getCurrentUserId());
			cr.registerContentObserver(Settings.System
					.getUriFor(Settings.System.SCREEN_BRIGHTNESS), false, this,
					mUserTracker.getCurrentUserId());
		}
	}

	private final Context mContext;
	private final Handler mHandler;
	private final CurrentUserTracker mUserTracker;
	private final BrightnessObserver mBrightnessObserver;

	public BrightnessSettingModel(Context context) {
		mContext = context;
		mHandler = new Handler();
		mUserTracker = new CurrentUserTracker(mContext) {
			@Override
			public void onReceive(Context context, Intent intent) {
				super.onReceive(context, intent);
				onUserSwitched();
			}
		};

		mBrightnessObserver = new BrightnessObserver(mHandler);
		mBrightnessObserver.startObserving();

	}

//	void updateResources() {
//		refreshBrightnessTile();
//	}


	@Override
	public void onBrightnessLevelChanged() {
		// we can do sh userful here if needed.
	}

//	void refreshBrightnessTile() {
//		onBrightnessLevelChanged();
//	}

	// User switch: need to update visuals of all tiles known to have per-user
	// state
	void onUserSwitched() {
		mBrightnessObserver.startObserving();
		onBrightnessLevelChanged();
	}
}
