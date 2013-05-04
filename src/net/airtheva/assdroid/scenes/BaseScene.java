package net.airtheva.assdroid.scenes;

import android.opengl.Matrix;
import net.airtheva.assdroid.AssDroid;
import net.airtheva.assdroid.Program;

public abstract class BaseScene implements IScene {

	float mTranslateX = 0.0f;
	float mTranslateY = 0.0f;
	float mTranslateZ = 0.0f;
	float mRotateX = 0.0f;
	float mRotateY = 0.0f;
	float mRotateZ = 0.0f;
	float mScaleX = 0.0f;
	float mScaleY = 0.0f;
	float mScaleZ = 0.0f;
	
	float[] mModelMatrix = null;
	
	public BaseScene() {
		// TODO Auto-generated constructor stub
		ResetTransformation();
		mModelMatrix = new float[16];
		Matrix.setIdentityM(mModelMatrix, 0);
	}
	
	@Override
	public void SetTranslate(float x, float y, float z) {
		// TODO Auto-generated method stub
		mTranslateX = x;
		mTranslateY = y;
		mTranslateZ = z;
	}

	@Override
	public float[] GetTranslate() {
		// TODO Auto-generated method stub
		return new float[]{ mTranslateX, mTranslateY, mTranslateZ };
	}

	@Override
	public void SetRotate(float x, float y, float z) {
		// TODO Auto-generated method stub
		mRotateX = x;
		mRotateY = y;
		mRotateZ = z;
	}

	@Override
	public float[] GetRotate() {
		// TODO Auto-generated method stub
		return new float[]{ mRotateX, mRotateY, mRotateZ };
	}

	@Override
	public void SetScale(float x, float y, float z) {
		// TODO Auto-generated method stub
		mScaleX = x;
		mScaleY = y;
		mScaleZ = z;
	}

	@Override
	public float[] GetScale() {
		// TODO Auto-generated method stub
		return new float[]{ mScaleX, mScaleY, mScaleZ };
	}

	@Override
	public void SetScaleTo(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] GetScaleTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ResetTransformation() {
		// TODO Auto-generated method stub
		mTranslateX = 0.0f;
		mTranslateY = 0.0f;
		mTranslateZ = 0.0f;
		mRotateX = 0.0f;
		mRotateY = 0.0f;
		mRotateZ = 0.0f;
		mScaleX = 1.0f;
		mScaleY = 1.0f;
		mScaleZ = 1.0f;
	}
	
	@Override
	public void ResetResource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		Matrix.setIdentityM(mModelMatrix, 0);
		//AssDroid.PrintMatrix4x4(mModelMatrix);
		Matrix.scaleM(mModelMatrix, 0, mScaleX, mScaleY, mScaleZ);
		//AssDroid.PrintMatrix4x4(mModelMatrix);
		Matrix.rotateM(mModelMatrix, 0, mRotateX, 1.0f, 0.0f, 0.0f);
		Matrix.rotateM(mModelMatrix, 0, mRotateY, 0.0f, 1.0f, 0.0f);
		Matrix.rotateM(mModelMatrix, 0, mRotateZ, 0.0f, 0.0f, 1.0f);
		//AssDroid.PrintMatrix4x4(mModelMatrix);
		Matrix.translateM(mModelMatrix, 0, mTranslateX, mTranslateY, mTranslateZ);
		//AssDroid.PrintMatrix4x4(mModelMatrix);
	}

	@Override
	public void Draw(Program program) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BaseScene";
	}

}
