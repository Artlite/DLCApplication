package com.artlite.pluginmanagerapi.core;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public abstract class PluginApplication extends BaseApplication {

    /**
     * Instance of the {@link PluginApplication}
     */
    private static PluginApplication instance;

    /**
     * Method which provide the getting of the instance of the {@link PluginApplication}
     *
     * @return instance of the {@link ManagerApplication}
     */
    @Nullable
    public static PluginApplication getInstance() {
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
     * Method which provide the task executing
     *
     * @param handler instance of the {@link Handler}
     */
    public abstract void startTask(@NonNull final Handler handler);

    /**
     * Method which provide the task executing
     *
     * @param handler instance of the {@link Handler}
     */
    public abstract void endTask(@NonNull final Handler handler);

}
