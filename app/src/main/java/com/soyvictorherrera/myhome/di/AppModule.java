package com.soyvictorherrera.myhome.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vHerrera on 04/02/2018.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

}
