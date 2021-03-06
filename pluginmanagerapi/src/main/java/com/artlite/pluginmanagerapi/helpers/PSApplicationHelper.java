package com.artlite.pluginmanagerapi.helpers;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.annotations.Nullable;
import com.artlite.pluginmanagerapi.models.PSPackageModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class which provide the additional functionality which provide to get information
 * about the installed applications
 */
public final class PSApplicationHelper {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSApplicationHelper.class.getSimpleName();

    /**
     * Method which provide the getting of the list of the installed applications
     *
     * @return list of the {@link PSPackageModel}
     */
    @NonNull
    public static List<PSPackageModel> getInstalledApplications(@Nullable final Context context) {
        Log.d(TAG, "getInstalledApplications: ---");
        List<PSPackageModel> models = new ArrayList<>();
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
                        PSPackageModel model = new PSPackageModel();
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
        Log.d(TAG, "getInstalledApplications: models " + models);
        Log.d(TAG, "getInstalledApplications: ---");
        return models;
    }

}
