package com.artlite.pluginmanagerapi.callbacks;

import com.artlite.pluginmanagerapi.annotations.NonNull;
import com.artlite.pluginmanagerapi.models.PSPackageModel;

import java.util.List;

/**
 * Callback which provide to delegate of the creating of the model
 */
public interface PSManagerCallback {

    /**
     * Method which provide the on result functional
     *
     * @param models {@link List} of the {@link PSPackageModel}
     */
    void onResult(@NonNull List<PSPackageModel> models);

}
