package com.artlite.pluginmanagerapi.core;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.services.DLCBaseService;
import com.artlite.pluginmanagerapi.services.DLCPluginActionService;

/**
 * Class which provide the application for the plugin
 */
public abstract class DLCPluginApplication extends DLCBaseApplication {
    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCPluginApplication.class.getSimpleName();

    /**
     * Instance of the {@link DLCPluginApplication}
     */
    private static DLCPluginApplication instance;

    /**
     * Method which provide the getting of the instance of the {@link DLCPluginApplication}
     *
     * @return instance of the {@link DLCManagerApplication}
     */
    @Nullable
    public static DLCPluginApplication getInstance() {
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
     * Method which provide the checking if current plugin need to answer to
     * the current manager package. This allow to divide the plugins from the manager.
     *
     * @param managerPackage {@link String} value of the manager package
     * @return {@link Boolean} value if current plugin need to answer to the plugin
     */
    public abstract boolean isNeedAnswer(@NonNull String managerPackage);

    /**
     * Method which provide the add of the additional parameters before answering to the
     * plugin
     *
     * @param intent instance of the {@link Intent}
     */
    public abstract void pluginWillAnswer(@NonNull Intent intent);

    /**
     * Method which provide the task executing
     *
     * @param service instance of the {@link DLCPluginActionService}
     * @param handler instance of the {@link Handler}
     */
    public abstract void startTask(@NonNull DLCBaseService service,
                                   @NonNull final Handler handler);

    /**
     * Method which provide the task executing
     *
     * @param service instance of the {@link DLCPluginActionService}
     * @param handler instance of the {@link Handler}
     */
    public abstract void endTask(@NonNull DLCBaseService service,
                                 @NonNull final Handler handler);

}
