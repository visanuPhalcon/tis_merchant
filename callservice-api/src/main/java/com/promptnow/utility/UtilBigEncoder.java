package com.promptnow.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class UtilBigEncoder
{

	public static String getMD5(String text)
	{
		MessageDigest m = null;
		try
		{
			m = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			UtilLog.e(UtilLog.getStackTraceString(e));
			return null;
		}
		
		m.update(text.getBytes(),0,text.length());
		return String.valueOf((new BigInteger(1,m.digest()).toString(16)));		
	}
	
	public static String getMD5onBase64(String text)
	{
		return getMD5(UtilBase64Coder.encodeString(text));
	}
}
