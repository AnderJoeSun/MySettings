/*
 * Copyright (C) 2011 The Android Open Source Project
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

package me.zhengnian.mysettings.developement.getbaseintents;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import me.zhengnian.mysettings.R;

public class GetBaseIntentsOfRecentTasksPreference extends DialogPreference {

	static final int ITEM_TYPE_APPWIDGET = 4;
	private View mDialogView;
	private ListView baseIntentsList;
	private Context mContext;
	private final int RECENTS_COUNT = 6;
	public GetBaseIntentsOfRecentTasksPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("ms", "GetLauncherLayoutPreference construc");
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		mContext = builder.getContext();
		View content = View.inflate(mContext,
				R.layout.get_base_intents_dialog, null);
		baseIntentsList = (ListView) content
				.findViewById(R.id.baseIntentsList);
		final ActivityManager am = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
		final List<ActivityManager.RecentTaskInfo> recentTasks =
				am.getRecentTasks(RECENTS_COUNT, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
		final PackageManager pm = mContext.getPackageManager();
		ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN)
        .addCategory(Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
		
		final List<ActivityManager.RecentTaskInfo> recentTasksWithoutHomeAndRecent = new ArrayList<ActivityManager.RecentTaskInfo>();
		for(int i = 0; i<recentTasks.size();i++ ){
			final ActivityManager.RecentTaskInfo recentInfo = recentTasks.get(i);

            Intent intent = new Intent(recentInfo.baseIntent);
            if (recentInfo.origActivity != null) {
                intent.setComponent(recentInfo.origActivity);
            }

            // Don't load the current home activity.
            if (isCurrentHomeActivity(intent.getComponent(), homeInfo)) {
                continue;
            }

            // Don't load the recent activity.
            if (intent.getComponent().getPackageName().equals(mContext.getPackageName())) {
                continue;
            }
            
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
            	recentTasksWithoutHomeAndRecent.add(recentInfo);
            }
			
		}
		
		
		MyListViewAdapter listViewAdapter = new MyListViewAdapter(mContext, recentTasksWithoutHomeAndRecent);  
		baseIntentsList.setAdapter(listViewAdapter); 
		builder.setView(content);
	}

	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

	private boolean isCurrentHomeActivity(ComponentName component, ActivityInfo homeInfo) {
        if (homeInfo == null) {
            final PackageManager pm = mContext.getPackageManager();
            homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
                .resolveActivityInfo(pm, 0);
        }
        return homeInfo != null
            && homeInfo.packageName.equals(component.getPackageName())
            && homeInfo.name.equals(component.getClassName());
    }

}
