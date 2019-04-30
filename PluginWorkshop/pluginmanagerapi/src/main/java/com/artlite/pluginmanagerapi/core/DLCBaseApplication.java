package com.artlite.pluginmanagerapi.core;

import android.app.Application;
import android.util.Log;

import com.artlite.pluginmanagerapi.managers.DLCApplicationManager;
import com.artlite.pluginmanagerapi.managers.DLCContextManager;

/**
 * Class which provide the base application functional
 */
abstract class DLCBaseApplication extends Application {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCBaseApplication.class.getSimpleName();

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ---");
        Log.d(TAG, "onCreate: init of the DLCApplicationManager");
        DLCApplicationManager.init(this);
        Log.d(TAG, "onCreate: init of the DLCContextManager");
        DLCContextManager.init(this);
        Log.d(TAG, "onCreate: ---");
    }
}
