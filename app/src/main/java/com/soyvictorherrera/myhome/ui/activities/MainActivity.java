package com.soyvictorherrera.myhome.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
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
        isLoading(true);
    }

    @Override
    public void updateTemperature(int newTemperature) {
        String resourceString = getString(R.string.temperature_placeholder);
        lblTemperature.setText(String.format(resourceString, newTemperature));
        try {
            // Actualizar el ícono
            SunView sunView = new SunView(this, false, true, getResources().getColor(R.color.colorAccent), Color.TRANSPARENT);
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
            loadingDialog = new ProgressDialog(this);
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
