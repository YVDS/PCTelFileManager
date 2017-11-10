#include <jni.h>
#include <string>
#include "scanData.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_telesoftas_pctelfilemanager_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jboolean JNICALL
Java_com_telesoftas_pctelfilemanager_MainActivity_writeScanData(
        JNIEnv *env,
        jobject ) {
    return true;
}
