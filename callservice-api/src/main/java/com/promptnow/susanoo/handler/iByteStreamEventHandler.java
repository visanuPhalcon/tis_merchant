package com.promptnow.susanoo.handler;

import com.promptnow.susanoo.model.CommonResponseModel;

public interface iByteStreamEventHandler extends iBaseEventHandler {


	public void serviceDidFinish(CommonResponseModel response, byte[] stream);
}
