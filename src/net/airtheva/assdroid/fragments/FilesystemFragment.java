package net.airtheva.assdroid.fragments;

import java.io.File;
import java.util.Locale;
import java.util.regex.Pattern;

import net.airtheva.assdroid.FileListView;
import net.airtheva.assdroid.MainActivity;
import net.airtheva.assdroid.R;
import net.airtheva.assdroid.SceneManager;
import net.airtheva.assdroid.scenes.NativeScene;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FilesystemFragment extends Fragment implements OnItemClickListener {

	// TODO: Save where the CurrentDir previously be.
	
	public static final String TAG = "FilesystemFragment";
	
	static FilesystemFragment mInstance = null;
	
	public static FilesystemFragment Instance()
	{
		if(mInstance == null)
		{
			mInstance = new FilesystemFragment();
		}
		return mInstance;
	}
	
	FileListView mFileListView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mFileListView = new FileListView(getActivity());
		mFileListView.setOnItemClickListener(this);
		return mFileListView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(mFileListView.CurrentDir, mFileListView.GetItem(position - 1).get("label").toString());
		if(file.isDirectory())
		{
			mFileListView.CurrentDir = new File(file.getAbsolutePath());
			mFileListView.UpdateList();
		}
		else
		{
			// TODO: Open Scene.
			String path = file.getAbsolutePath();
			if(path.toUpperCase().matches(".*\\.(3DS|BLEND|DAE|IFC-STEP|ASE|DXF|HMP|MD2|MD3|MD5|MDC|MDL|NFF|PLY|STL|X|OBJ|SMD|LWO|LXO|LWS|XML|TER|AC3D|MS3D)$"))
			{
				Bundle args = new Bundle();
				args.putString(OpenSceneFragment.KEY_PATH, file.getAbsolutePath());
				OpenSceneFragment openSceneFragment = new OpenSceneFragment();
				openSceneFragment.setArguments(args);
				openSceneFragment.show(getFragmentManager(), OpenSceneFragment.TAG);
			}
			else if(path.toUpperCase().matches(".*\\.(BMP|PNG|TGA|DDS)$"))
			{
				// TODO: Load textures.
			}
		}
	}

}
