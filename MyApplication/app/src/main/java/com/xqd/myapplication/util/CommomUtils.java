package com.xqd.myapplication.util;

import android.content.Context;
import android.provider.Settings;

import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by 谢邱东 on 2019/3/1 14:23.
 * NO bug
 */

public class CommomUtils {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Collection list) {
        return !(list != null && list.size() != 0);
    }

    /**
     * 把时间戳转换成 00:00:00 格式
     *
     * @param timeMs 时间戳
     * @return 00:00:00 时间格式
     */
    public static String getStringTime(int timeMs) {
        StringBuilder formatBuilder = new StringBuilder();
        Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        formatBuilder.setLength(0);
        return formatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    /**
     * 把 00:00:00 格式转成时间戳
     *
     * @param formatTime 00:00:00 时间格式
     * @return 时间戳(毫秒)
     */
    public static int getIntTime(String formatTime) {
        if (isNull(formatTime)) {
            return 0;
        }

        String[] tmp = formatTime.split(":");
        if (tmp.length < 3) {
            return 0;
        }
        int second = Integer.valueOf(tmp[0]) * 3600 + Integer.valueOf(tmp[1]) * 60 + Integer.valueOf(tmp[2]);

        return second * 1000;
    }

    public static boolean enableAdb(Context mContext){
        return Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0;
    }

    public static boolean enableLocation(Context mContext){
        return  Settings.Secure.getInt(mContext.getContentResolver(),Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
    }


}
