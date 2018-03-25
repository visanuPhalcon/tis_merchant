package com.promptnow.susanoo.handler;

import com.promptnow.susanoo.model.CommonRequestModel;
import com.promptnow.susanoo.model.CommonResponseModel;

public interface iBaseEventHandler {
	void serviceDidFail(CommonResponseModel response);
	void serviceDidTimeout(CommonResponseModel response);
}
