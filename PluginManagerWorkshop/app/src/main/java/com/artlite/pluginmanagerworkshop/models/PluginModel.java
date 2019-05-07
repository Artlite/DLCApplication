package com.artlite.pluginmanagerworkshop.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artlite.adapteredrecyclerview.models.ARCell;
import com.artlite.adapteredrecyclerview.models.ARObject;
import com.artlite.pluginmanagerapi.models.PSPackageModel;
import com.artlite.pluginmanagerworkshop.ui.recycler.PluginRecyclerView;

/**
 * Class which provide the object for the view for the {@link PSPackageModel}
 */
@SuppressLint("ParcelCreator")
public class PluginModel extends ARObject {

    /**
     * Instance of the {@link PSPackageModel}
     */
    protected final PSPackageModel model;

    /**
     * Method which provide to create the {@link PluginModel} from {@link PSPackageModel}
     *
     * @param model instance of the {@link PSPackageModel}
     */
    public PluginModel(@NonNull PSPackageModel model) {
        this.model = model;
    }

    /**
     * Method which provide the getting of the current recycler item
     *
     * @param context current context
     * @return current instance for the Recycler item
     */
    @Override
    public ARCell getRecyclerItem(@NonNull Context context) {
        return new PluginRecyclerView(context);
    }

    /**
     * Method which provide the getting of the {@link PSPackageModel}
     *
     * @return instance of the {@link PSPackageModel}
     */
    public PSPackageModel getModel() {
        return model;
    }
}
