package com.webskygroup.opencart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class ContextWrapper {
    public static Context wrap(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("language_app",Context.MODE_PRIVATE);
        String lang=sharedPreferences.getString("lang_prefs_name","en");
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}