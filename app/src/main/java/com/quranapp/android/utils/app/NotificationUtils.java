/*
 * (c) Faisal Khan. Created on 18/2/2022.
 */
package com.quranapp.android.utils.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.quranapp.android.R;
import com.quranapp.android.utils.receivers.CrashReceiver;

public class NotificationUtils {
    public static final String CHANNEL_ID_DEFAULT = "default";
    private static final String CHANNEL_NAME_DEFAULT = "Default Channel";
    private static final String CHANNEL_DESC_DEFAULT = "Miscellaneous notifications";

    public static final String CHANNEL_ID_VOTD = "votd";
    private static final String CHANNEL_NAME_VOTD = "Verse of The Day";
    private static final String CHANNEL_DESC_VOTD = "Daily verse reminder notifications";

    public static final String CHANNEL_ID_RECITATION_PLAYER = "recitation_player";
    private static final String CHANNEL_NAME_RECITATION_PLAYER = "Recitation Player";
    private static final String CHANNEL_DESC_RECITATION_PLAYER = "Recitation Player notifications";

    public static final String CHANNEL_ID_DOWNLOADS = "downloads";
    private static final String CHANNEL_NAME_DOWNLOADS = "Downloads";
    private static final String CHANNEL_DESC_DOWNLOADS = "Notifications for downloads";

    public static void createNotificationChannels(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(createDefaultChannel());
                notificationManager.createNotificationChannel(createVOTDChannel());
                notificationManager.createNotificationChannel(createDownloadsChannel());
                notificationManager.createNotificationChannel(createRecitationChannel());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createDefaultChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID_DEFAULT,
                CHANNEL_NAME_DEFAULT,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(CHANNEL_DESC_DEFAULT);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setVibrationPattern(new long[]{500, 500});

        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.enableVibration(true);

        channel.setSound(
                Settings.System.DEFAULT_NOTIFICATION_URI,
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
        );

        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createVOTDChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID_VOTD,
                CHANNEL_NAME_VOTD,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription(CHANNEL_DESC_VOTD);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setVibrationPattern(new long[]{500, 500});

        channel.enableLights(true);
        channel.setShowBadge(true);
        channel.enableVibration(true);

        channel.setSound(
                Settings.System.DEFAULT_NOTIFICATION_URI,
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
        );

        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createDownloadsChannel() {
        return createChannel(
                CHANNEL_ID_DOWNLOADS,
                CHANNEL_NAME_DOWNLOADS,
                CHANNEL_DESC_DOWNLOADS
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createRecitationChannel() {
        return createChannel(
                CHANNEL_ID_RECITATION_PLAYER,
                CHANNEL_NAME_RECITATION_PLAYER,
                CHANNEL_DESC_RECITATION_PLAYER
        );
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private static NotificationChannel createChannel(String channelId, String channelName, String desc) {
        NotificationChannel channel = new NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(desc);
        channel.setVibrationPattern(null);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        channel.enableLights(false);
        channel.setSound(null, null);
        channel.enableVibration(false);

        return channel;
    }

    public static Notification createEmptyNotif(Context ctx, String channelId) {
        return new NotificationCompat.Builder(ctx, channelId)
                .setContentTitle("")
                .setSmallIcon(R.drawable.dr_logo)
                .setContentText("")
                .build();
    }

    public static void showCrashNotification(Context ctx, String stackTraceString) {
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = flag | PendingIntent.FLAG_IMMUTABLE;
        }

        Intent copyIntent = new Intent(ctx, CrashReceiver.class);
        copyIntent.setAction(CrashReceiver.CRASH_ACTION_COPY_LOG);
        copyIntent.putExtra(Intent.EXTRA_TEXT, stackTraceString);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID_DEFAULT);
        builder.setContentTitle(ctx.getString(R.string.lastCrashLog))
                .setSmallIcon(R.drawable.dr_logo)
                .setContentText(stackTraceString)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(stackTraceString))
                .addAction(
                        new NotificationCompat.Action.Builder(
                                R.drawable.icon_copy,
                                ctx.getString(R.string.strLabelCopy),
                                PendingIntent.getBroadcast(ctx, 0, copyIntent, flag)
                        ).build()
                );

        Notification notification = builder.build();

        NotificationManager notificationManager = ContextCompat.getSystemService(ctx, NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.notify(CrashReceiver.NOTIFICATION_ID_CRASH, notification);
        }
    }
}
