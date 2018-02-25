package com.soyvictorherrera.myhome.domain;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.data.remote.AppRemoteData;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import rx.Observable;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class GetTemperature extends BaseUseCase {

    private AppRemoteData mRemoteData;

    @Getter
    @Setter
    private String deviceId;
    @Getter
    @Setter
    private Long start;
    @Getter
    @Setter
    private Long end;

    @Inject
    public GetTemperature(@Nonnull AppRemoteData mRemoteData) {
        this.mRemoteData = mRemoteData;
    }

    @Override
    protected Observable<List<SensorReading>> buildObservableUseCase() {
        return mRemoteData.getTemperature(deviceId, start, end);
    }

}
