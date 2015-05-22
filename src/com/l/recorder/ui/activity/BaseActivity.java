package com.l.recorder.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import com.l.recorder.ui.service.LRecorderService;

/**
 * Created by zhangjiahao on 15-5-22.
 */
public abstract class BaseActivity extends Activity {

    private Intent mService;
    private ServiceConnection mConn;
    private LRecorderService.RecorderBinder mBinder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initService();
        bind();
    }

    private void initService() {
        mService = new Intent(this, LRecorderService.class);
        startService(mService);
    }

    private void bind() {
        mConn = onBind();
        bindService(mService, mConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConn != null && mBinder != null) {
            unbindService(mConn);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unbindService(mConn);
        stopService(mService);
        mBinder = null;
        mConn = null;
    }

    protected abstract ServiceConnection onBind();

}