package com.promptnow.activity;

import android.support.v4.app.FragmentActivity;

import com.promptnow.application.PromptNowActivityState;
import com.promptnow.utility.UtilApplication;

public class BasePromptNowFragmentActivity extends FragmentActivity {

	public boolean isVisibled()
	{
		return UtilApplication.getApplication().isActivityVisible(this);
	}

	public PromptNowActivityState getActivityState()
	{
		return UtilApplication.getApplication().getActivityState(this);
	}
}
