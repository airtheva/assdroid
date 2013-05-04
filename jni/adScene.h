/*
 * adScene.h
 *
 *  Created on: Apr 15, 2013
 *      Author: unknown
 */

#ifndef ADSCENE_H_
#define ADSCENE_H_

#include "assdroid.h"
#include "adProgram.h"

#define SIZE_POSITION 3
#define SIZE_COLOR 4
#define SIZE_NORMAL 3
#define SIZE_TEXCOORD 2
#define SIZE_FACE 3

class adScene;
class adNode;
class adMesh;
class adFace;
class adTexture;

class adScene
{
public:
	std::string* mPath;
	adNode* mRootNode;
	uint mNumMeshes;
	adMesh** mMeshes;
	uint mNumTextures;
	uint* mTextureIndices;
	adScene(std::string* path);
	void loadNodes(const aiScene* scene);
	void loadTextures(const aiScene* scene);
	void LoadMesh(uint index, adMesh* mesh);
	void Draw(adProgram* program);
	~adScene();
};

class adNode
{
public:
	adScene* mScene;
	uint mNumChildren;
	adNode** mChildren;
	uint mNumMeshes;
	uint* mMeshIndices;
	adNode(adScene* scene, const aiScene* aiscene, const aiNode* node);
	void Draw(adProgram* program);
	~adNode();
};

class adMesh
{
public:
	adScene* mScene;
	uint mNumVertices;
	aiVector3D* mPositions;
	float* mColors;
	aiVector3D* mNormals;
	float* mTexCoords;
	GLuint mVBOPositions;
	GLuint mVBOColors;
	GLuint mVBONormals;
	GLuint mVBOTexCoords;
	uint mTextureIndex;
	uint mNumFaces;
	adFace** mFaces;
	uint mNumIndices;
	GLuint mIBO;
	adMesh(adScene* scene, const adNode* node, const aiMesh* mesh);
	void Draw(adProgram* program);
	~adMesh();
protected:
	void directDraw(adProgram* program);
	void bufferedDraw(adProgram* program);
};

class adFace
{
public:
	uint mNumIndices;
	unsigned short* mIndices;
	adFace(const aiFace* face);
	void Draw(adProgram* program);
	~adFace();
};

class adTexture
{
public:
	static void SetDefault(adTexture* texture);
	static adTexture* GetDefault();
	std::string* mPath;
	ILuint mImageHandle;
	uint mTextureHandle;
	adTexture(std::string* path);
	uint GetWidth();
	uint GetHeight();
	void Attach(adProgram* program);
	void Detach();
	~adTexture();
protected:
	static adTexture* mDefault;
};

#endif /* ADSCENE_H_ */
