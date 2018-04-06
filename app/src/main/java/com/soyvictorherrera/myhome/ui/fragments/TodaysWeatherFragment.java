package com.soyvictorherrera.myhome.ui.fragments;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseFragment;
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

    @Inject
    TodaysWeatherPresenter mPresenter;

    private LineData mData;
    private final int ANIMATION_DURATION = 2500;

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
        todayTemperatures.getLegend().setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }

    @Override
    public void drawTemperature(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.temperature));
        // TODO: Style dataset
        dataSet.setFillColor(getResources().getColor(R.color.colorSunYellow));
        dataSet.setFillAlpha(100);
        dataSet.setColor(getResources().getColor(R.color.colorSunYellow));
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);

        mData = new LineData(dataSet);
        todayTemperatures.setData(mData);
        todayTemperatures.animateX(ANIMATION_DURATION);
    }

    @Override
    public void drawHumidity(List<Entry> entries) {

    }

}
