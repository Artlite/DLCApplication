package com.artlite.pluginmanagerworkshop.core;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.artlite.pluginmanagerapi.core.PSManagerApplication;

/**
 * Class which provide the extension for the application
 */
public class CurrentApplication extends PSManagerApplication {

    /**
     * Method which provide the action when the application was created
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Method which provide the getting of the secret
     * (WARNING SHOULD BE MORE THAN 20 SYMBOLS)
     *
     * @return {@link String} value of the secret
     */
    @Override
    public String getSecret() {
        return "BOc8lVt24PI5dEPGZMZFifPJJzBxT67DEe3QgxMtBlihP6Ijc8dj4u0eytRdZgTdFBvCv" +
                "N8hRrJ3dAWrlCzUdSPj5EBsurhnmUC0Cfbra9xgHAV2dlYIuteoTEGONhzJSK0IwSqvGUHd" +
                "jfCHMmkilfFr0Rig8LRsIqWHlHRfBI61ajfZnyAxxvcRXwCKCqJybfWnhtMvFiSN0KMcpD" +
                "1YOF6fY7gWxVhh4AxYLSPKER7AWxEHvU4jYjFjdHG3PerlEdYgCmQKErOJhzUXzMWGs8ZO" +
                "EFLrz3LctvT0OUoxNWpetPUQ8SyU7DIgBhMp2nhvzFSmogTFEOimvDX5sAC53H3pnuMNB0" +
                "IEp15sobcS121AuRNXSsuFKmSN1tzfE8HgHUvCODQmfPk824ri5RNLm4YRHIxOEgUb68AL" +
                "aLmG1UTXxQfTwcQwYFw1Gnd5cswc1bLrWBecIOu7hlMpXA9KSMEZepCOBKrxOliV7BGHMv" +
                "XVWxmbGoJD";
    }

    /**
     * Method which provide the configure the intent before plugin start
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    @Override
    public void pluginWillStart(@NonNull String pluginName,
                                @NonNull Intent intent) {
    }

    /**
     * Method which provide the configure the intent before plugin stopped
     *
     * @param pluginName {@link String} value of the plugin name
     * @param intent     instance of the {@link Intent}
     */
    @Override
    public void pluginWillStop(@NonNull String pluginName,
                               @NonNull Intent intent) {

    }

}
