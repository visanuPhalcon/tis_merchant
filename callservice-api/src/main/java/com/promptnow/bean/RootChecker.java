package com.promptnow.bean;

import com.promptnow.utility.UtilDevice;

public class RootChecker {

	private static RootChecker checker = null;
	private boolean isRooted = false;
	
	public static RootChecker getInstance()
	{
		if(checker == null)
		{
			checker = new RootChecker();
			checker.isRooted = UtilDevice.isRootedDevice();
		}
		
		return checker;
	}
	
	public static void resetInstance()
	{
		checker = null;
	}
	
	public boolean isRooted()
	{
		return isRooted;
	}
}
