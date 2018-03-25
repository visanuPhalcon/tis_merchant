package com.tis.merchant.app;

import android.app.Application;

import com.promptnow.application.PromptNowApplication;
import com.tis.merchant.app.base.BaseApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Nanthakorn on 7/26/2017.
 */

public class MyApplication extends PromptNowApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
