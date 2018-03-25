package com.tis.merchant.app.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by prewsitthirat on 7/31/2017 AD.
 */

@Parcel
public class PaymentHistoryModel {

    private List<HistoryModel> historyModels;

    public List<HistoryModel> getHistoryModels() {
        return historyModels;
    }

    public PaymentHistoryModel setHistoryModels(List<HistoryModel> historyModels) {
        this.historyModels = historyModels;
        return this;
    }
}
