package com.promptnow.utility;

import android.content.Context;
import android.content.res.Resources;

import com.promptnow.application.PromptNowApplication;

public class BaseUtility {

	protected static PromptNowApplication getApplication()
	{
		return UtilApplication.getApplication();
	}
	
	protected static Context getContext()
	{
		return getApplication().getApplicationContext();
	}
	
	protected static Resources getResources()
	{
		return getApplication().getResources();
	}
}
