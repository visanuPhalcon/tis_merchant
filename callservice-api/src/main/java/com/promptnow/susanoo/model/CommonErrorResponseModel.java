package com.promptnow.susanoo.model;

import com.promptnow.utility.UtilApplication;
import com.promptnow.app.R;

public class CommonErrorResponseModel extends CommonResponseModel {

	public CommonErrorResponseModel(String statusError, String codeError,
			String response) {
		// TODO Auto-generated constructor stub
		super(statusError, codeError, response);
	}

	public static CommonErrorResponseModel getInternetDisconnectErrorResponseModel()
	{
		String resMessage = UtilApplication.getResourceText(R.string.framework_checkInternet);
		return new CommonErrorResponseModel(STATUS_NO_INTERNET, CODE_ERROR, resMessage);
	}
	public static CommonErrorResponseModel getInternetTimeoutErrorResponseModel()
	{
		String resMessage = UtilApplication.getResourceText(R.string.framework_redo_transaction_pls);
		return new CommonErrorResponseModel(STATUS_ERROR, CODE_ERROR, resMessage);
	}
	
	public static CommonErrorResponseModel getKeyExchangeErrorResponseModel()
	{
		String resMessage = UtilApplication.getResourceText(R.string.framework_redo_transaction_pls);
		return new CommonErrorResponseModel(STATUS_ERROR, CODE_ERROR, resMessage);
	}
	
	public static CommonErrorResponseModel getSystemErrorResponseModel()
	{
		String resMessage = UtilApplication.getResourceText(R.string.framework_close_app_error);
		return new CommonErrorResponseModel(STATUS_ERROR, CODE_ERROR, resMessage);
	}
	
	public static  CommonErrorResponseModel getDecryptErrorResponseModel()
	{
		return getCommonErrorResponseModel("Decrypt Error");
	}

	public static  CommonErrorResponseModel getCommonErrorResponseModel(String resMessage)
	{
		return new CommonErrorResponseModel(STATUS_ERROR, CODE_ERROR, resMessage);
	}
	
}
