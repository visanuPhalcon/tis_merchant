package com.promptnow.bean;

import com.promptnow.application.PromptNowActivityState;
import com.promptnow.utility.UtilApplication;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;


public class ActivityStateDetector implements ActivityLifecycleCallbacks {

	private static ActivityStateDetector instance = null;

	public static ActivityStateDetector getInstance()
	{
		if(instance == null)
		{
			instance = new ActivityStateDetector();
			UtilApplication.getApplication().registerActivityLifecycleCallbacks(instance);
		}
		
		return instance;
	}
	
	public static void clearInstance()
	{
		if(instance != null)
		{
			UtilApplication.getApplication().unregisterActivityLifecycleCallbacks(instance);
			instance = null;
		}
	}
	
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Created);
	}

	@Override
	public void onActivityStarted(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Started);
	}

	@Override
	public void onActivityResumed(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Resumed);
	}

	@Override
	public void onActivityPaused(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Paused);
	}

	@Override
	public void onActivityStopped(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Stoped);
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().activityStateChanged(activity, PromptNowActivityState.Activity_Destroyed);
	}

}
