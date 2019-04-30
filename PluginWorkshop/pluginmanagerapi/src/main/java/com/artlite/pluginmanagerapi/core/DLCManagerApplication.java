package com.artlite.pluginmanagerapi.core;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.managers.DLCApiManager;

/**
 * Class which provide the extension for the manager application
 */
public abstract class DLCManagerApplication extends DLCBaseApplication {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCManagerApplication.class.getSimpleName();

    /**
     * Instance of the {@link DLCManagerApplication}
     */
    private static DLCManagerApplication instance;

    /**
     * Method which provide the getting of the instance of the {@link DLCManagerApplication}
     *
     * @return instance of the {@link DLCManagerApplication}
     */
    @Nullable
    public static DLCManagerApplication getInstance() {
        return instance;
    }

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ---");
        super.onCreate();
        Log.d(TAG, "onCreate: instance = this");
        instance = this;
        Log.d(TAG, "onCreate: ---");
    }

    /**
     * Method which provide the terminate functionality
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ---");
        DLCApiManager.getInstance().stopAll();
        super.onTerminate();
        Log.d(TAG, "onTerminate: ---");
    }

    /**
     * Method which provide the configure the intent before plugin start
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    public abstract void pluginWillStart(@NonNull String pluginName,
                                         @NonNull Intent intent);

    /**
     * Method which provide the configure the intent before plugin stopped
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    public abstract void pluginWillStop(@NonNull String pluginName,
                                        @NonNull Intent intent);
}
