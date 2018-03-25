package com.promptnow.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;

import com.promptnow.application.PromptNowApplication;

import java.util.List;

public class UtilApplication {

	public static PromptNowApplication applicaiton = null;
	public static PromptNowApplication getApplication()
	{
		return applicaiton;
	}
	
	public static Context getContext()
	{
		return getApplication().getApplicationContext();
	}
	
	public static Resources getResources()
	{
		return getContext().getResources();
	}
	
	public static ActivityManager getActivityManager()
	{
		return (ActivityManager) getContext().getSystemService( Activity.ACTIVITY_SERVICE );
	}
	
	public static PackageManager getPackageManager()
	{
		return getContext().getPackageManager();
	}
	
	public static PackageInfo getPackageInfo(int flags)
	{
		PackageInfo result = null;
		
		try
		{
			result = getPackageManager().getPackageInfo(getPackageName(), flags); 
		}
		catch(NameNotFoundException e)
		{
			UtilLog.d(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}
	
	public static String getAppVersionName()
	{
		PackageInfo packageInfo = getPackageInfo(0);
		return packageInfo.versionName;
	}
	
	public static int getAppVersionCode()
	{
		PackageInfo packageInfo = getPackageInfo(0);
		return packageInfo.versionCode;
	}
	
	public static String getPackageName()
	{
		return getContext().getPackageName();
	}
	
	public static String getResourceText(int id)
	{
		return applicaiton.getString(id);
	}

	public static String getSignedAPK()
	{
		String result = "";
		
		try
		{
			Signature[] sigs = getPackageInfo(PackageManager.GET_SIGNATURES).signatures;    
			for (Signature sig : sigs)
			{
			    UtilLog.d("Signature : " + sig.toCharsString());
			}
			
			if(sigs.length > 0)
			{
				byte[] md5 = UtilDigest.getMessageDigestMD5(sigs[0].toByteArray());
				result = UtilCipher.getHexString(md5);
			}
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}
	
	public static boolean isApplicationRunning()
	{
		boolean result = false;
		ActivityManager activityManager = getActivityManager();
        List<RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++)
        {
            if(procInfos.get(i).processName.equals(getPackageName())) 
            {
                result = true;
            }
        }
        
        return result;
	}
	
}
