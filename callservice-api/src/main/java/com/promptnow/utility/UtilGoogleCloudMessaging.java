package com.promptnow.utility;

public class UtilGoogleCloudMessaging {

	public static String getGcmRegistrationId()
	{
		String result = "";
		
		String reg_id = UtilPreferences.getStringPref(UtilPreferences.GCM_REG_ID);
		
		if(reg_id.length() > 0)
		{
			int versionCode = UtilPreferences.getIntegerPref(UtilPreferences.GCM_APP_VERSION);
			
			if(versionCode == UtilApplication.getAppVersionCode())
			{
				result = reg_id;
			}
		}

		return result;
	}
	
	public static void saveGcmRegistrationId(String gcmRegistrationId)
	{
		UtilPreferences.savePref(UtilPreferences.GCM_REG_ID, gcmRegistrationId);
        UtilPreferences.savePref(UtilPreferences.GCM_APP_VERSION, UtilApplication.getAppVersionCode());
	}
}
