package com.artlite.pluginmanagerapi.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import java.util.UUID;

/**
 * Class which provide the base service
 */
abstract class BaseService extends Service {

    /**
     * {@link String} constant of the channel id
     */
    protected static final String CHANNEL_ID = UUID.randomUUID().toString();

    /**
     * Instance of the {@link Handler}
     */
    protected Handler handler = new Handler();

    /**
     * Method which provide the create service functional
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.applyAndroidOreoFix();
        }
    }

    /**
     * Method which provide to applying of the android oreo fix
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void applyAndroidOreoFix() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_NONE;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new Notification.Builder(this)
                .setOngoing(true)
                .setChannelId(CHANNEL_ID)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_NONE)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        this.startForeground(101, notification);
    }

}
