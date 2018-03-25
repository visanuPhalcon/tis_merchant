package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 28/8/2560.
 */

public class PaymentHistoryRequestModel extends CommonRequestModel {

    @SerializedName("index")
    String index;

    public static PaymentHistoryRequestModel requestModel;
    public static PaymentHistoryRequestModel newInstance(Context context){
        requestModel = new PaymentHistoryRequestModel();
        requestModel.initialCommonData(context);

        return requestModel;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
