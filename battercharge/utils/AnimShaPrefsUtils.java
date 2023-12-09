package com.app.battercharge.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class AnimShaPrefsUtils {
    private AnimShaPrefsUtils() {
    }

    public static boolean getFirst(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("ShaPrefsU", false);
    }

    public static boolean setFirst(Context context, boolean z) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("ShaPrefsU", z);
        return edit.commit();
    }
}
