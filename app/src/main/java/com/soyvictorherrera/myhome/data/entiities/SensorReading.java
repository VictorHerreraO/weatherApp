package com.soyvictorherrera.myhome.data.entiities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by vHerrera on 25/02/2018.
 */

public class SensorReading {

    @SerializedName("humidity")
    @Expose
    @Getter
    @Setter
    private Integer humidity;
    @SerializedName("deviceId")
    @Expose
    @Getter
    @Setter
    private String deviceId;
    @SerializedName("temperature")
    @Expose
    @Getter
    @Setter
    private Integer temperature;
    @SerializedName("timestamp")
    @Expose
    @Getter
    @Setter
    private Long timestamp;

}
