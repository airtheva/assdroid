package net.airtheva.assdroid.fragments;

import net.airtheva.assdroid.SceneManager;
import net.airtheva.assdroid.scenes.IScene;
import android.os.Bundle;

public class SceneScaleDialog extends VectorInputDialog{

	public static final String TAG = "SceneScaleDialog";
	
	static final String KEY_INDEX = "index";
	
	IScene mScene = null;
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		mScene = SceneManager.GetScene(args.getInt(KEY_INDEX));
		setTitle(TAG);
		set(mScene.GetScale());
		setPositiveButtonText("Scale");
	}

	@Override
	void onPositiveButtonClick() {
		// TODO Auto-generated method stub
		float[] vector = get();
		mScene.SetScale(vector[0], vector[1], vector[2]);
	}

}
