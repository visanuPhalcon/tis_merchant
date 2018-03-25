package com.promptnow.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilDigest {

	public static byte[] getMessageDigestMD5(byte[] data)
	{
		byte[] result = null;
		
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
		
		m.update(data,0,data.length);
		result = m.digest();
		
		return result;
	}
	
	public static byte[] getMessageDigestSHA1(byte[] data)
	{
		byte[] result = null;
		
		return result;
	}
}
