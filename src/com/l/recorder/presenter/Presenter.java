package com.l.recorder.presenter;

import android.content.Context;
import com.l.recorder.engine.Player.IPlayer;
import com.l.recorder.engine.Player.Player;
import com.l.recorder.engine.Recorder.IRecorder;
import com.l.recorder.engine.Recorder.Recorder;
import com.l.recorder.model.Timer;
import com.l.recorder.ui.activity.IRecorderView;
import com.l.recorder.utils.RecordTool;
import com.l.recorder.utils.feature.FeatureUtil;
import com.l.recorder.utils.LogUtil;
import com.l.recorder.utils.thread.DxOptThreadPool;

/**
 * Created by dupengtao on 15-5-5.
 */
public class Presenter implements IPresenter {

    private static final String TAG = "Presenter";

    public final int RECORD_IDLE = 0;
    public final int RECORD_RECORDING = 1;
    public final int RECORD_PAUSE = 2;
    private int currentState;

    private IRecorder mRecorder;
    private IPlayer mPlayer;
    private IRecorderView mRecorderView;
    private DxOptThreadPool mThreadPool;
    private boolean platform;
    private Timer mTimer;
    private long during;
    private long mDuring;

    public Presenter() {
        init();
    }

    @Override
    public void holdView(IRecorderView iRecorderView, int tag) {
        this.mRecorderView = iRecorderView;
        platform = FeatureUtil.hasSceneSetting((Context) iRecorderView);
    }

    private void init() {
        mRecorder = Recorder.getInstence();
        mPlayer = Player.getInstance();
        mThreadPool = DxOptThreadPool.getInstance();
        currentState = RECORD_IDLE;
    }

    public void onMainButtonClicked() {
        if (currentState == RECORD_IDLE) {
            currentState = RECORD_RECORDING;
            mRecorderView.updateButtonState(currentState);
            mTimer = new Timer() {
                @Override
                public void update(long msec) {
                    mRecorderView.updateTimer(RecordTool.recordTimeFormat(msec + mDuring));
                }
            };
            mTimer.start(20);
            mThreadPool.addBkgTask(new Runnable() {
                @Override
                public void run() {
                    mRecorder.prepare(getRecordName(), null, platform);
                    mRecorder.start();
                }
            });
        } else if (currentState == RECORD_RECORDING) {
            currentState = RECORD_PAUSE;
            mRecorderView.updateButtonState(currentState);
            mRecorder.pause();
            mDuring += mTimer.stop();
        } else if (currentState == RECORD_PAUSE) {
            currentState = RECORD_RECORDING;
            mRecorderView.updateButtonState(currentState);
            mRecorder.resume();
            mTimer.start(20);
        }
    }

    private String getRecordName() {
        //query database
        return "新录音max";
    }

    public void onRightButtonClicked() {
        if (currentState == RECORD_PAUSE || currentState == RECORD_RECORDING) {
            currentState = RECORD_IDLE;
            mRecorderView.updateButtonState(currentState);
            mRecorderView.updateTimer(RecordTool.recordTimeFormat(0));
            mThreadPool.addBkgTask(new Runnable() {
                @Override
                public void run() {
                    mTimer.stop();
                    mTimer = null;
                    mDuring = 0;
                    mRecorder.stop();
                    mRecorder.release();
                }
            });
        }
    }

    public void onLeftButtonClicked() {
        if (currentState == RECORD_RECORDING) {
            //add a flag,save to database.
            LogUtil.i(TAG, "add a flag...");
            mRecorderView.updateFlag(0);
        }
    }
}
