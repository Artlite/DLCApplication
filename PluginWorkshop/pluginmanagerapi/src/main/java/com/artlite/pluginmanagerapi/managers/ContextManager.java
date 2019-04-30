package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Class which provide the context container functionality
 */
public final class ContextManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = ContextManager.class.getSimpleName();

    /**
     * Instance of the {@link ApplicationManager}
     */
    private static ContextManager instance;

    /**
     * Method which provide the getting of the instance of the {@link ApplicationManager}
     *
     * @return instance of the {@link ApplicationManager}
     */
    @Nullable
    public static ContextManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link ApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        if (instance == null) {
            instance = new ContextManager(context);
        }
    }

    /**
     * Instance of the {@link WeakReference}
     */
    private final WeakReference<Context> contextReference;

    /**
     * Constructor which provide to create the {@link ApplicationManager} with parameters
     *
     * @param context instance of the {@link Context}
     */
    private ContextManager(@NonNull Context context) {
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
