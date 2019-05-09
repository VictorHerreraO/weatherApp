package com.soyvictorherrera.myhome.domain;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.data.local.AppLocalData;
import com.soyvictorherrera.myhome.data.remote.AppRemoteData;

import java.util.List;

import android.support.annotation.NonNull;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import rx.Observable;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class GetTemperature extends BaseUseCase {

    public static final int ORIGIN_REMOTE_ONLY = 201;
    public static final int ORIGIN_LOCAL_ONLY = 200;

    private AppRemoteData mRemoteData;
    private AppLocalData mLocalData;

    @Getter
    @Setter
    private String deviceId;
    @Getter
    @Setter
    private Long start;
    @Getter
    @Setter
    private Long end;
    @Setter
    private int origin;

    @Inject
    public GetTemperature(@NonNull AppRemoteData mRemoteData, @NonNull AppLocalData mLocalData) {
        this.mRemoteData = mRemoteData;
        this.mLocalData = mLocalData;
    }

    @Override
    protected Observable<List<SensorReading>> buildObservableUseCase() {
        switch (origin) {
            case ORIGIN_REMOTE_ONLY:
                origin = 0;
                return mRemoteData.getSensorReadings(start, end);
            case ORIGIN_LOCAL_ONLY:
                origin = 0;
                return mLocalData.getSensorReadings(deviceId, start, end);
            default:
                return Observable.concat(
                        mRemoteData.getSensorReadings(start, end),
                        mLocalData.getSensorReadings(deviceId, start, end)
                );
        }
    }

}
