package com.app.battercharge.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppLocalDB {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public AppLocalDB(Context context2) {
        this.context = context2;
        this.sharedPreferences = context2.getSharedPreferences("prefFile", 0);
    }

    public final Boolean getCheckedIn() {
        return Boolean.valueOf(this.sharedPreferences.getBoolean("checkedBoxAsIn", false));
    }

    public final void setCheckedIn(Boolean bool) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putBoolean("checkedBoxAsIn", bool.booleanValue());
        edit.apply();
    }
}
