package com.artlite.pluginmanagerapi.callbacks;

import android.support.annotation.NonNull;

import com.artlite.pluginmanagerapi.models.PackageModel;

import java.util.List;

/**
 * Callback which provide to delegate of the creating of the model
 */
public interface ApiManagerCallback {

    /**
     * Method which provide the on result functional
     *
     * @param models {@link List} of the {@link PackageModel}
     */
    void onResult(@NonNull List<PackageModel> models);

}
