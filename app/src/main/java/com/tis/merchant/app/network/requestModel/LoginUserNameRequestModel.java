package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class LoginUserNameRequestModel extends CommonRequestModel{

//    @SerializedName("username")
//    String username ;
    @SerializedName("password")
    String password ;
//    @SerializedName("pushToken")
//    String pushToken;

    public static LoginUserNameRequestModel requestModel;
    public static LoginUserNameRequestModel newInstance(Context context){
        requestModel = new LoginUserNameRequestModel();
        requestModel.initialCommonData(context);

        return requestModel;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsernameFromEditText(String username) {
//        this.username = username;
//    }

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
