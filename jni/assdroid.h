/*
 * assdroid.h
 *
 *  Created on: Mar 24, 2013
 *      Author: unknown
 */

#ifndef ASSDROID_H_
#define ASSDROID_H_

#include <cstdint>
#include <string>
#include <map>
#include <unistd.h>
#include <libgen.h>
#include <sys/stat.h>

#include <cimport.h>
#include <postprocess.h>
#include <scene.h>

#include <IL/il.h>
#include <IL/ilu.h>

#include <GLES2/gl2.h>

#include <android/log.h>

#define LOG_TAG "AssDroid"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#include "adScene.h"
#include "adProgram.h"

#define SIZE_FLOAT 4
#define SIZE_INT 4
#define SIZE_SHORT 2

#define PATH_FILES_DIR "/data/data/net.airtheva.assdroid/files/"
#define PATH_DEFAULT_TEXTURE "/data/data/net.airtheva.assdroid/files/default_texture.png"

void CheckGLError(std::string* function);
bool StringEndWith(std::string* source, std::string* target);
void PrintMatrix4x4(aiMatrix4x4* matrix);
void Initialize();
uint LoadScene(std::string* path);
void DrawScene(uint sceneKey, adProgram* program);
void ReleaseScene(uint sceneKey);
uint LoadTexture(std::string* path);
adTexture* GetTexture(uint textureKey);
void ReleaseTexture(uint textureKey);

#endif /* ASSDROID_H_ */
