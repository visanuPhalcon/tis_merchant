// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CookieStorage.java

package com.promptnow.network;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.promptnow.utility.UtilLog;


public class CookieStorage
{
    public String getCookies()
    {
        StringBuilder tempCookies = new StringBuilder();
        if(JSESSIONID.length() > 0)
        {
            tempCookies.append("JSESSIONID=").append(JSESSIONID);
        }
        
        if(STATE.length() > 0)
        {
        	tempCookies.append("STATE=").append(STATE);
        }
        
        if(VULCAN.length() > 0)
        {
        	tempCookies.append("VULCAN=").append(VULCAN);
        }
        
        if(ANOTHER.length() > 0)
        {
        	tempCookies.append(ANOTHER);
        }

        String cookies = tempCookies.toString();
        UtilLog.d("getCookie:" + cookies);
        return cookies;
    }
    
    public void setCookies(Map<String,List<String>> headerFields)
    {
    	try
        {
            Set<String> headerFieldsSet = headerFields.keySet();
            for(Iterator<String> hearerFieldsIter = headerFieldsSet.iterator(); hearerFieldsIter.hasNext();)
            {
                String headerFieldKey = (String)hearerFieldsIter.next();
                if("Set-Cookie".equalsIgnoreCase(headerFieldKey))
                {
                    List<String> headerFieldValue = (List<String>)(headerFields.get(headerFieldKey));
                    
                    for(Iterator<String> iterator = headerFieldValue.iterator(); iterator.hasNext();)
                    {
                        String headerValue = (String)iterator.next();
                        String cookieTemp = headerValue.toString();
                        
                        UtilLog.d("Set Cookie : "+cookieTemp);
                        if(cookieTemp.indexOf("JSESSIONID") != -1)
                            JSESSIONID = cookieTemp.substring(cookieTemp.indexOf("=") + 1) + ";";
                        else if(cookieTemp.indexOf("STATE") != -1)
                            STATE = cookieTemp.substring(cookieTemp.indexOf("=") + 1) + ";";
                        else if(cookieTemp.indexOf("VULCAN") != -1)
                            VULCAN = cookieTemp.substring(cookieTemp.indexOf("=") + 1) + ";";
                        else
                        	ANOTHER = ANOTHER + cookieTemp + ";";
                    }

                    break;
                }
            }

        }
        catch(Exception e)
        {
			UtilLog.e(UtilLog.getStackTraceString(e));
        }
    }

    public String JSESSIONID = "";
    private String STATE = "";
    private String VULCAN = "";
    private String ANOTHER = "";
}