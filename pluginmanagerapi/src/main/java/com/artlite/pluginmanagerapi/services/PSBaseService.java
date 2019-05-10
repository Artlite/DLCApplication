package com.artlite.pluginmanagerapi.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.Nullable;
import com.artlite.pluginmanagerapi.constants.PSConstants;
import com.artlite.pluginmanagerapi.core.PSPluginApplication;
import com.artlite.pluginmanagerapi.helpers.PSCryptHelper;

import java.util.UUID;

/**
 * Class which provide the base service
 */
public abstract class PSBaseService extends Service {

    /**
     * {@link String} constant of the TAG value
     */
    private static final String TAG = PSBaseService.class.getSimpleName();

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
            this.applyOreoFix();
        }
    }

    /**
     * Method which provide to applying of the android oreo fix
     */
    private void applyOreoFix() {
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
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setOngoing(true)
                    .setChannelId(CHANNEL_ID)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_NONE)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
        }
        this.startForeground(101, notification);
    }

    /**
     * Method which provide the stop service functional
     */
    public void stopService() {
        if (PSPluginApplication.getInstance() != null) {
            PSPluginApplication.getInstance()
                    .endTask(this, this.handler);
        }
        try {
            this.stopForeground(true);
            this.stopSelf();
        } catch (Exception ex) {
            Log.e(TAG, "stopService: ", ex);
        }
    }

    /**
     * Method which provide the crypt {@link String}
     *
     * @param value {@link String} value of the content
     * @param key   {@link String} value of the key
     * @return {@link String} value of the crypted value
     */
    @Nullable
    protected String crypt(@Nullable String value,
                           @Nullable String key) {
        try {
            if (key.length() < PSConstants.K_CRYPT_KEY_MIN_LENGHT) {
                throw new Exception("Secret for the application should " +
                        "be more that " + (PSConstants.K_CRYPT_KEY_MIN_LENGHT - 1) + " symbols");
            }
            return PSCryptHelper.encrypt(value, key);
        } catch (Exception ex) {
            Log.e(TAG, "crypt: ", ex);
        }
        return null;
    }


    /**
     * Method which provide the getting of the crypted extra
     *
     * @param intent    instance of the {@link Intent}
     * @param intentKey {@link String} value of the intent key
     * @param cryptKey  {@link String} value of the key
     * @return {@link String} value of the encrypted value
     */
    protected String getEncryptedExtra(@Nullable Intent intent,
                                       @Nullable String intentKey,
                                       @Nullable String cryptKey) {
        try {
            if (cryptKey.length() < PSConstants.K_CRYPT_KEY_MIN_LENGHT) {
                throw new Exception("Secret for the application should " +
                        "be more that " + (PSConstants.K_CRYPT_KEY_MIN_LENGHT - 1) + " symbols");
            }
            final String value = intent.getStringExtra(intentKey);
            return PSCryptHelper.decrypt(value, cryptKey);
        } catch (Exception ex) {
            Log.e(TAG, "getEncryptedExtra: ", ex);
        }
        return null;
    }

}
