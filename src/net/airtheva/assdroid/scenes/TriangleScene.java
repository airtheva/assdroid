package net.airtheva.assdroid.scenes;

import java.nio.FloatBuffer;

import net.airtheva.assdroid.AssDroid;
import net.airtheva.assdroid.Program;
import android.opengl.GLES20;
import android.opengl.Matrix;

@Deprecated
public class TriangleScene extends BaseScene {
	
	static final String TAG = "TriangleScene";
	
	static final int SIZE_POSITION = 3;
	static final int SIZE_COLOR = 4;
	
	FloatBuffer mTrianglePositionBuffer = null;
	FloatBuffer mTriangleColorBuffer = null;
	
	float mRotateDegrees = 0.0f;
	
	public TriangleScene() {
		super();
		// TODO Auto-generated constructor stub
		mTrianglePositionBuffer = AssDroid.WrapNativeFloatBuffer(new float[]{
			-0.5f, 0.0f, 0.0f,
			0.5f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f
		});

		mTriangleColorBuffer = AssDroid.WrapNativeFloatBuffer(new float[]{
			1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f
		});
	}
	
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		//super.Update();
		Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.rotateM(mModelMatrix, 0, mRotateDegrees++, 0.0f, 1.0f, 0.0f);
	}


	@Override
	public void Draw(Program program)
	{
		// FIXME: How to rotate!!!
		GLES20.glUniformMatrix4fv(program.ModelMatrixSlot, 1, false, mModelMatrix, 0);
		
		GLES20.glEnableVertexAttribArray(program.PositionSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.PositionSlot, SIZE_POSITION, GLES20.GL_FLOAT, false, 0, mTrianglePositionBuffer);
		AssDroid.CheckGLError("glVertexAttribPointer");
		
		GLES20.glEnableVertexAttribArray(program.ColorSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.ColorSlot, SIZE_COLOR, GLES20.GL_FLOAT, false, 0, mTriangleColorBuffer);
		AssDroid.CheckGLError("glVertexAttribPointer");
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mTrianglePositionBuffer.capacity());
		AssDroid.CheckGLError("glDrawArrays");
		
		GLES20.glDisableVertexAttribArray(program.PositionSlot);
		GLES20.glDisableVertexAttribArray(program.ColorSlot);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return TAG;
	}

}
