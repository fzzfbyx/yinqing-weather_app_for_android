package com.example.iweather.util;

import android.content.Context;
import android.content.SharedPreferences;



public class MySharedpreference {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor getInstance(Context context)
    {
        preferences = context.getSharedPreferences("Info",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        return editor;
    }
}
