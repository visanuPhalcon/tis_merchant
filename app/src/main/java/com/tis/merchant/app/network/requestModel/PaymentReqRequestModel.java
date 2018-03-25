package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class PaymentReqRequestModel extends CommonRequestModel {

    @SerializedName("qrRefNo")
    String qrRefNo;

    public static PaymentReqRequestModel requestModel;
    public static PaymentReqRequestModel newInstance(Context context){
        requestModel = new PaymentReqRequestModel();
        requestModel.initialCommonData(context);

        return requestModel;
    }

    public String getQrRefNo() {
        return qrRefNo;
    }

    public void setQrRefNo(String qrRefNo) {
        this.qrRefNo = qrRefNo;
    }
}
