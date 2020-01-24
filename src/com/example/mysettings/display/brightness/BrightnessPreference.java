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

package me.zhengnian.mysettings.display.brightness;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;

import me.zhengnian.mysettings.R;

public class BrightnessPreference extends DialogPreference {

	private BrightnessController mBrightnessController;
	private BrightnessSettingModel mModel;

	public BrightnessPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		// View content = View.inflate(builder.getContext(),
		// R.layout.brightness_toggle_slider, null);
		// TextView value_tv = (TextView)comtent.findViewById(R.id.value_tv);

		mModel = new BrightnessSettingModel(builder.getContext());
		View content = View.inflate(builder.getContext(),
				R.layout.brightness_dialog, null);
		builder.setView(content);
		mBrightnessController = new BrightnessController(builder.getContext(),
				(BrightnessSlider) content.findViewById(R.id.brightness_slider));
		mBrightnessController.addStateChangedCallback(mModel);
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
		mBrightnessController = null;

	}
}
