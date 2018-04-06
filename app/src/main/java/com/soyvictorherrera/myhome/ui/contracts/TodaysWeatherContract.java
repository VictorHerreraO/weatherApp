package com.soyvictorherrera.myhome.ui.contracts;

import com.soyvictorherrera.myhome.ui.BasePresenter;

/**
 * Created by vHerrera on 11/03/2018.
 */

public abstract class TodaysWeatherContract {

    public interface View<T extends Iterable> extends BasePresenter.View{
        void drawTemperature(T entries);
        void drawHumidity(T entries);
    }

    public interface Presenter {
        //TODO: methods from presenter
    }

}
