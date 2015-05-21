package com.l.recorder.model;

import android.os.CountDownTimer;

/**
 * Created by zhangjiahao on 15-5-20.
 */
public abstract class Timer {

    private CountDownTimer mCountDownTimer;
    private long mCountDownInterval;
    private long mDuring;

    public Timer() {
    }

    public void start(long countDownInterval) {
        mDuring = 0;
        this.mCountDownInterval = countDownInterval;
        mCountDownTimer = new CountDownTimer(Long.MAX_VALUE, mCountDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDuring += mCountDownInterval;
                update(mDuring);
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    protected abstract void update(long msec);

    public long stop() {
        mCountDownTimer.cancel();
        return mDuring;
    }
}
