package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;
import com.promptnow.susanoo.model.CommonResponseModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class PaymentReqResponseModel extends CommonResponseModel {

    @SerializedName("consumerName")
    String consumerName ;

    public static PaymentReqResponseModel responseModel;
    public static PaymentReqResponseModel getInstance(Context context)
    {
        responseModel = new PaymentReqResponseModel();
        return responseModel;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
}
