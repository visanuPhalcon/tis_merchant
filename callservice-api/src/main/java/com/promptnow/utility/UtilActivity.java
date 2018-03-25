package com.promptnow.utility;

import android.app.Activity;

public class UtilActivity extends BaseUtility {
	
	public static Activity getCurrentActivity()
	{
		return getApplication().getCurrentActivity();
	}
	
	public static boolean isActivityVisible(Activity activity)
	{
		return getApplication().isActivityVisible(activity);
	}
}
