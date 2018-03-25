package com.tis.merchant.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Nanthakorn on 24/1/2559.
 */
public class LanguageManager {

    /*
        #Example
        LanguageManager.with(context).setLanguage(LanguageManager.LANG.EN);
        startActivity(getIntent());
        finish();
     */

    private static LanguageManager instance;
    private final Context context;
    private SharedPreferences prefs;
    private final String KEY_VALUE = "app_lang";
    private final String TAG = "LanguageManager";

    public enum LANG {
        EN("en"),
        LO("lo"),
        TH("th"),
        JA("ja");

        private String language;

        LANG(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }
    }

    public LanguageManager(Context context) {
        this.context = context;
    }

    public static LanguageManager with(Context context) {
        if (instance == null) {
            synchronized (LanguageManager.class) {
                if (instance == null) {
                    instance = new LanguageManager(context);
                }
            }
        }
        return instance;
    }

    public LanguageManager setLanguage(LANG lang) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang.getLanguage());
        res.updateConfiguration(conf, dm);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_VALUE, lang.getLanguage());
        editor.commit();
        return instance;
    }

    public String getLanguage() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(KEY_VALUE, LANG.EN.getLanguage());

    }
}
