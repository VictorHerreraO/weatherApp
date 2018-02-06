package com.soyvictorherrera.myhome.Utilities;

import android.content.SharedPreferences;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class PreferenceUtils {

    private SharedPreferences mPreferences;

    @Inject
    public PreferenceUtils(@Nonnull SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }
}
