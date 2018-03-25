package com.tis.merchant.app.network;

/**
 * Created by DARM on 4/21/2016.
 */
public enum ServiceParam {

    OA01("authen/update-version",true),
    OA02("authen/login-username",true),
    OA03("authen/logout",true),

    WL01("wallet/payment-request",true),
    WL02("wallet/payment-confirm",true),
    WL03("wallet/payment-history",true);

    private String name;
    private boolean isSecure;

    ServiceParam(String name, boolean isSecure) {
        this.name = name;
        this.isSecure = isSecure;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public String getServiceName() {
        return name;
    }
}
