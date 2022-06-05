package com.habitdark.myapplication.Others;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatDelegate;

public class ChangeDarkMode {
    SharedPreferences sharedPreferences = null;
    Context context;

    public ChangeDarkMode(Context context) {
        this.context=context;
    }

    public static boolean PRIVATE_MODE = true;

    //    public  void setCoDinhDen(boolean mode)
    //    {
    //        editor.putBoolean("night_mode",PRIVATE_MODE);
    //        editor.commit();
    //    }

    public void setModeScreen() {
        sharedPreferences = context.getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",PRIVATE_MODE);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PRIVATE_MODE =true;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PRIVATE_MODE=false;
        }
    }

    public boolean modeDark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("night_mode",PRIVATE_MODE);
        editor.commit();

        return true;
    }

    public boolean modeNight() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("night_mode",PRIVATE_MODE);
        editor.commit();

        return false;
    }

    public static class DatabaseActivity  {
        public static final String DBName="trackinghabit.sqlite";
        public static SQLiteDatabase sqLiteDatabase;
    }
}
