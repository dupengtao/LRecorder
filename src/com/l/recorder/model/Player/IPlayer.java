package com.l.recorder.model.Player;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IPlayer {
    void startPlay();

    void pausePlay();

    void stopPlay();

    void seekTo(int msec);
}
