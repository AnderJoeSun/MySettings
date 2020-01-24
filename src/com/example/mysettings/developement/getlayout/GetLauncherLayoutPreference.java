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

package me.zhengnian.mysettings.developement.getlayout;

import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import me.zhengnian.getlayoutservice.IGetLayout;
import me.zhengnian.mysettings.R;

public class GetLauncherLayoutPreference extends DialogPreference {

	static final int ITEM_TYPE_APPWIDGET = 4;
	private View mDialogView;
	private TextView msgTv;
	private Context mContext;

	public GetLauncherLayoutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.e("ms", "GetLauncherLayoutPreference construc");
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		View content = View.inflate(builder.getContext(),
				R.layout.get_launcher_layout_dialog, null);
		msgTv = (TextView) content
				.findViewById(R.id.get_launcher_layout_message_tv);
		builder.setView(content);
		mContext = builder.getContext();
		mContext.bindService(new Intent("me.zhengnian.intent.action.GET_LAYOUT"),
				conn, Service.BIND_AUTO_CREATE);
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

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("ms", " client GetLayoutService Connected--");
			try {
				if ((IGetLayout.Stub.asInterface(service)).getLayout()) {
					msgTv.setText("布局信息已成功保存于文件：“/data/data/me.zhengnian.getlayoutservice/files/home_layout.xml”。");

				} else {
					msgTv.setText("布局信息获取失败，请用logcat查看相关日志以了解失败原因...");
				}
				mContext.unbindService(conn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("ms", " client GetLayoutService Disconnected--");
		}
	};

}
