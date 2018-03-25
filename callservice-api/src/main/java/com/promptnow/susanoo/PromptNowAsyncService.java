package com.promptnow.susanoo;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.promptnow.bean.ConfigurationsManager;
import com.promptnow.network.PromptnowConnection;
import com.promptnow.network.PromptnowConnectionResult;
import com.promptnow.network.PromptnowConnectionResultEnum;
import com.promptnow.network.PromptnowRequestTypeEnum;
import com.promptnow.network.bean.KeyExchangeHelper;
import com.promptnow.network.bean.NetworkHelper;
import com.promptnow.susanoo.handler.iBaseEventHandler;
import com.promptnow.susanoo.handler.iByteStreamEventHandler;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.handler.iStaticServiceEventHandler;
import com.promptnow.susanoo.model.CommonErrorResponseModel;
import com.promptnow.susanoo.model.CommonRequestModel;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.promptnow.utility.UtilApplication;
import com.promptnow.utility.UtilDevice;
import com.promptnow.utility.UtilFile;
import com.promptnow.utility.UtilFormat;
import com.promptnow.utility.UtilLog;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;

public class PromptNowAsyncService<T1 extends CommonRequestModel, T2 extends CommonResponseModel> extends AsyncTask<String, Void, PromptnowConnectionResult> {
	
	protected iBaseEventHandler eventHandler = null;
	private String serviceName = null;
	private boolean secure = true;
	private boolean allowSessionChange = false;
	private PromptnowRequestTypeEnum requestMethod = PromptnowRequestTypeEnum.PromptNowRequestTypeJSON;
	
	protected T1 requestModel = null;
	private Class<T1> requestType = null;
	private Class<T2> responseType = null;

	
	private PromptnowConnection connServer = null;
	
	private static HashMap<Object,iStaticServiceEventHandler> observerMap = new HashMap<Object, iStaticServiceEventHandler>();

	public static void registerTracerServiceEvent(Object obj, iStaticServiceEventHandler handler)
	{
		if(observerMap.containsKey(obj) == false)
		{
			observerMap.put(obj, handler);
		}
	}
	
	public static void deRegisterTracerServiceEvent(Object obj)
	{
		if(observerMap.containsKey(obj))
		{
			observerMap.remove(obj);
		}
	}
	
	private static void notifyServiceDidFinished(String serviceName)
	{
		for(iStaticServiceEventHandler handler : observerMap.values())
		{
			handler.serviceDidFinish(serviceName);
		}
	}

	private static void notifyServiceDidFailed(String serviceName)
	{
		for(iStaticServiceEventHandler handler : observerMap.values())
		{
			handler.serviceDidFail(serviceName);
		}
	}
	
	private PromptNowAsyncService(Activity act,String svName, iBaseEventHandler evt, T1 reqModel, Class<T1> reqClass, Class<T2> resClass) throws Exception
	{
		if(svName == null || svName.length() == 0)
		{
			throw new Exception("Service name could not be empty.");
		}
		
		if(evt == null)
		{
			throw new Exception("PromptNow service event handler could not be null.");
		}
		
		if(reqModel == null)
		{
			reqModel = reqClass.newInstance();
			reqModel.initialCommonData(UtilApplication.getContext());
		}
		
		//activity = act;
		serviceName = svName;
		eventHandler = evt;
		requestModel = reqModel;
		requestType = reqClass;
		responseType = resClass;
		
		connServer = new PromptnowConnection();
	}
	
	public PromptNowAsyncService(Activity act,String svName, iServiceEventHandler evt, T1 reqModel, Class<T1> reqClass, Class<T2> resClass) throws Exception
	{
		this(act, svName, (iBaseEventHandler)evt, reqModel, reqClass, resClass);
	}
	
	public PromptNowAsyncService(Activity act,String svName, iServiceEventHandler evt, Class<T1> reqClass, Class<T2> resClass) throws Exception
	{
		this(act, svName, (iBaseEventHandler)evt, null, reqClass, resClass);
	}

	public PromptNowAsyncService(Activity act,String svName, iByteStreamEventHandler evt, T1 reqModel, Class<T1> reqClass, Class<T2> resClass) throws Exception
	{
		this(act, svName, (iBaseEventHandler) evt, reqModel, reqClass, resClass);
	}
	
	public PromptNowAsyncService(Activity act,String svName, iByteStreamEventHandler evt, Class<T1> reqClass, Class<T2> resClass) throws Exception
	{
		this(act, svName, (iBaseEventHandler) evt, null, reqClass, resClass);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected PromptnowConnectionResult doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		PromptnowConnectionResult result = null;
		
		if(UtilDevice.isOnline() == false) {
    		UtilLog.d("Internet disconnect.");
    		result = new PromptnowConnectionResult();
    		result.resultType = PromptnowConnectionResultEnum.ResultNoInternet;
    		return result;
    	}

		if(NetworkHelper.doAutoKeyExchange() == false)
		{
			result = new PromptnowConnectionResult();
    		result.resultType = PromptnowConnectionResultEnum.ResultKeyExchangeFail;
			return result;
		} else {
			requestModel.sessionID = PromptnowProperties.SessionID;
		}
		
		requestModel.applyAllAnnotation();
		String req = getRequest();
		UtilLog.i("Begin call service '" + serviceName + "'");
		UtilLog.d("Request msg: '" + req + "'");
		UtilLog.i("Secure request: " + String.valueOf(secure && PromptnowProperties.encryptKey.length > 0 && PromptnowProperties.decryptKey.length > 0));
		
		String res = "";
		byte reqTemp[];
		
		try
		{
			reqTemp = req.getBytes("UTF-8");
		}
		catch(Exception e)
		{
			UtilLog.w("Cannot change request to UTF-8 character");
			reqTemp = req.getBytes();
		}

		if(secure)
		{
			result = connServer.connectWithSecureJSON(serviceName, reqTemp);

			while(result.resultType == PromptnowConnectionResultEnum.ResultRequestFail404 || 
				result.resultType == PromptnowConnectionResultEnum.ResultDecryptFail)
			{
				if(NetworkHelper.doKeyExchange())
				{
					UtilLog.d("Auto key exhange is success");
					result = connServer.connectWithSecureJSON(serviceName, reqTemp);
				}
				else
				{
					UtilLog.d("Auto key exchange is fail");
					result = new PromptnowConnectionResult();
		    		result.resultType = PromptnowConnectionResultEnum.ResultKeyExchangeFail;
					return result;
				}
			}
		}
		else
		{
			result = connServer.connectWithJSON(serviceName, reqTemp);
		}
		
		if(result.jsessionChange)
		{
			KeyExchangeHelper.resetKeyExchangeServiceState();
		}
		
		res = result.resultString;
		UtilLog.d("Response msg: '" + res + "'");
		
		result = getResponseResult(result);
		
		logNetworkToFile(req, result.resultString);
		
		return result;
	}

	@Override
	protected void onPostExecute(PromptnowConnectionResult pnResult) {
		super.onPostExecute(pnResult);

		String response = pnResult.resultString;
		Gson gs = new Gson();
		
		if(pnResult.resultType == PromptnowConnectionResultEnum.ResultSuccess) {
			T2 result = null;
			boolean errorSessionChange = false;
			if(UtilFormat.isJsonFormat(pnResult.resultString)) {
				try {
					result = gs.fromJson(response, responseType);
					
					if(result.sessionID != null && result.sessionID.length() > 0 && result.sessionID.equals(PromptnowProperties.SessionID) == false) {
						UtilLog.d("old session: '" + PromptnowProperties.SessionID + "', new session: '" + result.sessionID + "'");
						if(allowSessionChange || ConfigurationsManager.isAllowSessionChange()) {
							PromptnowProperties.SessionID = result.sessionID; 
						} else {
							UtilLog.d("Unauthorized sessionID, so abort the response for service: " + serviceName);
							errorSessionChange = true;
						}
					}
					if(result.authenToken != null && result.authenToken.length() > 0) {
						PromptnowProperties.authenToken = result.authenToken;
					}
					if (result.tokenId != null && result.tokenId.length() > 0){
						PromptnowProperties.tokenId = result.tokenId;
					}
				} catch(Exception e) {
					UtilLog.e(UtilLog.getStackTraceString(e));
				}
			} else {
				try {
					result = responseType.newInstance();
					result.setCommonResponseModel(CommonResponseModel.STATUS_SUCCESS, CommonResponseModel.CODE_SUCCESS, pnResult.resultString);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					UtilLog.e(UtilLog.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					UtilLog.e(UtilLog.getStackTraceString(e));
				}
			}
			
			if(errorSessionChange) {
				CommonResponseModel resultTimeout = CommonErrorResponseModel.getInternetTimeoutErrorResponseModel();
				UtilLog.logModel(resultTimeout);
				UtilLog.i("Service return timeout");
				eventHandler.serviceDidTimeout(resultTimeout);
			} else if(result != null && CommonResponseModel.STATUS_SUCCESS.equalsIgnoreCase(result.responseStatus)) {
				if (!isCancelled()){
					UtilLog.i("Service return Success");
					if(eventHandler instanceof iServiceEventHandler) {
						((iServiceEventHandler)eventHandler).serviceDidFinish(result);
					} else if(eventHandler instanceof iByteStreamEventHandler) {
						((iByteStreamEventHandler)eventHandler).serviceDidFinish(result, pnResult.resultByteStream);
					}
				}else {
					UtilLog.i("Service Cancelled");
				}
			} else if(result == null) {
				CommonResponseModel error = new CommonErrorResponseModel(CommonResponseModel.STATUS_ERROR, CommonResponseModel.CODE_ERROR, response);
				UtilLog.logModel(error);
				if (!isCancelled()){
					UtilLog.i("Service return Fail");
					eventHandler.serviceDidFail(error);
				}else {
					UtilLog.i("Service Cancelled");
				}
			} else {
				UtilLog.logModel(result);
				if (!isCancelled()){
					UtilLog.i("Service return Fail");
					eventHandler.serviceDidFail(result);
					//notifyServiceDidFailed(serviceName);
				}else {
					UtilLog.i("Service Cancelled");
				}
			}
		}
		else if(pnResult.resultType == PromptnowConnectionResultEnum.ResultRequestFail)
		{
			CommonResponseModel result = CommonErrorResponseModel.getInternetTimeoutErrorResponseModel();
			UtilLog.logModel(result);
			if (!isCancelled()){
				UtilLog.i("Service return fail");
				eventHandler.serviceDidFail(result);
				//notifyServiceDidFailed(serviceName);
			}else {
				UtilLog.i("Service cancelled");
			}
		}
		else if(pnResult.resultType == PromptnowConnectionResultEnum.ResultRequestTimeout)
		{
			CommonResponseModel result = CommonErrorResponseModel.getInternetTimeoutErrorResponseModel();
			UtilLog.logModel(result);
			if (!isCancelled()){
				UtilLog.i("Service return timeout");
				eventHandler.serviceDidTimeout(result);
			}else {
				UtilLog.i("Service cancelled");
			}
		}
		else if(pnResult.resultType == PromptnowConnectionResultEnum.ResultNoInternet)
		{
			CommonResponseModel result = CommonErrorResponseModel.getInternetDisconnectErrorResponseModel();
			UtilLog.logModel(result);
			UtilLog.i("Service return disconnected");
			eventHandler.serviceDidFail(result);
			//notifyServiceDidFailed(serviceName);
		}
		else if(pnResult.resultType == PromptnowConnectionResultEnum.ResultKeyExchangeFail)
		{
			CommonResponseModel result = CommonErrorResponseModel.getKeyExchangeErrorResponseModel();
			UtilLog.logModel(result);
			if (!isCancelled()){
				UtilLog.i("Service return key exchange fail");
				eventHandler.serviceDidFail(result);
				//notifyServiceDidFailed(serviceName);
			}else {
				UtilLog.i("Service cancelled");
			}
		}
		else
		{
			String responseMsg = "Unknown error: " + response;
			CommonResponseModel result = new CommonErrorResponseModel(CommonResponseModel.STATUS_ERROR, CommonResponseModel.CODE_ERROR, responseMsg);
			UtilLog.logModel(result);
			if (!isCancelled()){
				UtilLog.i("Service return error");
				eventHandler.serviceDidFail(result);
				//notifyServiceDidFailed(serviceName);
			}else {
				UtilLog.i("Service cancelled");
			}
		}
		UtilLog.i("End call service '" + serviceName + "'");
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		UtilLog.i("Cancelled service '" + serviceName + "'");
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	public String getServiceName()
	{
		return this.serviceName;
	}
	
	public Type getRequestModelClass()
	{
		return requestType;
	}
	
	public Type getResponseModelClass()
	{
		return responseType;
	}
	
	public PromptnowRequestTypeEnum getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(PromptnowRequestTypeEnum requestMethod) {
		this.requestMethod = requestMethod;
	}

	public static boolean tryExecuteService(PromptNowAsyncService<?,?> service)
	{
		boolean result = false;
		
		try
		{
			service.execute();
			result = true;
		}
		catch(Exception e)
		{
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return result;
	}

	public static PromptNowAsyncService executeService(PromptNowAsyncService<?,?> service) {
		PromptNowAsyncService<?,?> result = null;
		try {
			service.execute();
			result = service;
		} catch(Exception e) {
			UtilLog.e(UtilLog.getStackTraceString(e));
		}
		return result;
	}
	
	protected String getRequest()
	{
		String result = "";
		
		switch(requestMethod)
		{
		case PromptNowRequestTypeJSON:
			result = getGson().toJson(requestModel, requestType);
			break;
		case PromptNowRequestTypePOST:
			result = getPostRequest();
			break;
		case PromptNowRequestTypeGET:
			result = getPostRequest();
			break;
		}
		
		return result;
	}
	
	private Gson getGson()
	{
		Gson gs = null;
		
		if(PromptnowProperties.prettyJson)
		{
			gs = (new GsonBuilder()).setPrettyPrinting().create();
		}
		else
		{
			gs =new Gson();
		}
		
		return gs;
	}
	
	protected PromptnowConnectionResult getResponseResult(PromptnowConnectionResult resResult)
	{
		String res = "";
		
        if(PromptnowProperties.prettyJson && res.startsWith("{") && res.endsWith("}"))
        {
        	JsonParser jsonParser = new JsonParser();
        	JsonObject obj = jsonParser.parse(res).getAsJsonObject();
        	resResult.resultString = getGson().toJson(obj);
        }
        
		return resResult;
	}
	
	private void logNetworkToFile(String req, String res)
	{
		if(PromptnowProperties.enableNetworkLog)
        {
        	String resTemp = null;
        	
        	if(res.startsWith("{") && res.endsWith("}"))
        	{
        		resTemp = res;
        	}
        	else
        	{
        		resTemp = String.format("\"%s\"", res);
        	}
        	
        	String format = "\n{\n\"serviceName\":\"%s\",\n\"requestJSON\":%s,\n\"responseJSON\":%s\n}\n";
        	String networkLog = String.format(format, serviceName, req, resTemp);
    		UtilFile.writeFile(PromptnowProperties.networkLogFilePath, networkLog.getBytes());
        }
	}
	
	private String getPostRequest()
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		Field[] fields = requestType.getDeclaredFields();
		
		for(Field field : fields)
		{
			field.setAccessible(true);
			
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        try
	        {
		        result.append(URLEncoder.encode(field.getName(), "UTF-8"));
		        result.append("=");
		        result.append(URLEncoder.encode(field.get(requestModel).toString(), "UTF-8"));
	        }
	        catch(Exception e)
	        {
	        	UtilLog.e(UtilLog.getStackTraceString(e));
	        }
		}
		
		return result.toString();
	}
}
