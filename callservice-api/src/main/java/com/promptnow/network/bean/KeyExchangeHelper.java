package com.promptnow.network.bean;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.promptnow.bean.ConfigurationsManager;
import com.promptnow.network.PromptnowConnection;
import com.promptnow.network.PromptnowConnectionResult;
import com.promptnow.network.PromptnowConnectionResultEnum;
import com.promptnow.network.secure.BaseSecureProvider;
import com.promptnow.susanoo.PromptnowProperties;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.keyexchange.PromptnowDHECKeyExchange;
import com.promptnow.susanoo.keyexchange.PromptnowDHKeyExchange;
import com.promptnow.susanoo.keyexchange.model.KeyExchangeRequestModel;
import com.promptnow.susanoo.keyexchange.model.KeyExchangeResponseModel;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.promptnow.utility.UtilCipher;
import com.promptnow.utility.UtilLog;


public final class KeyExchangeHelper {

	public static String serviceName = "";
	BaseSecureProvider keyexchange;
	iServiceEventHandler eventHandler;
	
	enum KeyExchangeServiceState
	{
		KeyExchangeInvalid,
		CallingKeyExchange,
		KeyExchangeTesting,
		KeyExchangeValid,
		KeyExchangeMaybeExpired,
		KeyExchangeExpiredChecking,
		KeyExchangeExpired,
	};
	
	private static Handler timerHandler = new Handler(Looper.getMainLooper());
	private static KeyExchangeServiceState keyState = KeyExchangeServiceState.KeyExchangeInvalid;
	
	public static void resetKeyExchangeServiceState()
	{
		keyState = KeyExchangeServiceState.KeyExchangeInvalid;
	}
	
	public static boolean isNeedKeyExchange()
	{
		boolean keyMaybeExpired = keyState == KeyExchangeServiceState.KeyExchangeExpired || keyState == KeyExchangeServiceState.KeyExchangeInvalid;
		
		return keyMaybeExpired;
	}
	
	public static boolean isKeyExchangeMaybeExpired()
	{
		return keyState == KeyExchangeServiceState.KeyExchangeMaybeExpired;
	}
	
	public static boolean waitForKeyExchange()
	{
		boolean result = false;

		while(keyState == KeyExchangeServiceState.CallingKeyExchange ||
			keyState == KeyExchangeServiceState.KeyExchangeTesting ||
			keyState == KeyExchangeServiceState.KeyExchangeMaybeExpired ||
			keyState == KeyExchangeServiceState.KeyExchangeExpiredChecking)
		{
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				UtilLog.e(UtilLog.getStackTraceString(e));
			}
		}
		
		result = keyState == KeyExchangeServiceState.KeyExchangeValid;
		return result;
	}

	public static boolean doCheckNewJSESSION()
	{
		boolean result = false;
		String request = "test";
		PromptnowConnection conn = new PromptnowConnection();
		PromptnowConnectionResult connResult = conn.connectWithJSON("index.jsp", request.getBytes());//new byte[0]); 
		
		if(connResult.jsessionChange)
		{
			result = true;
			keyState = KeyExchangeServiceState.KeyExchangeInvalid;
		}
		else
		{
			keyState = KeyExchangeServiceState.KeyExchangeValid;
			UtilLog.d("JSESSIONID is still valid.");
			restartKeyExchangeTimer();
		}

        UtilLog.i("JSESSION change: " + String.valueOf(result));
        
		return result;
	}
	
	public static void restartKeyExchangeTimer()
	{
		UtilLog.d("Auto check key exchange timeout did start.");
		if(updateTimerThread != null)
		{
			timerHandler.removeCallbacks(updateTimerThread);
			updateTimerThread = null;
		}
		
		updateTimerThread = new Runnable() 
		{
			public void run()
			{
				updateTimerThread = null;
				keyState = KeyExchangeServiceState.KeyExchangeMaybeExpired;
				UtilLog.d("JSESSIONID maybe expired.");
			}
		};
		
		timerHandler.postDelayed(updateTimerThread, 1*60*1000);
	}
	
	private static Runnable updateTimerThread = null;
	
	public KeyExchangeHelper() {
		// TODO Auto-generated constructor stub
	}

	public boolean doKeyExchange()
	{
		boolean result = false;
		keyState = KeyExchangeServiceState.CallingKeyExchange;
		result = getKeyExchange();
		return result;
	}

	private boolean getKeyExchange() {
		boolean result = false;
		KeyExchangeResponseModel responseModel = null;
		KeyExchangeRequestModel request = new KeyExchangeRequestModel();
		if (ConfigurationsManager.getInstance().isDHECKeyExchange()){
			serviceName = "ec-key-exchange";
			keyexchange = new PromptnowDHECKeyExchange();
			keyexchange.generateMyKeyPair();
			request.publicKey = keyexchange.getMyPublicKeyInHexString();
			//request.identify = keyexchange.getMyPublicKeyInHexString();
		}else {
			serviceName = "key-exchange";
			keyexchange = new PromptnowDHKeyExchange();
			keyexchange.generateMyKeyPair();
			request.publicKey = keyexchange.getMyPublicKeyInDecString();
			//request.identify = keyexchange.getMyPublicKeyInDecString();
		}
        
        Gson gson = new Gson();
        String jsonObjSend = gson.toJson(request);
        UtilLog.d("key-exchange request: " + jsonObjSend);
		PromptnowConnection conn = new PromptnowConnection();
		PromptnowConnectionResult connResult = conn.connectWithJSON(serviceName, jsonObjSend.getBytes()); 
		String response = connResult.resultString;
		byte[] byteArray = connResult.resultByteStream;
		
		if(connResult.resultType == PromptnowConnectionResultEnum.ResultSuccess ) {
			conn.closeConnection();
			
			UtilLog.d("key-exchange response: " + response);
			if(response.equals(PromptnowConnection.BYTESTREAM_OUTPUT)) {
				UtilLog.d("key-exchange response data: " + new String(byteArray));
			}

            responseModel = (KeyExchangeResponseModel)gson.fromJson(response, KeyExchangeResponseModel.class);
            if(responseModel.responseStatus.equals(CommonResponseModel.STATUS_SUCCESS)) {
            	UtilLog.logModel(responseModel);
                //String Independent = responseModel.independent;
                //String Identify = responseModel.identify;
                //keyexchange.generateShareSecret(Identify);
				keyexchange.generateShareSecret(responseModel.publicKey);
                
                PromptnowProperties.SessionID = responseModel.sessionID;
                PromptnowProperties.encryptKey = keyexchange.getEncryptKey();
                PromptnowProperties.decryptKey = keyexchange.getDecryptKey();
                
                try {
	                String de = UtilCipher.getHexString(PromptnowProperties.decryptKey);
	                String en = UtilCipher.getHexString(PromptnowProperties.encryptKey);
	                
	                UtilLog.d("encryptkey: " + en);
	                UtilLog.d("decryptkey: " + de);
	                result = true;
                }
                catch(Exception e) {
                	UtilLog.e(UtilLog.getStackTraceString(e));
                }
            }
		}

		if(result) {
            keyState = KeyExchangeServiceState.KeyExchangeValid;
            restartKeyExchangeTimer();
		} else {
            keyState = KeyExchangeServiceState.KeyExchangeInvalid;
		}
        UtilLog.i("Key exchange success: " + String.valueOf(result));
		return result;
	}
}
