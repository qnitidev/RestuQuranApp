package com.mietrix.restu_digital_quran;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatDelegate;

import com.mietrix.restu_digital_quran.utils.app.NotificationUtils;
import com.mietrix.restu_digital_quran.utils.app.ThemeUtils;
import com.mietrix.restu_digital_quran.utils.exceptions.CustomExceptionHandler;
import com.mietrix.restu_digital_quran.utils.univ.FileUtils;

public class QuranApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        initBeforeBaseAttach(base);
        super.attachBaseContext(base);
    }

    private void initBeforeBaseAttach(Context base) {
        FileUtils.appFilesDir = base.getFilesDir();
        updateTheme(base);
    }

    private void updateTheme(Context base) {
        AppCompatDelegate.setDefaultNightMode(ThemeUtils.resolveThemeModeFromSP(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationUtils.createNotificationChannels(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String process = getProcessName();
            if (!getPackageName().equals(process)) {
                WebView.setDataDirectorySuffix(process);
            }
        }

        // Handler for uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
    }
}
