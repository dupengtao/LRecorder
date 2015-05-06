package com.l.recorder.ui.impl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import com.l.recorder.R;
import com.l.recorder.model.impl.LRecorderService;
import com.l.recorder.presenter.impl.RecorderPresenter;
import com.l.recorder.ui.IRecorderView;

public class RecorderActivity extends Activity implements IRecorderView {
    private RecorderPresenter mRecorderPresenter;
    private TextView tv;
    private RecorderConnection conn;

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

    private void initService() {
        Intent intent = new Intent(this, LRecorderService.class);
        startService(intent);
    }

    private void bind() {
        Intent intent = new Intent(this, LRecorderService.class);
        conn = new RecorderConnection();
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        tv=(TextView)findViewById(R.id.tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void begin(View view){
        mRecorderPresenter.startRecord();
    }

    @Override
    public void test(String str) {
        tv.setText(str);
    }


    private class RecorderConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRecorderPresenter = ((LRecorderService.RecorderBinder) service).getPresenter();
            mRecorderPresenter.onTakeView(RecorderActivity.this,0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

}
