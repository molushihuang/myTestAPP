package com.xqd.myapplication.javatest

import java.util.HashSet

/**
 * Created by 谢邱东 on 2020/4/28 10:14.
 * NO bug
 */
fun main(args: Array<String>) {
//        println("求和" + sum(7, 8));
//    println("求和" + sum2(3, 7))
//    box3()
//    var list: ArrayList<String> = ArrayList()

    println(isHappy(168))
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

    when (a) {
        1 -> println("value 1")
        100 -> println("value 100")
        1000 -> println("value 1000")
        else -> println("no value")
    }
}

//leetCode 算法题：171.Excel表列序号
fun titleToNumber(columnTitle: String): Int {
    var num = 0
    for (index in columnTitle.indices) {
        num = num * 26 + (columnTitle[index].toUpperCase() - 'A' + 1)
    }
    return num

}


fun goNext(come: Int): Int {
    var n = come
    var num = 0
    while (n > 0) {
        val d = n % 10
        n /= 10
        num += d * d
    }
    return num
}

//LeetCode 算法题202，快乐数。快乐数总结，将int数据每个数字提出来的方法可以用除以10的余数来获取，循环获取就能取到这些数字。灵活运用哈希集合来存储数据
fun isHappy(come: Int): Boolean {
    var n = come
    val set = HashSet<Int>()
    while (n != 1 && !set.contains(n)) {
        set.add(n)
        n = goNext(n)
    }
    return n == 1
}




