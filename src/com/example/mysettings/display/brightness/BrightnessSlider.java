/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import me.zhengnian.mysettings.R;


public class BrightnessSlider extends LinearLayout 
        implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "StatusBar.ToggleSlider";

    public interface Listener {
        public void onInit(BrightnessSlider v);
        public void onChanged(BrightnessSlider v, boolean tracking, int value);
    }

    private Listener mListener;
    private boolean mTracking;

    // private CompoundButton mToggle;
    private SeekBar mSlider;

    public BrightnessSlider(Context context) {
        this(context, null);
    }

    public BrightnessSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrightnessSlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.brightness_toggle_slider, this);

        mSlider = (SeekBar)findViewById(R.id.slider);
        mSlider.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mListener != null) {
            mListener.onInit(this);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListener != null) {
            mListener.onChanged(this, mTracking, progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mTracking = true;
        if (mListener != null) {
            mListener.onChanged(this, mTracking, mSlider.getProgress());
        }
        // mToggle.setChecked(false);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTracking = false;
        if (mListener != null) {
            mListener.onChanged(this, mTracking, mSlider.getProgress());
        }
    }

    public void setOnChangedListener(Listener l) {
        mListener = l;
    }

    public boolean isChecked() {
        return false/*mToggle.isChecked()*/;
    }

    public void setMax(int max) {
        mSlider.setMax(max);
    }

    public void setValue(int value) {
        mSlider.setProgress(value);
    }
}

