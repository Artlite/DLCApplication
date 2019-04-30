package com.artlite.pluginmanagerapi.core;

import android.app.Application;

import com.artlite.pluginmanagerapi.managers.ApplicationManager;
import com.artlite.pluginmanagerapi.managers.ContextManager;

/**
 * Class which provide the base application functional
 */
abstract class BaseApplication extends Application {

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationManager.init(this);
        ContextManager.init(this);
    }
}
