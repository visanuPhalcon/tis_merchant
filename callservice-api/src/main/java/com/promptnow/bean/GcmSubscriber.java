package com.promptnow.bean;

import java.io.IOException;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.promptnow.utility.UtilApplication;
import com.promptnow.utility.UtilGoogleCloudMessaging;
import com.promptnow.utility.UtilLog;

public class GcmSubscriber {

	private static GcmSubscriber subscriber = null;
	
	private String senderId = "";
	
	public static GcmSubscriber getInstance() throws Exception
	{
		if(subscriber == null)
		{
			subscriber = new GcmSubscriber();
		}
		return subscriber;
	}
	
	public static void clearInstance()
	{
		subscriber = null;
	}
	
	public GcmSubscriber() throws Exception
	{
		senderId = ConfigurationsManager.getInstance().getConfiguration("gcm.sender_id"); 
		
		if(senderId.length() == 0)
		{
			throw new Exception("Unidentified GCM sender id.");
		}
		// If this check succeeds, proceed with normal processing.
        // Otherwise, prompt user to get valid Play Services APK.
        
        String reg_id = UtilGoogleCloudMessaging.getGcmRegistrationId();

        if (reg_id.length() == 0)
        {
            doSubscribeGcmInBackground();
        }
        else
        {
        	UtilLog.d("Register Id : "+reg_id);
        }
	}
	
	private void doSubscribeGcmInBackground()
	{
		final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(UtilApplication.getContext());
		
	    new AsyncTask<Object,Object,String>() {
	    	protected String doInBackground(Object... params) {
	    		String msg = "";
	            try {
	                String regId = gcm.register(senderId);
	                msg = "Device registered, registration ID=" + regId;

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                UtilGoogleCloudMessaging.saveGcmRegistrationId(regId);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        protected void onPostExecute(String msg) {
	        	UtilLog.d("GCM subscribe result: " + msg);
	        }

	    }.execute(null, null, null);
	    
	}	
	
}
