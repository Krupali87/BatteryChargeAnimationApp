package com.app.battercharge.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public final class SharedPreferenceUtils {
    private static final String LOOP_TIME_DURATION_KEY = "loop_time_duration";
    private SharedPreferenceUtils() {
    }

    public static boolean getBooleanPreference(Context context, String str, boolean z) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences != null ? defaultSharedPreferences.getBoolean(str, z) : z;
    }

    public static int getIntegerPreference(Context context, String str, int i) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences != null ? defaultSharedPreferences.getInt(str, i) : i;
    }

    public static String getStringPreference(Context context, String str, String str2) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences != null) {
            return defaultSharedPreferences.getString(str, str2);
        }
        return null;

    }

    public static void setBooleanPreference(Context context, String str, boolean z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(str, z);
        editor.apply();
    }

    public static void setIntegerPreference(Context context, String str, int i) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void setStringPreference(Context context, String str, String str2) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null || TextUtils.isEmpty(str)) {
            return;
        }
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putString(str, str2);
        edit.apply();
    }
    public static int getSelectedLoopTimeDuration(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences != null ? defaultSharedPreferences.getInt(LOOP_TIME_DURATION_KEY, 0) : 0;
    }

    public static void setSelectedLoopTimeDuration(Context context, int selectedLoopTimeDuration) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putInt(LOOP_TIME_DURATION_KEY, selectedLoopTimeDuration);
        edit.apply();
    }
}


