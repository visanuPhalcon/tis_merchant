package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.models.HistoryModel;

import java.util.ArrayList;

/**
 * Created by Admin on 28/8/2560.
 */

public class PaymentHistoryResponseModel extends CommonResponseModel {

    @SerializedName("index")
    String index;

    @SerializedName("transList")
    ArrayList<HistoryModel> transList = new ArrayList<HistoryModel>();

    public static PaymentHistoryResponseModel responseModel;

    public static PaymentHistoryResponseModel getResponseModel() {
        return responseModel;
    }

    public static void setResponseModel(PaymentHistoryResponseModel responseModel) {
        PaymentHistoryResponseModel.responseModel = responseModel;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public ArrayList<HistoryModel> getTransList() {
        return transList;
    }

    public void setTransList(ArrayList<HistoryModel> transList) {
        this.transList = transList;
    }
}
