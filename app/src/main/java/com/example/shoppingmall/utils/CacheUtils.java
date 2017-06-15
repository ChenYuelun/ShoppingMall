package com.example.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class CacheUtils {
    private static SharedPreferences sp;

    public static Boolean getBoolean(Context mContext, String key) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }



    public static void putBoolean(Context mContext, String key, Boolean value) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }



    public static String getString(Context mContext, String key) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }



    public static void putString(Context mContext, String key, String value) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static int getInt(Context mContext, String key) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return mContext.getSharedPreferences("config",Context.MODE_PRIVATE).getInt(key,0);
        //return sp.getInt(key,0);
    }



    public static void putInt(Context mContext, String key, int value) {
        sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
}
