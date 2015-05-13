package com.l.recorder.utils.log;
/**
 * Created by zhangjiahao on 15-5-11.
 */

import android.util.Log;

public class LogUtil {

    public static final int PRINT_NOTHING = 0;
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;

    public static final int LEVEL = PRINT_NOTHING;

    //自定义日志打印，打印时设置LEVEL的值
    //eg：设置为2将会把debug、verbose的日志打印出来

    public static void v(String tag, String msg) {
        if (LEVEL >= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL >= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL >= INFO) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL >= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL >= ERROR) {
            Log.e(tag, msg);
        }
    }

}
