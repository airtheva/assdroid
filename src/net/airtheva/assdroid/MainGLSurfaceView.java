package net.airtheva.assdroid;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MainGLSurfaceView extends GLSurfaceView {
	
	class LocalRenderer implements Renderer
	{
		
		static final String TAG = "LocalRenderer";
		
		boolean mIsStopped = false;
		
		// TODO: Pending.
		//Stack<Runnable> mRunnables = null;
		
		// Drawing.
		Program mProgram = null;
		
		float mClearColor = 0.8f;
		
		public LocalRenderer() {
			// TODO Auto-generated constructor stub
			//mRunnables = new Stack<Runnable>();
		}
		
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			//GLES20.glEnable(GLES20.GL_CULL_FACE);
			AssDroid.CheckGLError("glEnable");			
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			float ratio = (float) width / height;
			AssDroid.SetRatio(ratio);
			GLES20.glViewport(0, 0, width, height);
			AssDroid.CheckGLError("glViewport");
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			if(mIsStopped)
			{
				return;
			}
			draw();
		}
		
		void draw()
		{
			if(mProgram.IsLoaded == false)
			{
				mProgram.Load();
			}
			GLES20.glUseProgram(mProgram.ProgramHandle);
			AssDroid.CheckGLError("glUseProgram");
			
			GLES20.glClearColor(mClearColor, mClearColor, mClearColor, mClearColor);
			AssDroid.CheckGLError("glClearColor");
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			AssDroid.CheckGLError("glClear");

			// Send in ViewMatrix and ProjectionMatrix, Model provides ModelMatrix, multiply by GPU.
			AssDroid.UpdateMatrix();
			GLES20.glUniformMatrix4fv(mProgram.ViewMatrixSlot, 1, false, AssDroid.ViewMatrix, 0);
			GLES20.glUniformMatrix4fv(mProgram.ProjectionMatrixSlot, 1, false, AssDroid.ProjectionMatrix, 0);
			
			SceneManager.Update();
			SceneManager.Draw(mProgram);
			
			GLES20.glFlush();
			GLES20.glFinish();
		}
		
		void SetProgram(Program program)
		{
			mProgram = program;
		}

	}
	
	static final String TAG = "MainGLSurfaceView";
	
	LocalRenderer mRenderer = null;

	// TouchEvent.
	float mPreviousX = 0.0f;
	float mPreviousY = 0.0f;
	
	public MainGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		getHolder().setFormat(PixelFormat.RGBA_8888);
		setEGLContextClientVersion(2);
		
		mRenderer = new LocalRenderer();
		// ProgramManager!!!
		mRenderer.SetProgram(new Program());
		setRenderer(mRenderer);
		
		//setRenderMode(RENDERMODE_WHEN_DIRTY);
		setRenderMode(RENDERMODE_CONTINUOUSLY);		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume");
		//SceneManager.ResetResource();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			float deltaX = x - mPreviousX;
			float deltaY = y - mPreviousY;
			
			AssDroid.HorizontalAngle -= deltaX * AssDroid.SPEED_VIEW_ROTATING;
			AssDroid.VerticalAngle -= deltaY * AssDroid.SPEED_VIEW_ROTATING;
		}
		
		mPreviousX = x;
		mPreviousY = y;
		
		super.onTouchEvent(event);
		return true;
	}
	
}
