package net.airtheva.assdroid;

import java.nio.IntBuffer;

import android.opengl.GLES20;
import android.util.Log;

public class Program {
	
	static final String TAG = "Program";
	
	String mVertexShaderSource = null;
	String mFragmentShaderSource = null;
	
	public boolean IsLoaded = false;
	
	// Arguments.
	public int PositionSlot = 0;
	public int ColorSlot = 1;
	public int NormalSlot = 2;
	public int TexCoordSlot = 3;
	
	public int ProgramHandle = 0;
	
	// Uniforms.
	public int ViewMatrixSlot = 0;
	public int ModelMatrixSlot = 0;
	public int ProjectionMatrixSlot = 0;
	public int TextureSlot = 0;
	
	IntBuffer NativeInfo = null;
	
	public Program()
	{
		this(AssDroid.GetTextResource(R.raw.vertex_shader), AssDroid.GetTextResource(R.raw.fragment_shader));
	}
	
	public Program(String vertexShaderSource, String fragmentShaderSource)
	{
		mVertexShaderSource = vertexShaderSource;
		mFragmentShaderSource = fragmentShaderSource;
	}
	
	int loadShader(int shaderType, String source)
	{
		int shaderHandle = GLES20.glCreateShader(shaderType);
		AssDroid.CheckGLError("glCreateShader");
		if(shaderHandle == 0)
		{
			Log.e(TAG, "Failed to create shader.");
			return 0;
		}
		GLES20.glShaderSource(shaderHandle, source);
		AssDroid.CheckGLError("glShaderSource");
		GLES20.glCompileShader(shaderHandle);
		AssDroid.CheckGLError("glCompileShader");
		int[] isCompiled = new int[1];
		GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, isCompiled, 0);
		AssDroid.CheckGLError("glGetShaderiv");
		if(isCompiled[0] == 0)
		{
			Log.e(TAG, "Failed to compile shader.");
			GLES20.glDeleteShader(shaderHandle);
			AssDroid.CheckGLError("glDeleteShader");
			return 0;
		}
		return shaderHandle;
	}
	
	// This method is called by GLThread.
	void Load()
	{
		int vertexShaderHandle = loadShader(GLES20.GL_VERTEX_SHADER, mVertexShaderSource);
		int fragmentShaderHandle = loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentShaderSource);

		int programHandle = GLES20.glCreateProgram();
		AssDroid.CheckGLError("glCreateProgram");
		if(programHandle == 0)
		{
			Log.e(TAG, "Failed to create program.");
			return;
		}
		GLES20.glAttachShader(programHandle, vertexShaderHandle);
		AssDroid.CheckGLError("glAttachShader");
		GLES20.glAttachShader(programHandle, fragmentShaderHandle);
		AssDroid.CheckGLError("glAttachShader");
		
		GLES20.glBindAttribLocation(programHandle, PositionSlot, "aPosition");
		AssDroid.CheckGLError("glBindAttribLocation");
		GLES20.glBindAttribLocation(programHandle, ColorSlot, "aColor");
		AssDroid.CheckGLError("glBindAttribLocation");
		GLES20.glBindAttribLocation(programHandle, NormalSlot, "aNormal");
		AssDroid.CheckGLError("glBindAttribLocation");
		GLES20.glBindAttribLocation(programHandle, TexCoordSlot, "aTexCoord");
		AssDroid.CheckGLError("glBindAttribLocation");
		
		GLES20.glLinkProgram(programHandle);
		AssDroid.CheckGLError("glLinkProgram");
		int[] isLinked = new int[1];
		GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, isLinked, 0);
		AssDroid.CheckGLError("glGetProgramiv");
		if(isLinked[0] == 0)
		{
			Log.e(TAG, "Failed to link program.");
			GLES20.glDeleteProgram(programHandle);
			AssDroid.CheckGLError("glDeleteProgram");
			return;
		}
		
		ProgramHandle = programHandle;
		
		// Uniform.
		ModelMatrixSlot = GLES20.glGetUniformLocation(ProgramHandle, "uModelMatrix");
		ViewMatrixSlot = GLES20.glGetUniformLocation(ProgramHandle, "uViewMatrix");
		ProjectionMatrixSlot = GLES20.glGetUniformLocation(ProgramHandle, "uProjectionMatrix");
		TextureSlot = GLES20.glGetUniformLocation(ProgramHandle, "uTexture");
		AssDroid.CheckGLError("glGetUniformLocation");
		Log.i(TAG, "Program initialized.");
		
		NativeInfo = AssDroid.WrapNativeIntBuffer(new int[]
		{
			ProgramHandle,
			ModelMatrixSlot,
			TextureSlot,
			PositionSlot,
			ColorSlot,
			NormalSlot,
			TexCoordSlot
		});
		
		IsLoaded = true;
	}

}
