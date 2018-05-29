package com.soyvictorherrera.myhome.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.soyvictorherrera.myhome.data.AppData;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.data.local.AppLocalData;
import com.soyvictorherrera.myhome.data.remote.domain.GetTemperatureResponse;

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
    private AppLocalData mLocalData;

    @Inject
    public AppRemoteData(@Nonnull Retrofit mRetrofit, @NonNull AppLocalData mLocalData) {
        this.mRetrofit = mRetrofit;
        this.mLocalData = mLocalData;
    }

    @Override
    public Observable<List<SensorReading>> getSensorReadings(@NonNull String device, Long start, Long end) {
        Log.d(TAG, "getSensorReadings() called with: device = [" + device + "], start = [" + start + "], end = [" + end + "]");
        return mRetrofit
                .create(WebServices.class)
                .getTemperature(device, start, end)
                .map(GetTemperatureResponse::getItems)
                .doOnNext(mLocalData::saveSensorReadings);
    }

}
