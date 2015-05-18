package com.l.recorder.ui.activity;

/**
 * Created by dupengtao on 15-5-5.
 */
public interface IRecorderView {

    void test(String str);

    void onRecordTimeChanged(long msec);

    void onRecordStateChanged(int state);
}
