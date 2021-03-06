//
// Created by Xionghu on 2017/11/24.
//

#include "inc/fmod.hpp"

#include <stdlib.h>
#include <unistd.h>
#include <android/log.h>
#include <jni.h>

#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO,"effects_qq_voicer",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,"effects_qq_voicer",FORMAT,##__VA_ARGS__);

#define MODE_NORMAL  0
#define MODE_LUOLI 1
#define MODE_DASHU 2
#define MODE_JINGSONG 3
#define MODE_GAOGUAI 4
#define MODE_KONGLING 5

using namespace FMOD;

extern "C" JNIEXPORT void JNICALL
Java_com_xqd_myapplication_util_JNIUtil_fix(JNIEnv *env, jobject jobj, jstring path_jstr, jint type) {
    LOGE("%s", "进入到变声方法");
	System *system;
	Sound *sound;
	Channel *channel;
	DSP *dsp;
	float frequency = 0;
	bool playing = true;

	const char* path_cstr = env->GetStringUTFChars(path_jstr, NULL);
    //设置正在播放声音
    jclass  jclaz = env->GetObjectClass(jobj);
    jmethodID mid = env->GetMethodID(jclaz,"setPlaying","(Z)V");
    env->CallVoidMethod(jobj,mid,playing);

	try {
	//初始化
	System_Create(&system);
	//手机录音一般是16位  如果是32位的音频要填32 否则无法播放声音
	system->init(16, FMOD_INIT_NORMAL, NULL);
	//创建声音
	system->createSound(path_cstr, FMOD_DEFAULT, NULL, &sound);
	switch (type) {
	case MODE_NORMAL:
		//原生播放
        LOGE("%s", path_cstr);
		system->playSound(sound, 0, false, &channel);
		LOGE("%s", "fix normal");
		break;
	case MODE_LUOLI:
		//萝莉
		//DSP digital signal process
		//dsp -> 音效
		//FMOD_DSP_TYPE_PITCH  dsp ，提升或者降低音调用的一种音效
		// FMOD_DSP_TYPE_PITCHSHIFT 在fmod_dsp_effects.h中
		system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
		//设置音调的参数
		dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 2.5);
		system->playSound(sound, 0, false, &channel);
		//添加到channel
		channel->addDSP(0, dsp);
		LOGE("%s", "fix luoli");
		break;
	case MODE_DASHU:
		//大叔
		system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
		dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.8);
		system->playSound(sound, 0, false, &channel);
		//添加到channel
		channel->addDSP(0, dsp);
		LOGE("%s", "fix dashu");
		break;
	case MODE_JINGSONG:
		//惊悚
		system->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &dsp);
		dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.5);
		system->playSound(sound, 0, false, &channel);
		channel->addDSP(0, dsp);
		break;
	case MODE_GAOGUAI:
		//搞怪
		//提高说话的速度
		system->playSound(sound, 0, false, &channel);
		channel->getFrequency(&frequency);
		frequency = frequency * 1.6;
		channel->setFrequency(frequency);
		LOGE("%s", "fix gaoguai");
		break;
	case MODE_KONGLING:
	    //空灵
	    system->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
        dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 300);
        dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 20);
        system->playSound(sound, 0, false, &channel);
        channel->addDSP(0, dsp);
        LOGE("%s", "fix kongling");

		break;

	default:
		break;
	}
	}catch(...){
		LOGE("%s","发生异常");
		goto END;
	}
	system->update();
	//进程休眠 单位微秒 us
	//每秒钟判断是否在播放
	while (playing) {
		channel->isPlaying(&playing);
		usleep(1 * 1000);
	}
	goto END;
	//释放资源
END:
    (env)->CallVoidMethod(jobj,mid,playing);
    env->ReleaseStringUTFChars(path_jstr,path_cstr);
	sound->release();
	system->close();
	system->release();


}