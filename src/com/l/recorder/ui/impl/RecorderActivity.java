package com.l.recorder.ui.impl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.l.recorder.R;
import com.l.recorder.presenter.impl.RecordPresenter;
import com.l.recorder.ui.IRecorderView;

public class RecorderActivity extends Activity implements IRecorderView {
    private RecordPresenter mRecordPresenter;
    private TextView tv;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (mRecordPresenter == null) {
            mRecordPresenter = new RecordPresenter();
        }
        mRecordPresenter.onViewCreate(savedInstanceState);
        mRecordPresenter.onTakeView(this,this, 0);
        initView();
    }

    private void initView() {
        tv=(TextView)findViewById(R.id.tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mRecordPresenter.onTakeView(this,null, 0);
        mRecordPresenter.onViewDestroy();
        if (isFinishing()) {
            mRecordPresenter = null;
        }
    }

    public void begin(View view){
        mRecordPresenter.startRecord(this);
    }

    @Override
    public void test(String str) {
        tv.setText(str);
    }
}
