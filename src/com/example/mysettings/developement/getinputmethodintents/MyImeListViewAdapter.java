package me.zhengnian.mysettings.developement.getinputmethodintents;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.widget.BaseAdapter;
import android.widget.TextView;

import me.zhengnian.mysettings.R;

public class MyImeListViewAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<InputMethodInfo> mImis;
	private final PackageManager pm;

	private final class ListItemView {
		public TextView imeName;
		public TextView imeServiceName;
		public TextView imeSettingActivityName;
	}

	public MyImeListViewAdapter(Context context,
			List<InputMethodInfo> imis) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mImis = imis;
		pm = mContext.getPackageManager();
	}

	//

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = mLayoutInflater.inflate(
					R.layout.ime_base_intent_item_view, null);
			listItemView.imeName = (TextView) convertView
					.findViewById(R.id.imeName);
			listItemView.imeServiceName = (TextView) convertView
					.findViewById(R.id.imeServiceName);
			listItemView.imeSettingActivityName = (TextView) convertView
					.findViewById(R.id.imeSettingActivityName);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		final InputMethodInfo imeInfo = mImis.get(position);
		listItemView.imeName.setText(imeInfo.loadLabel(pm).toString());
		listItemView.imeServiceName
				.setText("Service: "+imeInfo.getComponent().getPackageName()+"/"+imeInfo.getComponent().getShortClassName());
		listItemView.imeSettingActivityName.setText("Setting activity: "+imeInfo.getComponent().getPackageName()+"/"+imeInfo.getSettingsActivity());

		return convertView;
	}

	@Override
	public int getCount() {
		return mImis.size();
	}

	@Override
	public Object getItem(int position) {
		return mImis.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}