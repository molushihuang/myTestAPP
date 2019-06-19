//
// Created by Administrator on 2019/5/27.
//

#include <jni.h>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <random>

extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_util_JNIUtil_getName(JNIEnv *env, jobject /* this */) {
    std::string hello = " C++ 名字";
    return env->NewStringUTF(hello.c_str());
}

/**
 * 加法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_util_JNIUtil_addNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a + b;
}

/**
 * 减法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_util_JNIUtil_subNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a - b;
}

/**
 * 乘法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_util_JNIUtil_mulNumber(JNIEnv *env, jobject, jint a, jint b) {
    return a * b;
}

/**
 * 除法
 */
extern "C" JNIEXPORT jdouble JNICALL
Java_com_xqd_myapplication_util_JNIUtil_divNumber(JNIEnv *env, jobject, jdouble a, jdouble b) {
    return a / b;
}

/**
 * 访问java的非静态属性
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_util_JNIUtil_accessField(JNIEnv *env, jobject object) {
    jclass cls = env->GetObjectClass(object);//获取到这个方法所在的类

    jfieldID fieldTD = env->GetFieldID(cls, "name", "Ljava/lang/String;");//拿到属性或者变量的fieldID
    jstring str = reinterpret_cast<jstring>(env->GetObjectField(object, fieldTD));//取出值
    jboolean iscopy = NULL;
    char *c_str = (char *) (env->GetStringUTFChars(str, &iscopy));//将jstring转换成为UTF-8格式的char*
    if (c_str == NULL) { //不要忘记检测，否则分配内存失败会抛出异常
        return NULL; /* OutOfMemoryError already thrown */
    }
    char text[] = "访问java的非静态属性";
    strcat(c_str, text);//拼接字符串
    jstring new_str = env->NewStringUTF(c_str);//创建一个UTF-8格式的String对象
    env->SetObjectField(object, fieldTD, new_str);//给变量或者属性设置新的值
    env->ReleaseStringUTFChars(str, c_str); //最后释放资源，通知垃圾回收器来回收

    return new_str;
}

/**
 * 访问java非静态方法
 */
extern "C" JNIEXPORT jint JNICALL
Java_com_xqd_myapplication_util_JNIUtil_accessMethod(JNIEnv *env, jobject object) {
    jclass cls = env->GetObjectClass(object);//获取到这个方法所在的类

    jmethodID methodID = env->GetMethodID(cls, "getIntMethod", "()I");
    jint reint = env->CallIntMethod(object, methodID);
    reint++;
    return reint;
}

/**
 * 访问java构造方法s
 */
extern "C" JNIEXPORT jlong JNICALL
Java_com_xqd_myapplication_util_JNIUtil_accessConstructor(JNIEnv *env, jobject object) {
    jclass cls = env->FindClass("java/util/Date");

    jmethodID constructorID = env->GetMethodID(cls, "<init>", "()V");//获取构造方法的id
    jobject dateObj = env->NewObject(cls, constructorID);//用构造方法声明一个新类
    jmethodID timeID = env->GetMethodID(cls, "getTime", "()J");//获取时间的方法id
    jlong time = env->CallLongMethod(dateObj, timeID);//调用这个时间类里获取时间的方法
    return time;

}

//排序规则，小的在前
int compare(int *a, int *b) {
    return (*a) - (*b);
}

/**
 * 数组处理
 */
extern "C" JNIEXPORT void JNICALL
Java_com_xqd_myapplication_util_JNIUtil_giveArray(JNIEnv *env, jobject object,jintArray array) {
    jint *elems=env->GetIntArrayElements(array,NULL);//获取jint的指针

    qsort(elems, env->GetArrayLength(array), sizeof(jint),
          reinterpret_cast<int (*)(const void *, const void *)>(compare));

    env->ReleaseIntArrayElements(array,elems,0);//操作这个数组并且释放资源
}