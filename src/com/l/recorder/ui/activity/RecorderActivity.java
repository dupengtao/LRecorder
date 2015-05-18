package com.l.recorder.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.l.recorder.R;
import com.l.recorder.model.dao.RecordDB;
import com.l.recorder.presenter.Presenter;
import com.l.recorder.ui.LRecorderService;

public class RecorderActivity extends Activity implements IRecorderView, View.OnClickListener {
    private Intent mService;
    private RecorderConnection conn;
    private LRecorderService.RecorderBinder mBinder;
    private Presenter mPresenter;

    private TextView tv;
    private Button btn_begin;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        initService();
        bind();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        btn_begin = (Button) findViewById(R.id.btn_begin);

        btn_begin.setOnClickListener(this);
    }

    private void initService() {
        mService = new Intent(this, LRecorderService.class);
        startService(mService);
    }

    private void bind() {
        conn = new RecorderConnection();
        bindService(mService, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null && mBinder != null) {
            unbindService(conn);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unbindService(conn);
        stopService(mService);
        mBinder = null;
        conn = null;
    }

    @Override
    public void test(String str) {
        tv.setText(str);
    }

    @Override
    public void onRecordTimeChanged(long msec) {

    }

    @Override
    public void onRecordStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_begin:
                break;
        }
    }

    private class RecorderConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPresenter = ((LRecorderService.RecorderBinder) service).getPresenter(RecorderActivity.this, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

}
