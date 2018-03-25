package com.tis.merchant.app.network;

import android.content.DialogInterface;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.utils.Utils;

/**
 * Created by Nanthakorn on 6/20/2017.
 */

public abstract class ServiceEventHandler implements iServiceEventHandler {
    private BaseActivity activity;

    public ServiceEventHandler(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void serviceDidFinish(CommonResponseModel commonResponseModel) {

    }

    @Override
    public void serviceDidFail(CommonResponseModel commonResponseModel) {

    }

    @Override
    public void serviceDidTimeout(CommonResponseModel commonResponseModel) {
        Utils.dismissDialog();
        if (activity != null) {
            if (commonResponseModel.responseCode.equals("TO01")) {
                Utils.showErrorDialog(activity,
                        commonResponseModel.responseMessage,
                        commonResponseModel.responseCode, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                activity.doTimeout();
                            }
                        });
            } else {
                Utils.showErrorDialog(activity,
                        commonResponseModel.responseMessage,
                        commonResponseModel.responseCode, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
            }
        }
    }
}
