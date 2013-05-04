package net.airtheva.assdroid;

import java.util.ArrayList;

import net.airtheva.assdroid.fragments.DisplayFragment;
import net.airtheva.assdroid.fragments.MenuFragment;
import net.airtheva.assdroid.fragments.SceneRotateDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	static final String TAG = "MainActivity";
	
	ArrayList<Fragment> mFragments = null;
	
	long mLastBackTime = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AssDroid.Initialize(this);
		mFragments = new ArrayList<Fragment>();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setBehindContentView(R.layout.activity_main_menu);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.activity_main_menu, MenuFragment.Instance())
			.commit();
		setContentView(R.layout.activity_main_content);
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setFadeEnabled(false);
		slidingMenu.setShadowDrawable(null);
		updateSlidingMenu();
		setSlidingActionBarEnabled(false);
		PushStack(DisplayFragment.TAG, DisplayFragment.Instance());
		// TODO: Long press to show options menu.
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_R:
			(new SceneRotateDialog()).show(getSupportFragmentManager(), "SceneEulerRotate");
			break;
		case KeyEvent.KEYCODE_W:
			AssDroid.EyePosition[2]--;
			break;
		case KeyEvent.KEYCODE_Z:
			AssDroid.EyePosition[2]++;
			break;
		case KeyEvent.KEYCODE_A:
			AssDroid.EyePosition[0]--;
			break;
		case KeyEvent.KEYCODE_D:
			AssDroid.EyePosition[0]++;
			break;
		case KeyEvent.KEYCODE_E:
			AssDroid.EyePosition[1]++;
			break;
		case KeyEvent.KEYCODE_X:
			AssDroid.EyePosition[1]--;
			break;
		case KeyEvent.KEYCODE_MENU:
			SlidingMenu slidingMenu = getSlidingMenu();
			if(slidingMenu.isMenuShowing())
			{
				slidingMenu.showContent();
			}
			else if(!slidingMenu.isMenuShowing())
			{
				slidingMenu.showMenu();
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			Log.i(TAG, Integer.toString(mFragments.size()));
			if(mFragments.size() > 1)
			{
				PopStack();
			}
			else
			{
				long backTime = System.currentTimeMillis();
				if(backTime - mLastBackTime > 2000)
				{
					Toast.makeText(this, "Press BACK again to exit.", Toast.LENGTH_SHORT).show();
					mLastBackTime = backTime;
				}
				else
				{
					AssDroid.Exit();					
				}
			}
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		updateSlidingMenu();
	}
	
	void updateSlidingMenu()
	{
		getSlidingMenu().setBehindOffset((int) getResources().getFraction(R.dimen.activity_main_menu_offset, getResources().getDisplayMetrics().widthPixels, 1));
	}
	
	public void PushStack(String name, Fragment fragment)
	{
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.activity_main_content, fragment, name)
			//.addToBackStack(name)
			.commit();
	}
	
	public boolean StackExist(String name)
	{
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
		if(fragment != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void RemoveStack(Fragment fragment)
	{
		mFragments.remove(fragment);
		getSupportFragmentManager().beginTransaction()
			.remove(fragment)
			.commit();
	}
	
	public void PopStack()
	{
		RemoveStack(mFragments.get(mFragments.size() - 1));
	}
	
}
