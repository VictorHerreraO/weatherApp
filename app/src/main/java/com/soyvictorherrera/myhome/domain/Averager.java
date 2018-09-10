package com.soyvictorherrera.myhome.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.soyvictorherrera.myhome.data.entiities.SensorReading;

import java.util.Iterator;
import java.util.List;

/**
 * Created by vHerrera on 22/06/2018.
 */
public class Averager implements Iterable {
    private final String TAG = Averager.class.getSimpleName();

    private List<SensorReading> mReadings;
    private AveragerIterator mIterator;

    public Averager(List<SensorReading> readings) {
        mReadings = readings;
        mIterator = new AveragerIterator();
        mIterator.setSize(readings != null ? readings.size() : 0);
    }

    @NonNull
    @Override
    public AveragerIterator iterator() {
        return mIterator;
    }

    private final class AveragerIterator implements Iterator<SensorReading> {

        private static final int AVERAGE_SIZE = 10;

        private int size;
        private int index;
        private int tIndex;
        private int hIndex;
        private Integer[] tValuesArray;
        private Integer[] hValuesArray;

        private AveragerIterator() {
            size = 0;
            index = 0;
            tIndex = 0;
            hIndex = 0;
            tValuesArray = new Integer[AVERAGE_SIZE];
            hValuesArray = new Integer[AVERAGE_SIZE];
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Nullable
        @Override
        public SensorReading next() {
            if (mReadings == null) {
                return null;
            }
            return mReadings.get(index++);
        }

        @Nullable
        public Integer nextAveragedTemperatureReading() {
            if (mReadings == null) {
                return null;
            }
            int currentValue = mReadings.get(index).getTemperature();
            tValuesArray[tIndex++] = 0;
            /* TODO: guardar los valores en el arreglo correspondiente y devolver el
             * promedio de los últimos 10 en caso de existir o el promedio de los existentes
             */
            return 0;
        }

        @Nullable
        public Integer nextAveragedHumidityReading() {
            if (mReadings == null) {
                return null;
            }
            return 0;
        }

        private void setSize(int size) {
            this.size = size;
        }
    }

}
