package com.soyvictorherrera.myhome;

import android.util.Log;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;
import com.soyvictorherrera.myhome.domain.Averager;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final String TAG = ExampleUnitTest.class.getSimpleName();

    ThreadLocalRandom random;

    @Test
    public void addition_isCorrect() throws Exception {
        List<SensorReading> readings = generateRandomReadings(50);
        Averager avg = new Averager(readings);
        Iterator it = avg.iterator();
//        reading.po
    }

    private List<SensorReading> generateRandomReadings(final int size) {
        List<SensorReading> readings = new ArrayList<>();
        random = ThreadLocalRandom.current();

        for (int i = 0; i < size; i++) {
            SensorReading reading = new SensorReading();
            reading.setDeviceId("ESP8206");
            reading.setTimestamp(DateTime.now().getMillis());
            reading.setHumidity(random.nextInt(20, 40));
            reading.setTemperature(random.nextInt(15, 30));

            readings.add(reading);
        }

        return readings;
    }
}