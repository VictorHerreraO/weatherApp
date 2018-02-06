package com.soyvictorherrera.myhome.data.remote;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class AppRemoteData {
    private static final String TAG = AppRemoteData.class.getSimpleName();

    private Retrofit mRetrofit;

    @Inject
    public AppRemoteData(@Nonnull Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    public Observable<JSONObject> getTemperature(String device) {
        return mRetrofit
                .create(WebServices.class)
                .getTemperature(device, null, null)
                .map(new Func1<ResponseBody, JSONObject>() {
                    @Override
                    public JSONObject call(ResponseBody responseBody) {
                        try {
                            JSONObject json = new JSONObject(responseBody.string());
                            Log.i(TAG, "call: response was " + json.toString());
                            return json;
                        } catch (Exception e) {
                            Log.e(TAG, "call: ", e);
                            return new JSONObject();
                        }
                    }
                });
    }

}
