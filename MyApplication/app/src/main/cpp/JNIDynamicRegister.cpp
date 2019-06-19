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


char *jstringTostring(JNIEnv *env, jstring jstr);

string toMD5(string str);

jstring password(JNIEnv *env, jobject, jstring userid, jstring time);



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

jstring md5(JNIEnv *env, jobject, jstring str) {
    char *text = jstringTostring(env, str);
    string result = toMD5(text);
    return env->NewStringUTF(result.c_str());
}

jstring password(JNIEnv *env, jobject, jstring userid, jstring time) {
    string str1 = jstringTostring(env, userid);
    string str2 = jstringTostring(env, time);

    string timeLast = str2.substr(str2.size() - 2, str2.size());// 截取beginTime最后两位用于替换字符

    string p = toMD5(str1).replace(6, 2, timeLast);//id用时间的后两位字符替换
//    __android_log_print(ANDROID_LOG_ERROR, "JNITag",p.data() );

    string pp = toMD5(str2).replace(10, 2, timeLast);//id用时间的后两位字符替换
//    __android_log_print(ANDROID_LOG_ERROR, "JNITag", pp.c_str());

    string password = toMD5(p.append(pp)).replace(16, 2, timeLast);//id用时间的后两位字符替换
//    __android_log_print(ANDROID_LOG_ERROR, "JNITag", password.c_str());
    return env->NewStringUTF(password.c_str());
}

string toMD5(string str) {
    const char *text = str.c_str();
    CMD5 iMD5;
    iMD5.GenerateMD5((unsigned char *) text, strlen(text));
    string result = iMD5.ToString();
    return result;
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
            {"add",         "(II)I",                                                    (void *) addNumber},
            {"sub",         "(II)I",                                                    (void *) subNumber},
            {"mul",         "(II)I",                                                    (void *) mulNumber},
            {"div",         "(DD)D",                                                    (void *) divNumber},
            {"getPassword", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", (void *) password},
            {"getMD5",      "(Ljava/lang/String;)Ljava/lang/String;",                   (void *) md5}
    };

    //找到对应的JNITools类
    jclass jClassName = env->FindClass("com/xqd/myapplication/util/JNIUtil");

    //开始注册
    jint ret = env->RegisterNatives(jClassName, method, 6);

    //如果注册失败，打印日志
    if (ret != JNI_OK) {
        __android_log_print(ANDROID_LOG_ERROR, "JNITag", "jni_register Error");
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}

/**
 * jstring转string
 * @param env
 * @param jstr
 * @return
 */
char *jstringTostring(JNIEnv *env, jstring jstr) {
//    jboolean iscopy = NULL;
//    char *rtn = (char *)(env->GetStringUTFChars(jstr, &iscopy));//将jstring转换成为UTF-8格式的char*，这种方式，中文字符可能会有问题
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);

        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

/**
 * string 转jstring
 * @param env
 * @param pat
 * @return
 */
jstring charTojstring(JNIEnv *env, const char *pat) {
    //定义java String类 strClass
    jclass strClass = env->FindClass("Ljava/lang/String;");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("GB2312");
    //将byte数组转换为java String,并输出
    return (jstring) (env)->NewObject(strClass, ctorID, bytes, encoding);
}

