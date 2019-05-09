package com.soyvictorherrera.myhome.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soyvictorherrera.myhome.data.AppData;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.data.local.AppLocalData;
import com.soyvictorherrera.myhome.data.remote.domain.GetTemperatureResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.subjects.AsyncSubject;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class AppRemoteData implements AppData {
    private static final String TAG = AppRemoteData.class.getSimpleName();

    private Retrofit mRetrofit;
    private AppLocalData mLocalData;

    @Inject
    public AppRemoteData(@NonNull Retrofit mRetrofit, @NonNull AppLocalData mLocalData) {
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

    public Observable<List<SensorReading>> getSensorReadings(@Nullable Long start, @Nullable Long end) {
        Log.d(TAG, "getSensorReadings() called with: start = [" + start + "], end = [" + end + "]");
        final AsyncSubject<List<SensorReading>> subject = AsyncSubject.create();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    HashMap records = (HashMap) dataSnapshot.getValue();
                    if (records != null) {
                        List<SensorReading> readings = new ArrayList<>(records.size());
                        for (Object key : records.keySet()) {
                            Log.d(TAG, "onDataChange: key = [" + key + "]");
                            HashMap<String, Object> record = (HashMap<String, Object>) records.get(key);
                            SensorReading reading = new SensorReading(
                                    null,
                                    Integer.valueOf(record.get("h").toString()),
                                    "",
                                    Integer.valueOf(record.get("t").toString()),
                                    Long.valueOf(record.get("ts").toString())
                            );
                            readings.add(reading);
                        }
                        subject.onNext(readings);
                    } else {
                        subject.onNext(Collections.emptyList());
                    }
                }
                subject.onCompleted();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                subject.onError(new Exception(databaseError.getMessage()));
            }
        };

        if (start == null || end == null) {
            // return last reading
            FirebaseDatabase.getInstance()
                    .getReference("/records")
                    .orderByChild("ts")
                    .limitToLast(1)
                    .addListenerForSingleValueEvent(listener);
        } else {
            // return readings in range
            FirebaseDatabase.getInstance()
                    .getReference("/records")
                    .orderByChild("ts")
                    .startAt(start)
                    .endAt(end)
                    .addListenerForSingleValueEvent(listener);
        }

        return subject.doOnNext(mLocalData::saveSensorReadings);
    }

}
