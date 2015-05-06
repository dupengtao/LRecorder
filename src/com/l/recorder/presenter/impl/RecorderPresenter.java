package com.l.recorder.presenter.impl;

import android.os.Bundle;
import com.l.recorder.model.IRecorderModel;
import com.l.recorder.presenter.IRecordPresenter;
import com.l.recorder.ui.IRecorderView;

/**
 * Created by dupengtao on 15-5-5.
 */
public class RecorderPresenter implements IRecordPresenter {
    private IRecorderView mRecorderView;
    private IRecorderModel mRecorderModel;

    public RecorderPresenter(IRecorderModel recorderModel) {
        mRecorderModel=recorderModel;
    }


    @Override
    public void onViewCreate(Bundle savedState) {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onTakeView(IRecorderView view, int sign) {
        mRecorderView = view;
    }


    public void startRecord() {
        // in service
        mRecorderModel.test();
    }

    public void test(final String str) {
        mRecorderView.test(str);
    }

}
