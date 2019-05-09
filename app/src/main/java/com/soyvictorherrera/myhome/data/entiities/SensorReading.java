package com.soyvictorherrera.myhome.data.entiities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by vHerrera on 25/02/2018.
 */

@Entity(indexes = {
        @Index(value = "timestamp")
})
public class SensorReading {

    @Id(autoincrement = true)
    private Long id;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @NotNull
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("temperature")
    @Expose
    private Integer temperature;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

    @Generated(hash = 147786835)
    public SensorReading(Long id, Integer humidity, @NotNull String deviceId, Integer temperature,
                         Long timestamp) {
        this.id = id;
        this.humidity = humidity;
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1462980359)
    public SensorReading() {
    }

    public Integer getHumidity() {
        return this.humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
