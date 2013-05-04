package net.airtheva.assdroid.fragments;

import java.util.ArrayList;

import net.airtheva.assdroid.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment implements OnItemClickListener {

	class Item
	{
		String Name = null;
		Class<? extends Fragment> Clazz = null;
		
		Item(String name, Class<? extends Fragment> clazz)
		{
			Name = name;
			Clazz = clazz;
		}
	}
	
	static MenuFragment mInstance = null;
	
	public static MenuFragment Instance()
	{
		if(mInstance == null)
		{
			mInstance = new MenuFragment();
		}
		return mInstance;
	}
	
	ArrayList<Item> mList = null;
	
	ListView mListView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mListView = new ListView(getActivity());
		String[] items = new String[mList.size()];
		for(int i = 0; i < mList.size(); i++)
		{
			items[i] = mList.get(i).Name;
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
		return mListView;
	}
	
	
	
	public MenuFragment() {
		// TODO Auto-generated constructor stub
		mList = new ArrayList<Item>();
		AddItem(FilesystemFragment.TAG, FilesystemFragment.class);
		AddItem(SceneListFragment.TAG, SceneListFragment.class);
		AddItem(JoystickFragment.TAG, JoystickFragment.class);
	}
	
	void AddItem(String name, Class<? extends Fragment> clazz)
	{
		mList.add(new Item(name, clazz));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		try {
			Item item = mList.get(position);
			MainActivity activity = (MainActivity) getActivity();
			Fragment fragment = (Fragment) item.Clazz.getMethod("Instance").invoke(null, (Object[]) null);
			if(activity.StackExist(item.Name))
			{
				activity.RemoveStack(fragment);
			}
			else
			{
				activity.PushStack(item.Name, fragment);
			}
			activity.getSlidingMenu().showContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
