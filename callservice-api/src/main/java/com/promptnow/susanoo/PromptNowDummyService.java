package com.promptnow.susanoo;

import android.app.Activity;

import com.promptnow.susanoo.handler.iByteStreamEventHandler;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonRequestModel;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.promptnow.network.*;
import com.promptnow.network.bean.KeyExchangeHelper;
import com.promptnow.utility.UtilAsset;
import com.promptnow.utility.UtilLog;

public class PromptNowDummyService<T1 extends CommonRequestModel, T2 extends CommonResponseModel> extends PromptNowAsyncService<T1, T2> {
	
	public PromptNowDummyService(Activity act, String svName,
			iByteStreamEventHandler evt, Class<T1> reqClass, Class<T2> resClass)
			throws Exception {
		super(act, svName, evt, reqClass, resClass);
		// TODO Auto-generated constructor stub
	}

	public PromptNowDummyService(Activity act, String svName,
			iByteStreamEventHandler evt, T1 reqModel, Class<T1> reqClass,
			Class<T2> resClass) throws Exception {
		super(act, svName, evt, reqModel, reqClass, resClass);
		// TODO Auto-generated constructor stub
	}

	public PromptNowDummyService(Activity act, String svName,
			iServiceEventHandler evt, Class<T1> reqClass, Class<T2> resClass)
			throws Exception {
		super(act, svName, evt, reqClass, resClass);
		// TODO Auto-generated constructor stub
	}

	public PromptNowDummyService(Activity act, String svName,
			iServiceEventHandler evt, T1 reqModel, Class<T1> reqClass,
			Class<T2> resClass) throws Exception {
		super(act, svName, evt, reqModel, reqClass, resClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PromptnowConnectionResult doInBackground(String... params) {
		// TODO Auto-generated method stub
		PromptnowConnectionResult result = null;
		
		requestModel.applyAllAnnotation();
		String req = getRequest();
		UtilLog.i("Begin call service '" + getServiceName() + "'");
		UtilLog.d("Request msg: '" + req + "'");
		UtilLog.i("Secure request: false");
		
		String res = "";

		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException e)
		{
			
		}
		
		result = new PromptnowConnectionResult();
		result.jsessionChange = false;
		result.resultType = PromptnowConnectionResultEnum.ResultSuccess;
		result.resultString = UtilAsset.loadTextFile("dummynetwork/" + getServiceName() + ".json");
		
		if(result.jsessionChange)
		{
			KeyExchangeHelper.resetKeyExchangeServiceState();
		}
		
		res = result.resultString;
		UtilLog.d("Response msg: '" + res + "'");
		
		result = getResponseResult(result);
		
		return result;
	}

}
