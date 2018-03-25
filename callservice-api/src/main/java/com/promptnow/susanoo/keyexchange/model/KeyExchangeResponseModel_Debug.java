// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 20/6/2014 18:03:59
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   KeyexchangeResponse.java

package com.promptnow.susanoo.keyexchange.model;

import com.google.gson.annotations.SerializedName;

public class KeyExchangeResponseModel_Debug extends KeyExchangeResponseModel
{

    public KeyExchangeResponseModel_Debug(String status, String code, String msg)
    {
        super(status, code, msg);
    }

    @SerializedName("secret-key")
    public String secret_key;
    @SerializedName("enc-key")
    public String enc_key;
    @SerializedName("dec-key")
    public String dec_key;
    
}