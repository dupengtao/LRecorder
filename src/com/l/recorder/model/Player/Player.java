package com.l.recorder.model.Player;

import android.media.MediaPlayer;
import com.l.recorder.utils.log.LogUtil;

import java.io.IOException;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public class Player implements IPlayer {

    private static final String LOG_TAG = "Player";
    private static final int IDLE = 0;
    private static final int PLAYING = 1;
    private static final int STOP = 3;
    private int currentState = IDLE;

    private MediaPlayer mPlayer;
    private static Player mInstance;
    private String mFileName;

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
        mPlayer = new MediaPlayer();
    }

    public void setDataSource(String path) {
        this.mFileName = path;
    }

    @Override
    public void startPlay() {
        init();
        mPlayer.start();
    }

    private void init() {
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "prepare() failed");
        }
    }

    @Override
    public void pausePlay() {
        mPlayer.pause();
    }

    @Override
    public void stopPlay() {
        mPlayer.stop();
    }

    @Override
    public void seekTo(int msec) {

        mPlayer.seekTo(msec);
    }


}
