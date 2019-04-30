package com.artlite.pluginmanagerapi.core;

import android.support.annotation.Nullable;

import com.artlite.pluginmanagerapi.managers.ApiManager;

/**
 * Class which provide the extension for the application
 */
public abstract class ManagerApplication extends BaseApplication {

    /**
     * Instance of the {@link ManagerApplication}
     */
    private static ManagerApplication instance;

    /**
     * Method which provide the getting of the instance of the {@link ManagerApplication}
     *
     * @return instance of the {@link ManagerApplication}
     */
    @Nullable
    public static ManagerApplication getInstance() {
        return instance;
    }

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Method which provide the terminate functionality
     */
    @Override
    public void onTerminate() {
        ApiManager.getInstance().stopAll();
        super.onTerminate();
    }
}
