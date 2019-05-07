package com.artlite.pluginmanagerapi.callbacks;

import android.support.annotation.NonNull;

import com.artlite.pluginmanagerapi.models.DLCPackageModel;

import java.util.List;

/**
 * Callback which provide to delegate of the creating of the model
 */
public interface DLCManagerCallback {

    /**
     * Method which provide the on result functional
     *
     * @param models {@link List} of the {@link DLCPackageModel}
     */
    void onResult(@NonNull List<DLCPackageModel> models);

}
