package com.l.recorder.model.Player;


import android.os.Handler;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IPlayer {
    void startPlay(String path);

    void pausePlay();

    void stopPlay();

    void seekTo(float percent);

    void next();

    void prev();

    void getHandler(Handler handler);
}
