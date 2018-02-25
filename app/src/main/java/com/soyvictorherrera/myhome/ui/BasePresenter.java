package com.soyvictorherrera.myhome.ui;

/**
 * Created by vHerrera on 04/02/2018.
 */

public abstract class BasePresenter<T extends BasePresenter.View> {
    protected final String TAG = this.getClass().getSimpleName();

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
