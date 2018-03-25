package com.tis.merchant.app.network.responseModel;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.promptnow.susanoo.model.CommonResponseModel;

/**
 * Created by Admin on 29/8/2560.
 */

public class UpdateVersionResponseModel extends CommonResponseModel {

    @SerializedName("update")
    String updateFlag;
    @SerializedName("newVersion")
    String newVersion;
    @SerializedName("url")
    String url;

    public static UpdateVersionResponseModel responseModel;
    public static UpdateVersionResponseModel getInstance(Context context)
    {
        responseModel = new UpdateVersionResponseModel();
        return responseModel;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public String getUrl() {
        return url;
    }
}
