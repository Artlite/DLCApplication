package com.artlite.pluginmanagerapi.services;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.artlite.pluginmanagerapi.constants.PSConstants;
import com.artlite.pluginmanagerapi.core.PSPluginApplication;
import com.artlite.pluginmanagerapi.managers.PSApiManager;

public class PSPluginListeningService extends PSBaseService {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSPluginListeningService.class.getSimpleName();

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
        Log.d(TAG, "onStartCommand: ---");
        if (PSPluginApplication.getInstance() != null) {
            Log.d(TAG, "onStartCommand: plugin application isn't null");
            try {
                final String name = intent.getStringExtra(PSConstants.K_KEY_PACKAGE);
                Log.d(TAG, "onStartCommand: manager package " + name);
                if (PSPluginApplication.getInstance().isNeedAnswer(name)) {
                    Log.d(TAG, "onStartCommand: plugin is need to answer");
                    PSApiManager.getInstance().answer(name);
                } else {
                    Log.d(TAG, "onStartCommand: plugin isn't need to answer");
                }
            } catch (Exception ex) {
                Log.e(TAG, "onStartCommand: ", ex);
            }
        }
        this.stopService();
        Log.d(TAG, "onStartCommand: ---");
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
