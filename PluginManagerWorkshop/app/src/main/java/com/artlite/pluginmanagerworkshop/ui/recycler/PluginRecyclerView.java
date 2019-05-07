package com.artlite.pluginmanagerworkshop.ui.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.artlite.adapteredrecyclerview.models.ARCell;
import com.artlite.pluginmanagerapi.managers.PSApiManager;
import com.artlite.pluginmanagerworkshop.R;
import com.artlite.pluginmanagerworkshop.models.PluginModel;

/**
 * Class which provide the view representation for the {@link PluginModel}
 */
public class PluginRecyclerView
        extends ARCell<PluginModel>
        implements CompoundButton.OnCheckedChangeListener {

    /**
     * Instance of the {@link TextView}
     */
    private TextView labelTitle;

    /**
     * Instance of the {@link TextView}
     */
    private TextView labelDescription;

    /**
     * Instance of the {@link AppCompatCheckBox}
     */
    private AppCompatCheckBox checkBox;

    /**
     * Default constructor
     *
     * @param context context to set
     */
    public PluginRecyclerView(@NonNull Context context) {
        super(context);
    }

    /**
     * Method which provide the setting up for the current recycler item
     *
     * @param baseObject current object
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void setUp(@NonNull PluginModel baseObject) {
        this.labelTitle.setText(baseObject.getIndex() +
                " : " + baseObject.getModel().getApplicationName());
        this.labelDescription.setText(baseObject.getModel().getPackageName());
        this.checkBox.setChecked(baseObject.getModel().isEnabled());
    }

    /**
     * Method which provide to getting of the layout ID
     *
     * @return layout ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.recycle_layout_plugin;
    }

    /**
     * Method which provide the action when view will create
     */
    @Override
    protected void onCreateView() {
        this.labelTitle = this.findViewById(R.id.label_title);
        this.labelDescription = this.findViewById(R.id.label_description);
        this.checkBox = this.findViewById(R.id.checkbox);
        this.checkBox.setOnCheckedChangeListener(this);
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final PluginModel model = getObject();
        if (model != null) {
            if (isChecked) {
                PSApiManager.getInstance().start(model.getModel());
            } else {
                PSApiManager.getInstance().stop(model.getModel());
            }
        }
    }
}
