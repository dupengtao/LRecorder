package com.l.recorder.model.dao;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhangjiahao on 15-5-6.
 */
public class Constant {
    public static final String RECORDER_PATH = Environment.getExternalStorageDirectory() + File.separator + "mRecorder";
    public static final String TEMP_FILE_PATH = RECORDER_PATH + File.separator + ".temp";
}
