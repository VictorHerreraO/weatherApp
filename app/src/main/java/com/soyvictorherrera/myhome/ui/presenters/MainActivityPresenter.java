package com.soyvictorherrera.myhome.ui.presenters;

import android.util.Log;

import com.soyvictorherrera.myhome.domain.GetTemperature;
import com.soyvictorherrera.myhome.ui.BasePresenter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.View> {
    private final String TAG = MainActivityPresenter.class.getSimpleName();

    private GetTemperature getTemperature;

    @Inject
    public MainActivityPresenter(@Nonnull GetTemperature getTemperature) {
        this.getTemperature = getTemperature;
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        getTemperature.setDeviceId("ESP8266_home");
        getTemperature.execute(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: called!");
                getTemperature.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                try {
                    JSONArray items = jsonObject.getJSONArray("items");
                    if(items.length() > 0) {
                        int newTemp = items.getJSONObject(0).getInt("temperature");
                        getView().updateTemperature(newTemp);
                        int newHumid = items.getJSONObject(0).getInt("humidity");
                        getView().updateHumidity(newHumid);
                        long newTime = items.getJSONObject(0).getLong("timestamp");
                        getView().updateDateTime(parseMillis(newTime));
                    }
                } catch (JSONException e1) {
                    Log.e(TAG, "onNext: ", e1);
                }
            }
        });
    }

    private String parseMillis(long millis) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm aa EEE d, yyyy");
        return formatter.print(millis);
    }

    public interface View extends BasePresenter.View {
        void updateTemperature(int newTemperature);
        void updateHumidity(int newHumidity);
        void updateDateTime(String newDateTime);
    }
}
