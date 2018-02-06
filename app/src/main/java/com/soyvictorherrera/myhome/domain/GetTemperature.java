package com.soyvictorherrera.myhome.domain;

import com.soyvictorherrera.myhome.data.remote.AppRemoteData;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class GetTemperature extends BaseUseCase {

    private AppRemoteData mRemoteData;

    private String deviceId;

    @Inject
    public GetTemperature(@Nonnull AppRemoteData mRemoteData) {
        this.mRemoteData = mRemoteData;
    }

    @Override
    protected Observable buildObservableUseCase() {
        return mRemoteData.getTemperature(deviceId);
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
