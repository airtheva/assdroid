package net.airtheva.assdroid;

import java.util.ArrayList;
import java.util.List;

import net.airtheva.assdroid.scenes.GroundScene;
import net.airtheva.assdroid.scenes.IScene;
import net.airtheva.assdroid.scenes.TriangleScene;

public class SceneManager {
	
	static ArrayList<IScene> mScenes = null;
	
	static
	{
		mScenes = new ArrayList<IScene>();
		
		//AssDroid.AddModel(new MeshModel());
		AddScene(new GroundScene());
		//AddScene(new TriangleScene());
	}
	
	// Drawing related.
	
	public static void AddScene(IScene scene)
	{
		mScenes.add(scene);
	}
	
	public static IScene GetScene(int index)
	{
		return mScenes.get(index);
	}
	
	public static List<IScene> GetScenes()
	{
		return mScenes;
	}
	
	public static void RemoveScene(int index)
	{
		mScenes.remove(index);
	}
	
	static void Update()
	{
		for(IScene scene : mScenes)
		{
			scene.Update();
		}
	}
	
	static void Draw(Program program)
	{
		for(IScene scene : mScenes)
		{
			scene.Draw(program);
		}
	}
	
}
