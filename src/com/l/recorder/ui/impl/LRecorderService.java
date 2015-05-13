package com.l.recorder.ui.impl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.l.recorder.presenter.impl.RecorderPresenter;
import com.l.recorder.ui.IRecorderView;

/**
 * For playing and recording services
 */
public class LRecorderService extends Service {

    private static final String TAG = "LRecorderService";
    private RecorderPresenter mRecorderPresenter;

    @Override
    public IBinder onBind(Intent intent) {
        return new RecorderBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service create");
        if (mRecorderPresenter == null) {
            mRecorderPresenter = new RecorderPresenter();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecorderPresenter = null;
        Log.i(TAG, "service destroy");
    }

    private void mStartRecord() {
        mRecorderPresenter.startRecord();
    }


    public class RecorderBinder extends Binder {

        public void linkPresenter(IRecorderView iRecorderView, int tag) {
            mRecorderPresenter.holdView(iRecorderView, tag);
        }

        public void record() {
            mStartRecord();
        }
    }
}
