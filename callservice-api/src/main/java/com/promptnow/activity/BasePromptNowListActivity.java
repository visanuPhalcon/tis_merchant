package com.promptnow.activity;

import android.app.ListActivity;

import com.promptnow.application.PromptNowActivityState;
import com.promptnow.utility.UtilApplication;

public class BasePromptNowListActivity extends ListActivity {

	public boolean isVisibled()
	{
		return UtilApplication.getApplication().isActivityVisible(this);
	}

	public PromptNowActivityState getActivityState()
	{
		return UtilApplication.getApplication().getActivityState(this);
	}
}

