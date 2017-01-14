package com.lithium.authproject;

import android.app.Application;

import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.StormpathLogger;

import timber.log.Timber;

/**
 * Created by lithium on 1/14/2017.
 */

public class AuthApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        if (BuildConfig.DEBUG) {
            Stormpath.setLogLevel(StormpathLogger.VERBOSE);
        }

        StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                .baseUrl("https://full-orbit.apps.stormpath.io/")
                .build();
        Stormpath.init(this, stormpathConfiguration);
    }
}
