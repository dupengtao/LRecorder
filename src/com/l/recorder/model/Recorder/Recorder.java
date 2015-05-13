package com.l.recorder.model.Recorder;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.l.recorder.utils.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangjiahao on 15-5-6.
 */
public class Recorder implements IRecorder {

    private static final String LOG_TAG = "Recorder";

    /**
     * handler what
     */
    public static final int RECORDING_TIME = 10;

    private static final int IDLE = 0;
    private static final int RECORDING = 1;
    private static final int PAUSE = 2;
    private int currentState = IDLE;

    private MediaRecorder mRecorder;
    private FileManager fileManager;
    private AudioQulityParam mAudioQulityParam;
    private ArrayList<File> mTempList;
    private int mTempCount = 1;
    private String fileName = System.currentTimeMillis() + "";

    private Handler mHandler;
    private long startRecordTime;
    private Timer mTimer;
    private CountTimerTask mTimerTask;

    private static Recorder mInstence;

    public static Recorder getInstence() {
        if (mInstence == null) {
            synchronized (Recorder.class) {
                if (mInstence == null) {
                    mInstence = new Recorder();
                }
            }
        }
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
    public void startRecord() {
        if (currentState == RECORDING) {
            return;
        }
        currentState = RECORDING;

        File tempFile = fileManager.getTempFile(mTempCount + "");
        mTempList.add(tempFile);

        init(tempFile);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "prepare() failed");
        }
        startCountTime();
        mRecorder.start();
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
    public void pauseRecord() {
        if (currentState == IDLE || currentState == PAUSE) {
            return;
        }
        currentState = PAUSE;
        pauseCountTime();
        mRecorder.stop();
        mRecorder.release();
        mTempCount++;
    }

    @Override
    public void stopRecord() {
        if (currentState == RECORDING) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        stopCountTime();
        currentState = IDLE;
        mTempCount = 1;
        fileManager.merginTempFile(fileName, mTempList);
        fileManager.clearTempDir();
        mTempList.clear();
    }

    @Override
    public void getHandler(Handler handler) {
        this.mHandler = handler;
    }

    private void startCountTime() {
        if (currentState == IDLE) {
            startRecordTime = SystemClock.uptimeMillis();
        }
        mTimer = new Timer();
        mTimerTask = new CountTimerTask();
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private void pauseCountTime() {
        mTimer.cancel();
    }

    private void stopCountTime() {
        mTimer.cancel();
        mTimer = null;
        mTimerTask = null;
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

    class CountTimerTask extends TimerTask {
        @Override
        public void run() {
            long recordingDuring = SystemClock.uptimeMillis() - startRecordTime;
            Message message = Message.obtain();
            message.obj = recordingDuring;
            message.what = RECORDING_TIME;
            mHandler.sendMessage(message);
        }

        @Override
        public boolean cancel() {
            return super.cancel();
        }
    }
}
