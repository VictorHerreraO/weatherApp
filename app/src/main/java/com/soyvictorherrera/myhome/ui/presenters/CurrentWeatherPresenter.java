package com.soyvictorherrera.myhome.ui.presenters;

import android.util.Log;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.domain.GetTemperature;
import com.soyvictorherrera.myhome.ui.BasePresenter;
import com.soyvictorherrera.myhome.ui.contracts.CurrentWeatherContract;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import android.support.annotation.NonNull;
import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by vHerrera on 25/02/2018.
 */

public class CurrentWeatherPresenter extends BasePresenter<CurrentWeatherContract.View> {

    private final GetTemperature getTemperature;
    private final Subscriber<List<SensorReading>> getLastTemperatureSubscriber;

    @Inject
    public CurrentWeatherPresenter(@NonNull GetTemperature getTemperature) {
        this.getTemperature = getTemperature;
        getLastTemperatureSubscriber = new GetLastTemperatureSubscriber();
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        super.setView(view);
        getTemperature.setDeviceId("ESP8266_home");
        getTemperature.setOrigin(GetTemperature.ORIGIN_REMOTE_ONLY);
        getTemperature.execute(getLastTemperatureSubscriber);
    }

    public void detach() {
        super.setView(null);
    }

    private String parseMillis(long millis) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm aa EEE d, yyyy");
        return formatter.print(millis);
    }

    ///////////////////////////
    /* S U B S C R I B E R S */
    ///////////////////////////

    private final class GetLastTemperatureSubscriber extends Subscriber<List<SensorReading>> {
        @Override
        public void onCompleted() {
            getTemperature.unsubscribe();
            if (getView() != null) {
                getView().isLoading(false);
            }
            Log.d(TAG, "onCompleted: un-subscribed");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
            getView().toggleOffline(true);
            onCompleted();
        }

        @Override
        public void onNext(List<SensorReading> sensorReadings) {
            try {
                Log.d(TAG, "onNext: sensorReadings.size is " + sensorReadings.size());
                if (getView() != null && !sensorReadings.isEmpty()) {
                    CurrentWeatherContract.View v = getView();
                    SensorReading lastReading = sensorReadings.get(0);

                    v.toggleOffline(false);
                    v.updateTemperature(lastReading.getTemperature());
                    v.updateHumidity(lastReading.getHumidity());
                    v.updateDateTime(parseMillis(lastReading.getTimestamp()));
                }
            } catch (NullPointerException ex) {
                Log.e(TAG, "onNext: ", ex);
            }
        }
    }

}
