package com.l.recorder.model.Recorder;


/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IRecorder {

    void prepare(String name, AudioQulityParam audioQulityParam, boolean platform);

    int start();

    int pause();

    int resume();

    int stop();

    void release();

    void setRecordName(String name);

}
