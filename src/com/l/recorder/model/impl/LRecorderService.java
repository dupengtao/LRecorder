package com.l.recorder.model.impl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.l.recorder.model.IRecorderModel;
import com.l.recorder.presenter.impl.RecorderPresenter;

/**
 * For playing and recording services
 */
public class LRecorderService extends Service implements IRecorderModel {


    private RecorderPresenter mRecorderPresenter;
    private String TAG = "LRecorderService";

    public LRecorderService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecorderPresenter = new RecorderPresenter(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RecorderBinder();
    }
    @Override
    public void test() {
        mRecorderPresenter.test("xxx");
    }

    public class RecorderBinder extends Binder {

        public RecorderPresenter getPresenter() {
            return mRecorderPresenter;
        }

    }
}
