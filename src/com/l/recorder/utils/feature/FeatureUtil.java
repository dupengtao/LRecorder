package com.l.recorder.utils.feature;

import android.content.Context;

/**
 * Created by dupengtao on 15-4-2.
 */
public class FeatureUtil {

    public static boolean hasSceneSetting(Context cx){
        return cx.getApplicationContext().getPackageManager().hasSystemFeature(FeatureInfo.HAS_SCENE_SETTINGS);
    }
}
