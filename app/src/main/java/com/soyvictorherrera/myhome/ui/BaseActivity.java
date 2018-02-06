package com.soyvictorherrera.myhome.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by vHerrera on 04/02/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inyectar dependencias
        initDagger();

        // Inicializar los controles
        setContentView(getResourceId());
        ButterKnife.bind(this);

        // Inicializar la vista
        initView();
    }



    protected abstract void initDagger();
    protected abstract int getResourceId();
    protected abstract void initView();

}
