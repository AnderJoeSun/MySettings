package me.zhengnian.mysettings.developement.getbaseintents;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.zhengnian.mysettings.R;

public class MyListViewAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<ActivityManager.RecentTaskInfo> mRecents;
	private final PackageManager pm;

	private final class ListItemView {
		public ImageView appIcon;
		public TextView appName;
		public TextView packageName;
		public TextView className;
	}

	public MyListViewAdapter(Context context,
			List<ActivityManager.RecentTaskInfo> recents) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mRecents = recents;
		pm = mContext.getPackageManager();
	}

	//

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = mLayoutInflater.inflate(
					R.layout.base_intent_item_view, null);
			listItemView.appIcon = (ImageView) convertView
					.findViewById(R.id.appIcon);
			listItemView.appName = (TextView) convertView
					.findViewById(R.id.appName);
			listItemView.packageName = (TextView) convertView
					.findViewById(R.id.packageName);
			listItemView.className = (TextView) convertView
					.findViewById(R.id.className);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		final ActivityManager.RecentTaskInfo info = mRecents.get(position);
		Intent intent = new Intent(info.baseIntent);
		final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
		final ActivityInfo activityInfo = resolveInfo.activityInfo;
		final String title = activityInfo.loadLabel(pm).toString();
		listItemView.appName.setText(title);
		listItemView.packageName
				.setText(intent.getComponent().getPackageName());
		listItemView.className.setText(intent.getComponent().getClassName());
		Drawable icon = getFullResIcon(resolveInfo, pm);
		listItemView.appIcon.setImageDrawable(icon);

		return convertView;
	}

	@Override
	public int getCount() {
		return mRecents.size();
	}

	@Override
	public Object getItem(int position) {
		return mRecents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private Drawable getFullResDefaultActivityIcon() {
		return getFullResIcon(Resources.getSystem(),
				com.android.internal.R.mipmap.sym_def_app_icon);
	}

	private Drawable getFullResIcon(Resources resources, int iconId) {
		try {
			return resources.getDrawableForDensity(iconId, mContext
					.getResources().getDisplayMetrics().densityDpi);
		} catch (Resources.NotFoundException e) {
			return getFullResDefaultActivityIcon();
		}
	}

	private Drawable getFullResIcon(ResolveInfo info,
			PackageManager packageManager) {
		Resources resources;
		try {
			resources = packageManager
					.getResourcesForApplication(info.activityInfo.applicationInfo);
		} catch (PackageManager.NameNotFoundException e) {
			resources = null;
		}
		if (resources != null) {
			int iconId = info.activityInfo.getIconResource();
			if (iconId != 0) {
				return getFullResIcon(resources, iconId);
			}
		}
		return getFullResDefaultActivityIcon();
	}
}