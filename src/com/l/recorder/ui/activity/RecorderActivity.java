package com.l.recorder.ui.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.l.recorder.R;
import com.l.recorder.engine.Recorder.Recorder;
import com.l.recorder.presenter.Presenter;
import com.l.recorder.ui.service.BaseService;
import com.l.recorder.utils.ToastUtil;

public class RecorderActivity extends BaseActivity implements IRecorderView, View.OnClickListener {

    private TextView tv;
    private Button btn_begin;
    private Button btn_stop;
    private Button btn_flag;
    private Presenter mPresenter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findView();
        initView();
    }

    @Override
    protected ServiceConnection onBind() {
        return new RecorderConnection();
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

    @Override
    public void updateTimer(String time) {
        tv.setText(time);
    }

    @Override
    public void updateButtonState(int state) {
        switch (state) {
            case Recorder.IDLE:
                ToastUtil.showToast(this, "停止");
                break;
            case Recorder.PAUSE:
                ToastUtil.showToast(this, "暂停");
                break;
            case Recorder.RECORDING:
                ToastUtil.showToast(this, "录音");
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
                mPresenter.onMainButtonClicked();
                break;
            case R.id.btn_flag:
                mPresenter.onLeftButtonClicked();
                break;
            case R.id.btn_stop:
                mPresenter.onRightButtonClicked();
                break;
        }
    }

    private class RecorderConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPresenter = (Presenter) ((BaseService.RecorderBinder) service).getPresenter(RecorderActivity.this, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}
