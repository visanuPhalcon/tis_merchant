// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PromptnowConnection.java

package com.promptnow.network;

import com.promptnow.bean.ConfigurationsManager;
import com.promptnow.bean.RootChecker;
import com.promptnow.network.secure.BaseSecureProvider;
import com.promptnow.susanoo.PromptnowProperties;
import com.promptnow.utility.UtilCipher;
import com.promptnow.utility.UtilFormat;
import com.promptnow.utility.UtilLog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class PromptnowConnection
{
    HttpURLConnection httpconn = null;

    protected static HttpsURLConnection connectHttpsWithXML(String service, byte data[]) throws Exception
    {
        return connectHTTPS(CONTENT_TYPE_XML, service, data);
    }

    protected static HttpURLConnection connectHttpWithXML(String service, byte data[]) throws Exception
    {
        return connectHTTP(CONTENT_TYPE_XML, service, data);
    }

    public void closeConnection()
    {
    	if(httpconn != null)
    	{
    		httpconn.disconnect();
    		httpconn = null;
    	}
    }
    
    private PromptnowConnectionResult connectWithGET(String service, boolean isHTTPS)
    {
    	PromptnowConnectionResult result = new PromptnowConnectionResult();
    	
    	try
        {
            InputStream is = null;
            StringBuilder url = (new StringBuilder(PromptnowProperties.getStringURL())).append(service);
            
            if(service.indexOf(".jsp") == -1)
            {
            	url.append(".service");
            }
            String serviceURL = url.toString();
            UtilLog.d("Service URL: " + serviceURL);
            
            if(isHTTPS)
            {
            	httpconn = connectHTTPS(CONTENT_TYPE_HTML, serviceURL, null);
            }
            else
            {
            	httpconn = connectHTTP(CONTENT_TYPE_HTML, serviceURL, null);
            }
            
            is = null;
            int responseCode = httpconn.getResponseCode();
            String responseMessage = httpconn.getResponseMessage();
            UtilLog.d("Response code: " + responseCode + ", Response message: " + responseMessage);

            if(RootChecker.getInstance().isRooted())
            {
            	result.resultType = PromptnowConnectionResultEnum.ResultRequestTimeout;
            	result.resultString = "";
            }
            else if(responseCode == 200)
            {
                is = httpconn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte buffer[] = new byte[16384];
                int nRead;
                while((nRead = is.read(buffer, 0, buffer.length)) != -1) 
                	outStream.write(buffer, 0, nRead);
                outStream.flush();
                is.close();
                setCookie(httpconn.getHeaderFields());
                httpconn.disconnect();
                result.resultString = new String(outStream.toByteArray(), "UTF8");
                
            	String hexResponse = new String(outStream.toByteArray(), "UTF8");
                String temp = hexResponse.trim();
                
                if(UtilFormat.isJsonFormat(temp))
            	{
            		result.resultString = temp; // outStream is json
            	}
            	else if(UtilFormat.isXmlFormat(temp))
            	{
            		result.resultString = temp; // outStream is xml
            	}
            	else
            	{
            		result.resultString = BYTESTREAM_OUTPUT;
	                result.resultByteStream = outStream.toByteArray(); // outStream is binary
            	}
            }
            else
            {
            	result.resultType = PromptnowConnectionResultEnum.ResultRequestFail;
            	result.resultString = String.format(CONNECTION_ERROR_FORMAT, httpconn.getResponseMessage() + "(code:" + httpconn.getResponseCode() + ")");
                UtilLog.d("Connection fail. Response code: " + responseCode);
            }
        }
    	/*
    	catch(SocketTimeoutException e)
    	{
    		result.resultType = PromptNowConnectionResultEnum.ResultRequestTimeout;
    		result.resultString = String.format(CONNECTION_ERROR_FORMAT, e.getMessage());
            UtilLog.e(UtilLog.getStackTraceString(e));
    	}
        catch(Exception e)
        {
        	result.resultType = PromptNowConnectionResultEnum.ResultUnknownError;
        	result.resultString = String.format(CONNECTION_ERROR_FORMAT, e.getMessage());
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        */
        catch(Exception e)
        {
        	UtilLog.d(e.getClass().getName());
        	result.resultType = PromptnowConnectionResultEnum.ResultRequestTimeout;
        	result.resultString = e.getMessage();
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
    	
    	return result;
    }
    
    public PromptnowConnectionResult connectWithGET(String service)
    {
    	String oldCookie = PromptnowProperties.COOKIE;
    	PromptnowConnectionResult result = connectWithGET(service, PromptnowProperties.isHttpsURL());
    	
        if(oldCookie.equals(PromptnowProperties.COOKIE) == false)
        {
        	result.jsessionChange = true;
        }
        
        return result;
    }
    
    private PromptnowConnectionResult connectWithJSON(String service, byte data[], boolean isHTTPS, boolean isSecure)
    {
    	PromptnowConnectionResult result = new PromptnowConnectionResult();
    	
    	try
        {
            InputStream is = null;
            StringBuilder url = (new StringBuilder(PromptnowProperties.getStringURL())).append(service);
            
            if(service.indexOf(".jsp") == -1)
            {
            	url.append(".service");
            }
            String serviceURL = url.toString();
            UtilLog.d("Service URL: " + serviceURL);
            
            if(isSecure && PromptnowProperties.encryptKey.length > 0 && PromptnowProperties.decryptKey.length > 0)
            {
            	//byte encrypts[] = PromptnowDHKeyExchange.getEncryptString(data, PromptnowCommandData.encryptKey, PromptnowCommandData.initialVector);
            	byte encrypts[] = BaseSecureProvider.getEncrypted(data, PromptnowProperties.encryptKey, PromptnowProperties.initialVector);
            	String hexRequest = UtilCipher.getHexString(encrypts);
                UtilLog.v("Encrypted Request Msg: " + hexRequest);
                data = hexRequest.getBytes();
            }
            
            if(isHTTPS)
            {
            	httpconn = connectHTTPS(CONTENT_TYPE_JSON, serviceURL, data);
            }
            else
            {
            	httpconn = connectHTTP(CONTENT_TYPE_JSON, serviceURL, data);
            }
            
            is = null;
            int responseCode = httpconn.getResponseCode();
            String responseMessage = httpconn.getResponseMessage();
            UtilLog.d("Response code: " + responseCode + ", Response message: " + responseMessage);
            
            if(RootChecker.getInstance().isRooted() && !ConfigurationsManager.isAllowRootedDevice())
            {
            	result.resultType = PromptnowConnectionResultEnum.ResultRequestTimeout;
            	result.resultString = "";
            	UtilLog.d("Device is rooted, so framework will response as timeout response.");
            }
            else if(responseCode == 200)
            {
                is = httpconn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte buffer[] = new byte[16384];
                int nRead;
                while((nRead = is.read(buffer, 0, buffer.length)) != -1) 
                	outStream.write(buffer, 0, nRead);
                outStream.flush();
                is.close();
                setCookie(httpconn.getHeaderFields());
                httpconn.disconnect();
                result.resultString = new String(outStream.toByteArray(), "UTF8");
                
                if(isSecure)
                {
                	String hexResponse = new String(outStream.toByteArray(), "UTF8");
                	UtilLog.v("Encrypted Response Msg: " + hexResponse);
                    String temp = hexResponse.trim();
                    
                    if(!PromptnowProperties.forceDecryption && UtilFormat.isJsonFormat(temp))
                    {
                    	UtilLog.d("Response data does not encrypted, so do not decrypt it.");
                    	result.resultString = hexResponse;
                    }
                    else if(!PromptnowProperties.forceDecryption && UtilFormat.isXmlFormat(temp))
                    {
                    	UtilLog.d("Response data is html and not encrypted, so do not decrypt it.");
                    	result.resultString = hexResponse;
                    }
                    else
                    {	
    	                //byte decrypts[] = PromptnowDHKeyExchange.getDecryptString(PromptnowDHKeyExchange.toByte(hexResponse), PromptnowCommandData.decryptKey, PromptnowCommandData.initialVector);
                    	byte decrypts[] = BaseSecureProvider.getDecrypted(UtilCipher.hexStringToByteArray(hexResponse), PromptnowProperties.decryptKey, PromptnowProperties.initialVector);
    	                
    	                temp = (new String(decrypts, 0, decrypts.length)).trim();
    	                UtilLog.d("Decrypted Response Msg:" + temp);
    	                
    	                if(decrypts != null && decrypts.length > 0)
    	                {
    	                	if(UtilFormat.isJsonFormat(temp))
    	                	{
    	                		result.resultString = temp;
    	                	}
    	                	else if(UtilFormat.isXmlFormat(temp))
    	                	{
    	                		result.resultString = temp;
    	                	}
    	                	else
    	                	{
    	                		result.resultString = BYTESTREAM_OUTPUT;
		    	                result.resultByteStream = decrypts;
    	                	}
    	                }
    	                else
    	                {
    	                	result.resultType = PromptnowConnectionResultEnum.ResultDecryptFail;
    	                }
                    }
                }
                else
                {
                	String hexResponse = new String(outStream.toByteArray(), "UTF8");
                    String temp = hexResponse.trim();
                    
                    if(UtilFormat.isJsonFormat(temp))
                	{
                		result.resultString = temp; // outStream is json
                	}
                	else if(UtilFormat.isXmlFormat(temp))
                	{
                		result.resultString = temp; // outStream is xml
                	}
                	else
                	{
                		result.resultString = BYTESTREAM_OUTPUT;
    	                result.resultByteStream = outStream.toByteArray(); // outStream is binary
                	}
                }
            }
            else
            {
            	if(responseCode == 404 && isSecure)
            	{
            		result.resultType = PromptnowConnectionResultEnum.ResultRequestFail404;
            	}
            	else 
            	{
            		result.resultType = PromptnowConnectionResultEnum.ResultRequestFail;
            	}
            	
            	result.resultString = String.format(CONNECTION_ERROR_FORMAT, httpconn.getResponseMessage() + "(code:" + httpconn.getResponseCode() + ")");
                UtilLog.d("Connection fail. Response code: " + responseCode);
            }
        }
    	/*
    	catch(SocketTimeoutException e)
    	{
    		result.resultType = PromptNowConnectionResultEnum.ResultRequestTimeout;
    		result.resultString = String.format(CONNECTION_ERROR_FORMAT, e.getMessage());
            UtilLog.e(UtilLog.getStackTraceString(e));
    	}
        catch(Exception e)
        {
        	result.resultType = PromptNowConnectionResultEnum.ResultUnknownError;
        	result.resultString = String.format(CONNECTION_ERROR_FORMAT, e.getMessage());
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        */
        catch(Exception e)
        {
        	UtilLog.d(e.getClass().getName());
        	result.resultType = PromptnowConnectionResultEnum.ResultRequestTimeout;
        	result.resultString = e.getMessage();
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
    	
    	return result;
    }
    
    public PromptnowConnectionResult connectWithJSON(String serviceName, byte data[])
    {
    	String oldCookie = PromptnowProperties.COOKIE;
    	PromptnowConnectionResult result = connectWithJSON(serviceName, data, PromptnowProperties.isHttpsURL(), false);
    	
        if(oldCookie.equals(PromptnowProperties.COOKIE) == false)
        {
        	result.jsessionChange = true;
        }
        
        return result;
    }

    public PromptnowConnectionResult connectWithSecureJSON(String serviceName, byte data[])
    {
    	String oldCookie = PromptnowProperties.COOKIE;
    	PromptnowConnectionResult result = connectWithJSON(serviceName, data, PromptnowProperties.isHttpsURL(), true);

        if(oldCookie.equals(PromptnowProperties.COOKIE) == false)
        {
        	result.jsessionChange = true;
        }
    	
    	return result;
    }

    private static HttpURLConnection connectHTTP(String contentType, String service, byte data[]) throws Exception
    {
    	HttpURLConnection conn = null;

    	int contentLength = 0;
    	
    	if(data != null && data.length > 0)
    	{
    		contentLength = data.length;
    	}
    	
        try
        {
            URL url = new URL(service);
            conn = (HttpURLConnection)url.openConnection();
            conn.addRequestProperty("Content-type", contentType);
            conn.setRequestProperty("accept-charset", "UTF-8");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("http.keepAlive", "false");
            conn.setRequestProperty("Device-Type", "android");
            conn.setReadTimeout(0x1d4c0);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestProperty("User-Agent", PromptnowProperties.USER_AGENT);
            conn.setRequestProperty("Content-Length", String.valueOf(contentLength));
            if(PromptnowProperties.COOKIE.length() > 0)
            {
            	conn.setRequestProperty("Cookie", PromptnowProperties.COOKIE);
            }
            
            for(String key : conn.getRequestProperties().keySet()){
            	UtilLog.d("Key : "+ key +"/" + conn.getRequestProperty(key));
            }        
            
            if(data != null && data.length > 0)
            {
	            conn.setDoInput(true);
	            conn.setDoOutput(true);
	            OutputStream outs = null;
	            outs = conn.getOutputStream();
	            outs.write(data, 0, data.length);
	            outs.flush();
            }
            else
            {
            	conn.connect();
            }
        }
        catch(Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
            throw e;
        }
        return conn;
    }

    private static HttpsURLConnection connectHTTPS(String contentType, String service, byte data[]) throws Exception
    {
    	HttpsURLConnection conn = null;
    	
    	int contentLength = 0;
    	
    	if(data != null && data.length > 0)
    	{
    		contentLength = data.length;
    	}
    	
        try
        {
            if (ConfigurationsManager.isTrustAllCertificates()){
                trustAllCertificates();
            }
            URL url = new URL(service);
            conn = (HttpsURLConnection)url.openConnection();
            conn.addRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("accept-charset", "UTF-8");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("PlatForm", PromptnowProperties.APPLICATION_TYPE);
            conn.setRequestProperty("http.keepAlive", "true");
            conn.setRequestProperty("Pragma", "no-cache");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Expires", "-1");
            conn.setRequestProperty("Device-Type", "android");
            conn.setReadTimeout(0x1d4c0);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestProperty("User-Agent", PromptnowProperties.USER_AGENT);
            conn.setRequestProperty("Content-Length", String.valueOf(contentLength));           
            if(PromptnowProperties.COOKIE.length() > 0)
            {
            	conn.setRequestProperty("Cookie", PromptnowProperties.COOKIE);
            } 
  
            for(String key : conn.getRequestProperties().keySet()){
            	UtilLog.d("Key : "+ key +"/" + conn.getRequestProperty(key));
            }
            
            if(data != null && data.length > 0)
            {
	            conn.setDoInput(true);
	            conn.setDoOutput(true);
	            OutputStream outs = null;
	            outs = conn.getOutputStream();
	            outs.write(data, 0, data.length);
	            outs.flush();
            }
            else
            {
            	conn.connect();
            }

            String curSuite = conn.getCipherSuite();
            UtilLog.d(String.format("current suite name: %s", curSuite));
        }
        catch(Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
            throw e;
        }
        return conn;
    }

    private static void trustAllCertificates() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    private void setCookie(Map<String,List<String>> headerFields)
    {
    	CookieStorage cookiesStorage;
        cookiesStorage = new CookieStorage();

        cookiesStorage.setCookies(headerFields);
        if(cookiesStorage.JSESSIONID.length() > 0)
        {
        	PromptnowProperties.COOKIE = cookiesStorage.getCookies();
        	PromptnowProperties.JSESSIONID = cookiesStorage.JSESSIONID;
        }
    }

    protected static BufferedReader in = null;
    
    protected static final String CONNECTION_ERROR = "CONNECTION_ERROR";
    public static final String BYTESTREAM_OUTPUT = "BYTESTREAM";

    private static final String CONNECTION_ERROR_FORMAT = CONNECTION_ERROR + ": %s";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE_XML = "text/xml; charset=utf-8";
    private static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";
    private static final int CONNECTION_TIMEOUT = 10000;
}