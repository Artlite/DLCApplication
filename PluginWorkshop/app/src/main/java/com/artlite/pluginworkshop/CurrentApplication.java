package com.artlite.pluginworkshop;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.artlite.pluginmanagerapi.core.PluginApplication;


/**
 * Instance of the current application
 */
public class CurrentApplication extends PluginApplication {

    /**
     * {@link Integer}value of the interval
     */
    private final static int INTERVAL = 1000 * 10; //10 seconds

    /**
     * Instance of the {@link Handler}
     */
    private Handler handler;

    /**
     * {@link Boolean} value if the task needed
     */
    private boolean isNeedTask = true;

    /**
     * {@link Integer} value of the step
     */
    private int step = 0;

    /**
     * Instance of the {@link Runnable}
     */
    protected Runnable handlerTask = new Runnable() {
        @Override
        public void run() {
            if (isNeedTask) {
                execute();
                handler.postDelayed(handlerTask, INTERVAL);
            }
        }
    };

    /**
     * Method which provide the task executing
     *
     * @param handler instance of the {@link Handler}
     */
    @Override
    public void startTask(@NonNull Handler handler) {
        this.handler = handler;
        this.isNeedTask = true;
        this.step = 0;
        this.handler.post(handlerTask);
    }

    /**
     * Method which provide the task executing
     *
     * @param handler instance of the {@link Handler}
     */
    @Override
    public void endTask(@NonNull Handler handler) {
        this.isNeedTask = false;
    }

    /**
     * Method which provide the task executing
     */
    protected void execute() {
        final String packageName = getPackageName();
        if (packageName.equalsIgnoreCase("com.artlite.pluginworkshop1")) {
            beep();
        } else {
            toast();
        }
    }

    /**
     * Method which provide to show the toast
     */
    protected void toast() {
        Toast.makeText(getApplicationContext(),
                "Hello, World! #" + step, Toast.LENGTH_SHORT).show();
        step++;
    }

    /**
     * Method which provide to make a beep
     */
    protected void beep() {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

}
