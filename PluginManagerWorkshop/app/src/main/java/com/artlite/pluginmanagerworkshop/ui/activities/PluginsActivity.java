package com.artlite.pluginmanagerworkshop.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredBaseCallback;
import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredRefreshCallback;
import com.artlite.adapteredrecyclerview.core.ARView;
import com.artlite.adapteredrecyclerview.events.AREvent;
import com.artlite.pluginmanagerapi.callbacks.DLCManagerCallback;
import com.artlite.pluginmanagerapi.managers.DLCApiManager;
import com.artlite.pluginmanagerapi.models.DLCPackageModel;
import com.artlite.pluginmanagerworkshop.R;
import com.artlite.pluginmanagerworkshop.models.PluginModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class which provide to show of the available plugins
 */
public class PluginsActivity extends AppCompatActivity implements DLCManagerCallback {

    /**
     * Instance of the {@link ARView}
     */
    private ARView viewRecycle;

    /**
     * Perform initialization of all fragments and loaders
     *
     * @param bundle instance of {@link Bundle}
     */
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_pluggins);
        this.viewRecycle = (ARView) this.findViewById(R.id.view_recycle);
        this.setTitle(R.string.text_plugins);
        this.viewRecycle.init(new GridLayoutManager(this, 1),
                this.baseCallback, this.refreshCallback);
        this.viewRecycle.setIsNeedResfresh(true);
        this.viewRecycle.setRefreshColoursRes(R.color.colorAccent, R.color.colorPrimary);
        this.receivePlugins();
    }

    /**
     * Method which provide the receiving plugins
     */
    private void receivePlugins() {
        this.viewRecycle.showRefresh();
        DLCApiManager.getInstance()
                .receivePlugins(this);
    }

    //==============================================================================================
    //                                      CALLBACKS
    //==============================================================================================

    /**
     * Instance of the {@link OnAdapteredBaseCallback}
     */
    private final OnAdapteredBaseCallback<PluginModel> baseCallback
            = new OnAdapteredBaseCallback<PluginModel>() {

        /**
         * Method which provide the action when user press on the channel object
         *
         * @param index  current index
         * @param object current object
         */
        @Override
        public void onItemClick(int index,
                                @NonNull PluginModel object) {

        }

        /**
         * Method which provide the action when user doing the long press on item
         *
         * @param index  index
         * @param object object
         */
        @Override
        public void onItemLongClick(int index,
                                    @NonNull PluginModel object) {

        }

        /**
         * Method which provide the action listening
         *
         * @param AREvent event
         * @param index   index
         * @param object  object
         */
        @Override
        public void onActionReceived(@NonNull AREvent AREvent,
                                     int index,
                                     @NonNull PluginModel object) {

        }
    };

    /**
     * Instance of the {@link OnAdapteredRefreshCallback}
     */
    private OnAdapteredRefreshCallback refreshCallback
            = new OnAdapteredRefreshCallback() {
        /**
         * Method which provide the swipe down to refresh listening
         */
        @Override
        public void onRefreshData() {
            receivePlugins();
        }
    };

    /**
     * Method which provide the on result functional
     *
     * @param models {@link List} of the {@link DLCPackageModel}
     */
    @Override
    public void onResult(@NonNull List<DLCPackageModel> models) {
        final Iterator<DLCPackageModel> iterator = models.listIterator();
        final List<PluginModel> pluginModels = new ArrayList<>();
        while (iterator.hasNext()) {
            pluginModels.add(new PluginModel(iterator.next()));
        }
        viewRecycle.set(pluginModels);
        viewRecycle.hideRefresh();
    }
}
