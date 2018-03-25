package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonResponseModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class LoginUserNameResponseModel extends CommonResponseModel {

    @SerializedName("lastLogin")
    String lastLogin ;
    @SerializedName("name")
    String name;
    @SerializedName("merchantNo")
    String merchantNo;



    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
