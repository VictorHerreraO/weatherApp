package com.soyvictorherrera.myhome.di;

import com.soyvictorherrera.myhome.ui.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vHerrera on 04/02/2018.
 */

@Singleton
@Component(modules = {AppModule.class, Modules.class})
public interface Components {
    // Activities
    void inject(MainActivity activity);
}
