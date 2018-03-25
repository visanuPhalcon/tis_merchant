package com.promptnow.utility;

import java.io.File;
import com.promptnow.utility.UtilShellCmd.SHELL_CMD;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class UtilDevice extends BaseUtility {

	public static String getDeviceID() {
		String result = "";
//		Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getDeviceId() != null && !telephonyManager.getDeviceId().equals(""))
		{
			result = telephonyManager.getDeviceId();
		}else {
			result = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID);
		}
		
		return result;
	}
	
	public static String getDeviceVersion() {
		return Build.VERSION.RELEASE;
	}

	public static String getDeviceModel() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		}
		return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		char[] arr = str.toCharArray();
		boolean capitalizeNext = true;
		String phrase = "";
		for (char c : arr) {
			if (capitalizeNext && Character.isLetter(c)) {
				phrase += Character.toUpperCase(c);
				capitalizeNext = false;
				continue;
			} else if (Character.isWhitespace(c)) {
				capitalizeNext = true;
			}
			phrase += c;
		}
		return phrase;
	}
	
	public static boolean isOnline()
	{
		boolean result = false;
		
		ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			String networkType = netInfo.getTypeName();
			UtilLog.d(String.format("Network is available by '%s'", networkType));
			result = true;
		}

		return result;
	}
	
	public static int getDisplayWidthPX(Activity activity)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		return width;
	}

	public static int getDisplayHeightPX(Activity activity)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		return height;
	}
	
	public static float getDisplayWidthDP(Activity activity)
	{
	    float dpWidth; 
		DisplayMetrics metrics = new DisplayMetrics();
		dpWidth = metrics.widthPixels / metrics.density;
		return dpWidth;
	}
	
	public static float getDisplayHeightDP(Activity activity)
	{
		float dpHeight;
		DisplayMetrics metrics = new DisplayMetrics();
		dpHeight = metrics.heightPixels / metrics.density;
		return dpHeight;
	}

	private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e){} return false;
    }

    private static boolean checkRootMethod3() {
        return new UtilShellCmd().executeCommand(SHELL_CMD.check_su_binary)!=null;
    }
	
    private static boolean checkRootMethod4() {
    	String binaryName = "su";
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    
    private static boolean checkRootMethod5() {
    	boolean result = false;
    	String packagename = "eu.chainfire.supersu";
    	PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            result = true;
        } catch (NameNotFoundException e) {
        }
        
        return result;
    }
    
	public static boolean isRootedDevice()
	{
		boolean result = false;
		
		if( checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
				|| checkRootMethod4() || checkRootMethod5() )
		{
			result = true;
		}
		
		return result;
	}

	public static boolean isTelephone() {
		boolean result = false;
		TelephonyManager telephony = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE); 
		int type = telephony.getPhoneType();
		if (type != TelephonyManager.PHONE_TYPE_NONE) { 
			result = true;
		}
		
		UtilLog.d(String.format("isTelephone: %b", result));
		return result;
	}

	public static String getDeviceUserAgent()
	{
		return System.getProperty( "http.agent" );
	}
	
	public static int getDeviceSDKVersion()
	{
		return Build.VERSION.SDK_INT;
	}
}
