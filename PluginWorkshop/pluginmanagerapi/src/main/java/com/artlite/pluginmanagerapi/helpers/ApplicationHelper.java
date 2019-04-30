package com.artlite.pluginmanagerapi.helpers;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.models.PackageModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class which provide the additional functionality which provide to get information
 * about the installed applications
 */
public final class ApplicationHelper {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = ApplicationHelper.class.getSimpleName();

    /**
     * Method which provide the getting of the list of the installed applications
     *
     * @return list of the {@link PackageModel}
     */
    @NonNull
    public static List<PackageModel> getInstalledApplications(@Nullable final Context context) {
        List<PackageModel> models = new ArrayList<>();
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packs = packageManager.getInstalledPackages(0);
            Iterator<PackageInfo> iterator = packs.listIterator();
            while (iterator.hasNext()) {
                PackageInfo packageInfo = iterator.next();
                int mask = ApplicationInfo.FLAG_SYSTEM
                        | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
                if ((packageInfo.applicationInfo.flags & mask) == 0) {
                    try {
                        PackageModel model = new PackageModel();
                        model.setApplicationName(packageInfo.applicationInfo
                                .loadLabel(packageManager).toString());
                        model.setPackageName(packageInfo.packageName);
                        model.setVersion(packageInfo.versionName);
                        model.setVersionCode(packageInfo.versionCode);
                        model.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                        models.add(model);
                    } catch (Exception ex) {
                        Log.e(TAG, "getInstalledApplications: ", ex);
                    }
                }
            }
        }
        return models;
    }

}
