package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonResponseModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class PaymentConfirmResponseModel extends CommonResponseModel {

    @SerializedName("transNo")
    String transNo;
    @SerializedName("transDate")
    String transDate;
    @SerializedName("transTime")
    String transTime;
    @SerializedName("cardNo")
    String cardNo;
    @SerializedName("consumerName")
    String consumerName;
    @SerializedName("amount")
    String amount;

    public static PaymentConfirmResponseModel responseModel;
    public static PaymentConfirmResponseModel getInstance(Context context)
    {
        responseModel = new PaymentConfirmResponseModel();
        return responseModel;
    }


    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
