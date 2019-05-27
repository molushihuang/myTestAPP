package com.xqd.myapplication.javatest;

/**
 * Created by 谢邱东 on 2018/12/27 10:29.
 * NO bug
 */

public class OutClass {
    private int i = 1;
    private String str = "第三方拿到三分";


    class InnerClass {
        private int i = 2;

        public void InnerMethod() {
            int i = 3;
            System.out.println("i=" + i);
            System.out.println("i=" + this.i);
            System.out.println("i=" + OutClass.this.i);
            System.out.println("str=" + str);
        }
    }
}
