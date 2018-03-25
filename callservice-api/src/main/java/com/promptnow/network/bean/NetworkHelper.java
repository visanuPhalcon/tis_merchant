package com.promptnow.network.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.promptnow.network.PromptnowConnectionResult;
import com.promptnow.network.PromptnowConnectionResultEnum;
import com.promptnow.network.PromptnowConnection;
import com.promptnow.utility.UtilLog;


public final class NetworkHelper {

	public static boolean doKeyExchange()
	{
		boolean result = false;
		KeyExchangeHelper service = new KeyExchangeHelper();
		result = service.doKeyExchange();
		return result;
	}

	public static boolean doAutoKeyExchange()
	{
		boolean result = false;

		if(KeyExchangeHelper.isNeedKeyExchange())
		{
			if(NetworkHelper.doKeyExchange())
			{
				UtilLog.d("Auto key exhange is success");
				result = true;
			}
			else
			{
				UtilLog.d("Auto key exchange is fail");
			}
		}
		else if(KeyExchangeHelper.isKeyExchangeMaybeExpired())
		{
			if(KeyExchangeHelper.doCheckNewJSESSION())
			{
				if(NetworkHelper.doKeyExchange())
				{
					UtilLog.d("Auto key exhange is success");
					result = true;
				}
				else
				{
					UtilLog.d("Auto key exchange is fail");
				}
			}
			else
			{
				result = true;
			}
		}
		else
		{
			result = KeyExchangeHelper.waitForKeyExchange();
		}
		
		return result;
	}
	
	private static String getPostRequest(HashMap<String,String> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (String key : params.keySet())
	    {
	        if (first)
	        {
	            first = false;
	            result.append("?");
	        }
	        else
	        {
	            result.append("&");
	        }

	        result.append(URLEncoder.encode(key, "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(params.get(key), "UTF-8"));
	    }

	    UtilLog.d("POST request: " + result.toString());
	    return result.toString();
	}
	
	public static String requestPOST(final String serviceName, HashMap<String,String> params) throws UnsupportedEncodingException
	{
		String response = "";
		String dataString = getPostRequest(params);
		PromptnowConnectionResult connResult;
		
		PromptnowConnection connServer = new PromptnowConnection();
		connResult = connServer.connectWithGET(serviceName + dataString);
		
		if(connResult.resultType == PromptnowConnectionResultEnum.ResultSuccess)
		{
			if(connResult.resultString == PromptnowConnection.BYTESTREAM_OUTPUT)
			{
				response = new String(connResult.resultByteStream, "UTF8");
			}
			else
			{
				response = connResult.resultString;
			}
		}
		
		UtilLog.d("POST response: " + response);
		return response;
	}
}
