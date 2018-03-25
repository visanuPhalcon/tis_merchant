package com.promptnow.susanoo.handler;

import com.promptnow.susanoo.model.CommonResponseModel;

public interface iServiceEventHandler extends iBaseEventHandler {
	void serviceDidFinish(CommonResponseModel response);
}
