package com.webskygroup.opencart.Hilt.Modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

public class SessionManager {

    Context context;
    SharedPreferences preferences;
    String pr_name = "prefs";

    boolean loggedIn = false;

    String prl = "loggedin";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(pr_name,Context.MODE_PRIVATE);
    }


    public boolean isLoggedIn() {
        return preferences.getBoolean(prl,false);
    }


    public void setLoggedIn(boolean loggedIn) {
        preferences.edit().putBoolean(prl,loggedIn).apply();
    }
}
