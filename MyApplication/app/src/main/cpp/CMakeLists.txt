# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#设置要引用的第三方so库所在的路径，${CMAKE_CURRENT_SOURCE_DIR}表示当前CMakeLists.txt所在的位置
#通过 ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs 表示出libs的位置
set(distribution_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs)
#创建自己的库
#库名字 动态库 文件
add_library( # Sets the name of the library.
        demo

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        # 资源路径是相对路径，相对于本CMakeLists.txt所在目录
        effects_qq_voicer.cpp
        JNItest.cpp
        md5.h
        md5.cpp
        JNIDynamicRegister.cpp)

add_library( # Sets the name of the library.
        my_ffmpeg

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ffm/jx_yuv_encode_h264.cpp
        ffm/jx_pcm_encode_aac.cpp
        ffm/jx_media_muxer.cpp
        ffm/jx_jni_handler.cpp
        ffm/jx_ffmpeg_jni.cpp
        ffm/threadsafe_queue.cpp
        ffm/jx_log.cpp
        ffm/cmdutils.c
        ffm/ffmpeg.c
        ffm/ffmpeg_filter.c
        ffm/ffmpeg_opt.c
        ffm/jx_ffmpeg_cmd_run.c
        ffm/jx_ffmpeg_config.cpp
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

#把libfmod.so预加载进来
#库名字 动态库 导入的
add_library(fmod SHARED IMPORTED)
#改变这个库的属性
#库名字 属性 导入位置
set_target_properties(fmod
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libfmod.so)

#把libfmodL.so预加载进来
add_library(fmodL SHARED IMPORTED)
set_target_properties(fmodL
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libfmodL.so)

add_library(avcodec SHARED IMPORTED)
set_target_properties(
        avcodec
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libavcodec.so
)

add_library(avfilter SHARED IMPORTED)
set_target_properties(
        avfilter
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libavfilter.so
)

add_library(avformat SHARED IMPORTED)
set_target_properties(
        avformat
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libavformat.so
)

add_library(avutil SHARED IMPORTED)
set_target_properties(
        avutil
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libavutil.so
)

add_library(swresample SHARED IMPORTED)
set_target_properties(
        swresample
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libswresample.so
)

add_library(swscale SHARED IMPORTED)
set_target_properties(
        swscale
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libswscale.so
)

add_library(fdk-aac SHARED IMPORTED)
set_target_properties(
        fdk-aac
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libfdk-aac.so
)

include_directories(
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../ffmpeg-3.2.5/

)

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        demo
        my_ffmpeg
        fmod
        fmodL
        fdk-aac
        avcodec
        avfilter
        avformat
        avutil
        swresample
        swscale
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})