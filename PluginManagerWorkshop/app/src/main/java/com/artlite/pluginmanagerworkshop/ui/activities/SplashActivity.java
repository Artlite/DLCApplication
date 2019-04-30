package com.artlite.pluginmanagerworkshop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.artlite.pluginmanagerworkshop.R;

/**
 * Class which provide the interface for the splash screen
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Instance of the {@link Handler}
     */
    private final Handler handler = new Handler();

    /**
     * Perform initialization of all fragments and loaders
     *
     * @param bundle instance of {@link Bundle}
     */
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, PluginsActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

}
