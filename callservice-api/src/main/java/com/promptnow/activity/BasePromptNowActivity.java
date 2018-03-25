package com.promptnow.activity;

import android.app.Activity;

import com.promptnow.application.PromptNowActivityState;
import com.promptnow.utility.UtilApplication;

public class BasePromptNowActivity extends Activity {
	
	public boolean isVisibled()
	{
		return UtilApplication.getApplication().isActivityVisible(this);
	}
	
	public PromptNowActivityState getActivityState()
	{
		return UtilApplication.getApplication().getActivityState(this);
	}
}

