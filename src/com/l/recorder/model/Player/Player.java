package com.l.recorder.model.Player;

import android.media.MediaPlayer;
import android.os.Handler;
import com.l.recorder.utils.log.LogUtil;

import java.io.IOException;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public class Player implements IPlayer, MediaPlayer.OnCompletionListener {

    private static final String LOG_TAG = "Player";
    private static final int IDLE = 0;
    private static final int PLAYING = 1;
    private static final int PAUSE = 2;
    private int currentState = IDLE;

    private MediaPlayer mPlayer;
    private static Player mInstance;
    private String mFileName;
    private Handler mHandler;

    public static Player getInstance() {
        if (mInstance == null) {
            synchronized (Player.class) {
                if (mInstance == null) {
                    mInstance = new Player();
                }
            }
        }
        return mInstance;
    }

    private Player() {
    }

    @Override
    public void startPlay(String path) {
        if (!mFileName.equals(path)) {
            init();
        } else if (currentState == PLAYING) {
            return;
        }
        currentState = PLAYING;
        mPlayer.start();
    }

    private void init() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(this);
        } else {
            mPlayer.reset();
        }

        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "prepare() failed");
        }
    }

    @Override
    public void pausePlay() {
        if (currentState == PLAYING) {
            mPlayer.pause();
            currentState = PAUSE;
        }
    }

    @Override
    public void stopPlay() {
        if (currentState == PLAYING || currentState == PAUSE) {
            release();
        }
    }

    private void release() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        mFileName = System.currentTimeMillis() + "";
        currentState = IDLE;
    }

    @Override
    public void seekTo(float percent) {
        if (currentState != IDLE) {
            int position = (int) (mPlayer.getDuration() * percent);
            mPlayer.seekTo(position);
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
        if (currentState == PLAYING) {
            release();
        }
    }

    @Override
    public void getHandler(Handler handler) {
        this.mHandler = handler;
    }

}
