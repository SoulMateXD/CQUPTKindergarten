package com.cqupt.kindergarten.model.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.cqupt.kindergarten.injection.ApplicationContext;

import javax.inject.Inject;

public class PreferencesOperatorHelper {
    private static final String PRE_FILE_NAME = "";
    private SharedPreferences mSpf;

    @Inject
    public PreferencesOperatorHelper(@ApplicationContext Context context) {
        mSpf = context.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
    }

}
