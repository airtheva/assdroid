package net.airtheva.assdroid.fragments;

import java.io.File;

import net.airtheva.assdroid.MainActivity;
import net.airtheva.assdroid.R;
import net.airtheva.assdroid.SceneManager;
import net.airtheva.assdroid.scenes.NativeScene;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

public class OpenSceneFragment extends DialogFragment implements OnClickListener{
	
	
	public static final String TAG = "OpenSceneFragment";
	public static final String KEY_PATH = "path";
	
	File mFile = null;
	
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		mFile = new File(args.getString(KEY_PATH));
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_open_scene, null);
		((TextView) view.findViewById(R.id.dialog_open_scene_path)).setText(mFile.getAbsolutePath());
		((TextView) view.findViewById(R.id.dialog_open_scene_size)).setText(Long.toString(mFile.length()));
		AlertDialog dialog = (new AlertDialog.Builder(getActivity()))
				.setTitle(TAG)
				.setView(view)
				.setPositiveButton("Open", this)
				.setNegativeButton("Cancel", this)
				.create();
		return dialog;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which)
		{
		case DialogInterface.BUTTON_POSITIVE:
			SceneManager.AddScene(new NativeScene(mFile.getAbsolutePath()));
			getActivity().getSupportFragmentManager().beginTransaction()
				.remove(FilesystemFragment.Instance())
				.commit();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dismiss();
			break;
		}
	}
	
	

}
