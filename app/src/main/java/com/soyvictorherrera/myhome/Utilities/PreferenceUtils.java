package com.soyvictorherrera.myhome.Utilities;

import android.content.SharedPreferences;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by vHerrera on 04/02/2018.
 */

public class PreferenceUtils {
    public static final String LAST_READING_TIMESTAMP = "last_reading_timestamp";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Inject
    public PreferenceUtils(@Nonnull SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
        this.mEditor = mPreferences.edit();
    }

    public void setLastReadingTimestamp(long timestamp) {
        mEditor.putLong(LAST_READING_TIMESTAMP, timestamp).apply();
    }

    public long getLastReadingTimestamp(long defaultValue) {
        return mPreferences.getLong(LAST_READING_TIMESTAMP, defaultValue);
    }

}
