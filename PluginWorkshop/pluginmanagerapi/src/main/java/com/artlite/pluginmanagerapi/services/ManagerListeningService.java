package com.artlite.pluginmanagerapi.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.artlite.pluginmanagerapi.constants.AppConstants;
import com.artlite.pluginmanagerapi.core.ManagerApplication;
import com.artlite.pluginmanagerapi.managers.ApiManager;

public class ManagerListeningService extends BaseService {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = ManagerListeningService.class.getSimpleName();

    /**
     * Method which provide the on start service functionality
     *
     * @param intent  instance of the {@link Intent}
     * @param flags   {@link Integer} value of the flags
     * @param startId {@link Integer} value of the start id
     * @return {@link Integer} value of the start command
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ManagerApplication.getInstance() != null) {
            try {
                final String name = intent.getStringExtra(AppConstants.K_KEY_PACKAGE);
                ApiManager.getInstance().add(name);
            } catch (Exception ex) {
                Log.e(TAG, "onStartCommand: ", ex);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.
     *
     * @param intent The Intent that was used to bind to this service
     * @return Return an IBinder through which clients can call on to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
