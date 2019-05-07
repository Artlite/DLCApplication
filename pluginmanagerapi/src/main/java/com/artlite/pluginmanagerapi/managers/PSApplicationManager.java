package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.annotations.Nullable;
import com.artlite.pluginmanagerapi.helpers.PSApplicationHelper;
import com.artlite.pluginmanagerapi.models.PSPackageModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Manager which provide the application manager functional
 */
public final class PSApplicationManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSApplicationManager.class.getSimpleName();

    /**
     * Instance of the {@link PSApplicationManager}
     */
    private static PSApplicationManager instance;

    /**
     * Method which provide the getting of the instance of the {@link PSApplicationManager}
     *
     * @return instance of the {@link PSApplicationManager}
     */
    @Nullable
    public static PSApplicationManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link PSApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        Log.d(TAG, "init: create the manager from context");
        if (instance == null) {
            Log.d(TAG, "init: instance is null");
            instance = new PSApplicationManager(context);
        } else {
            Log.d(TAG, "init: instance is already inited");
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
    private PSApplicationManager(@NonNull Context context) {
        Log.d(TAG, "PSApplicationManager: default constructor");
        this.contextReference = new WeakReference<>(context);
    }

    /**
     * Method which provide the getting {@link Context}
     *
     * @return instance of the {@link Context}
     */
    @Nullable
    private Context getContext() {
        try {
            return this.contextReference.get();
        } catch (Exception ex) {
            Log.e(TAG, "getContext: ", ex);
        }
        return null;
    }

    /**
     * Method which provide the getting of the list of the installed applications
     *
     * @return list of the {@link PSPackageModel}
     */
    @NonNull
    public List<PSPackageModel> getInstalledApplications() {
        return PSApplicationHelper.getInstalledApplications(this.getContext());
    }


}
