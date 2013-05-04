package net.airtheva.assdroid.fragments;

import net.airtheva.assdroid.MainGLSurfaceView;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;

public class DisplayFragment extends Fragment implements OnFocusChangeListener{

	public static final String TAG = "DisplayFragment";
	
	static DisplayFragment mInstance = null;
	
	public static DisplayFragment Instance()
	{
		if(mInstance == null)
		{
			mInstance = new DisplayFragment();
		}
		return mInstance;
	}
	
	MainGLSurfaceView mSurfaceView = null;
	Canvas mSavedCanvas = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mSurfaceView = new MainGLSurfaceView(getActivity());
		// Work with SlidingMenu.
		mSurfaceView.setBackgroundColor(0x00000000);
		//mSurfaceView.setZOrderOnTop(true);
		mSurfaceView.setOnFocusChangeListener(this);
		return mSurfaceView;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		//mSurfaceView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//mSurfaceView.onResume();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(v == mSurfaceView)
		{
			if(hasFocus)
			{
				if(mSavedCanvas != null)
				{
					mSurfaceView.getHolder().unlockCanvasAndPost(mSavedCanvas);
				}				
			}
			else
			{
				mSavedCanvas = mSurfaceView.getHolder().lockCanvas();
			}
		}
	}

}
