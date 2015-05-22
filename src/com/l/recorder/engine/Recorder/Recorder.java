package com.l.recorder.engine.Recorder;

import android.media.MediaRecorder;
import com.l.recorder.model.RecordFileManager;
import com.l.recorder.utils.LogUtil;

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
    private RecordFileManager mRecordFileManager;
    private AudioQulityParam mAudioQulityParam;
    private ArrayList<File> mTempList;
    private int mTempCount = 1;
    private String mRecordName = System.currentTimeMillis() + "";

    private static Recorder mInstence = new Recorder();
    private int offset;//metadata offset

    public static Recorder getInstence() {
        return mInstence;
    }

    private Recorder() {
        this.mTempList = new ArrayList<>();
        mRecordFileManager = new RecordFileManager();
    }

    @Override
    public void setRecordName(String mRecordName) {
        this.mRecordName = mRecordName;
    }

    @Override
    public void prepare(String name, AudioQulityParam audioQulityParam, boolean platform) {
        release();

        if (audioQulityParam == null) {
            //default audio qulity
            mAudioQulityParam = new AudioQulityParam();
        }

        setOffsetByPlatform(platform);

        String suffix = mAudioQulityParam.OutputFormat == MediaRecorder.OutputFormat.AMR_WB ? ".amr" : ".3gp";
        this.mRecordName = name + suffix;
    }

    @Override
    public int start() {
        File tempFile = mRecordFileManager.getTempFile(mTempCount + ".temp");
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

    private void setOffsetByPlatform(boolean platform) {
        if (platform) {
            if (mAudioQulityParam.AudioEncoder == 1) {
                offset = 6;
            } else {
                offset = 9;
            }
        } else {
            offset = 6;
        }
    }

    private void init(File tempFile) {
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
        mRecorder.stop();
        mRecorder.release();
        mTempCount++;
        currentState = PAUSE;
        return currentState;
    }

    @Override
    public int resume() {
        start();
        currentState = RECORDING;
        return currentState;
    }

    @Override
    public int stop() {
        if (currentState == RECORDING) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        mTempCount = 1;
        mRecordFileManager.merginTempFile(mRecordName, mTempList, offset);
        mRecordFileManager.clearTempDir();
        mTempList.clear();
        currentState = IDLE;
        return currentState;
    }

    @Override
    public void release() {
        mRecordName = null;
        mAudioQulityParam = null;
        mTempList.clear();
        mRecordFileManager.clearTempDir();
    }

}
