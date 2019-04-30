package com.artlite.pluginmanagerworkshop.core;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.artlite.pluginmanagerapi.core.DLCManagerApplication;

/**
 * Class which provide the extension for the application
 */
public class CurrentApplication extends DLCManagerApplication {

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Method which provide the configure the intent before plugin start
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    @Override
    public void pluginWillStart(@NonNull String pluginName,
                                @NonNull Intent intent) {

    }

    /**
     * Method which provide the configure the intent before plugin stopped
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    @Override
    public void pluginWillStop(@NonNull String pluginName,
                               @NonNull Intent intent) {

    }

}
