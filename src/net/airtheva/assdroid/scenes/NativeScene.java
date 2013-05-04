package net.airtheva.assdroid.scenes;

import net.airtheva.assdroid.AssDroid;
import net.airtheva.assdroid.Program;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class NativeScene extends BaseScene {

	String mPath = null;
	
	int mSceneKey = 0;
	
	public NativeScene(String path) {
		super();
		// TODO Auto-generated constructor stub
		// TODO: Not used at all. Need transfer to native code.
		mPath = path;
		mSceneKey = AssDroid.LoadScene(mPath);
	}
	
	@Override
	public void Update() {
		// FIXME: Why after override this, ConcurrentModificationException will not raise?
		super.Update();
	}

	@Override
	public void Draw(Program program) {
		// TODO Auto-generated method stub
		GLES20.glUniformMatrix4fv(program.ModelMatrixSlot, 1, false, mModelMatrix, 0);
		AssDroid.DrawScene(mSceneKey, program);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		AssDroid.ReleaseScene(mSceneKey);
		super.finalize();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "NativeScene: " + mPath;
	}
	
	

}
