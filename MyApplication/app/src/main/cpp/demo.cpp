//
// Created by Administrator on 2019/5/27.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_Java2CJNI_getName(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = " C++ 名字";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_Java2CJNI_getPassword(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = " C++ 密码";
    return env->NewStringUTF(hello.c_str());
}