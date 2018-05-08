package com.soyvictorherrera.myhome.ui.adapters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.soyvictorherrera.myhome.Utilities.DateTimeUtils;

/**
 * Created by vHerrera on 07/05/2018.
 */
public class PlotDateFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        long timeStamp = (long) value;
        return DateTimeUtils.getHourFromMillis(timeStamp);
    }

}
