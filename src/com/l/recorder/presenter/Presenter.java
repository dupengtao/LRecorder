package com.l.recorder.presenter;

import com.l.recorder.model.Recorder.IRecorder;
import com.l.recorder.model.Recorder.Recorder;
import com.l.recorder.ui.IRecorderView;

/**
 * Created by dupengtao on 15-5-5.
 */
public class Presenter implements IPresenter {

    private IRecorder mRecorder;
    private IRecorderView mRecorderView;

    public Presenter() {
        mRecorder = Recorder.getInstence();
        Recorder.AudioQulityParam param = new Recorder.AudioQulityParam();
        mRecorder.setAudioQulityParam(param);
    }

    public void startRecord() {
        mRecorder.setRecordFileName("aaa");
        mRecorder.startRecord();


        mRecorderView.test("start record");
    }

    public void pauseRecord() {
        mRecorder.pauseRecord();

        mRecorderView.test("pause record");
    }

    public void stopRecord() {
        mRecorder.stopRecord();

        mRecorderView.test("success");
    }

    public void holdView(IRecorderView iRecorderView, int tag) {
        this.mRecorderView = iRecorderView;
    }

    @Override
    public void onMainButtonClicked() {

    }

    @Override
    public void onFlagButtonClicked() {

    }

    @Override
    public void onStopButtonClicked() {

    }
}
