package com.xqd.myapplication;

/**
 * Created by 谢邱东 on 2019/5/28 14:50.
 * NO bug
 */
public class JNIUtil {

    static {
        System.loadLibrary("demo");
    }

    /**
     * 静态注册的方法
     */

    public native String getName();

    public native int addNumber(int a, int b);

    public native int subNumber(int a, int b);

    public native int mulNumber(int a, int b);

    public native double divNumber(double a, double b);

    /**
     * 动态注册的方法
     **/

    public native int add(int a, int b);

    public native int sub(int a, int b);

    public native int mul(int a, int b);

    public native double div(double a, double b);

    public native String getPassword(String userid,String time);

    public native String getMD5(String str);
}
