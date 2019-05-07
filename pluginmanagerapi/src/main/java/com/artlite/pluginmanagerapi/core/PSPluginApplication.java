package com.artlite.pluginmanagerapi.core;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.annotations.Nullable;
import com.artlite.pluginmanagerapi.services.PSBaseService;
import com.artlite.pluginmanagerapi.services.PSPluginActionService;

/**
 * Class which provide the application for the plugin
 */
public abstract class PSPluginApplication extends PSBaseApplication {
    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSPluginApplication.class.getSimpleName();

    /**
     * Instance of the {@link PSPluginApplication}
     */
    private static PSPluginApplication instance;

    /**
     * Method which provide the getting of the instance of the {@link PSPluginApplication}
     *
     * @return instance of the {@link PSManagerApplication}
     */
    @Nullable
    public static PSPluginApplication getInstance() {
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
     * @param intent  instance of the {@link Intent}
     * @param service instance of the {@link PSPluginActionService}
     * @param handler instance of the {@link Handler}
     */
    public abstract void startTask(@NonNull Intent intent,
                                   @NonNull PSBaseService service,
                                   @NonNull final Handler handler);

    /**
     * Method which provide the task executing
     *
     * @param service instance of the {@link PSPluginActionService}
     * @param handler instance of the {@link Handler}
     */
    public abstract void endTask(@NonNull PSBaseService service,
                                 @NonNull final Handler handler);

}
