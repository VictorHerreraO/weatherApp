package com.soyvictorherrera.myhome;

import android.app.Application;

import com.soyvictorherrera.myhome.di.AppModule;
import com.soyvictorherrera.myhome.di.Components;
import com.soyvictorherrera.myhome.di.DaggerComponents;
import com.soyvictorherrera.myhome.di.Modules;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class BaseApplication extends Application {

    private Components mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializar Dagger
        mComponent = DaggerComponents
                .builder()
                .appModule(new AppModule(this))
                .modules(new Modules())
                .build();
    }

    public Components getComponent() {
        return mComponent;
    }

}
