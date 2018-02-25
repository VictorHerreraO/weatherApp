package com.soyvictorherrera.myhome.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by vHerrera on 25/02/2018.
 */

public abstract class BaseFragment extends Fragment {
    protected final String TAG =  this.getClass().getSimpleName();

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inyectar dependencias
        initDagger();

        // Inicializar los controles
        View view = inflater.inflate(getResourceId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);

        // Inicializar la vista
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected abstract void initDagger();
    protected abstract int getResourceId();
    protected abstract void initView();

}
