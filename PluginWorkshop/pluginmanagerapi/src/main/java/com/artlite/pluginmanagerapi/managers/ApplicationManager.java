package com.artlite.pluginmanagerapi.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.helpers.ApplicationHelper;
import com.artlite.pluginmanagerapi.models.PackageModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Manager which provide the application manager functional
 */
public final class ApplicationManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = ApplicationManager.class.getSimpleName();

    /**
     * Instance of the {@link ApplicationManager}
     */
    private static ApplicationManager instance;

    /**
     * Method which provide the getting of the instance of the {@link ApplicationManager}
     *
     * @return instance of the {@link ApplicationManager}
     */
    @Nullable
    public static ApplicationManager getInstance() {
        return instance;
    }

    /**
     * Method which provide the creating of the {@link ApplicationManager}
     *
     * @param context instance of the {@link Context}
     */
    public static void init(@NonNull Context context) {
        if (instance == null) {
            instance = new ApplicationManager(context);
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
    private ApplicationManager(@NonNull Context context) {
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
     * @return list of the {@link PackageModel}
     */
    @NonNull
    public List<PackageModel> getInstalledApplications() {
        return ApplicationHelper.getInstalledApplications(this.getContext());
    }


}
