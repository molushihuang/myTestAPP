package com.example.pherson.myapplication;


public class Java2CJNI {
    static {
        System.loadLibrary("Java2C");
    }

    public native String java2c();
	
	
}
