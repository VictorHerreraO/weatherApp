package com.soyvictorherrera.myhome.ui.fragments;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseFragment;
import com.soyvictorherrera.myhome.ui.adapters.PlotDateFormatter;
import com.soyvictorherrera.myhome.ui.contracts.TodaysWeatherContract;
import com.soyvictorherrera.myhome.ui.presenters.TodaysWeatherPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by vHerrera on 26/02/2018.
 */

public class TodaysWeatherFragment extends BaseFragment implements TodaysWeatherContract.View<List<Entry>> {

    @BindView(R.id.today_temperatures)
    LineChart todayTemperatures;
    @BindView(R.id.today_humidities)
    LineChart todayHumidities;

    @Inject
    TodaysWeatherPresenter mPresenter;

    private final int ANIMATION_DURATION = 2500;
    private final float CUBIC_INTENSITY = 0.25f;

    @Override
    protected void initDagger() {
        ((BaseApplication) getActivity().getApplication())
                .getComponent()
                .inject(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_todays_weather;
    }

    @Override
    protected void initView() {
        mPresenter.setView(this);
        mPresenter.getTemperatureData();

        setupPlot(todayTemperatures);
        setupPlot(todayHumidities);
    }

    @Override
    public void onResume() {
        super.onResume();
        todayTemperatures.animateX(ANIMATION_DURATION);
        todayHumidities.animateX(ANIMATION_DURATION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }

    @Override
    public void drawTemperature(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.temperature));
        dataSet.setFillColor(getResources().getColor(R.color.colorSunYellow));
        dataSet.setFillAlpha(100);
        dataSet.setColor(getResources().getColor(R.color.colorSunYellow));
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(CUBIC_INTENSITY);

        todayTemperatures.setData(new LineData(dataSet));
    }

    @Override
    public void drawHumidity(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.humidity));
        dataSet.setFillColor(getResources().getColor(R.color.colorWetBlue));
        dataSet.setFillAlpha(100);
        dataSet.setColor(getResources().getColor(R.color.colorWetBlue));
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(CUBIC_INTENSITY);

        todayHumidities.setData(new LineData(dataSet));
    }

    private void setupPlot(LineChart chart) {
        chart.getXAxis().setValueFormatter(new PlotDateFormatter());
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setLabelCount(5);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
    }

}
