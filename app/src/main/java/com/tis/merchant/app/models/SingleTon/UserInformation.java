package com.tis.merchant.app.models.SingleTon;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 1/9/2560.
 */

public class UserInformation {

    public static UserInformation user;

    @SerializedName("password")
    private static String password ;
//    @SerializedName("pushToken")
//    private static String pushToken;

    @SerializedName("name")
    private static String name;
    @SerializedName("merchantNo")
    private static String merchantNo;

    @SerializedName("lastLogin")
    String lastLogin ;


    public static UserInformation getInstance()
    {
        if (user == null)
        {
            user = new UserInformation();
        }
        return user;
    }


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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getPushToken() {
//        return pushToken;
//    }
//
//    public void setPushToken(String pushToken) {
//        this.pushToken = pushToken;
//    }
}
