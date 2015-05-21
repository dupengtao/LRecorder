package com.l.recorder.model.Player;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import com.l.recorder.utils.log.LogUtil;

import java.io.IOException;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public class Player implements IPlayer, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    public static final int IDLE = 0;
    public static final int PLAYING = 1;
    public static final int PAUSE = 2;

    private int currentState = IDLE;
    private static final String LOG_TAG = "Player";
    private MediaPlayer mPlayer;
    private static Player mInstance = new Player();

    private Player() {
    }

    public static Player getInstance() {
        return mInstance;
    }

    @Override
    public int start(String path) {
        if (currentState == PLAYING) {
            return currentState;
        }
        if (currentState == IDLE) {
            init(path);
        }

        mPlayer.start();
        currentState = PLAYING;
        return currentState;
    }

    private void init(String path) {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);

        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "prepare() failed");
        }
    }

    @Override
    public int pause() {
        if (currentState == PLAYING) {
            mPlayer.pause();
            currentState = PAUSE;
        }
        return currentState;
    }

    @Override
    public int stop() {
        if (currentState == PLAYING || currentState == PAUSE) {
            release();
            currentState = IDLE;
        }
        return currentState;
    }

    private void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void seekTo(int msec) {
        if (currentState != IDLE) {
            mPlayer.seekTo(msec);
        }
    }

    @Override
    public void next() {
    }

    @Override
    public void prev() {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtil.e(LOG_TAG, "onCompletion run");
        if (currentState == PLAYING) {
            release();
            currentState = IDLE;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtil.e(LOG_TAG, "what = " + what + ",extra = " + extra);
        return false;
    }

    private CountDownTimer changeProgressTimer;

    private void makeCountdownTimerChangeProgress() {
        if (mPlayer != null) {

            int countDownInterval = 50;

            if (mPlayer.getDuration() > 3 * 60 * 1000) {
                countDownInterval = 200;
            }

            changeProgressTimer = new CountDownTimer(Integer.MAX_VALUE, countDownInterval) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                }
            };
            changeProgressTimer.start();
        }
    }
}
