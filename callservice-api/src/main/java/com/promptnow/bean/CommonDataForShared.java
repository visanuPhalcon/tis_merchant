package com.promptnow.bean;

import com.promptnow.susanoo.PromptnowProperties;
import com.promptnow.susanoo.model.CommonRequestModel;

public class CommonDataForShared {
	
	public static final String prefLanguageName = "lang";
	public static final String LANG_THAI = CommonRequestModel.LANG_THAI;
	public static final String LANG_ENG = CommonRequestModel.LANG_ENG;
	public static final String prefNotShowAgain = "notShow";

	public static int screen_width = 0;
	public static int screen_height = 0;
	
	public static String getLanguage()
	{
		return PromptnowProperties.LANGUAGE;
	}
	
	public static void setLanguage(String language)
	{
		PromptnowProperties.LANGUAGE = language;
	}

	public static void setLanguageThai()
	{
		setLanguage(LANG_THAI);
	}

	public static void setLanguageEng()
	{
		setLanguage(LANG_ENG);
	}

	// Setter Getter
	public static int getScreenWidth() {
		return screen_width;
	}

	public static int getScreenHeight() {
		return screen_height;
	}

	public static void setScreenWidth(int width) {
		screen_width = width;
	}

	public static void setScreenHeight(int height) {
		screen_height = height;
	}

} 

