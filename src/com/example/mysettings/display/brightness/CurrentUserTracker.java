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

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CurrentUserTracker extends BroadcastReceiver {

    private int mCurrentUserId;

    public CurrentUserTracker(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_SWITCHED);
        context.registerReceiver(this, filter);
        mCurrentUserId = ActivityManager.getCurrentUser();
    }

    public int getCurrentUserId() {
        return mCurrentUserId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_USER_SWITCHED.equals(intent.getAction())) {
            mCurrentUserId = intent.getIntExtra(Intent.EXTRA_USER_HANDLE, 0);
        }
    }
}
