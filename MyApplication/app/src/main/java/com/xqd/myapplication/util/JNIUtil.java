package com.xqd.myapplication.util;

import android.content.Context;

/**
 * Created by 谢邱东 on 2019/5/28 14:50.
 * NO bug
 */
public class JNIUtil {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_LUOLI = 1;
    public static final int MODE_DASHU = 2;
    public static final int MODE_JINGSONG = 3;
    public static final int MODE_GAOGUAI = 4;
    public static final int MODE_KONGLING = 5;
    public boolean playing = false;

    public String name = "东爷";

    public int getIntMethod() {
        return 500;
    }

    static {

        System.loadLibrary("fmod");
        System.loadLibrary("fmodL");
        System.loadLibrary("demo");
    }

    /**
     * 专门提供给JNI使用
     * @param flag
     */
    private void setPlaying(boolean flag){
        playing = flag;
    }

    /**
     * 用来判断是否正在播放，如果是就不能再播放
     * @return
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * 静态注册的方法
     */

    public static  native String getName();

    public native int addNumber(int a, int b);

    public native int subNumber(int a, int b);

    public native int mulNumber(int a, int b);

    public native double divNumber(double a, double b);

    public native String accessField();

    public native int accessMethod();

    public native long accessConstructor();

    public native void giveArray(int[] array);

    public  native void fix(String path,int type);

    public static native void startClient(String msg,String serverIp,int serverPort);

    /**
     * 动态注册的方法
     **/

    public native int add(int a, int b);

    public native int sub(int a, int b);

    public native int mul(int a, int b);

    public native double div(double a, double b);

    public native String getPassword(String userid, String time);

    public native String getMD5(String str);

    public static native String getSignature(Context context);
}
