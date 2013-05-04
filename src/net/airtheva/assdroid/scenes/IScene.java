package net.airtheva.assdroid.scenes;

import net.airtheva.assdroid.Program;

public interface IScene {
	
	void SetTranslate(float x, float y, float z);
	float[] GetTranslate();
	void SetRotate(float x, float y, float z);
	float[] GetRotate();
	void SetScale(float x, float y, float z);
	float[] GetScale();
	void SetScaleTo(float x, float y, float z);
	float[] GetScaleTo();
	void ResetTransformation();
	void ResetResource();
	void Update();
	void Draw(Program program);

}