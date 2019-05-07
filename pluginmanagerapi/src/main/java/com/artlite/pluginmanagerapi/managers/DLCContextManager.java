package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Class which provide the context container functionality
 */
public final class DLCContextManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCContextManager.class.getSimpleName();

    /**
     * Instance of the {@link DLCApplicationManager}
     */
    private static DLCContextManager instance;

    /**
     * Method which provide the getting of the instance of the {@link DLCApplicationManager}
     *
     * @return instance of the {@link DLCApplicationManager}
     */
    @Nullable
    public static DLCContextManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link DLCApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        if (instance == null) {
            instance = new DLCContextManager(context);
        }
    }

    /**
     * Instance of the {@link WeakReference}
     */
    private final WeakReference<Context> contextReference;

    /**
     * Constructor which provide to create the {@link DLCApplicationManager} with parameters
     *
     * @param context instance of the {@link Context}
     */
    private DLCContextManager(@NonNull Context context) {
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
