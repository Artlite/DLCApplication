package com.artlite.pluginmanagerapi.managers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.callbacks.ApiManagerCallback;
import com.artlite.pluginmanagerapi.constants.AppConstants;
import com.artlite.pluginmanagerapi.core.ManagerApplication;
import com.artlite.pluginmanagerapi.core.PluginApplication;
import com.artlite.pluginmanagerapi.models.PackageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class which provide the api interaction
 */
public final class ApiManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = ApiManager.class.getSimpleName();

    /**
     * Instance of the {@link ApiManager}
     */
    private static ApiManager instance;

    /**
     * Method which provide the getting of the instance of the {@link ApiManager}
     *
     * @return instance of the {@link ApiManager}
     */
    @NonNull
    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    /**
     * Instance of the {@link Set}
     */
    private volatile Set<String> packages = new HashSet<>();

    /**
     * Instance of the {@link Set}
     */
    private volatile Set<String> started = new HashSet<>();

    /**
     * Instance of the {@link Set}
     */
    private volatile Set<String> plugins = new HashSet<>();

    /**
     * Instance of the {@link Map}
     */
    private volatile Map<String, PackageModel> models = new HashMap<>();

    /**
     * Default constructor
     */
    private ApiManager() {
        Log.d(TAG, "ApiManager: default constructor");
    }

    /**
     * Method which provide the receive the plugins
     *
     * @param callback instance of the {@link ApiManagerCallback}
     */
    public void recievePlugins(@Nullable ApiManagerCallback callback) {
        if (callback == null) return;
        new PluginReceiver(callback).execute();
    }

    /**
     * Method which provide the getting plugins
     *
     * @return instance of the {@link List}
     */
    @NonNull
    public List<PackageModel> getPlugins() {
        final List<PackageModel> models = new ArrayList<>();
        final Iterator<String> iterator = this.plugins.iterator();
        while (iterator.hasNext()) {
            final String packageName = iterator.next();
            final PackageModel packageModel = this.models.get(packageName);
            if (packageModel != null) {
                packageModel.setEnabled(this.started.contains(packageModel.getPackageName()));
                models.add(packageModel);
            }
        }
        return models;
    }

    /**
     * Method which provide the updating of the application lists
     */
    private void update() {
        final List<PackageModel> models = ApplicationManager
                .getInstance().getInstalledApplications();
        final Iterator<PackageModel> iterator = models.listIterator();
        final String managerName = this.getManagerPackage();
        this.packages.clear();
        while (iterator.hasNext()) {
            final PackageModel model = iterator.next();
            try {
                this.packages.add(model.getPackageName());
                this.models.put(model.getPackageName(), model);
            } catch (Exception ex) {
                Log.e(TAG, "update: ", ex);
            }
        }
        if (managerName != null) {
            this.packages.remove(managerName);
            this.models.remove(managerName);
        }
    }

    /**
     * Method which provide the ping functionality to plugins
     */
    private synchronized void ping() {
        Log.d(TAG, "ping: pinging to the service");
        this.update();
        final Iterator<String> iterator = this.packages.iterator();
        final String name = this.getManagerPackage();
        while (iterator.hasNext()) {
            final String packageName = iterator.next();
            try {
                final Intent intent = new Intent();
                intent.setClassName(packageName, AppConstants.K_PLUGIN_LISTEN_SERVICE);
                intent.putExtra(AppConstants.K_KEY_PACKAGE, name);
                this.startService(intent);
            } catch (Exception ex) {
                Log.e(TAG, "ping: ", ex);
            }
        }
    }

    /**
     * Method which provide the answering to the plugin manager
     *
     * @param managerPackage {@link String} value of the manager package name
     */
    public synchronized void answer(@Nullable String managerPackage) {
        try {
            String name = this.getPluginPackage();
            final Intent intent = new Intent();
            intent.setClassName(managerPackage, AppConstants.K_MANAGER_LISTEN_SERVICE);
            intent.putExtra(AppConstants.K_KEY_PACKAGE, name);
            this.startService(intent);
        } catch (Exception ex) {
            Log.e(TAG, "answer: ", ex);
        }
    }

    /**
     * Method which provide the adding of the received package name
     *
     * @param packageName {@link String} value of the package name
     */
    public synchronized void add(@Nullable String packageName) {
        Log.d(TAG, "add: package was recieved -> " + packageName);
        this.plugins.add(packageName);
    }

    /**
     * Method which provide the start service
     *
     * @param intent instance of the {@link Intent}
     */
    private void startService(@Nullable Intent intent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextManager.getInstance()
                        .getContext().startForegroundService(intent);
            } else {
                ContextManager.getInstance()
                        .getContext().startService(intent);
            }
        } catch (Exception ex) {
            Log.e(TAG, "startService: ", ex);
        }
    }

    /**
     * Method which provide the start of the action for the {@link PackageModel}
     *
     * @param model instance of the {@link PackageModel}
     */
    public boolean start(@Nullable PackageModel model) {
        return this.start((model != null) ? model.getPackageName() : null);
    }

    /**
     * Method which provide the start of the action for the {@link PackageModel}
     *
     * @param model instance of the {@link String}
     */
    public boolean start(@Nullable String model) {
        try {
            final Intent intent = new Intent();
            intent.setClassName(model,
                    AppConstants.K_PLUGIN_ACTION_SERVICE);
            intent.putExtra(AppConstants.K_KEY_NEED_STOP, false);
            this.started.add(model);
            this.startService(intent);
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "start: ", ex);

        }
        return false;
    }

    /**
     * Method which provide the start of the action for the {@link PackageModel}
     *
     * @param model instance of the {@link PackageModel}
     */
    public boolean stop(@Nullable PackageModel model) {
        return this.stop((model != null) ? model.getPackageName() : null);
    }

    /**
     * Method which provide the start of the action for the {@link PackageModel}
     *
     * @param model instance of the {@link String}
     */
    public boolean stop(@Nullable String model) {
        try {
            final Intent intent = new Intent();
            intent.setClassName(model,
                    AppConstants.K_PLUGIN_ACTION_SERVICE);
            intent.putExtra(AppConstants.K_KEY_NEED_STOP, true);
            this.started.remove(model);
            this.startService(intent);
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "stop: ", ex);

        }
        return false;
    }

    /**
     * Method which provide the stop of the all services
     */
    public void stopAll() {
        final Iterator<String> iterator = this.started.iterator();
        while (iterator.hasNext()) {
            final String name = iterator.next();
            this.stop(name);
        }
    }

    /**
     * Method which provide the getting of the plugin package name
     *
     * @return {@link String} value of the plugin package name
     */
    @Nullable
    private String getPluginPackage() {
        try {
            return PluginApplication.getInstance().getPackageName();
        } catch (Exception ex) {
            Log.e(TAG, "getPluginPackage: ", ex);
        }
        return null;
    }

    /**
     * Method which provide the getting of the plugin manager package name
     *
     * @return {@link String} value of the plugin manager package name
     */
    @Nullable
    private String getManagerPackage() {
        try {
            return ManagerApplication.getInstance().getPackageName();
        } catch (Exception ex) {
            Log.e(TAG, "getPluginPackage: ", ex);
        }
        return null;
    }

    /**
     * Method which provide the
     */
    private static class PluginReceiver extends AsyncTask<Void, Void, List<PackageModel>> {

        /**
         * {@link String} constant of the TAG
         */
        private static final String TAG = PluginReceiver.class.getSimpleName();

        /**
         * Instance of the {@link ApiManagerCallback}
         */
        private final ApiManagerCallback callback;

        /**
         * Constructor which provide to create the {@link PluginReceiver} with parameters
         *
         * @param callback instance of the {@link ApiManagerCallback}
         */
        private PluginReceiver(ApiManagerCallback callback) {
            this.callback = callback;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         */
        @Override
        protected List<PackageModel> doInBackground(Void... voids) {
            final List<PackageModel> models = new ArrayList<>();
            ApiManager.getInstance().ping();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Log.e(TAG, "doInBackground: ", ex);
            }
            models.addAll(ApiManager.getInstance().getPlugins());
            return models;
        }

        /**
         * Method which provide the port result functionality
         *
         * @param list {@link List} of the {@link PackageModel}
         */
        @Override
        protected void onPostExecute(List<PackageModel> list) {
            super.onPostExecute(list);
            if (this.callback != null) {
                this.callback.onResult(list);
            }
        }
    }

}
