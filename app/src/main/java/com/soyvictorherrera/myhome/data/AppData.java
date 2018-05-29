package com.soyvictorherrera.myhome.data;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;

import java.util.List;

import rx.Observable;

/**
 * Created by vHerrera on 25/02/2018.
 */

public interface AppData {

    Observable<List<SensorReading>> getSensorReadings(String device, Long start, Long end);

}
