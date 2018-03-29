package vr.xinjing.com.vrmc.utils;

import android.util.Log;

/**
 * Created by raytine on 2017/8/16.
 */

public class MyLog {
    private static final int DEBUG_LEVEL = 5;// 0:none;1:error;2:error and warning
    // 3:e,w,d;4:e,w,d,i;5:all

    @SuppressWarnings("unused")
    public static void e(String tag, String msg) {
        if (DEBUG_LEVEL >= 1) {
            Log.e(tag, msg);
        }

    }

    @SuppressWarnings("unused")
    public static void w(String tag, String msg) {
        if (DEBUG_LEVEL >= 2) {
            Log.w(tag, msg);
        }

    }

    @SuppressWarnings("unused")
    public static void d(String tag, String msg) {
        if (DEBUG_LEVEL >= 3) {
            Log.d(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void i(String tag, String msg) {
        if (DEBUG_LEVEL >= 4) {
            Log.i(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg) {
        if (DEBUG_LEVEL >= 5) {
            Log.v(tag, msg);
        }
    }
}