/*
 * (c) Faisal Khan. Created on 7/10/2021.
 */

package com.mietrix.restu_digital_quran.activities;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mietrix.restu_digital_quran.R;
import com.mietrix.restu_digital_quran.activities.base.BaseActivity;
import com.mietrix.restu_digital_quran.databinding.ActivityDownloadsBinding;
import com.mietrix.restu_digital_quran.views.BoldHeader;

public class ActivityDownloads extends BaseActivity {
    private ActivityDownloadsBinding mBinding;

    @Override
    protected boolean shouldInflateAsynchronously() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_downloads;
    }

    @Override
    protected void onActivityInflated(@NonNull View activityView, @Nullable Bundle savedInstanceState) {
        mBinding = ActivityDownloadsBinding.bind(activityView);
        initHeader(mBinding.header);
    }

    private void initHeader(BoldHeader header) {
        header.setTitleText("Downloads");
        header.setCallback(this::finish);
        header.setBackgroundColor(color(R.color.colorBGPage));
    }
}
