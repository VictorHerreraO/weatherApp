package com.soyvictorherrera.myhome.ui.presenters;

import android.util.Log;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.domain.GetTemperature;
import com.soyvictorherrera.myhome.ui.BasePresenter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.View> {
    private final String TAG = MainActivityPresenter.class.getSimpleName();

    private final GetTemperature getTemperature;
    private final Subscriber<List<SensorReading>> getLastTemperatureSubscriber;

    @Inject
    public MainActivityPresenter(@Nonnull GetTemperature getTemperature) {
        this.getTemperature = getTemperature;
        getLastTemperatureSubscriber = new GetLastTemperatureSubscriber();
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        getTemperature.setDeviceId("ESP8266_home");
        getTemperature.execute(getLastTemperatureSubscriber);
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
            getView().isLoading(false);
            Log.d(TAG, "onCompleted: un-subscribed");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
            onCompleted();
        }

        @Override
        public void onNext(List<SensorReading> sensorReadings) {
            try {
                Log.d(TAG, "onNext: sensorReadings.size is " + sensorReadings.size());
                if (!sensorReadings.isEmpty()) {
                    SensorReading lastReading = sensorReadings.get(0);
                    View view = getView();

                    view.toggleOffline(false);
                    view.updateTemperature(lastReading.getTemperature());
                    view.updateHumidity(lastReading.getHumidity());
                    view.updateDateTime(parseMillis(lastReading.getTimestamp()));
                }
            } catch (NullPointerException ex) {
                Log.e(TAG, "onNext: ", ex);
            }
        }
    }

    /* C O N T R A C T */
    public interface View extends BasePresenter.View {
        void updateTemperature(int newTemperature);
        void updateHumidity(int newHumidity);
        void updateDateTime(String newDateTime);
        void toggleOffline(boolean state);
        void isLoading(boolean loading);
    }
}
