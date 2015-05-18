package com.l.recorder.model.Recorder;


/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IRecorder {

    void setAudioQulityParam(Recorder.AudioQulityParam audioQulityParam);

    void setRecordFileName(String name);

    int start();

    int pause();

    int stop();

}
