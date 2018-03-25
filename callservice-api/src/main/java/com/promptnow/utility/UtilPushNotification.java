package com.promptnow.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.HashMap;

public class UtilPushNotification {

	@SuppressWarnings("deprecation")
	public static void showPushNotification(Context context, String title, String message, int icon, HashMap<String, String> mapExtras)
	{
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		CharSequence tickerText = message;
		long when = System.currentTimeMillis();

		/*Notification notification = new Notification(icon, tickerText, when);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;*/


		Notification.Builder builder = new Notification.Builder(context);
		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		builder.setAutoCancel(true);


		Intent in = null;
		PackageManager manager = context.getPackageManager();
		try {
		    in = manager.getLaunchIntentForPackage(UtilApplication.getPackageName());
		    if (in == null)
		    {
		        throw new PackageManager.NameNotFoundException();
		    }
		    
		    in.addCategory(Intent.CATEGORY_LAUNCHER);
		    
		    if(mapExtras != null)
		    {
		    	for(String key : mapExtras.keySet())
		    	{
		    		String value = mapExtras.get(key);
		    		in.putExtra(key, value);
		    	}
		    }
		} catch (PackageManager.NameNotFoundException e) {
			UtilLog.e(UtilLog.getStackTraceString(e));
		}

		PendingIntent pendingIntentInApp = PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
//		builder.setLatestEventInfo(context, title, message, pendingIntentInApp);
		builder.setContentIntent(pendingIntentInApp);
		notificationManager.notify(0, builder.build());
	}
	
	public static void showPushNotification(Context context, String title, String message, int icon)
	{
		showPushNotification(context, title, message, icon, null);
	}
}
