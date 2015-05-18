package com.l.recorder.Player;

/**
 * Created by zhangjiahao on 15-5-12.
 */
public interface IPlayer {
    int start(String path);

    int pause();

    int stop();

    void seekTo(int msec);

    void next();

    void prev();

}
