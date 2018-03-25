package com.promptnow.susanoo.model;

import com.google.gson.annotations.SerializedName;

public abstract class CommonResponseModel {
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_ERROR = "ERROR";
	public static final String STATUS_TIMEOUT = "TIMEOUT";
	public static final String STATUS_NO_INTERNET = "NOINTERNET";
	
	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_ERROR = "1";

	@SerializedName("authenToken")
	public String authenToken;
	@SerializedName("tokenId")
	public String tokenId;
	@SerializedName("sessionID")
	public String sessionID;
	@SerializedName("responseCode")
    public String responseCode;
	@SerializedName("responseStatus")
    public String responseStatus;
	@SerializedName("responseMessage")
    public String responseMessage;
	@SerializedName("serverDatetime")
    public String serverDatetime;
	@SerializedName("serverDatetimeMS")
	public String serverDatetimeMS;
    
    public CommonResponseModel() {
    	responseCode = "";
    	responseStatus = "";
		responseMessage = "";
		serverDatetime = "";
    }
    
    public CommonResponseModel(String status, String code,String msg){
    	this();
    	setCommonResponseModel(status, code, msg);
    }
    
    public void setCommonResponseModel(String status, String code,String msg) {
    	responseStatus = status;
        responseCode = code;
        responseMessage = msg;
    }
    
}
