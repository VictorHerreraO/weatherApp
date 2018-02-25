package com.soyvictorherrera.myhome.data.remote;

import android.support.annotation.NonNull;

import com.soyvictorherrera.myhome.data.AppData;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class AppRemoteData implements AppData {
    private static final String TAG = AppRemoteData.class.getSimpleName();

    private Retrofit mRetrofit;

    @Inject
    public AppRemoteData(@Nonnull Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    @Override
    public Observable<List<SensorReading>> getTemperature(@NonNull String device, Long start, Long end) {
        return mRetrofit
                .create(WebServices.class)
                .getTemperature(device, start, end)
                .map(getTemperatureResponse -> getTemperatureResponse.getItems());
    }

}
