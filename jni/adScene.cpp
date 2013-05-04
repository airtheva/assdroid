/*
 * adScene.cpp
 *
 *  Created on: Apr 15, 2013
 *      Author: unknown
 */

#include "assdroid.h"
#include "adScene.h"

adScene::adScene(std::string* path)
{
	mPath = path;

	const aiScene* scene = aiImportFile(path->c_str(), aiProcess_Triangulate | aiProcess_MakeLeftHanded);
	// FIXME: Nodes may help make meshes be in the proper position.
	loadNodes(scene);
	loadTextures(scene);
	// adNode will not load the meshes, only import the data needed.
	aiReleaseImport(scene);
}

void adScene::loadNodes(const aiScene* scene)
{
	mNumMeshes = scene->mNumMeshes;
	mMeshes = new adMesh*[mNumMeshes];
	mRootNode = new adNode(this, scene, scene->mRootNode);
}

void adScene::loadTextures(const aiScene* scene)
{
	char* cwd = new char[255];
	getcwd(cwd, 255);
	LOGI("CWD = %s.", cwd);
	chdir(dirname(mPath->c_str()));
	mNumTextures = scene->mNumMaterials;
	mTextureIndices = new uint[mNumTextures];
	for(uint i = 0; i < mNumTextures; i++)
	{
		// FIXME: Not all textures are loaded.
		aiReturn isFound;
		aiString path;
		// The zero may cause ignorant.
		isFound = scene->mMaterials[i]->GetTexture(aiTextureType_DIFFUSE, 0, &path);
		LOGI("Texture path = %s.", path.C_Str());
		if(isFound == AI_SUCCESS)
		{
			// FIXME: Add it to mTextureIndices. Maybe mNumTextures is needed.
			mTextureIndices[i] = LoadTexture(new std::string(path.C_Str()));
			LOGI("Texture added.");
		}
	}
	chdir(cwd);
}

void adScene::LoadMesh(uint index, adMesh* mesh)
{
	mMeshes[index] = mesh;
}

void adScene::Draw(adProgram* program)
{
	mRootNode->Draw(program);
}

adScene::~adScene()
{
	delete mPath;
	delete mMeshes;
	delete mTextureIndices;
}

adNode::adNode(adScene* scene, const aiScene* aiscene, const aiNode* node)
{
	mScene = scene;
	// Place there for mesh initializing.
	mNumChildren = node->mNumChildren;
	mChildren = new adNode*[mNumChildren];
	for(uint i = 0; i < mNumChildren; i++)
	{
		mChildren[i] = new adNode(mScene, aiscene, node->mChildren[i]);
	}
	mNumMeshes = node->mNumMeshes;
	mMeshIndices = new uint[mNumMeshes];
	for(uint i = 0; i < mNumMeshes; i++)
	{
		mMeshIndices[i] = node->mMeshes[i];
		adMesh* mesh = new adMesh(mScene, this, aiscene->mMeshes[mMeshIndices[i]]);
		mScene->LoadMesh(mMeshIndices[i], mesh);
	}
}

void adNode::Draw(adProgram* program)
{

	// FIXME: Handle transformation!
	for(uint i = 0; i < mNumMeshes; i++)
	{
		mScene->mMeshes[mMeshIndices[i]]->Draw(program);
	}
	for(uint i = 0; i < mNumChildren; i++)
	{
		mChildren[i]->Draw(program);
	}
}

adNode::~adNode()
{
	delete mChildren;
	delete mMeshIndices;
}

adMesh::adMesh(adScene* scene, const adNode* node, const aiMesh* mesh)
{
	mScene = scene;
	mNumVertices = mesh->mNumVertices;
	mPositions = new aiVector3D[mNumVertices];
	memcpy(mPositions, mesh->mVertices, mNumVertices * SIZE_POSITION * SIZE_FLOAT);
	mColors = new float[mNumVertices * SIZE_COLOR];
	for(uint i = 0; i < mNumVertices; i++)
	{
		if(mesh->mColors != NULL && mesh->mColors[0] != NULL)
		{
			LOGI("sizeof(mColors) = %d.", sizeof(mesh->mColors));
			LOGI("sizeof(mColors[0]) = %d.", sizeof(mesh->mColors[0]));
			mColors[i * SIZE_COLOR + 0] = mesh->mColors[0][i].r;
			mColors[i * SIZE_COLOR + 1] = mesh->mColors[0][i].g;
			mColors[i * SIZE_COLOR + 2] = mesh->mColors[0][i].b;
			mColors[i * SIZE_COLOR + 3] = mesh->mColors[0][i].a;
		}
		else
		{
		mColors[i * SIZE_COLOR + 0] = 1.0f;
		mColors[i * SIZE_COLOR + 1] = 1.0f;
		mColors[i * SIZE_COLOR + 2] = 1.0f;
		mColors[i * SIZE_COLOR + 3] = 1.0f;
		}
	}
	mNormals = new aiVector3D[mNumVertices];
	memcpy(mNormals, mesh->mNormals, mNumVertices * SIZE_NORMAL * SIZE_FLOAT);
	mTexCoords = new float[mNumVertices * SIZE_TEXCOORD];
	for(uint i = 0; i < mNumVertices; i++)
	{
		mTexCoords[i * SIZE_TEXCOORD + 0] = mesh->mTextureCoords[0][i].x;
		mTexCoords[i * SIZE_TEXCOORD + 1] = 1.0f - mesh->mTextureCoords[0][i].y;
	}

	mTextureIndex = mesh->mMaterialIndex;

	mNumFaces = mesh->mNumFaces;
	mFaces = new adFace*[mNumFaces];
	for(uint i = 0; i < mNumFaces; i++)
	{
		mFaces[i] = new adFace(&mesh->mFaces[i]);
	}

	mVBOPositions = 0;
	mVBOColors = 0;
	mVBONormals = 0;
	mVBOTexCoords = 0;
	mNumIndices = 0;
	mIBO = 0;

}

void adMesh::directDraw(adProgram* program)
{
	glEnableVertexAttribArray(program->PositionSlot);
	glVertexAttribPointer(program->PositionSlot, SIZE_POSITION, GL_FLOAT, GL_FALSE, 0, mPositions);
	glEnableVertexAttribArray(program->ColorSlot);
	glVertexAttribPointer(program->ColorSlot, SIZE_COLOR, GL_FLOAT, GL_FALSE, 0, mColors);
	glEnableVertexAttribArray(program->NormalSlot);
	glVertexAttribPointer(program->NormalSlot, SIZE_NORMAL, GL_FLOAT, GL_FALSE, 0, mNormals);
	glEnableVertexAttribArray(program->TexCoordSlot);
	glVertexAttribPointer(program->TexCoordSlot, SIZE_TEXCOORD, GL_FLOAT, GL_FALSE, 0, mTexCoords);
	adTexture* texture = GetTexture(mScene->mTextureIndices[mTextureIndex]);
	texture->Attach(program);
	for(uint i = 0; i < mNumFaces; i++)
	{
		mFaces[i]->Draw(program);
	}
	glDisableVertexAttribArray(program->PositionSlot);
	glDisableVertexAttribArray(program->ColorSlot);
	glDisableVertexAttribArray(program->NormalSlot);
	glDisableVertexAttribArray(program->TexCoordSlot);
	texture->Detach();
}

void adMesh::bufferedDraw(adProgram* program)
{
	if(mVBOPositions == 0)
	{
		// Setup VBO.
		glGenBuffers(1, &mVBOPositions);
		glBindBuffer(GL_ARRAY_BUFFER, mVBOPositions);
		glBufferData(GL_ARRAY_BUFFER, mNumVertices * SIZE_POSITION * SIZE_FLOAT, mPositions, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glGenBuffers(1, &mVBOColors);
		glBindBuffer(GL_ARRAY_BUFFER, mVBOColors);
		glBufferData(GL_ARRAY_BUFFER, mNumVertices * SIZE_COLOR * SIZE_FLOAT, mColors, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glGenBuffers(1, &mVBONormals);
		glBindBuffer(GL_ARRAY_BUFFER, mVBONormals);
		glBufferData(GL_ARRAY_BUFFER, mNumVertices * SIZE_NORMAL * SIZE_FLOAT, mNormals, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glGenBuffers(1, &mVBOTexCoords);
		glBindBuffer(GL_ARRAY_BUFFER, mVBOTexCoords);
		glBufferData(GL_ARRAY_BUFFER, mNumVertices * SIZE_TEXCOORD * SIZE_FLOAT, mTexCoords, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	if(mIBO == 0)
	{
		// Setup IBO.
		mNumIndices = mNumFaces * SIZE_FACE;
		unsigned short* indices = new unsigned short[mNumIndices];
		for(uint i = 0; i < mNumFaces; i++)
		{
			indices[i * SIZE_FACE + 0] = mFaces[i]->mIndices[0];
			indices[i * SIZE_FACE + 1] = mFaces[i]->mIndices[1];
			indices[i * SIZE_FACE + 2] = mFaces[i]->mIndices[2];
		}
		glGenBuffers(1, &mIBO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, mNumIndices * SIZE_SHORT, indices, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		delete indices;
	}
	glBindBuffer(GL_ARRAY_BUFFER, mVBOPositions);
	glEnableVertexAttribArray(program->PositionSlot);
	glVertexAttribPointer(program->PositionSlot, SIZE_POSITION, GL_FLOAT, GL_FALSE, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ARRAY_BUFFER, mVBOColors);
	glEnableVertexAttribArray(program->ColorSlot);
	glVertexAttribPointer(program->ColorSlot, SIZE_COLOR, GL_FLOAT, GL_FALSE, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ARRAY_BUFFER, mVBONormals);
	glEnableVertexAttribArray(program->NormalSlot);
	glVertexAttribPointer(program->NormalSlot, SIZE_NORMAL, GL_FLOAT, GL_FALSE, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ARRAY_BUFFER, mVBOTexCoords);
	glEnableVertexAttribArray(program->TexCoordSlot);
	glVertexAttribPointer(program->TexCoordSlot, SIZE_TEXCOORD, GL_FLOAT, GL_FALSE, 0, 0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	adTexture* texture = GetTexture(mScene->mTextureIndices[mTextureIndex]);
	texture->Attach(program);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
	glDrawElements(GL_TRIANGLES, mNumIndices, GL_UNSIGNED_SHORT, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	glDisableVertexAttribArray(program->PositionSlot);
	glDisableVertexAttribArray(program->ColorSlot);
	glDisableVertexAttribArray(program->NormalSlot);
	glDisableVertexAttribArray(program->TexCoordSlot);
	texture->Detach();

}

void adMesh::Draw(adProgram* program)
{
	bufferedDraw(program);
}

adMesh::~adMesh()
{
	delete mPositions;
	delete mNormals;
	delete mTexCoords;
	delete mFaces;
}

adFace::adFace(const aiFace* face)
{
	// Only triangles are supported.
	mIndices = new unsigned short[3];
	mIndices[0] = (unsigned short) face->mIndices[0];
	mIndices[1] = (unsigned short) face->mIndices[1];
	mIndices[2] = (unsigned short) face->mIndices[2];
}

void adFace::Draw(adProgram* program)
{
	// TODO: Draw per face.
	glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, mIndices);
}

adFace::~adFace()
{
	delete mIndices;
}

adTexture* adTexture::mDefault = NULL;

void adTexture::SetDefault(adTexture* texture)
{
	mDefault = texture;
}

adTexture* adTexture::GetDefault()
{
	if(mDefault == NULL)
	{
		LOGE("adTexture::mDefault == NULL.");
	}
	return mDefault;
}

adTexture::adTexture(std::string* path)
{
	//if(access(path, 0));
	mPath = path;
	mImageHandle = ilGenImage();
	mTextureHandle = 0;

	LOGI("adTexture = %s.", mPath->c_str());

	ilBindImage(mImageHandle);

	ILboolean isLoaded = ilLoadImage(mPath->c_str());

	if(isLoaded == IL_FALSE)
	{
		LOGI("Image is not loaded.");
	}

	LOGI("width = %d, height = %d.", ilGetInteger(IL_IMAGE_WIDTH), ilGetInteger(IL_IMAGE_HEIGHT));

	// Strange, when loaded, the image is upside down.
	// Tweak.
	if(StringEndWith(mPath, new std::string(".bmp")))
	{
		LOGI("BMP Tweaking.");
		iluRotate(180);
	}

	ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);

	ilBindImage(0);

}

void adTexture::Attach(adProgram* program)
{
	if(mTextureHandle == 0)
	{
		ilBindImage(mImageHandle);

		glGenTextures(1, &mTextureHandle);

		glBindTexture(GL_TEXTURE_2D, mTextureHandle);

		// FIXME: May cause problems.
		//glTexImage2D(GL_TEXTURE_2D, 0, ilGetInteger(IL_IMAGE_BPP), ilGetInteger(IL_IMAGE_WIDTH), ilGetInteger(IL_IMAGE_HEIGHT), 0, ilGetInteger(IL_IMAGE_FORMAT), GL_UNSIGNED_BYTE, ilGetData());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ilGetInteger(IL_IMAGE_WIDTH), ilGetInteger(IL_IMAGE_HEIGHT), 0, GL_RGBA, GL_UNSIGNED_BYTE, ilGetData());
		glBindTexture(GL_TEXTURE_2D, 0);

		ilBindImage(0);
	}
	// TODO: What is this?
	glActiveTexture(GL_TEXTURE0);
	glBindTexture(GL_TEXTURE_2D, mTextureHandle);
	glUniform1i(program->TextureSlot, 0);
}

void adTexture::Detach()
{
	glBindTexture(GL_TEXTURE_2D, 0);
}

adTexture::~adTexture()
{
	delete mPath;
	ilDeleteImage(mImageHandle);
}
