package net.airtheva.assdroid.scenes;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.airtheva.assdroid.AssDroid;
import net.airtheva.assdroid.Program;
import net.airtheva.assdroid.R;
import net.airtheva.assdroid.R.drawable;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class GroundScene extends BaseScene {
	
	static final String TAG = "GroundScene";
	
	static final int SIZE_STRIDE_BYTES = (AssDroid.SIZE_POSITION + AssDroid.SIZE_COLOR + AssDroid.SIZE_NORMAL + AssDroid.SIZE_TEXCOORD) * AssDroid.SIZE_FLOAT;
	
	FloatBuffer mGroundVerticesBuffer = null;
	ShortBuffer mGroundIndicesBuffer = null;
	int[] mTextures = null;
	int[] mVBOs = null;
	
	public GroundScene() {
		// TODO Auto-generated constructor stub
		super();
		mGroundVerticesBuffer = AssDroid.WrapNativeFloatBuffer(new float[]{
			32.0f, 0.0f, 32.0f, // Position.
			1.0f, 1.0f, 1.0f, 1.0f, // Color.
			0.0f, 1.0f, 0.0f, // Normal.
			1.0f, 0.0f, // TexCoord.
			
			32.0f, 0.0f, -32.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 0.0f,
			1.0f, 1.0f,
			
			-32.0f, 0.0f, -32.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 1.0f,

			-32.0f, 0.0f, 32.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f
		});

		mGroundIndicesBuffer = AssDroid.WrapNativeShortBuffer(new short[]{
			0, 1, 2,
			2, 3, 0
		});

		mTextures = new int[1];
		mVBOs = new int[1];
		
	}
	
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		super.Update();
	}

	@Override
	public void Draw(Program program)
	{
		if(mTextures[0] == 0)
		{
			GLES20.glGenTextures(1, mTextures, 0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
			Bitmap grassBitmap = AssDroid.GetBitmapResource(R.drawable.noisy_grass_public_domain);;
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, grassBitmap, 0);
			grassBitmap.recycle();
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		}
		if(mVBOs[0] == 0)
		{
			GLES20.glGenBuffers(1, mVBOs, 0);
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOs[0]);
			GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mGroundVerticesBuffer.capacity() * AssDroid.SIZE_FLOAT, mGroundVerticesBuffer, GLES20.GL_STATIC_DRAW);
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		}
		// Texture.
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
		// 0 means GL_TEXTURE0.
		GLES20.glUniform1i(program.TextureSlot, 0);
		// Matrix.
		GLES20.glUniformMatrix4fv(program.ModelMatrixSlot, 1, false, mModelMatrix, 0);
		// Start drawing.
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOs[0]);
		// Position.
		GLES20.glEnableVertexAttribArray(program.PositionSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.PositionSlot, AssDroid.SIZE_POSITION, GLES20.GL_FLOAT, false, SIZE_STRIDE_BYTES, 0);
		AssDroid.CheckGLError("glVertexAttribPointer");			
		// Color.
		GLES20.glEnableVertexAttribArray(program.ColorSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.ColorSlot, AssDroid.SIZE_COLOR, GLES20.GL_FLOAT, false, SIZE_STRIDE_BYTES, AssDroid.SIZE_POSITION * AssDroid.SIZE_FLOAT);
		AssDroid.CheckGLError("glVertexAttribPointer");
		// Normal.
		GLES20.glEnableVertexAttribArray(program.NormalSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.NormalSlot, AssDroid.SIZE_NORMAL, GLES20.GL_FLOAT, false, SIZE_STRIDE_BYTES, (AssDroid.SIZE_POSITION + AssDroid.SIZE_COLOR) * AssDroid.SIZE_FLOAT);
		AssDroid.CheckGLError("glVertexAttribPointer");
		// TexCoord.
		GLES20.glEnableVertexAttribArray(program.TexCoordSlot);
		AssDroid.CheckGLError("glEnableVertexAttribArray");
		GLES20.glVertexAttribPointer(program.TexCoordSlot, AssDroid.SIZE_TEXCOORD, GLES20.GL_FLOAT, false, SIZE_STRIDE_BYTES, (AssDroid.SIZE_POSITION + AssDroid.SIZE_COLOR + AssDroid.SIZE_NORMAL) * AssDroid.SIZE_FLOAT);
		AssDroid.CheckGLError("glVertexAttribPointer");

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, mGroundIndicesBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, mGroundIndicesBuffer);
		AssDroid.CheckGLError("glDrawElements");
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

		GLES20.glDisableVertexAttribArray(program.PositionSlot);
		GLES20.glDisableVertexAttribArray(program.ColorSlot);
		GLES20.glDisableVertexAttribArray(program.NormalSlot);
		GLES20.glDisableVertexAttribArray(program.TexCoordSlot);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return TAG;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		GLES20.glDeleteBuffers(1, mVBOs, 0);
		GLES20.glDeleteTextures(1, mTextures, 0);
		super.finalize();
	}

}
