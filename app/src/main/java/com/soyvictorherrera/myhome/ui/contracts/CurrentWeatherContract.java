package com.soyvictorherrera.myhome.ui.contracts;

import com.soyvictorherrera.myhome.ui.BasePresenter;

/**
 * Created by vHerrera on 11/03/2018.
 */

public abstract class CurrentWeatherContract {

    public interface View extends BasePresenter.View {
        void updateTemperature(int newTemperature);
        void updateHumidity(int newHumidity);
        void updateDateTime(String newDateTime);
        void toggleOffline(boolean state);
        void isLoading(boolean loading);
    }

    public interface Presenter {
        // TODO: methods from presenter
    }

}
