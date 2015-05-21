package com.l.recorder.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.l.recorder.R;
import com.l.recorder.model.Recorder.Recorder;
import com.l.recorder.presenter.Presenter;
import com.l.recorder.ui.LRecorderService;

public class RecorderActivity extends Activity implements IRecorderView, View.OnClickListener {
    private Intent mService;
    private RecorderConnection conn;
    private LRecorderService.RecorderBinder mBinder;
    private Presenter mPresenter;

    private TextView tv;
    private Button btn_begin;
    private Button btn_stop;
    private Button btn_flag;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findView();
        initView();
        initService();
        bind();
    }

    private void findView() {
        tv = (TextView) findViewById(R.id.tv);
        btn_flag = (Button) findViewById(R.id.btn_flag);
        btn_begin = (Button) findViewById(R.id.btn_record);
        btn_stop = (Button) findViewById(R.id.btn_stop);
    }

    private void initView() {
        btn_begin.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_flag.setOnClickListener(this);
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
    public void updateTimer(String time) {
        tv.setText(time);
    }

    @Override
    public void updateButtonState(int state) {
        switch (state) {
            case Recorder.IDLE:
                Toast.makeText(this, "停止", Toast.LENGTH_SHORT).show();
                break;
            case Recorder.PAUSE:
                Toast.makeText(this, "暂停", Toast.LENGTH_SHORT).show();
                break;
            case Recorder.RECORDING:
                Toast.makeText(this, "录音", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void updateFlag(int num) {
        Toast.makeText(this, "flag " + num, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_record:
                mPresenter.startOrPauseRecord();
                break;
            case R.id.btn_flag:
                mPresenter.addFlag();
                break;
            case R.id.btn_stop:
                mPresenter.stopRecord();
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
