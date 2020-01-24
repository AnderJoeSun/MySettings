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

package me.zhengnian.mysettings.developement.getinputmethodintents;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import me.zhengnian.mysettings.R;

public class GetBaseIntentsOfAllImesPreference extends DialogPreference {

	private View mDialogView;
	private ListView imeBaseIntentsList;
	private Context mContext;
	private InputMethodManager mImm;
	private List<InputMethodInfo> mImis;
	
	
	public GetBaseIntentsOfAllImesPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("ms", "GetLauncherLayoutPreference construc");
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		mContext = builder.getContext();
		View content = View.inflate(mContext,
				R.layout.get_ime_base_intents_dialog, null);
		imeBaseIntentsList = (ListView) content
				.findViewById(R.id.imeBaseIntentsList);
		
		mImm = (InputMethodManager) builder.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mImis = mImm.getInputMethodList();
        final int N = (mImis == null ? 0 : mImis.size());
        if(N > 0){
        	MyImeListViewAdapter listViewAdapter = new MyImeListViewAdapter(mContext, mImis);  
    		imeBaseIntentsList.setAdapter(listViewAdapter); 
    		builder.setView(content);
        }
		
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
