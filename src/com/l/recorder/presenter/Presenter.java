package com.l.recorder.presenter;

import com.l.recorder.model.Player.IPlayer;
import com.l.recorder.model.Player.Player;
import com.l.recorder.model.Recorder.IRecorder;
import com.l.recorder.model.Recorder.Recorder;
import com.l.recorder.ui.activity.IRecorderView;
import com.l.recorder.utils.thread.DxOptThreadPool;

/**
 * Created by dupengtao on 15-5-5.
 */
public class Presenter {

    private IRecorder mRecorder;
    private IPlayer mPlayer;
    private IRecorderView mRecorderView;
    private DxOptThreadPool threadPool;

    public Presenter() {
        initRecorderAndPlayer();
        initThreadPool();
    }

    private void initThreadPool() {
        threadPool = DxOptThreadPool.getInstance();
    }

    private void initRecorderAndPlayer() {
        mRecorder = Recorder.getInstence();
        Recorder.AudioQulityParam param = new Recorder.AudioQulityParam();
        mRecorder.setAudioQulityParam(param);

        mPlayer = Player.getInstance();
    }

    public void startRecord() {
//        threadPool
    }

    public void holdView(IRecorderView iRecorderView, int tag) {
        this.mRecorderView = iRecorderView;
    }

}
