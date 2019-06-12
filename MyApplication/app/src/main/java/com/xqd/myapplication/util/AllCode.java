package com.xqd.myapplication.util;

/**
 * Created by 谢邱东 on 2019/6/11 15:02.
 * 静态变量
 * NO bug
 */
public class AllCode {
    public static String TEST_URL = "https://1146.cdn-vod.huaweicloud.com/asset/e1677b0edf077431494d1ec5b56ca961/33cb8d6a4079c9053aa76c14aa29a811.mp4";
//    public static String TEST_URL = "https://56.com-t-56.com/20190602/23126_43452c92/index.m3u8";
//    public static String TEST_URL = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3794319251,3934771263&fm=26&gp=0.jpg";
//    public static String TEST_URL = "http://dl.stream.qqmusic.qq.com/M500004DI5Vw0DnEOW.mp3?vkey=00CA210BC1AA2264A70245E0450B23EB2AC726E13A7C4DC1838500928C0144F42EE74D1B1C7C8E951B39715E6CA100CC22E69C509A279195&guid=5150825362&fromtag=1";

    /**
     * Prefix for all intents created
     */
    public static final String INTENT_PREFIX = "com.zane.androidupnpdemo.";

    /**
     * Prefix for all extra data added to intents
     */
    public static final String INTENT_EXTRA_PREFIX = INTENT_PREFIX + "extra.";

    /**
     * Prefix for all action in intents
     */
    public static final String INTENT_ACTION_PREFIX = INTENT_PREFIX + "action.";

    /**
     * Playing action for MediaPlayer
     */
    public static final String ACTION_PLAYING = INTENT_ACTION_PREFIX + "playing";

    /**
     * Paused playback action for MediaPlayer
     */
    public static final String ACTION_PAUSED_PLAYBACK = INTENT_ACTION_PREFIX + "paused_playback";

    /**
     * Stopped action for MediaPlayer
     */
    public static final String ACTION_STOPPED = INTENT_ACTION_PREFIX + "stopped";

    /**
     * transitioning action for MediaPlayer
     */
    public static final String ACTION_TRANSITIONING = INTENT_ACTION_PREFIX + "transitioning";

    /**
     * Change device action for MediaPlayer
     */
    public static final String ACTION_CHANGE_DEVICE = INTENT_ACTION_PREFIX + "change_device";

    /**
     * Set volume action for MediaPlayer
     */
    public static final String ACTION_SET_VOLUME = INTENT_ACTION_PREFIX + "set_volume";

    /**
     * 主动获取播放进度
     */
    public static final String ACTION_GET_POSITION = INTENT_ACTION_PREFIX + "get_position";
    /**
     * 远程设备回传播放进度
     */
    public static final String ACTION_POSITION_CALLBACK = INTENT_ACTION_PREFIX + "position_callback";
    /**
     * 音量回传
     */
    public static final String ACTION_VOLUME_CALLBACK = INTENT_ACTION_PREFIX + "volume_callback";
    /**
     * 播放进度回传值
     */
    public static final String EXTRA_POSITION = INTENT_ACTION_PREFIX + "extra_position";
    /**
     * 音量回传值
     */
    public static final String EXTRA_VOLUME = INTENT_ACTION_PREFIX + "extra_volume";
    /**
     * 投屏端播放完成
     */
    public static final String ACTION_PLAY_COMPLETE = INTENT_ACTION_PREFIX + "play_complete";

    /**
     * Update the lastChange value action for MediaPlayer
     */
    public static final String ACTION_UPDATE_LAST_CHANGE = INTENT_ACTION_PREFIX + "update_last_change";

}
