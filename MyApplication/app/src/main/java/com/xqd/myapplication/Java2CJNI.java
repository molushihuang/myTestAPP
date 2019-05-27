package com.xqd.myapplication;


public class Java2CJNI {
    static {
        System.loadLibrary("Java2C");
    }

    public native String java2c();

    public native String getPassword();
    public native String getName();
}
