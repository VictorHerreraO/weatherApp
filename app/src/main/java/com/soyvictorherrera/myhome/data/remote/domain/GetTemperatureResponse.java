package com.soyvictorherrera.myhome.data.remote.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.soyvictorherrera.myhome.data.entiities.SensorReading;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by vHerrera on 25/02/2018.
 */

public class GetTemperatureResponse {

    @SerializedName("error")
    @Expose
    @Getter
    @Setter
    private Boolean error;
    @SerializedName("message")
    @Expose
    @Getter
    @Setter
    private String message;
    @SerializedName("item_count")
    @Expose
    @Getter
    @Setter
    private Integer itemCount;
    @SerializedName("items")
    @Expose
    @Getter
    @Setter
    private List<SensorReading> items;

}
