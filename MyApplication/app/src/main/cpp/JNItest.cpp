//
// Created by Administrator on 2019/5/27.
//

#include <jni.h>
#include <string>
#include <random>

#include<cstdio>
#include<cstdlib>
#include<cstring>
//第一步：导入Socket编程的标准库
//这个标准库：linux数据类型(size_t、time_t等等......)
#include<sys/types.h>
//提供socket函数及数据结构
#include<sys/socket.h>
//数据结构(sockaddr_in)
#include<netinet/in.h>
//ip地址的转换函数
#include<arpa/inet.h>

#include <android/log.h>

#define  LOG_TAG    "socket_client"
#define  LOGI(FORMAT, ...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,FORMAT,##__VA_ARGS__);
#define  LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,FORMAT,##__VA_ARGS__);

extern "C" JNIEXPORT jstring JNICALL
Java_com_xqd_myapplication_util_JNIUtil_getName(JNIEnv *env, jobject ) {
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
    env->DeleteLocalRef(dateObj);//通知垃圾回收器回收这些对象
    return time;

}

//排序规则，小的在前
int compare (const void * a, const void * b)
{
    return ( *(int*)a - *(int*)b );
}

/**
 * 数组处理
 */
extern "C" JNIEXPORT void JNICALL
Java_com_xqd_myapplication_util_JNIUtil_giveArray(JNIEnv *env, jobject object,jintArray array) {
    jint *elems=env->GetIntArrayElements(array,NULL);//获取jint的指针

    qsort(elems, env->GetArrayLength(array), sizeof(jint),compare);
    env->ReleaseIntArrayElements(array,elems,0);//操作这个数组并且释放资源
}

/**
 * 启动客户端socket
 */
extern "C" JNIEXPORT void JNICALL
Java_com_xqd_myapplication_util_JNIUtil_startClient(JNIEnv *env, jobject jobj,jstring msg, jstring server_ip_jstr,
                                                    jint server_port) {
    //ip地址
    const char *server_ip = env->GetStringUTFChars(server_ip_jstr, NULL);
    //发送的消息
    const char *message = env->GetStringUTFChars(msg, NULL);

    //服务端网络地址
    struct sockaddr_in server_addr;

    //初始化网络地址
    //参数一：传变量的地址($server_addr)
    //参数二：开始位置
    //参数三：大小
    //初始化服务端网络地址
    memset(&server_addr, 0, sizeof(server_addr));
    //AF_INET:TCP/IP协议、UDP
    //AF_ISO:ISO 协议
    server_addr.sin_family = AF_INET;
    //设置服务端IP地址(自动获取系统默认的本机IP，自动分配)
    server_addr.sin_addr.s_addr = inet_addr(server_ip);
    //设置服务端端口
    server_addr.sin_port = htons(server_port);

    //创建客户端
    int client_socket_fd = socket(PF_INET, SOCK_STREAM, 0);
    //判断是否创建成功
    if (client_socket_fd < 0) {
        LOGE("create error!");
        return;
    }

    //连接服务器
    //参数一：哪一个客户端
    //参数二：连接服务器地址
    //参数三：地址大小
    int con_result = connect(client_socket_fd, (struct sockaddr *) &server_addr, sizeof(server_addr));
    if (con_result < 0) {
        LOGE("connect error!");
        return;
    }

    //发送消息(向服务器发送内容)
    //参数一：指定客户端
    //参数二：指定缓冲区(冲那里数据读取)
    //参数三：实际读取的大小strlen(buffer)(其实读取到"\0"结束)
    //参数四：从哪里开始读取
    send(client_socket_fd, message, strlen(message), 0);

    //关闭
    shutdown(client_socket_fd, SHUT_RDWR);
    LOGI("client--- end-----");

    //必须调用这个函数释放内存
    env->ReleaseStringUTFChars(server_ip_jstr, server_ip);
    env->ReleaseStringUTFChars(msg, message);
    return;

}