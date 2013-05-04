package net.airtheva.assdroid.fragments;

import java.util.ArrayList;
import java.util.List;

import net.airtheva.assdroid.R;
import net.airtheva.assdroid.SceneManager;
import net.airtheva.assdroid.scenes.IScene;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SceneListFragment extends Fragment implements OnClickListener
{

	static final String TAG = "SceneListFragment";
	
	static SceneListFragment mInstance = null;
	
	public static SceneListFragment Instance()
	{
		if(mInstance == null)
		{
			mInstance = new SceneListFragment();
		}
		return mInstance;
	}
	
	List<IScene> mList = null;
	
	ListView mSceneListView = null;
	Button mRefresh = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mSceneListView = new ListView(getActivity());
		mRefresh = new Button(getActivity());
		mRefresh.setText("Refresh");
		mRefresh.setOnClickListener(this);
		mSceneListView.addHeaderView(mRefresh);
		registerForContextMenu(mSceneListView);
		UpdateList();
		return mSceneListView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mRefresh)
		{
			UpdateList();
		}
	}
	
	public void UpdateList()
	{
		mList = SceneManager.GetScenes();
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < mList.size(); i++)
		{
			list.add(mList.get(i).toString());
		}
		if(getActivity() == null)
		{
			Log.i(TAG, "getActivity() == null.");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
		mSceneListView.setAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = new MenuInflater(getActivity());
		menuInflater.inflate(R.menu.scene, menu);
	}

	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Am I called?");
		Log.i(TAG, Integer.toString(item.getItemId()));
		Bundle args = new Bundle();
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int index = menuInfo.position - 1;
		args.putInt(SceneRotateDialog.KEY_INDEX, index);
		switch(item.getItemId())
		{
		case R.id.menu_scene_translate:
			SceneTranslateDialog sceneTranslateFragment = new SceneTranslateDialog();
			sceneTranslateFragment.setArguments(args);
			sceneTranslateFragment.show(getFragmentManager(), SceneTranslateDialog.TAG);
			Log.i(TAG, "Showed.");
			break;
		case R.id.menu_scene_rotate:
			SceneRotateDialog sceneRotateFragment = new SceneRotateDialog();
			sceneRotateFragment.setArguments(args);
			sceneRotateFragment.show(getFragmentManager(), SceneRotateDialog.TAG);
			Log.i(TAG, "Showed.");
			break;
		case R.id.menu_scene_scale:
			SceneScaleDialog sceneScaleFragment = new SceneScaleDialog();
			sceneScaleFragment.setArguments(args);
			sceneScaleFragment.show(getFragmentManager(), SceneScaleDialog.TAG);
			Log.i(TAG, "Showed.");
			break;
		case R.id.menu_scene_remove:
			SceneManager.RemoveScene(index);
			UpdateList();
			break;
		}
		return false;
	}

}
