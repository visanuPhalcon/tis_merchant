package com.promptnow.application;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;

import com.promptnow.bean.ActivityStateDetector;
import com.promptnow.listener.OnActivityVisibilityListener;
import com.promptnow.susanoo.PromptnowProperties;
import com.promptnow.utility.UtilApplication;
import com.promptnow.utility.UtilDevice;
import com.promptnow.utility.UtilLog;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PromptNowApplication extends MultiDexApplication {

	private HashMap<Activity, PromptNowActivityState> mapActivityState = new HashMap<Activity, PromptNowActivityState>();
	private HashMap<Fragment, PromptNowFragmentState> mapFragmentState = new HashMap<Fragment, PromptNowFragmentState>();
	private CopyOnWriteArrayList<OnActivityVisibilityListener> visibleListeners = new CopyOnWriteArrayList<OnActivityVisibilityListener>();
	private Activity currentActivity = null;

	private static PromptNowApplication instance;

	public static PromptNowApplication getInstance() {
		if(instance == null){
			instance = new PromptNowApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		UtilApplication.applicaiton = this;
		UtilLog.d("PromptNowApplication has been started.");
		ActivityStateDetector.getInstance();
		PromptnowProperties.USER_AGENT = UtilDevice.getDeviceUserAgent();
		PromptnowProperties.initPrompnowCommandData();
		setDeveloperMode();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		ActivityStateDetector.clearInstance();
		UtilLog.d("PromptNowApplication has been terminated.");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		UtilLog.d("PromptNowApplication has configuration changed.");
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		UtilLog.d("PromptNowApplication has low memory.");
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

	@Override
	protected void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public void activityStateChanged(Activity activity, PromptNowActivityState state)
	{
		UtilLog.v("activity state: '" + activity + "'= " + state.toString());
		if(mapActivityState.containsKey(activity))
		{
			if(state == PromptNowActivityState.Activity_Destroyed)
			{
				mapActivityState.remove(activity);
				
				if(activity == currentActivity)
				{
					setCurrentActivity(null);
				}
				
				return;
			}
		}
		
		mapActivityState.put(activity, state);
		
		if(state == PromptNowActivityState.Activity_Created)
		{
			if(currentActivity == null)
			{
				setCurrentActivity(activity);
			}
		}
		else if(state == PromptNowActivityState.Activity_Resumed)
		{
			setCurrentActivity(activity);
			dispatchVisibilityEvent(activity, state);
		}
		else if(state == PromptNowActivityState.Activity_Paused)
		{
			dispatchVisibilityEvent(activity, state);
		}
	}
	
	public PromptNowActivityState getActivityState(Activity activity)
	{
		PromptNowActivityState state = PromptNowActivityState.Activity_Destroyed;
		
		if(mapActivityState.containsKey(activity))
		{
			state = mapActivityState.get(activity);
		}
		
		return state;
	}
	

	public void fragmentStateChanged(Fragment fragment, PromptNowFragmentState state)
	{
		UtilLog.v("fragment state: '" + fragment + "'= " + state.toString());
		if(mapFragmentState.containsKey(fragment))
		{
			if(state == PromptNowFragmentState.Fragment_Detached)
			{
				mapFragmentState.remove(fragment);
			}
			else
			{
				mapFragmentState.put(fragment, state);
			}
		}
		else
		{
			mapFragmentState.put(fragment, state);
		}
	}
	
	public PromptNowFragmentState getFragmentState(Activity fragment)
	{
		PromptNowFragmentState state = PromptNowFragmentState.Fragment_Attached;
		
		if(mapFragmentState.containsKey(fragment))
		{
			state = mapFragmentState.get(fragment);
		}
		
		return state;
	}
	
	private void setDeveloperMode()
	{
		UtilLog.w("PromptNow developer mode is ON.");
		PromptnowProperties.isLockSignedAPK = false;
	}

	public boolean isActivityVisible(Activity activity)
	{
		boolean result = false;
		
		PromptNowActivityState state = getActivityState(activity);
		
		if(state == PromptNowActivityState.Activity_Started ||
			state == PromptNowActivityState.Activity_Resumed ||
			state == PromptNowActivityState.Activity_Created)
		{
			result = true;
		}
		
		UtilLog.d("activity visible: " + result);
		return result;
	}
	
	public Activity getCurrentActivity()
	{
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity)
	{
		Activity previousAct = this.currentActivity;
		this.currentActivity = currentActivity;
		UtilLog.d("Current Activity: from '" + previousAct + "' to '" + currentActivity + "'");
	}
	
	public void registerOnVisibilityListener(OnActivityVisibilityListener listener)
	{
		if(!visibleListeners.contains(listener))
		{
			visibleListeners.add(listener);
		}
	}
	
	public void unRegisterOnVisibilityListener(OnActivityVisibilityListener listener)
	{
		if(visibleListeners.contains(listener))
		{
			visibleListeners.remove(listener);
		}
	}
	
	private void dispatchVisibilityEvent(Activity act, PromptNowActivityState state)
	{
		for(OnActivityVisibilityListener listener : visibleListeners)
		{
			if(state == PromptNowActivityState.Activity_Resumed)
			{
				listener.onBecameVisible(act);
			}
			else if(state == PromptNowActivityState.Activity_Paused)
			{
				listener.onBecameInvisible(act);
			}
		}
	}
}
