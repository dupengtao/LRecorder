package com.l.recorder.model.Recorder;

import android.media.MediaRecorder;
import com.l.recorder.model.dao.FileManager;
import com.l.recorder.utils.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zhangjiahao on 15-5-6.
 */
public class Recorder implements IRecorder {

    private static final String LOG_TAG = "Recorder";

    public static final int IDLE = 0;
    public static final int RECORDING = 1;
    public static final int PAUSE = 2;
    public int currentState = IDLE;

    private MediaRecorder mRecorder;
    private FileManager fileManager;
    private AudioQulityParam mAudioQulityParam;
    private ArrayList<File> mTempList;
    private int mTempCount = 1;
    private String fileName = System.currentTimeMillis() + "";

    private static Recorder mInstence = new Recorder();

    public static Recorder getInstence() {
        return mInstence;
    }

    private Recorder() {
        this.mTempList = new ArrayList<>();
        fileManager = new FileManager();
        fileManager.clearTempDir();
    }

    @Override
    public void setAudioQulityParam(AudioQulityParam audioQulityParam) {
        this.mAudioQulityParam = audioQulityParam;
    }

    @Override
    public void setRecordFileName(String name) {
        this.fileName = name;
    }

    @Override
    public int start() {
        if (currentState == RECORDING) {
            return currentState;
        }

        File tempFile = fileManager.getTempFile(mTempCount + ".temp");
        mTempList.add(tempFile);

        init(tempFile);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        currentState = RECORDING;
        return currentState;
    }

    private void init(File tempFile) {
        if (mAudioQulityParam == null) {
            throw new IllegalStateException("please call setAudioQulityParam() first");
        }
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(mAudioQulityParam.OutputFormat);
        mRecorder.setOutputFile(tempFile.getAbsolutePath());
        mRecorder.setAudioSamplingRate(mAudioQulityParam.SampleRate);
        mRecorder.setAudioEncodingBitRate(mAudioQulityParam.EncodeBitRate);
        mRecorder.setAudioChannels(mAudioQulityParam.AudioChannel);
        mRecorder.setAudioEncoder(mAudioQulityParam.AudioEncoder);
    }

    @Override
    public int pause() {
        if (currentState == IDLE || currentState == PAUSE) {
            return currentState;
        }
        mRecorder.stop();
        mRecorder.release();
        mTempCount++;
        currentState = PAUSE;
        return currentState;
    }

    @Override
    public int stop() {
        if (currentState == IDLE) {
            return currentState;
        }

        if (currentState == RECORDING) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        mTempCount = 1;
        fileManager.merginTempFile(fileName, mTempList, 6);
        fileManager.clearTempDir();
        mTempList.clear();
        currentState = IDLE;
        return currentState;
    }

    public static class AudioQulityParam {
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

}
