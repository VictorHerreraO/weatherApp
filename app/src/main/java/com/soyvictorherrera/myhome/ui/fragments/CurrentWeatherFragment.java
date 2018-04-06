package com.soyvictorherrera.myhome.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseFragment;
import com.soyvictorherrera.myhome.ui.contracts.CurrentWeatherContract;
import com.soyvictorherrera.myhome.ui.presenters.CurrentWeatherPresenter;
import com.thbs.skycons.library.SunView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by vHerrera on 25/02/2018.
 */

public class CurrentWeatherFragment extends BaseFragment implements CurrentWeatherContract.View {

    @Inject
    CurrentWeatherPresenter mPresenter;

    @BindView(R.id.main_linear_holder)
    LinearLayout linearHolder;
    @BindView(R.id.main_icon_weather)
    View iconWeather;
    @BindView(R.id.main_lbl_temperature)
    TextView lblTemperature;
    @BindView(R.id.main_lbl_humidity)
    TextView lblHumidity;
    @BindView(R.id.main_lbl_date)
    TextView lblDate;
    @BindView(R.id.main_linear_offline)
    LinearLayout linearOffline;

    private ProgressDialog loadingDialog;

    @Override
    protected void initDagger() {
        ((BaseApplication) getActivity().getApplication())
                .getComponent()
                .inject(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_current_weather;
    }

    @Override
    protected void initView() {
        isLoading(true);
        mPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }

    @Override
    public void updateTemperature(int newTemperature) {
        String resourceString = getString(R.string.temperature_placeholder);
        lblTemperature.setText(String.format(resourceString, newTemperature));
        try {
            // Actualizar el ícono
            SunView sunView = new SunView(getContext(), false, true, getResources().getColor(R.color.colorAccent), Color.TRANSPARENT);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iconWeather.getLayoutParams();
            sunView.setLayoutParams(params);
            int index = linearHolder.indexOfChild(iconWeather);
            linearHolder.removeView(iconWeather);
            linearHolder.addView(sunView, index);
            iconWeather = sunView;
        } catch (Exception ex) {
            Log.e(TAG, "updateTemperature: ", ex);
        }
    }

    @Override
    public void updateHumidity(int newHumidity) {
        String resourceString = getString(R.string.humidity_placeholder);
        lblHumidity.setText(String.format(resourceString, newHumidity));
    }

    @Override
    public void updateDateTime(String newDateTime) {
        lblDate.setText(newDateTime);
    }

    @Override
    public void toggleOffline(boolean state) {
        linearHolder.setVisibility(state ? View.GONE : View.VISIBLE);
        linearOffline.setVisibility(state ? View.VISIBLE: View.GONE);
    }

    @Override
    public void isLoading(boolean loading) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getContext());
            loadingDialog.setMessage(getResources().getString(R.string.connecting_with_sensor));
            loadingDialog.setCancelable(false);
            loadingDialog.setIndeterminate(true);
        }
        if (loading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

}
