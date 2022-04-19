package com.android.myapplication.Others;

/**
 * Created by kapil on 20/01/17.
 */
import android.content.Context;
import android.content.SharedPreferences;


public class DarkModePrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "education-dark-mode";

    private static final String IS_NIGHT_MODE = "IsNightMode";


    public DarkModePrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("education-dark-mode", 0);
        editor = pref.edit();
    }

    public void setDarkMode(boolean isFirstTime) {
        editor.putBoolean("IsNightMode", isFirstTime);
        editor.commit();
    }

    public boolean isNightMode() {
        return pref.getBoolean("IsNightMode", true);
    }

}