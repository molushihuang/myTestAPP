//
// Created by Administrator on 2019/5/28.
//动态注册native函数
//

#include <jni.h>
#include <string>
#include <android/log.h>
#include <cstdio>
#include <cstdlib>


jint addNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a + b;
}

jint subNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a - b;
}

jint mulNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a * b;
}

jdouble divNumber(JNIEnv *env, jclass clazz, jdouble a, jdouble b) {
    return a / b;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    //打印日志，说明已经进来了
    __android_log_print(ANDROID_LOG_ERROR, "JNITag", "enter jni_onload");

    JNIEnv *env = NULL;

    // 判断是否正确
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    //注册四个方法，注意签名
    const JNINativeMethod method[] = {
            {"add", "(II)I", (void *) addNumber},
            {"sub", "(II)I", (void *) subNumber},
            {"mul", "(II)I", (void *) mulNumber},
            {"div", "(DD)D", (void *) divNumber}
    };

    //找到对应的JNITools类
    jclass jClassName = env->FindClass("com/xqd/myapplication/JNIUtil");

    //开始注册
    jint ret = env->RegisterNatives(jClassName, method, 4);

    //如果注册失败，打印日志
    if (ret != JNI_OK) {
        __android_log_print(ANDROID_LOG_ERROR, "JNITag", "jni_register Error");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}


