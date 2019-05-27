package com.xqd.myapplication;


public class Java2CJNI {
    static {
        System.loadLibrary("demo");
    }

    public native String getPassword();
    public native String getName();
}
