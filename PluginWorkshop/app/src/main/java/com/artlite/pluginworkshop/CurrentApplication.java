package com.artlite.pluginworkshop;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.artlite.pluginmanagerapi.core.DLCPluginApplication;
import com.artlite.pluginmanagerapi.services.DLCBaseService;
import com.artlite.pluginmanagerapi.services.DLCPluginActionService;


/**
 * Instance of the current application
 */
public class CurrentApplication extends DLCPluginApplication {

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
     * {@link String} value of the message
     */
    private String message;

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
                this.message + "" + step, Toast.LENGTH_SHORT).show();
        step++;
    }

    /**
     * Method which provide to make a beep
     */
    protected void beep() {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    /**
     * Method which provide the checking if current plugin need to answer to
     * the current manager package. This allow to divide the plugins from the manager.
     *
     * @param managerPackage {@link String} value of the manager package
     * @return {@link Boolean} value if current plugin need to answer to the plugin
     */
    @Override
    public boolean isNeedAnswer(@NonNull String managerPackage) {
        return true;
    }

    /**
     * Method which provide the add of the additional parameters before answering to the
     * plugin
     *
     * @param intent instance of the {@link Intent}
     */
    @Override
    public void pluginWillAnswer(@NonNull Intent intent) {

    }

    /**
     * Method which provide the task executing
     *
     * @param intent  instance of the {@link Intent}
     * @param service instance of the {@link DLCPluginActionService}
     * @param handler instance of the {@link Handler}
     */
    @Override
    public void startTask(@NonNull Intent intent,
                          @NonNull DLCBaseService service,
                          @NonNull Handler handler) {
        this.handler = handler;
        this.isNeedTask = true;
        this.step = 0;
        this.handler.post(handlerTask);
        this.message = intent.getStringExtra("com.artlite.message");
        if (this.message == null) {
            this.message = "Hello, World! #";
        }
    }

    /**
     * Method which provide the task executing
     *
     * @param service instance of the {@link DLCBaseService}
     * @param handler instance of the {@link Handler}
     */
    @Override
    public void endTask(@NonNull DLCBaseService service,
                        @NonNull Handler handler) {
        this.isNeedTask = false;
    }
}
