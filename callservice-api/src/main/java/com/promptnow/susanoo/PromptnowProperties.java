// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PromptnowCommandData.java

package com.promptnow.susanoo;

import com.promptnow.bean.ConfigurationsManager;
import com.promptnow.bean.HardCodeDecryptor;
import com.promptnow.susanoo.model.CommonRequestModel;
import com.promptnow.utility.UtilApplication;
import com.promptnow.utility.UtilLog;
import com.promptnow.utility.UtilNetwork;

//@SuppressWarnings("unused")
public final class PromptnowProperties
{
    public static final String HTTPS = "https://";
    public static final String HTTP = "http://";
    public static final String APPLICATION_TYPE = "android";
    public static String USER_AGENT = "";

    private static String url;
    private static String cert_issuer;
    private static String cert_subject;
    public static final byte initialVector[] = "VULCANN_PLATFORM".getBytes();
    public static byte encryptKey[] = new byte[32];
    public static byte decryptKey[] = new byte[32];
    public static long RESPONSE_MAX = 0x100000L;
    public static String COOKIE = "";
    public static String JSESSIONID = "";
    public static String LANGUAGE = CommonRequestModel.LANG_THAI;
    public static String SessionID = "";//69d7aaf6-7fcf-4dc0-b71f-13eb29d6
    public static String authenToken = "";
    public static String userName = "";
    public static String tokenId = "";
    
    public static boolean isSuccess = false;
    public static boolean logShowLineOfCode = true;
    public static boolean forceDecryption = false;
    public static boolean prettyJson = false;
    public static boolean enableNetworkLog = false;
    public static boolean isLockSignedAPK = true;
    public static final String networkLogFilePath = "/mnt/extSdCard/network.txt";

    public static String getStringURL()
    {
    	return url;
    }
    
    public static boolean isHttpsURL()
    {
    	boolean result = false;
    	
    	if(url.startsWith(HTTPS))
    	{
    		result = true;
    	}
    	
    	return result;
    }

    public static String getCertificateIssuer()
    {
    	return cert_issuer;
    }
    
    public static String getCertificateSubject()
    {
    	return cert_subject;
    }
    
    public static void initPrompnowCommandData()
    {
		url = ConfigurationsManager.getInstance().getConfiguration("susanoo.url");
    	
    	if(cert_issuer != null && cert_subject != null)
    	{
    		UtilNetwork.setDefaultUrlCertificate(PromptnowProperties.getCertificateIssuer(), PromptnowProperties.getCertificateSubject());
    	}
    }
    

    public static boolean isAuthorizedSignedAPK()
    {
    	boolean result = !isLockSignedAPK;
    	
    	if(isLockSignedAPK)
    	{
    		String signed = UtilApplication.getSignedAPK();

			UtilLog.d("signed APK value: " + signed);
			//HardCode -> "a1999022dc98de06a21259b6e2b939b2"
			String signedApkProduction = HardCodeDecryptor.getInstance().getDecryptedValue("d329bfc57daa838c19ea28b7c743fa4c40fac11e9cc26c76a488cffa1f5842ff6aca40d76514ae104cd41bf2daf5ed785d4f924ac1cf8637d615cfa26c7a1b8d609e490874342eae3e7badcb64f7ba5efdf7e6620210bca4dfe365f09cee8c32594f809d7d49ba218b7a04d24bce38877ad77d05af79b1aad0c87a79f032f183");
    		if(signed.equals(signedApkProduction))
    		{
    			result = true;
    		}
    	}
    	
    	return result;
    }
}