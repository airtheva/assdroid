package net.airtheva.assdroid.fragments;

import net.airtheva.assdroid.AssDroid;
import net.airtheva.assdroid.R;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class JoystickFragment extends Fragment implements OnTouchListener{
	
	public static final String TAG = "JoystickFragment";

	static JoystickFragment mInstance = null;
	
	public static JoystickFragment Instance()
	{
		if(mInstance == null)
		{
			mInstance = new JoystickFragment();
		}
		return mInstance;
	}
	
	View mDirection = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_joystick, null);
		mDirection = view.findViewById(R.id.fragment_joystick_forward);
		mDirection.setOnTouchListener(this);
		return view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// TODO: Well, I am not so clear about how to implement a joystick.
		final float SPEED_VIEW_MOVING = 0.1f;
		AssDroid.EyePosition[0] += AssDroid.EyeDirection[0] * SPEED_VIEW_MOVING;
		//AssDroid.EyePosition[1] += AssDroid.EyeDirection[1] * SPEED_VIEW_MOVING;
		AssDroid.EyePosition[2] += AssDroid.EyeDirection[2] * SPEED_VIEW_MOVING;
		return false;
	}

}
