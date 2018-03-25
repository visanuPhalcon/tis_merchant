package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class PaymentConfirmRequestModel extends CommonRequestModel {
    @SerializedName("qrRefNo")
    String qrRefNo ;

    @SerializedName("amount")
    String amount;

    public static PaymentConfirmRequestModel requestModel;
    public static PaymentConfirmRequestModel newInstance(Context context){
        requestModel = new PaymentConfirmRequestModel();
        requestModel.initialCommonData(context);

        return requestModel;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQrRefNo() {
        return qrRefNo;
    }

    public void setQrRefNo(String qrRefNo) {
        this.qrRefNo = qrRefNo;
    }
}
