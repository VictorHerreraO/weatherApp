package com.soyvictorherrera.myhome.Utilities;

import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by vHerrera on 26/02/2018.
 */

public class DateTimeUtils {
    private static final String TAG = DateTimeUtils.class.getSimpleName();

    public static long getTodayStartInMillis() {
        long millis = DateTime
                .now()
                .withTimeAtStartOfDay()
                .getMillis();
        Log.d(TAG, "getTodayStartInMillis() returned: " + millis);
        return millis;
    }

    public static long getCurrentMillis() {
        long millis = DateTime
                .now()
                .getMillis();
        Log.d(TAG, "getCurrentMillis() returned: " + millis);
        return millis;
    }

}
