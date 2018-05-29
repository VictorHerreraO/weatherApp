package com.soyvictorherrera.myhome.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.soyvictorherrera.myhome.Utilities.PreferenceUtils;
import com.soyvictorherrera.myhome.data.AppData;
import com.soyvictorherrera.myhome.data.entiities.DaoSession;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.data.entiities.SensorReadingDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by vHerrera on 27/05/2018.
 */
public class AppLocalData implements AppData {
    private final String TAG = AppLocalData.class.getSimpleName();

    private SensorReadingDao mSensorReadingDao;
    private PreferenceUtils mPreferences;

    @Inject
    public AppLocalData(@NonNull DaoSession mDaoSession, @NonNull PreferenceUtils mPreferences) {
        this.mSensorReadingDao = mDaoSession.getSensorReadingDao();
        this.mPreferences = mPreferences;
    }

    @Override
    public Observable<List<SensorReading>> getSensorReadings(String device, Long start, Long end) {
        Log.d(TAG, "getSensorReadings() called with: device = [" + device + "], start = [" + start + "], end = [" + end + "]");
        try {
            List<SensorReading> readings;
            if (start == null || end == null) {
                readings = new ArrayList<>();
                readings.add(getLastReading());
            } else {
                readings = mSensorReadingDao
                        .queryBuilder()
                        .where(
                                SensorReadingDao.Properties.DeviceId.eq(device),
                                SensorReadingDao.Properties.Timestamp.between(start, end)
                        ).build()
                        .list();
            }
            return Observable.just(readings);
        } catch (Exception ex) {
            Log.e(TAG, "getSensorReadings: ", ex);
            return Observable.error(ex);
        }
    }

    public void saveSensorReadings(List<SensorReading> readings) {
        Log.d(TAG, "saveSensorReadings() called with: readings.size = [" + readings.size() + "]");
        try {
            mSensorReadingDao.insertInTx(readings);
            SensorReading lastReading = getLastReading();
            mPreferences.setLastReadingTimestamp(lastReading.getTimestamp());
        } catch (Exception ex) {
            Log.e(TAG, "saveSensorReadings: ", ex);
        }
    }

    private SensorReading getLastReading() throws Exception {
        Log.d(TAG, "getLastReading() called");
        return mSensorReadingDao
                .queryBuilder()
                .orderDesc(SensorReadingDao.Properties.Timestamp)
                .limit(1)
                .build()
                .uniqueOrThrow();
    }


}
