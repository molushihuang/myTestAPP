//
// Created by Administrator on 2019/5/24.
//
#include "com_xqd_myapplication_Java2CJNI.h"

JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_Java2CJNI_java2c(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "Hello World，测试成功");
}


JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_Java2CJNI_getPassword(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "Hello World，密码");
}


JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_Java2CJNI_getName(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "Hello World，名字");
}