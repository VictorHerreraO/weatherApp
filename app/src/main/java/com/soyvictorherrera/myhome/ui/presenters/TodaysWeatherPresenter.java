package com.soyvictorherrera.myhome.ui.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.soyvictorherrera.myhome.Utilities.DateTimeUtils;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.domain.GetTemperature;
import com.soyvictorherrera.myhome.ui.BasePresenter;
import com.soyvictorherrera.myhome.ui.contracts.TodaysWeatherContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by vHerrera on 26/02/2018.
 */

public class TodaysWeatherPresenter extends BasePresenter<TodaysWeatherContract.View> {

    /* U S E   C A S E S */
    private final  GetTemperature getTemperature;

    /* S U B S C R I B E R S */
    private final GetTemperatureSubscriber getTemperatureSubscriber;

    @Inject
    public TodaysWeatherPresenter(
            @NonNull GetTemperature getTemperature
            ) {
        this.getTemperature = getTemperature;

        getTemperatureSubscriber = new GetTemperatureSubscriber();
    }

    public void getTemperatureData() {
        getTemperature.setDeviceId("ESP8266_home");
        getTemperature.setStart(DateTimeUtils.getTodayStartInMillis());
        getTemperature.setEnd(DateTimeUtils.getCurrentMillis());
        getTemperature.execute(getTemperatureSubscriber);
    }

    public void detach() {
        setView(null);
    }

    private List<Entry> averageData(List<SensorReading> sensorReadings) {
        List<Entry> entries = new ArrayList<>();
        try {
            if (sensorReadings != null && !sensorReadings.isEmpty()) {
                Iterator<SensorReading> iterator = sensorReadings.iterator();
                while (iterator.hasNext()) {
                    // Promediar 5 lecturas en una y agregar a nueva lista
                    int i;
                    int temperatureSum = 0;
                    SensorReading reading = null;
                    for (i = 0; i < 5; i++) {
                        if (iterator.hasNext()) {
                            reading = iterator.next();
                            temperatureSum += reading.getTemperature();
                        } else  {
                            break;
                        }
                    }
                    if (reading != null && i > 0) {
                        // Agregar el promedio al conjunto de datos
                        entries.add(new Entry(reading.getTimestamp(), (temperatureSum / i)));
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "averageData: ", ex);
        }
        return entries;
    }

    ///////////////////////////
    /* S U B S C R I B E R S */
    ///////////////////////////

    private final class GetTemperatureSubscriber extends Subscriber<List<SensorReading>> {
        @Override
        public void onCompleted() {
            getTemperature.unsubscribe();
            Log.d(TAG, "onCompleted: un-subscribed");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
        }

        @Override
        public void onNext(List<SensorReading> sensorReadings) {
            try {
                if(getView() != null) {
                    Log.d(TAG, "onNext: sensorReadings.size is " + sensorReadings.size());
                    List<Entry> entries = averageData(sensorReadings);
                    getView().drawTemperature(entries);
                }
            } catch (NullPointerException ex) {
                Log.e(TAG, "onNext: ", ex);
            }
        }
    }

}
