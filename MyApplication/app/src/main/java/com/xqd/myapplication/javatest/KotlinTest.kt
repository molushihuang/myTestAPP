package com.xqd.myapplication.javatest

/**
 * Created by 谢邱东 on 2020/4/28 10:14.
 * NO bug
 */
fun main(args: Array<String>) {
//        println("求和" + sum(7, 8));
//    println("求和" + sum2(3, 7))
    box3()
    var list :ArrayList<String> = ArrayList()
}

fun sum(a: Int, b: Int): Int {
    return a + b;
}

fun sum2(a: Int, b: Int): Int = a + b;

//都没装箱
fun box1() {
    var a: Int = 1000;
    println(a == a)
    var b: Int = a;
    var c: Int = a;
    println(b == c)
    println(b === c)
}

//全部装箱
fun box2() {
    var a: Int? = 1000;
    println(a == a)
    var b: Int? = a;
    var c: Int? = a;
    println(b == c)
    println(b === c)
}

//a不装箱，BC装箱
fun box3() {
    var a: Int = 1000;
    println(a == a)
    var b: Int? = a
    var c: Int? = a;
    println(b == c)//比较的是数值
    println(b === c)//比较的是地址

    when(a){
        1 -> println("value 1")
        100 -> println("value 100")
        1000 -> println("value 1000")
        else-> println("no value")
    }
}