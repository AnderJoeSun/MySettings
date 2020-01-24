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

package me.zhengnian.mysettings.scheduler;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import me.zhengnian.mysettings.MainActivity;
import me.zhengnian.mysettings.R;

public class SchedulerPreference extends DialogPreference {


	public SchedulerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		// View content = View.inflate(builder.getContext(),
		// R.layout.brightness_toggle_slider, null);
		// TextView value_tv = (TextView)comtent.findViewById(R.id.value_tv);

		Context context = builder.getContext();
		View content = View.inflate(context,
				R.layout.display_info_dialog, null);
		((TextView)content.findViewById(R.id.density_tv)).setText(" DPI(" + MainActivity.displayDensityDpi+"), Desity("+ MainActivity.displayDensity+")");
		((TextView)content.findViewById(R.id.sizeInPixel_tv)).setText(" " + MainActivity.displayWidthPixel+" × "+ MainActivity.displayHeightPixel);
		((TextView)content.findViewById(R.id.sizeInPixelWithoutNavigationBar_tv)).setText(" " + MainActivity.displayWidthWithoutNavigationBar+" × "+ MainActivity.displayHeightWithoutNavigationBar);
		
		if(MainActivity.statusBarHeight >= 0){
			((TextView)content.findViewById(R.id.statusBarHeight_tv)).setText(" " + MainActivity.statusBarHeight);
		} else {
			((TextView)content.findViewById(R.id.statusBarHeight_tv)).setText(" 状态栏不存在，或者：存在但其高度值获取失败，请调试程序。");
		}
		
		if(MainActivity.navigationBarHeight >= 0){
			((TextView)content.findViewById(R.id.navigationBarHeight_tv)).setText(" " + MainActivity.navigationBarHeight);
		} else {
			((TextView)content.findViewById(R.id.navigationBarHeight_tv)).setText(" 导航栏不存在，或者：存在但其高度值获取失败，请调试程序。");
		}
		
		
		
//		Log.e("ms","metric: w: "+ metric.widthPixels + metric.heightPixels);
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
    
}
