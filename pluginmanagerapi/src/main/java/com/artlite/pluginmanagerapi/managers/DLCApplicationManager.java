package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.helpers.DLCApplicationHelper;
import com.artlite.pluginmanagerapi.models.DLCPackageModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Manager which provide the application manager functional
 */
public final class DLCApplicationManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCApplicationManager.class.getSimpleName();

    /**
     * Instance of the {@link DLCApplicationManager}
     */
    private static DLCApplicationManager instance;

    /**
     * Method which provide the getting of the instance of the {@link DLCApplicationManager}
     *
     * @return instance of the {@link DLCApplicationManager}
     */
    @Nullable
    public static DLCApplicationManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link DLCApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        Log.d(TAG, "init: create the manager from context");
        if (instance == null) {
            Log.d(TAG, "init: instance is null");
            instance = new DLCApplicationManager(context);
        } else {
            Log.d(TAG, "init: instance is already inited");
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
    private DLCApplicationManager(@NonNull Context context) {
        Log.d(TAG, "DLCApplicationManager: default constructor");
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
     * @return list of the {@link DLCPackageModel}
     */
    @NonNull
    public List<DLCPackageModel> getInstalledApplications() {
        return DLCApplicationHelper.getInstalledApplications(this.getContext());
    }


}
