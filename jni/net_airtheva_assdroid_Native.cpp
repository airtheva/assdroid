#include <jni.h>
#include "net_airtheva_assdroid_Native.h"

#include "assdroid.h"

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv* env = NULL;
	vm->GetEnv((void**) &env, JNI_VERSION_1_6);

	LOGI("JNIEnv = %d.", env);

	return JNI_VERSION_1_6;
}

JNIEXPORT jstring JNICALL Java_net_airtheva_assdroid_Native_HelloWorld(JNIEnv* env, jclass thiz)
{
	Initialize();
	return env->NewStringUTF("Hello World! Greeting from JNI Native Code.");
}

JNIEXPORT jint JNICALL Java_net_airtheva_assdroid_Native_LoadScene(JNIEnv* env, jclass thiz, jstring jPath)
{
	std::string path;
	path.append(env->GetStringUTFChars(jPath, NULL));
	uint sceneKey = LoadScene(&path);
	return sceneKey;
}

JNIEXPORT void JNICALL Java_net_airtheva_assdroid_Native_DrawScene(JNIEnv* env, jclass thiz, jint jSceneKey, jobject jProgramInfo)
{
	adProgram* program = (adProgram*) env->GetDirectBufferAddress(jProgramInfo);
	DrawScene(jSceneKey, program);
}


JNIEXPORT void JNICALL Java_net_airtheva_assdroid_Native_ReleaseScene(JNIEnv* env, jclass thiz, jint jSceneKey)
{
	ReleaseScene(jSceneKey);
}
