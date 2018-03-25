package com.promptnow.susanoo.model;

import android.content.Context;
import android.content.res.Configuration;

import com.google.gson.annotations.SerializedName;
import com.promptnow.network.annotation.UrlEncoder;
import com.promptnow.susanoo.PromptnowProperties;
import com.promptnow.utility.UtilCalendar;
import com.promptnow.utility.UtilDevice;
import com.promptnow.utility.UtilFormat;
import com.promptnow.utility.UtilLog;
import com.promptnow.utility.UtilReflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class CommonRequestModel {
    
    public static final String LANG_THAI = "TH";
    public static final String LANG_ENG = "EN";
    public static final String DEVICE_OS = "android";
    public static final String DEVICE_TYPE = "Android";

    @SerializedName("authenToken")
    public String authenToken;
    @SerializedName("username")
    public String userName;
    @SerializedName("sessionID")
    public String sessionID;
    @SerializedName("deviceID")
    public String deviceID;
    @SerializedName("deviceOS")
    public String deviceOS;
    @SerializedName("deviceOSVersion")
    public String deviceOSVersion;
    @SerializedName("deviceType")
    public String deviceType;
    @SerializedName("deviceModel")
    public String deviceModel;
    @SerializedName("latitude")
    public String latitude;
    @SerializedName("longitude")
    public String longitude;
    @SerializedName("language")
    public String language;
    @SerializedName("clientDateTime")
    public String clientDateTime;

    public CommonRequestModel() {
        authenToken = PromptnowProperties.authenToken;
        userName = PromptnowProperties.userName;
        sessionID = PromptnowProperties.SessionID;
        language = PromptnowProperties.LANGUAGE.toUpperCase();
        deviceID = UtilDevice.getDeviceID();
        deviceOS = DEVICE_OS;
        deviceOSVersion = UtilDevice.getDeviceVersion();
        deviceType = DEVICE_TYPE;
        latitude = "0.0";
        longitude = "0.0";
        clientDateTime = UtilCalendar.getCurrentTimeFormat("yyyyMMddHHmmss");
    }
    
    public CommonRequestModel(Context context) {
    	this();
    	initialCommonData(context);
    }
    
    public void initialCommonData(Context context) {
        authenToken = PromptnowProperties.authenToken;
        userName = PromptnowProperties.userName;
    	sessionID = PromptnowProperties.SessionID;
    	language = PromptnowProperties.LANGUAGE.toUpperCase();
    	deviceID = UtilDevice.getDeviceID();
        deviceOS = DEVICE_OS;
        deviceOSVersion = UtilDevice.getDeviceVersion();
        deviceModel = UtilDevice.getDeviceModel();
        deviceType = isTablet(context)? "4" : "3";
        clientDateTime = UtilCalendar.getCurrentTimeFormat("yyyyMMddHHmmss");
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public void applyAllAnnotation() {
    	Class<?> thisCls = this.getClass();
    	ArrayList<Field> fields = UtilReflection.getAllFieldsMember(thisCls);

        for (Field field : fields) {
            try {
            	Object fieldObj = field.get(this);
                field.setAccessible(true);
                if (fieldObj == null) {
                    continue;
                }

                Annotation[] arrAnno = field.getAnnotations();
                
                for(Annotation anno : arrAnno)
                {
                	if(anno instanceof UrlEncoder)
                	{
                		if(fieldObj instanceof String)
                		{
                			Object fieldValue = UtilFormat.formatUrlEncoder((String)fieldObj);
                			field.set(this, fieldValue);
                		}
                	}
                }
            } catch (IllegalAccessException e) {
                UtilLog.e("Can not apply annotation to field: " + field.getName() + " of " + thisCls.getName());
            }
        }
    }
}
