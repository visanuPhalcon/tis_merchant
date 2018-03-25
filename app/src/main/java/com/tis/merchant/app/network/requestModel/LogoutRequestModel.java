package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 5/4/2560.
 */

public class LogoutRequestModel extends CommonRequestModel {

    public static LogoutRequestModel requestModel;
    public static LogoutRequestModel newInstance(Context context){
            requestModel = new LogoutRequestModel();
            requestModel.initialCommonData(context);
        return requestModel;
    }

}
