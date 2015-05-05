package com.l.recorder.model.impl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.l.recorder.model.IPlayModel;
import com.l.recorder.model.IRecordModel;
import com.l.recorder.presenter.impl.RecordPresenter;

/**
 * For playing and recording services
 */
public class LRecorderService extends Service implements IRecordModel,IPlayModel {


    private RecordPresenter mRecordPresenter;
    private String TAG = "LRecorderService";

    public LRecorderService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
            if (LRecorderConstants.ACTION_INIT.equals(action)) {
                init(intent);
            } else if (LRecorderConstants.ACTION_START_RECORD.equals(action)) {
                test();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void init(Intent intent) {
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = LRecorderConstants.MSG_SERVICE_OBJ;
        m.obj = this;
        try {
            callback.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void recordPresenter(RecordPresenter recordPresenter) {
        mRecordPresenter = recordPresenter;
    }

    @Override
    public void test() {
        mRecordPresenter.test("xxx");
    }
}
