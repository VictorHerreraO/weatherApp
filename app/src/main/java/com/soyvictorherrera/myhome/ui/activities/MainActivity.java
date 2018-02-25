package com.soyvictorherrera.myhome.ui.activities;

import android.support.v4.view.ViewPager;

import com.soyvictorherrera.myhome.BaseApplication;
import com.soyvictorherrera.myhome.R;
import com.soyvictorherrera.myhome.ui.BaseActivity;
import com.soyvictorherrera.myhome.ui.adapters.MainPagerAdapter;
import com.soyvictorherrera.myhome.ui.presenters.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements  MainActivityPresenter.View {

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;

    private MainPagerAdapter mPagerAdapter;

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
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mPagerAdapter);
    }

}
