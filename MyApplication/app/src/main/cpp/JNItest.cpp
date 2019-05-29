//
// Created by Administrator on 2019/5/27.
//

#include <jni.h>
#include <string>
#include <cstdio>
#include <cstdlib>

extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_JNIUtil_getName(JNIEnv *env, jobject /* this */) {
    std::string hello = " C++ 名字";
    return env->NewStringUTF(hello.c_str());
}

/**
 * 加法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_JNIUtil_addNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a + b;
}

/**
 * 减法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_JNIUtil_subNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a - b;
}

/**
 * 乘法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_JNIUtil_mulNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a * b;
}

/**
 * 除法
 */
extern "C" JNIEXPORT jdouble JNICALL
Java_com_xqd_myapplication_JNIUtil_divNumber(JNIEnv *env, jobject, jdouble a, jdouble b) {
    return a / b;
}
