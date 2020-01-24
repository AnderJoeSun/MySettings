布局：
	一级菜单：
		/mnt/mh/.fj/.j/0_android/n7/and4.3_r3/packages/apps/MySettings/src/me/zhengnian/mysettings/MainActivity.java：：：onBuildHeaders:
		loadHeadersFromResource(R.xml.settings_headers, headers); // res/xml/settings_headers.xml: me.zhengnian.mysettings.developement.DevelopmentSettings等 -被点击后> /mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/frameworks/base/core/java/android/preference/PreferenceActivity.java:::onHeaderClick() -> start SubSetting Activity(第二层Activity，内嵌Fragments，手机中，phone UI) 或 Setting（共只一层Activity）的一部份直接装载Fragments(平板, tablet UI)

	一级菜单内容界面：res/xml/development_settings_prefs.xml: me.zhengnian.mysettings.developement.getlayout.GetLauncherLayoutPreference等，而一级菜单中的me.zhengnian.mysettings.developement.DevelopmentSettings一项用到了development_settings_prefs.xml

说明：/mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/frameworks/base/core/res/res/values-sw720dp/bools.xml中覆盖了/mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/frameworks/base/core/res/res/values/bools.xml中的“preferences_prefer_dual_pane”，gn是sw720dp的，所以设置是分二层activity(二级菜单类都是Fragments，所以，多层显示时，Fragments必须被放到某个activity中(实际是SubSettings.java)！)来显示设置项的！这与平板上的一层activity（Settings这一个Activity中嵌套着各种Fragments）不一样！

preferences_prefer_dual_pane 
-> 
/mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/frameworks/base/core/java/android/preference/PreferenceActivity.java
	public boolean onIsMultiPane() {
        boolean preferMultiPane = getResources().getBoolean(
                com.android.internal.R.bool.preferences_prefer_dual_pane);
        return preferMultiPane;
    }
    
->
/mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/frameworks/base/core/java/android/preference/PreferenceActivity.java:
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.android.internal.R.layout.preference_list_content);

        mListFooter = (FrameLayout)findViewById(com.android.internal.R.id.list_footer);
        mPrefsContainer = (ViewGroup) findViewById(com.android.internal.R.id.prefs_frame);
        boolean hidingHeaders = onIsHidingHeaders();
        mSinglePane = hidingHeaders || !onIsMultiPane(); // 这里
...
			
			if (initialFragment != null && mSinglePane) { // 二层Activity: Settings和SubSettings
                // If we are just showing a fragment, we want to run in
                // new fragment mode, but don't need to compute and show
                // the headers.
                switchToHeader(initialFragment, initialArguments);
                if (initialTitle != 0) {
                    CharSequence initialTitleStr = getText(initialTitle);
                    CharSequence initialShortTitleStr = initialShortTitle != 0
                            ? getText(initialShortTitle) : null;
                    showBreadCrumbs(initialTitleStr, initialShortTitleStr);
                }

            } else { // 只有一层Activity(Settings)时
                // We need to try to build the headers.
                onBuildHeaders(mHeaders); // /mnt/mh/.fj/.j/0_android/gn/and-4.2.2_r1.2b/packages/apps/MySettings/src/me/zhengnian/mysettings/MainActivity.java覆盖了，所以会调用到MainActivity.java中的onBuildHeaders：搜上面“MainActivity.java：：：onBuildHeaders:”

                // If there are headers, then at this point we need to show
                // them and, depending on the screen, we may also show in-line
                // the currently selected preference fragment.
                if (mHeaders.size() > 0) {
                    if (!mSinglePane) {
                        if (initialFragment == null) {
                            Header h = onGetInitialHeader();
                            switchToHeader(h);
                        } else {
                            switchToHeader(initialFragment, initialArguments);
                        }
                    }
                }
            } 
...    
        // The default configuration is to only show the list view.  Adjust
        // visibility for other configurations.
        if (initialFragment != null && mSinglePane) {
            // Single pane, showing just a prefs fragment. 
            findViewById(com.android.internal.R.id.headers).setVisibility(View.GONE);
            mPrefsContainer.setVisibility(View.VISIBLE);
            if (initialTitle != 0) {
                CharSequence initialTitleStr = getText(initialTitle);
                CharSequence initialShortTitleStr = initialShortTitle != 0
                        ? getText(initialShortTitle) : null;
                showBreadCrumbs(initialTitleStr, initialShortTitleStr);
            }
        } else if (mHeaders.size() > 0) {
            setListAdapter(new HeaderAdapter(this, mHeaders));
            if (!mSinglePane) {
                // Multi-pane.
                getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                if (mCurHeader != null) {
                    setSelectedHeader(mCurHeader);
                }
                mPrefsContainer.setVisibility(View.VISIBLE);
            }
        } else {
            // If there are no headers, we are in the old "just show a screen
            // of preferences" mode.
            setContentView(com.android.internal.R.layout.preference_list_content_single);
            mListFooter = (FrameLayout) findViewById(com.android.internal.R.id.list_footer);
            mPrefsContainer = (ViewGroup) findViewById(com.android.internal.R.id.prefs);
            mPreferenceManager = new PreferenceManager(this, FIRST_REQUEST_CODE);
            mPreferenceManager.setOnPreferenceTreeClickListener(this);
        }
