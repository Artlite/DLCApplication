package com.artlite.pluginmanagerapi.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.annotations.Nullable;
import com.artlite.pluginmanagerapi.constants.PSConstants;
import com.artlite.pluginmanagerapi.managers.PSApplicationManager;
import com.artlite.pluginmanagerapi.managers.PSContextManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Class which provide the base application functional
 */
abstract class PSBaseApplication extends Application {

    /**
     * Callback which provide the configuration {@link Intent} of the callback
     *
     * @see PSBaseApplication#startActivity(Class, OnConfigureCallback)
     */
    protected interface OnConfigureCallback {

        /**
         * Method which provide the configuration of the {@link Intent}
         *
         * @param intent instance of the {@link Intent}
         */
        void configure(@NonNull Intent intent);
    }

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSBaseApplication.class.getSimpleName();

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ---");
        Log.d(TAG, "onCreate: init of the PSApplicationManager");
        PSApplicationManager.init(this);
        Log.d(TAG, "onCreate: init of the PSContextManager");
        PSContextManager.init(this);
        Log.d(TAG, "onCreate: ---");
        if (this.getSecret().length() < PSConstants.K_CRYPT_KEY_MIN_LENGHT) {
            Exception exception = new Exception("Secret for the application should " +
                    "be more that " + PSConstants.K_CRYPT_KEY_MIN_LENGHT + " symbols");
            Log.e(TAG, "onCreate: ", exception);
        }
    }

    /**
     * Method which provide the getting of the instance of the {@link Context}
     *
     * @return instance of the {@link Context}
     */
    protected Context getContext() {
        return this.getApplicationContext();
    }

    /**
     * Method which provide the start activity
     *
     * @param aClass instance of the {@link Class}
     */
    protected void startActivity(@NonNull final Class aClass) {
        this.startActivity(aClass, null);
    }

    /**
     * Method which provide the start activity
     *
     * @param aClass   instance of the {@link Class}
     * @param callback instance of the {@link OnConfigureCallback}
     */
    protected void startActivity(@NonNull final Class aClass,
                                 @Nullable final OnConfigureCallback callback) {
        Intent intent = new Intent(this.getContext(), aClass);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        if (callback != null) {
            callback.configure(intent);
        }
        this.getContext().startActivity(intent);
    }

    /**
     * Method which provide the getting of the secret
     * (WARNING SHOULD BE MORE THAN {@link PSConstants}.K_CRYPT_KEY_MIN_LENGHT SYMBOLS)
     *
     * @return {@link String} value of the secret
     */
    @NonNull
    public abstract String getSecret();
}
