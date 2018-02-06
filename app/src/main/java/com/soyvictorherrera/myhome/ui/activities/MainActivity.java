package com.soyvictorherrera.myhome.ui.activities;

import android.widget.TextView;

import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseActivity;
import com.soyvictorherrera.myhome.ui.presenters.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityPresenter.View {

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.main_lbl_temperature)
    TextView lblTemperature;

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
    public void updateTemperature(float newTemperature) {
        lblTemperature.setText(newTemperature + " °");
    }

    @Override
    public void updateHumidity(float newHumidity) {

    }

    @Override
    public void updateDateTime(String newDateTime) {

    }
}
