package com.tis.merchant.app.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by prewsitthirat on 7/31/2017 AD.
 */

@Parcel
public class HistoryModel {

    @SerializedName("amount")
    public String amount;

    @SerializedName("transDatetime")
    private String date;

    private String time;

    @SerializedName("consumerName")
    String name;

    @SerializedName("transNo")
    String transactionRef;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getAmount() {
        return amount;
    }

    public HistoryModel setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getDate() {
        return date;
    }

    public HistoryModel setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public HistoryModel setTime(String time) {
        this.time = time;
        return this;
    }
}
