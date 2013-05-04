// assdroid.cpp: .
// net_airtheva_assdroid_Native.cpp: JNI interface.

#include "assdroid.h"

std::map<uint, adScene*> _Scenes;
uint _NextSceneIndex = 0;
std::map<uint, adTexture*> _Textures;
uint _NextTextureIndex = 0;

void CheckGLError(std::string* function){
	for(GLenum error = glGetError(); error != 0; error = glGetError())
	{
		LOGE("OpenGL ES 2.0 error 0x%x occurred when doing %s.", error, function->c_str());
	}
}

bool StringEndWith(std::string* source, std::string* target)
{
	uint position = source->rfind(*target);
	return (position == source->length() - target->length());
}

void PrintMatrix4x4(aiMatrix4x4* matrix)
{
	LOGI("+PrintMatrix4x4.");
	LOGI("%f, %f, %f, %f,", matrix->a1, matrix->a2, matrix->a3, matrix->a4);
	LOGI("%f, %f, %f, %f,", matrix->b1, matrix->b2, matrix->b3, matrix->b4);
	LOGI("%f, %f, %f, %f,", matrix->c1, matrix->c2, matrix->c3, matrix->c4);
	LOGI("%f, %f, %f, %f", matrix->d1, matrix->d2, matrix->d3, matrix->d4);
	LOGI("-PrintMatrix4x4.");
}

void Initialize()
{
	ilInit();
	if(access(PATH_FILES_DIR, 0) != 0)
	{
		LOGE("PATH_FILES_DIR does not exist.");
	}
	adTexture::SetDefault(new adTexture(new std::string(PATH_DEFAULT_TEXTURE)));
}

uint LoadScene(std::string* path)
{
	adScene* scene = new adScene(path);
	uint sceneKey = _NextSceneIndex++;
	_Scenes.insert(std::make_pair<uint, adScene*>(sceneKey, scene));
	return sceneKey;
}

void DrawScene(uint sceneKey, adProgram* program)
{
	_Scenes[sceneKey]->Draw(program);
}

void ReleaseScene(uint sceneKey)
{
	delete _Scenes[sceneKey];
	_Scenes[sceneKey] = NULL;
}

uint LoadTexture(std::string* path)
{
	adTexture* texture = new adTexture(path);
	uint textureKey = _NextTextureIndex++;
	_Textures.insert(std::make_pair<uint, adTexture*>(textureKey, texture));
	return textureKey;
}

adTexture* GetTexture(uint textureKey)
{
	adTexture* texture = _Textures[textureKey];
	if(texture == NULL)
	{
		return adTexture::GetDefault();
	}
	else
	{
		return texture;
	}
}

void ReleaseTexture(uint textureKey)
{
	delete _Textures[textureKey];
	_Textures[textureKey] = NULL;
}
