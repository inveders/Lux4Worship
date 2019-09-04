package com.inved.lux4worship.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ManageChurchPreferences {

    private static final String KEY_CHURCH_ID = "KEY_CHURCH_ID";
    public static final String KEY_CHURCH_ID_DATA = "KEY_CHURCH_ID_DATA";
    private static final String KEY_CHURCH_NAME = "KEY_CHURCH_NAME";
    public static final String KEY_CHURCH_NAME_DATA = "KEY_CHURCH_NAME_DATA";
    private static final String KEY_CHURCH_ADDRESS = "KEY_CHURCH_ADDRESS";
    public static final String KEY_CHURCH_ADDRESS_DATA = "KEY_CHURCH_ADDRESS_DATA";

    public static void saveChurchId(Context context, String churchId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHURCH_ID_DATA, churchId);
        editor.apply();
    }

    public static String getChurchId(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHURCH_ID_DATA,null);
    }

    public static void saveChurchName(Context context, String churchName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHURCH_NAME_DATA, churchName);
        editor.apply();
    }

    public static String getChurchName(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHURCH_NAME_DATA,null);
    }

    public static void saveChurchAddress(Context context, String churchAddress) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_ADDRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHURCH_ADDRESS_DATA, churchAddress);
        editor.apply();
    }

    public static String getChurchAddress(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CHURCH_ADDRESS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHURCH_ADDRESS_DATA,null);
    }

}
