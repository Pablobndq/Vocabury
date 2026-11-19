package com.pbd.vocabury.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREF_NAME = "vocabury_preferences";
    private static final String KEY_LANGUAGE_ID = "language_id";

    private final SharedPreferences preferences;

    public AppPreferences(Context context) {

        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE);

    }

    public void setCurrentLanguageId(int id) {

        preferences.edit()
                .putInt(KEY_LANGUAGE_ID, id)
                .apply();

    }

    public int getCurrentLanguageId() {

        return preferences.getInt(KEY_LANGUAGE_ID, -1);

    }

}
