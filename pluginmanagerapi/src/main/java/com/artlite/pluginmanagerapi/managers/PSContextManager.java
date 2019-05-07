package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Class which provide the context container functionality
 */
public final class PSContextManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSContextManager.class.getSimpleName();

    /**
     * Instance of the {@link PSApplicationManager}
     */
    private static PSContextManager instance;

    /**
     * Method which provide the getting of the instance of the {@link PSApplicationManager}
     *
     * @return instance of the {@link PSApplicationManager}
     */
    @Nullable
    public static PSContextManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link PSApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        if (instance == null) {
            instance = new PSContextManager(context);
        }
    }

    /**
     * Instance of the {@link WeakReference}
     */
    private final WeakReference<Context> contextReference;

    /**
     * Constructor which provide to create the {@link PSApplicationManager} with parameters
     *
     * @param context instance of the {@link Context}
     */
    private PSContextManager(@NonNull Context context) {
        this.contextReference = new WeakReference<>(context);
    }

    /**
     * Method which provide the getting {@link Context}
     *
     * @return instance of the {@link Context}
     */
    @Nullable
    public Context getContext() {
        try {
            return this.contextReference.get();
        } catch (Exception ex) {
            Log.e(TAG, "getContext: ", ex);
        }
        return null;
    }
}
