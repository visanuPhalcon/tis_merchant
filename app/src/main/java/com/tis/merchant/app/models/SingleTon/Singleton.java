package com.tis.merchant.app.models.SingleTon;

/**
 * Created by Admin on 1/9/2560.
 */

public class Singleton {

    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    private UserInformation userInformation;

    public static Singleton getOurInstance() {
        return ourInstance;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public Singleton setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
        return this;
    }




}
