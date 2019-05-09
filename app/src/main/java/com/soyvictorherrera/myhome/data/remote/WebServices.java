package com.soyvictorherrera.myhome.data.remote;

import android.support.annotation.NonNull;

import com.soyvictorherrera.myhome.BuildConfig;
import com.soyvictorherrera.myhome.data.remote.domain.GetTemperatureResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by vHerrera on 04/02/2018.
 */

public interface WebServices {

    @Headers("x-api-key: rgitlL9JrB92niEuLtNci4HZkyfStlkV1LGVSZCe")
    @GET(BuildConfig.READ + BuildConfig.TEMPERATURE)
    Observable<GetTemperatureResponse> getTemperature(@NonNull @Query("device") String device,
                                                      @Query("start") Long start,
                                                      @Query("end") Long end);

}
