package net.airtheva.assdroid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FileListView extends ListView {

	static final String TAG = "FileListView";
	
	public File CurrentDir = null;
	
	public List<Map<String, Object>> mList = null;
	
	Button mParentButton = null;
	TextView mPathTextView = null;
	Button mRefreshButton = null;
	
	public FileListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	void initialize()
	{
		CurrentDir = Environment.getExternalStorageDirectory();
		addHeaderView(View.inflate(getContext(), R.layout.file_list_view_header, null));
		mParentButton = (Button) findViewById(R.id.activity_file_picker_list_view_header_parent);
		mParentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileListView.this.GoToParentDir();
			}
			
		});
		mPathTextView = (TextView) findViewById(R.id.activity_file_picker_list_view_header_path);
		mRefreshButton = (Button) findViewById(R.id.activity_file_picker_list_view_header_refresh);
		mRefreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FileListView.this.UpdateList();
			}
		});
		UpdateList();
	}
	
	void makeList()
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(File file : CurrentDir.listFiles())
		{
			Map<String, Object> map = new HashMap<String, Object>();
			if(file.isDirectory())
			{
				map.put("icon", R.drawable.folder);
			}
			else
			{
				map.put("icon", R.drawable.file);
			}
			map.put("label", file.getName());
			list.add(map);
		}
		mList = list;
	}
	
	public void UpdateList()
	{
		mPathTextView.setText(CurrentDir.getAbsolutePath());
		makeList();
		SimpleAdapter adapter = new SimpleAdapter(getContext(), mList, R.layout.file_list_view_item, new String[]{ "icon", "label" }, new int[]{ R.id.activity_file_picker_list_view_item_icon, R.id.activity_file_picker_list_view_item_label });
		setAdapter(adapter);
	}
	
	void GoToParentDir()
	{
		CurrentDir = CurrentDir.getParentFile();
		UpdateList();
	}
	
	public void SetCurrentDir(String path)
	{
		CurrentDir = new File(path);
		Log.i(TAG, "SetCurrentDir.");
	}
	
	public Map<String, Object> GetItem(int index)
	{
		return mList.get(index);
	}
	
}
