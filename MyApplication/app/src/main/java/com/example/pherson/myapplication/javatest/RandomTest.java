package com.example.pherson.myapplication.javatest;

import java.util.Random;

/**
 * Created by 谢邱东 on 2019/1/3 15:14.
 * NO bug
 */

public class RandomTest {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Random random = new Random(25);
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + random.nextInt(100) + ", ");
            }

            System.out.println("");
        }

    }
}
