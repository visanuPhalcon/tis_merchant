package com.tis.merchant.app.network.requestModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonRequestModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class UpdataeVersionRequestModel extends CommonRequestModel {

    @SerializedName("version")
    String version;

    public static UpdataeVersionRequestModel requestModel;
    public static UpdataeVersionRequestModel newInstance(Context context){
        requestModel = new UpdataeVersionRequestModel();
        requestModel.initialCommonData(context);

        return requestModel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
