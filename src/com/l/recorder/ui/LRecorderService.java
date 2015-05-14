package com.l.recorder.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.l.recorder.presenter.Presenter;
import com.l.recorder.ui.activity.IRecorderView;
import com.l.recorder.utils.log.LogUtil;

/**
 * For playing and recording services
 */
public class LRecorderService extends Service {

    private static final String TAG = "LRecorderService";
    private Presenter mRecorderPresenter;

    @Override
    public IBinder onBind(Intent intent) {
        return new RecorderBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "service create");
        if (mRecorderPresenter == null) {
            mRecorderPresenter = new Presenter();
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
        LogUtil.i(TAG, "service destroy");
    }

    public class RecorderBinder extends Binder {

        public Presenter getPresenter(IRecorderView iRecorderView, int tag) {
            mRecorderPresenter.holdView(iRecorderView, tag);
            return mRecorderPresenter;
        }

    }
}
