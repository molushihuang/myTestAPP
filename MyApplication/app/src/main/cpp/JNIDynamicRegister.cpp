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
//    __android_log_print(ANDROID_LOG_ERROR, "JNITag",p.c_str() );

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

const char *RELEASE_SIGN = "3082030d308201f5a00302010202046a36b362300d06092a864886f70d01010b05003037310b300906035504061302434e310b3009060355040813025343310b3009060355040713024344310e300c06035504031305796\n"
                           "f756d61301e170d3137303632323031343735385a170d3432303631363031343735385a3037310b300906035504061302434e310b3009060355040813025343310b3009060355040713024344310e300c06035504031305\n"
                           "796f756d6130820122300d06092a864886f70d01010105000382010f003082010a02820101009830d39649402f9a72152b09333d0874fdf3e2a55079b6d07f8e2867155314bb2bb4775ed27be02306d11b4888bc666206b\n"
                           "57d8c65cd601c35032c46a5d2cbca0b55f4899d3a3b9e689f51303b2bd0490e8e16b80e6d8ec8fcc46686659b0be17a6995844365dc081733fea6acdd389124551e776d0efef01a1bf8f4d3415fdbdbf8333f1c80480889\n"
                           "bcb560adb83089f963e0a1aa0ed2613ff1d523dce710ecd8b8a30d604723a3bb6236053e7e0b21c9394b5024fcfffab14778bd67fc843062ac8c1cd7a3cdea7e50ba50133468afb776ae2e9fcbbddea303560b113e2cc0c\n"
                           "919c3b5665f0baa2a34931a896929a5f551e3c167fd25f83bf6eac574400e150203010001a321301f301d0603551d0e041604147c4f9b647c9e7b6e8fa7f67268e256a855f0fdd4300d06092a864886f70d01010b050003\n"
                           "82010100042e25588903fedf3b127f1012bbce5d9978e0952086d8671c2ab9abed9ded26fc6fbb4741e78dddab07c35aa17e795d60f8054fe3859e11875c1c538f4fab0fe01185070473403a63673b2f52f41af7a27d20e\n"
                           "bb89be4bce69d8fa46bc6593e03bb7a4f9f3cdc6073cba6352832421a2e0a16ab77e1753e957c92c98e5e4632e3e006e71b7bae45309fc00633ee9191127971288f24373aaec9839ccfba1090ae0e3b153c3b40f5f993a0\n"
                           "c4c6e878954d50499c6c684c6628f8c868a08b0c9c36e9081bfcbc4e5ff9f591334f9a7503331c229024ac8c27cb059033c2afedace6150b8f9990d98383ab90829adada465ea38e051f61e877b468b5fb2795bc1b";

JNIEXPORT jstring JNICALL getbbCourseKeyFromC(JNIEnv *env, jobject contextObject) {

    jclass native_class = env->GetObjectClass(contextObject);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(contextObject, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo",
                                                 "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(contextObject);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(contextObject, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray) signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char *) env->GetStringUTFChars(str, 0);
    //return str;
    if (strcmp(c_msg, RELEASE_SIGN) == 0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF("true");
    } else {
        return (env)->NewStringUTF("error");
    }
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

