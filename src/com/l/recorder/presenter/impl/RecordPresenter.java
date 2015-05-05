package com.l.recorder.presenter.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import com.l.recorder.model.impl.LRecorderConstants;
import com.l.recorder.model.impl.LRecorderService;
import com.l.recorder.presenter.IRecordPresenter;
import com.l.recorder.ui.IRecorderView;

/**
 * Created by dupengtao on 15-5-5.
 */
public class RecordPresenter implements IRecordPresenter {
    private IRecorderView mRecorderView;

    @Override
    public void onViewCreate(Bundle savedState) {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onTakeView(Context context, IRecorderView view, int sign) {
        mRecorderView = view;

        Intent intent = new Intent(LRecorderConstants.SERVICE_NAME);
        intent.setAction(LRecorderConstants.ACTION_INIT);
        intent.setComponent(new ComponentName(context, LRecorderService.class));
        intent.putExtra("messenger", new Messenger(mHandler));
        context.startService(intent);
    }


    public void startRecord(Context context) {
        // start service
        Intent intent = new Intent(LRecorderConstants.SERVICE_NAME);
        intent.setAction(LRecorderConstants.ACTION_START_RECORD);
        intent.setComponent(new ComponentName(context, LRecorderService.class));
        context.startService(intent);
    }

    public void test(final String str) {
        mRecorderView.test(str);
    }


    public void doInMainThread(Runnable runnable) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        } else {
            runnable.run();
        }
    }

    Handler mHandler = new Handler(/* default looper */) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LRecorderConstants.MSG_SERVICE_OBJ: {
                    LRecorderService obj = (LRecorderService) msg.obj;
                    obj.recordPresenter(RecordPresenter.this);
                }
            }
        }
    };

}
