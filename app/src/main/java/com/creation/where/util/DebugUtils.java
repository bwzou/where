package com.creation.where.util;

import android.widget.Toast;

import com.creation.where.WhereApplication;


/**
 * Created by zbw on 2017/2/21.
 */

public class DebugUtils {
    /**
     * 输出提示信息
     * @param s
     */
    public static void ShowErrorInf(String s){
        Toast.makeText(WhereApplication.applicationContext, s, Toast.LENGTH_SHORT).show();
    }
}
