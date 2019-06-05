//
// Created by Administrator on 2019/5/28.
//动态注册native函数
//

#include <jni.h>
#include <string>
#include <android/log.h>
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include "md5.h"

using namespace std;

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

jstring password(JNIEnv *env, jobject, jstring str) {
    string hello = " C++ 密码";
    char *s = "sdssds";
    cout << hello;
    return env->NewStringUTF(hello.c_str());
}

jstring getMD5(JNIEnv *env, jobject, jstring str) {
    char *text = "hello world";
    CMD5 iMD5;
    iMD5.GenerateMD5((unsigned char *) text, strlen(text));
    std::string result = iMD5.ToString();
    return env->NewStringUTF(result.c_str());
}



/**
 * 动态注册
 * @param vm
 * @param reserved
 * @return
 */
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    //打印日志，说明已经进来了
    __android_log_print(ANDROID_LOG_ERROR, "JNITag", "enter JNI_OnLoad");

    JNIEnv *env = NULL;

    // 判断是否正确
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    //注册5个方法，注意签名
    const JNINativeMethod method[] = {
            {"add",         "(II)I",                                  (void *) addNumber},
            {"sub",         "(II)I",                                  (void *) subNumber},
            {"mul",         "(II)I",                                  (void *) mulNumber},
            {"div",         "(DD)D",                                  (void *) divNumber},
            {"getPassword", "(Ljava/lang/String;)Ljava/lang/String;", (void *) password},
            {"md5", "(Ljava/lang/String;)Ljava/lang/String;", (void *) getMD5}
    };

    //找到对应的JNITools类
    jclass jClassName = env->FindClass("com/xqd/myapplication/JNIUtil");

    //开始注册
    jint ret = env->RegisterNatives(jClassName, method, 6);

    //如果注册失败，打印日志
    if (ret != JNI_OK) {
        __android_log_print(ANDROID_LOG_ERROR, "JNITag", "jni_register Error");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}


