package com.l.recorder.utils;

/**
 * Created by zhangjiahao on 15-5-21.
 */
public class RecordTool {
    public static String recordTimeFormat(long msec) {
        long sec = (msec / 1000) % 60;
        long min = (msec / 1000 / 60) % 60;
        long hour = (msec / 1000 / 60 / 60);
        StringBuilder sb = new StringBuilder();
        sb.append(hour < 10 ? ("0") + hour : hour);
        sb.append(":");
        sb.append(min < 10 ? ("0" + min) : min);
        sb.append(":");
        sb.append(sec < 10 ? ("0" + sec) : sec);
        return sb.toString();
    }
}
