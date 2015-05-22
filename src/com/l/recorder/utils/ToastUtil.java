package com.l.recorder.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangjiahao on 15-5-22.
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
