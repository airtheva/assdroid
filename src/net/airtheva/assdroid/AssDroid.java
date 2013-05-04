package net.airtheva.assdroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.widget.Toast;

public class AssDroid {
	
	static final String TAG = "AssDroid";
	
	public static final int SIZE_POSITION = 3;
	public static final int SIZE_COLOR = 4;
	public static final int SIZE_NORMAL = 3;
	public static final int SIZE_TEXCOORD = 2;

	public static final int SIZE_FLOAT = 4;
	public static final int SIZE_SHORT = 2;
	
	static final String PATH_FILES_DIR = "/data/data/net.airtheva.assdroid/files/";

	static final float SPEED_VIEW_ROTATING = 0.008f;
	static final float ANGLE_HORIZONTAL_DEFAULT = (float) Math.PI;
	static final float ANGLE_VERTICAL_DEFAULT = 0.0f;
	static final float ANGLE_VERTICAL_MAX = (float) (ANGLE_VERTICAL_DEFAULT + Math.PI * 0.5);
	static final float ANGLE_VERTICAL_MIN = (float) (ANGLE_VERTICAL_DEFAULT - Math.PI * 0.5);
	
	static float[] ViewMatrix = null;
	static float[] ProjectionMatrix = null;

	public static float[] EyePosition = null;
	public static float[] EyeDirection = null;
	static float HorizontalAngle = 0.0f;
	static float VerticalAngle = 0.0f;
	
	static Context mContext = null;
	
	static float mFOV = 0.0f;
	
	static float mRatio = 0.0f;
		
	static
	{
		ViewMatrix = new float[16];
		Matrix.setIdentityM(ViewMatrix, 0);
		ProjectionMatrix = new float[16];
		Matrix.setIdentityM(ProjectionMatrix, 0);

		EyePosition = new float[]
		{
			0.0f, 1.0f, 8.0f
		};
		HorizontalAngle = ANGLE_HORIZONTAL_DEFAULT;
		VerticalAngle = 0.0f;
		mFOV = 60.0f;
		UpdateMatrix();
	}
	
	static void resToFiles(int resourceId, String path)
	{
		try {
			File file = new File(PATH_FILES_DIR, path);
			file.createNewFile();
			InputStream in =  mContext.getResources().openRawResource(resourceId);
			FileOutputStream out = new FileOutputStream(file);
			byte[] bytes = new byte[8192];
			while(in.available() > 0)
			{
				int size = in.read(bytes);
				out.write(bytes, 0, size);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void Initialize(Context context)
	{
		mContext = context;
		File fileDir = new File(PATH_FILES_DIR);
		if(!fileDir.exists())
		{
			fileDir.mkdir();
			resToFiles(R.drawable.default_texture, "default_texture.png");
		}
		Toast.makeText(mContext, Native.HelloWorld(), Toast.LENGTH_SHORT).show();
	}
	
	// Resource related.
	
	public static FloatBuffer WrapNativeFloatBuffer(float[] array)
	{
		FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(array);
		buffer.position(0);
		return buffer;
	}
	
	public static IntBuffer WrapNativeIntBuffer(int[] array)
	{
		IntBuffer buffer = ByteBuffer.allocateDirect(array.length * SIZE_FLOAT).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(array);
		buffer.position(0);
		return buffer;
	}
	
	public static ShortBuffer WrapNativeShortBuffer(short[] array)
	{
		ShortBuffer buffer = ByteBuffer.allocateDirect(array.length * SIZE_SHORT).order(ByteOrder.nativeOrder()).asShortBuffer();
		buffer.put(array);
		buffer.position(0);
		return buffer;
	}
	
	static String GetTextResource(int resourceId)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(resourceId)));
		StringBuilder builder = new StringBuilder();
		
		try {
			while(true)
			{
				String line = reader.readLine();
				if(line == null)
				{
					break;
				}
				builder.append(line);
				builder.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static Bitmap GetBitmapResource(int resourceId)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
		return bitmap;
	}
	
	// OpenGL ES related.
	
	static void PrintVector4(float[] vector)
	{
		Log.i(TAG, "+PrintVector4.");
		Log.i(TAG, String.format("%f, %f, %f, %f", vector[0], vector[1], vector[2], vector[3]));
		Log.i(TAG, "-PrintVector4.");
	}
	
	public static void PrintMatrix4x4(float[] matrix)
	{
		Log.i(TAG, "+PrintMatrix4x4.");
		Log.i(TAG, String.format("%f, %f, %f, %f", matrix[0], matrix[4], matrix[8], matrix[12]));
		Log.i(TAG, String.format("%f, %f, %f, %f", matrix[1], matrix[5], matrix[9], matrix[13]));
		Log.i(TAG, String.format("%f, %f, %f, %f", matrix[2], matrix[6], matrix[10], matrix[14]));
		Log.i(TAG, String.format("%f, %f, %f, %f", matrix[3], matrix[7], matrix[11], matrix[15]));
		Log.i(TAG, "-PrintMatrix4x4.");
	}
	
	// Copy from android.opengl.Matrix for back-compatible.
	static void MatrixPerspectiveM(float[] m, int offset, float fovy, float aspect, float zNear, float zFar)
	{
		float f = 1.0f / (float) Math.tan(fovy * (Math.PI / 360));
		float rangeReciprocal = 1.0f / (zNear - zFar);
		
		m[offset + 0] = f / aspect;
		m[offset + 1] = 0.0f;
		m[offset + 2] = 0.0f;
		m[offset + 3] = 0.0f;
		
		m[offset + 4] = 0.0f;
		m[offset + 5] = f;
		m[offset + 6] = 0.0f;
		m[offset + 7] = 0.0f;

		m[offset + 8] = 0.0f;
		m[offset + 9] = 0.0f;
		m[offset + 10] = (zFar + zNear) * rangeReciprocal;
		m[offset + 11] = -1.0f;

		m[offset + 12] = 0.0f;
		m[offset + 13] = 0.0f;
		m[offset + 14] = 2.0f * zFar * zNear * rangeReciprocal;
		m[offset + 15] = 0.0f;
	}
	
	public static void CheckGLError(String function)
	{
		for(int error = GLES20.glGetError(); error != 0; error = GLES20.glGetError())
		{
			Log.e(TAG, String.format("OpenGL ES 2.0 error 0x%x occurred when doing %s.", error, function));
		}
	}
	
	static void SetRatio(float ratio)
	{
		mRatio = ratio;
	}
	
	static void UpdateMatrix()
	{
		if(VerticalAngle > ANGLE_VERTICAL_MAX)
		{
			VerticalAngle = ANGLE_VERTICAL_MAX;
		}
		if(VerticalAngle < ANGLE_VERTICAL_MIN)
		{
			VerticalAngle = ANGLE_VERTICAL_MIN;
		}
		EyeDirection = new float[]
		{
				(float) (Math.cos(VerticalAngle) * Math.sin(HorizontalAngle)),
				(float) Math.sin(VerticalAngle),
				(float) (Math.cos(VerticalAngle) * Math.cos(HorizontalAngle))
		};
		MatrixPerspectiveM(ProjectionMatrix, 0, mFOV, mRatio, 0.1f, 1000f);
		Matrix.setLookAtM(ViewMatrix, 0, EyePosition[0], EyePosition[1], EyePosition[2], EyePosition[0] + EyeDirection[0], EyePosition[1] + EyeDirection[1], EyePosition[2] + EyeDirection[2], 0, 1, 0);		
	}
	
	// Assimp related.
		
	public static int LoadScene(String path)
	{
		return Native.LoadScene(path);
	}
	
	public static void DrawScene(int sceneKey, Program program)
	{
		Native.DrawScene(sceneKey, program.NativeInfo);
	}
	
	public static void ReleaseScene(int sceneKey)
	{
		Native.ReleaseScene(sceneKey);
	}
	
	static void Exit()
	{
		System.exit(0);
	}
	
}
