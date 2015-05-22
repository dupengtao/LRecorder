package com.l.recorder.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.l.recorder.presenter.IPresenter;
import com.l.recorder.ui.activity.IRecorderView;

/**
 * Created by zhangjiahao on 15-5-22.
 */
public abstract class BaseService extends Service {

    protected IPresenter mPresenter;

    @Override
    public IBinder onBind(Intent intent) {
        return new RecorderBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter = createPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    protected abstract IPresenter createPresenter();

    public class RecorderBinder extends Binder {

        public IPresenter getPresenter(IRecorderView iRecorderView, int tag) {
            mPresenter.holdView(iRecorderView, tag);
            return mPresenter;
        }

    }
}
