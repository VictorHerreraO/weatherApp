package com.soyvictorherrera.myhome.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soyvictorherrera.myhome.ui.fragments.CurrentWeatherFragment;
import com.soyvictorherrera.myhome.ui.fragments.TodaysWeatherFragment;

/**
 * Created by vHerrera on 25/02/2018.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private CurrentWeatherFragment currentWeatherFragment;
    private TodaysWeatherFragment todaysWeatherFragmentFragment;

    private final int NUM_ITEMS = 2;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (currentWeatherFragment == null) {
                    currentWeatherFragment = new CurrentWeatherFragment();
                }
                return currentWeatherFragment;
            case 1 :
                if (todaysWeatherFragmentFragment == null) {
                    todaysWeatherFragmentFragment = new TodaysWeatherFragment();
                }
                return todaysWeatherFragmentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}
