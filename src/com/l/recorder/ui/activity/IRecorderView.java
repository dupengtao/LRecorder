package com.l.recorder.ui.activity;

/**
 * Created by dupengtao on 15-5-5.
 */
public interface IRecorderView {

//    void updateProgress(long msec);

    void updateTimer(String time);

    void updateButtonState(int state);

    void updateFlag(int num);
}
