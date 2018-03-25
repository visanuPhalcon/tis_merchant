package com.promptnow.susanoo.handler;

public interface iStaticServiceEventHandler {
	public void serviceDidFinish(String serviceName);
	public void serviceDidFail(String serviceName);
}
