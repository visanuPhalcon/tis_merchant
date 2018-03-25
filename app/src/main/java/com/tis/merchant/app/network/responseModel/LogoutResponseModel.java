package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.promptnow.susanoo.model.CommonResponseModel;

/**
 * Created by Admin on 5/4/2560.
 */

public class LogoutResponseModel extends CommonResponseModel {

    public static LogoutResponseModel responseModel;
    public static LogoutResponseModel getInstance(Context context)
    {
        responseModel = new LogoutResponseModel();
        return responseModel;
    }
}
