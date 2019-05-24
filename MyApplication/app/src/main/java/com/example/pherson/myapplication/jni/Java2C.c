//
// Created by Administrator on 2019/5/24.
//
#include "Java2CJNI.h"
JNIEXPORT jstring JNICALL
Java_Java2CJNI_java2c(JNIEnv *env, jobject instance){

return (*env)->NewStringUTF(evn,"helloworld");
}
