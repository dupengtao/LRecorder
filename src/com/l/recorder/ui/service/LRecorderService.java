package com.l.recorder.ui.service;

import android.content.Intent;
import com.l.recorder.presenter.IPresenter;
import com.l.recorder.presenter.Presenter;
import com.l.recorder.utils.LogUtil;

/**
 * For playing and recording services
 */
public class LRecorderService extends BaseService {

    private static final String TAG = "LRecorderService";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "service create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtil.i(TAG, "service destroy");
    }

    @Override
    protected IPresenter createPresenter() {
        return new Presenter();
    }

}
