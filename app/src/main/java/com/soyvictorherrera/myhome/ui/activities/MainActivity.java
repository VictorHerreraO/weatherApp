package com.soyvictorherrera.myhome.ui.activities;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseActivity;
import com.soyvictorherrera.myhome.ui.presenters.MainActivityPresenter;
import com.thbs.skycons.library.SunView;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityPresenter.View {
    private final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.main_linear_holder)
    LinearLayout linearHoloder;
    @BindView(R.id.main_icon_weather)
    View iconWeather;
    @BindView(R.id.main_lbl_temperature)
    TextView lblTemperature;
    @BindView(R.id.main_lbl_humidity)
    TextView lblHumidity;
    @BindView(R.id.main_lbl_date)
    TextView lblDate;

    @Override
    protected void initDagger() {
        ((BaseApplication) getApplication())
                .getComponent()
                .inject(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPresenter.setView(this);
    }

    @Override
    public void updateTemperature(int newTemperature) {
        String resourceString = getString(R.string.temperature_placeholder);
        lblTemperature.setText(String.format(resourceString, newTemperature));
        try {
            // Actualizar el ícono
            SunView sunView = new SunView(this, false, true, Color.parseColor("#000000"), Color.TRANSPARENT);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iconWeather.getLayoutParams();
            sunView.setLayoutParams(params);
            int index = linearHoloder.indexOfChild(iconWeather);
            linearHoloder.removeView(iconWeather);
            linearHoloder.addView(sunView, index);
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
}
