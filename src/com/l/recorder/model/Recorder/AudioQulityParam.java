package com.l.recorder.model.Recorder;

import android.media.MediaRecorder;

/**
 * Created by zhangjiahao on 15-5-21.
 */
public class AudioQulityParam {
    public int OutputFormat = MediaRecorder.OutputFormat.AMR_WB;
    public int SampleRate = 32000;
    public int EncodeBitRate = 48000;
    public int AudioChannel = 2;
    public int AudioEncoder = MediaRecorder.AudioEncoder.AMR_WB;

    public AudioQulityParam() {
    }

    public AudioQulityParam(int[] arr) {
        OutputFormat = arr[0];
        SampleRate = arr[1];
        EncodeBitRate = arr[2];
        AudioChannel = arr[3];
        AudioEncoder = arr[4];
    }
}
