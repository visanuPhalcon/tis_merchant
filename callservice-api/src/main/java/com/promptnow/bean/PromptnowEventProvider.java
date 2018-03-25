package com.promptnow.bean;

//import com.squareup.otto.Bus;

public class PromptnowEventProvider {//extends Bus {

	private static PromptnowEventProvider bus = null;
	
	public static PromptnowEventProvider getInstance()
	{
		if(bus == null)
		{
			bus = new PromptnowEventProvider();
		}
		
		return bus;
	}
	
	public PromptnowEventProvider()
	{
		super();
	}
}
