package com.l.recorder.model.Recorder;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IRecorder {
    /**
     * set audio qulity
     *
     * @param audioQulityParam
     */
    void setAudioQulityParam(Recorder.AudioQulityParam audioQulityParam);

    void setRecordFileName(String name);

    void startRecord();

    void pauseRecord();

    void stopRecord();
}
