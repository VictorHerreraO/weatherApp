package com.soyvictorherrera.myhome.data;

import rx.Observable;

/**
 * Created by vHerrera on 25/02/2018.
 */

public interface AppData {

    Observable getTemperature(String device, Long start, Long end);

}
