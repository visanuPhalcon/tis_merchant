package com.tis.merchant.app.network;

import android.app.Activity;

import com.promptnow.susanoo.PromptNowAsyncService;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.utility.UtilLog;
import com.tis.merchant.app.network.requestModel.LoginUserNameRequestModel;
import com.tis.merchant.app.network.requestModel.LogoutRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentConfirmRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentHistoryRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentReqRequestModel;
import com.tis.merchant.app.network.requestModel.UpdataeVersionRequestModel;
import com.tis.merchant.app.network.responseModel.LoginUserNameResponseModel;
import com.tis.merchant.app.network.responseModel.LogoutResponseModel;
import com.tis.merchant.app.network.responseModel.PaymentConfirmResponseModel;
import com.tis.merchant.app.network.responseModel.PaymentHistoryResponseModel;
import com.tis.merchant.app.network.responseModel.PaymentReqResponseModel;
import com.tis.merchant.app.network.responseModel.UpdateVersionResponseModel;


//import com.promptnow.susanoox.kapao.network.requestModel.TimeoutRequestModel;
//import com.promptnow.susanoox.kapao.network.responseModel.TimeoutResponseModel;

/**
 * Created by Nanthakorn on 12/29/2016.
 */

public class TIS_SERVICE {


    ///////////////////// Update Version /////////////////////////
    public static PromptNowAsyncService UpdateVersion(Activity activity, iServiceEventHandler eventHandler, UpdataeVersionRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServiceUpdateVersion(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServiceUpdateVersion(Activity activity, iServiceEventHandler eventHandler, UpdataeVersionRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<UpdataeVersionRequestModel, UpdateVersionResponseModel>(activity, ServiceParam.OA01.getServiceName(), eventHandler, requestModel, UpdataeVersionRequestModel.class, UpdateVersionResponseModel.class);
            service.setSecure(ServiceParam.OA01.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }


    ///////////////////// Login Username /////////////////////////
    public static PromptNowAsyncService LoginUsername(Activity activity, iServiceEventHandler eventHandler, LoginUserNameRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServiceLoginUsername(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServiceLoginUsername(Activity activity, iServiceEventHandler eventHandler, LoginUserNameRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<LoginUserNameRequestModel, LoginUserNameResponseModel>(activity, ServiceParam.OA02.getServiceName(), eventHandler, requestModel, LoginUserNameRequestModel.class, LoginUserNameResponseModel.class);
            service.setSecure(ServiceParam.OA02.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }



    ///////////////////// logout ////////////////////////
    public static PromptNowAsyncService Logout(Activity activity, iServiceEventHandler eventHandler, LogoutRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServiceLogout(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServiceLogout(Activity activity, iServiceEventHandler eventHandler, LogoutRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<LogoutRequestModel, LogoutResponseModel>(activity, ServiceParam.OA03.getServiceName(), eventHandler, requestModel, LogoutRequestModel.class, LogoutResponseModel.class);
            service.setSecure(ServiceParam.OA03.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }




    ///////////////////// Payment Request ////////////////////////
    public static PromptNowAsyncService PaymentRequest(Activity activity, iServiceEventHandler eventHandler, PaymentReqRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServicePaymentRequest(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServicePaymentRequest(Activity activity, iServiceEventHandler eventHandler, PaymentReqRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<PaymentReqRequestModel, PaymentReqResponseModel>(activity, ServiceParam.WL01.getServiceName(), eventHandler, requestModel, PaymentReqRequestModel.class, PaymentReqResponseModel.class);
            service.setSecure(ServiceParam.WL01.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }




    ///////////////////// Payment confirm ////////////////////////
    public static PromptNowAsyncService PaymentConfirm(Activity activity, iServiceEventHandler eventHandler, PaymentConfirmRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServicePaymentConfirm(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServicePaymentConfirm(Activity activity, iServiceEventHandler eventHandler, PaymentConfirmRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<PaymentConfirmRequestModel, PaymentConfirmResponseModel>(activity, ServiceParam.WL02.getServiceName(), eventHandler, requestModel, PaymentConfirmRequestModel.class, PaymentConfirmResponseModel.class);
            service.setSecure(ServiceParam.WL02.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }



    ///////////////////// Payment History ////////////////////////
    public static PromptNowAsyncService PaymentHistory(Activity activity, iServiceEventHandler eventHandler, PaymentHistoryRequestModel requestModel) {
        return PromptNowAsyncService.executeService(getServicePaymentHistory(activity, eventHandler, requestModel));
    }

    private static PromptNowAsyncService<?, ?> getServicePaymentHistory(Activity activity, iServiceEventHandler eventHandler, PaymentHistoryRequestModel requestModel)
    {
        PromptNowAsyncService<?, ?> service = null;
        try
        {
            service = new PromptNowAsyncService<PaymentHistoryRequestModel, PaymentHistoryResponseModel>(activity, ServiceParam.WL03.getServiceName(), eventHandler, requestModel, PaymentHistoryRequestModel.class, PaymentHistoryResponseModel.class);
            service.setSecure(ServiceParam.WL03.isSecure());
        }
        catch (Exception e)
        {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        return service;
    }















}
