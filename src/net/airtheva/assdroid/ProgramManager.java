package net.airtheva.assdroid;

import java.util.ArrayList;

public class ProgramManager {
	
	static ArrayList<Program> mPrograms = null;
	
	static
	{
		mPrograms = new ArrayList<Program>();
		
		AddProgram(new Program());
	}
	
	static void AddProgram(Program program)
	{
		mPrograms.add(program);
	}
	
	static Program GetProgram()
	{
		return mPrograms.get(mPrograms.size() - 1);
	}

}
