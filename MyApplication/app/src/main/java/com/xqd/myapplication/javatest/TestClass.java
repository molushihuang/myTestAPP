package com.xqd.myapplication.javatest;

/**
 * Created by 谢邱东 on 2018/12/27 10:32.
 * NO bug
 */

public class TestClass {

    public static void main(String[] args) {
        OutClass outClass = new OutClass();
        OutClass.InnerClass innerClass = outClass.new InnerClass();
        innerClass.InnerMethod();
    }
}
