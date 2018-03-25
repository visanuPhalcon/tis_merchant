// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   KeyexchangeResponse.java

package com.promptnow.susanoo.keyexchange.model;

import com.promptnow.susanoo.model.CommonResponseModel;
import com.google.gson.annotations.SerializedName;

public class KeyExchangeResponseModel extends CommonResponseModel
{

    public KeyExchangeResponseModel(String status, String code, String msg) {
        super(status, code, msg);
        identify = "";
        independent = "";
    }

    @SerializedName("public-key")
    public String publicKey;
    @SerializedName("identify")
    public String identify;
    @SerializedName("independent")
    public String independent;
}