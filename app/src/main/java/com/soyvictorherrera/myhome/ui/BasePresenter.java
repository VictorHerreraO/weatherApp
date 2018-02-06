package com.soyvictorherrera.myhome.ui;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class BasePresenter<T extends BasePresenter.View> {

    private T mView;

    public void setView(T view) {
        this.mView = view;
    }

    public T getView() {
        return mView;
    }

    public interface View {

    }

}
