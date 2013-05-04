package net.airtheva.assdroid;

import java.nio.IntBuffer;


public class Native {

	static
	{
		System.loadLibrary("assimp");
		System.loadLibrary("png16");
		System.loadLibrary("devil");
		System.loadLibrary("devilu");
		System.loadLibrary("assdroid");
	}
	
	static final native String HelloWorld();
	static final native int LoadScene(String path);
	static final native void DrawScene(int sceneKey, IntBuffer programInfo);
	static final native void ReleaseScene(int sceneKey);
	
}
