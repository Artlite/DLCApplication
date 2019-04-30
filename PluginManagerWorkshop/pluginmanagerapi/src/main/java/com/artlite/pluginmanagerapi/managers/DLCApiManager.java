package com.artlite.pluginmanagerapi.managers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.pluginmanagerapi.callbacks.DLCManagerCallback;
import com.artlite.pluginmanagerapi.constants.DLCConstants;
import com.artlite.pluginmanagerapi.core.DLCManagerApplication;
import com.artlite.pluginmanagerapi.core.DLCPluginApplication;
import com.artlite.pluginmanagerapi.models.DLCPackageModel;

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
public final class DLCApiManager {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = DLCApiManager.class.getSimpleName();

    /**
     * Instance of the {@link DLCApiManager}
     */
    private static DLCApiManager instance;

    /**
     * Method which provide the getting of the instance of the {@link DLCApiManager}
     *
     * @return instance of the {@link DLCApiManager}
     */
    @NonNull
    public static DLCApiManager getInstance() {
        if (instance == null) {
            instance = new DLCApiManager();
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
    private volatile Map<String, DLCPackageModel> models = new HashMap<>();

    /**
     * Default constructor
     */
    private DLCApiManager() {
        Log.d(TAG, "DLCApiManager: default constructor");
    }

    /**
     * Method which provide the receive the plugins
     *
     * @param callback instance of the {@link DLCManagerCallback}
     */
    public void receivePlugins(@Nullable DLCManagerCallback callback) {
        Log.d(TAG, "receivePlugins: ---");
        if (callback == null) {
            Log.d(TAG, "receivePlugins: callback is null");
            return;
        }
        Log.d(TAG, "receivePlugins: callback isn't null");
        new PluginReceiver(callback).execute();
        Log.d(TAG, "receivePlugins: ---");
    }

    /**
     * Method which provide the getting plugins
     *
     * @return instance of the {@link List}
     */
    @NonNull
    public List<DLCPackageModel> getPlugins() {
        Log.d(TAG, "getPlugins: ---");
        final List<DLCPackageModel> models = new ArrayList<>();
        final Iterator<String> iterator = this.plugins.iterator();
        while (iterator.hasNext()) {
            final String packageName = iterator.next();
            final DLCPackageModel packageModel = this.models.get(packageName);
            if (packageModel != null) {
                packageModel.setEnabled(this.started.contains(packageModel.getPackageName()));
                models.add(packageModel);
            }
        }
        Log.d(TAG, "getPlugins: models " + models);
        Log.d(TAG, "getPlugins: ---");
        return models;
    }

    /**
     * Method which provide the updating of the application lists
     */
    private void update() {
        Log.d(TAG, "update: ---");
        final List<DLCPackageModel> models = DLCApplicationManager
                .getInstance().getInstalledApplications();
        Log.d(TAG, "update: installed applications " + models);
        final Iterator<DLCPackageModel> iterator = models.listIterator();
        final String managerName = this.getManagerPackage();
        Log.d(TAG, "update: manager name " + managerName);
        this.packages.clear();
        while (iterator.hasNext()) {
            final DLCPackageModel model = iterator.next();
            Log.d(TAG, "update: model " + model);
            try {
                this.packages.add(model.getPackageName());
                this.models.put(model.getPackageName(), model);
            } catch (Exception ex) {
                Log.e(TAG, "update: ", ex);
            }
        }
        if (managerName != null) {
            Log.d(TAG, "update: remove manager itself");
            this.packages.remove(managerName);
            this.models.remove(managerName);
        }
        Log.d(TAG, "update: ---");
    }

    /**
     * Method which provide the ping functionality to plugins
     */
    private synchronized void ping() {
        Log.d(TAG, "ping: pinging to the service");
        Log.d(TAG, "ping: ---");
        this.update();
        final Iterator<String> iterator = this.packages.iterator();
        final String name = this.getManagerPackage();
        Log.d(TAG, "ping: manager name " + name);
        while (iterator.hasNext()) {
            final String packageName = iterator.next();
            Log.d(TAG, "ping: package manager " + packageName);
            try {
                final Intent intent = new Intent();
                intent.setClassName(packageName, DLCConstants.K_PLUGIN_LISTEN_SERVICE);
                intent.putExtra(DLCConstants.K_KEY_PACKAGE, name);
                Log.d(TAG, "ping: pinging intent " + intent);
                this.startService(intent);
            } catch (Exception ex) {
                Log.e(TAG, "ping: ", ex);
            }
        }
        Log.d(TAG, "ping: ---");
    }

    /**
     * Method which provide the answering to the plugin manager
     *
     * @param managerPackage {@link String} value of the manager package name
     */
    public synchronized void answer(@Nullable String managerPackage) {
        Log.d(TAG, "answer: ---");
        Log.d(TAG, "answer: manager name " + managerPackage);
        try {
            String name = this.getPluginPackage();
            Log.d(TAG, "answer: plugin name " + name);
            final Intent intent = new Intent();
            intent.setClassName(managerPackage, DLCConstants.K_MANAGER_LISTEN_SERVICE);
            intent.putExtra(DLCConstants.K_KEY_PACKAGE, name);
            if (DLCPluginApplication.getInstance() != null) {
                DLCPluginApplication.getInstance().pluginWillAnswer(intent);
            }
            Log.d(TAG, "answer: starting intent " + intent);
            this.startService(intent);
        } catch (Exception ex) {
            Log.e(TAG, "answer: ", ex);
        }
        Log.d(TAG, "answer: ---");
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
        Log.d(TAG, "startService: ---");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "startService: Build.VERSION.SDK_INT " +
                        ">= Build.VERSION_CODES.O");
                DLCContextManager.getInstance()
                        .getContext().startForegroundService(intent);
            } else {
                Log.d(TAG, "startService: Build.VERSION.SDK_INT " +
                        "< Build.VERSION_CODES.O");
                DLCContextManager.getInstance()
                        .getContext().startService(intent);
            }
        } catch (Exception ex) {
            Log.e(TAG, "startService: ", ex);
        }
        Log.d(TAG, "startService: ---");
    }

    /**
     * Method which provide the start of the action for the {@link DLCPackageModel}
     *
     * @param model instance of the {@link DLCPackageModel}
     */
    public boolean start(@Nullable DLCPackageModel model) {
        return this.start((model != null) ? model.getPackageName() : null);
    }

    /**
     * Method which provide the start of the action for the {@link DLCPackageModel}
     *
     * @param model instance of the {@link String}
     */
    public boolean start(@Nullable String model) {
        Log.d(TAG, "start: ---");
        Log.d(TAG, "start: model name " + model);
        try {
            final Intent intent = new Intent();
            intent.setClassName(model,
                    DLCConstants.K_PLUGIN_ACTION_SERVICE);
            intent.putExtra(DLCConstants.K_KEY_NEED_STOP, false);
            this.started.add(model);
            if (DLCManagerApplication.getInstance() != null) {
                DLCManagerApplication.getInstance()
                        .pluginWillStart(model, intent);
            }
            Log.d(TAG, "start: starting intent " + intent);
            this.startService(intent);
            Log.d(TAG, "start: ---");
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "start: ", ex);

        }
        Log.d(TAG, "start: ---");
        return false;
    }

    /**
     * Method which provide the start of the action for the {@link DLCPackageModel}
     *
     * @param model instance of the {@link DLCPackageModel}
     */
    public boolean stop(@Nullable DLCPackageModel model) {
        return this.stop((model != null) ? model.getPackageName() : null);
    }

    /**
     * Method which provide the start of the action for the {@link DLCPackageModel}
     *
     * @param model instance of the {@link String}
     */
    public boolean stop(@Nullable String model) {
        Log.d(TAG, "stop: ---");
        Log.d(TAG, "stop: model name " + model);
        try {
            final Intent intent = new Intent();
            intent.setClassName(model,
                    DLCConstants.K_PLUGIN_ACTION_SERVICE);
            intent.putExtra(DLCConstants.K_KEY_NEED_STOP, true);
            this.started.remove(model);
            if (DLCManagerApplication.getInstance() != null) {
                DLCManagerApplication.getInstance()
                        .pluginWillStop(model, intent);
            }
            Log.d(TAG, "stop: starting intent " + intent);
            this.startService(intent);
            Log.d(TAG, "stop: ---");
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "stop: ", ex);

        }
        Log.d(TAG, "stop: ---");
        return false;
    }

    /**
     * Method which provide the stop of the all services
     */
    public void stopAll() {
        Log.d(TAG, "stopAll: ---");
        final Iterator<String> iterator = this.started.iterator();
        while (iterator.hasNext()) {
            final String name = iterator.next();
            Log.d(TAG, "stopAll: service name " + name);
            this.stop(name);
        }
        Log.d(TAG, "stopAll: ---");
    }

    /**
     * Method which provide the getting of the plugin package name
     *
     * @return {@link String} value of the plugin package name
     */
    @Nullable
    private String getPluginPackage() {
        try {
            return DLCPluginApplication.getInstance().getPackageName();
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
            return DLCManagerApplication.getInstance().getPackageName();
        } catch (Exception ex) {
            Log.e(TAG, "getPluginPackage: ", ex);
        }
        return null;
    }

    /**
     * Method which provide the
     */
    private static class PluginReceiver extends AsyncTask<Void,
            Void, List<DLCPackageModel>> {

        /**
         * {@link String} constant of the TAG
         */
        private static final String TAG = PluginReceiver.class.getSimpleName();

        /**
         * Instance of the {@link DLCManagerCallback}
         */
        private final DLCManagerCallback callback;

        /**
         * Constructor which provide to create the {@link PluginReceiver} with parameters
         *
         * @param callback instance of the {@link DLCManagerCallback}
         */
        private PluginReceiver(@Nullable DLCManagerCallback callback) {
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
        protected List<DLCPackageModel> doInBackground(Void... voids) {
            final List<DLCPackageModel> models = new ArrayList<>();
            DLCApiManager.getInstance().ping();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Log.e(TAG, "doInBackground: ", ex);
            }
            models.addAll(DLCApiManager.getInstance().getPlugins());
            return models;
        }

        /**
         * Method which provide the port result functionality
         *
         * @param list {@link List} of the {@link DLCPackageModel}
         */
        @Override
        protected void onPostExecute(List<DLCPackageModel> list) {
            super.onPostExecute(list);
            if (this.callback != null) {
                this.callback.onResult(list);
            }
        }
    }

}
