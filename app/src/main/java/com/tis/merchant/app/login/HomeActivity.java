package com.tis.merchant.app.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.ActivityHomeBinding;
import com.tis.merchant.app.models.SingleTon.UserInformation;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.LoginUserNameRequestModel;
import com.tis.merchant.app.network.requestModel.LogoutRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentHistoryRequestModel;
import com.tis.merchant.app.paymenthistory.PaymentHistoryActivity;
import com.tis.merchant.app.requestpayment.RequestPaymentActivity;
import com.tis.merchant.app.utils.Utils;

import org.bouncycastle.jcajce.provider.symmetric.ARC4;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    ActivityHomeBinding binding;
    private static final int MY_CAMERA_REQUEST_CODE = 100;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeActivity.this,R.layout.activity_home);


        binding.btnRequestPayment.setOnClickListener(this);
        binding.btnPaymentHistory.setOnClickListener(this);
        binding.btnLogout.setOnClickListener(this);

        binding.btnRequestPayment.setOnTouchListener(setOnTouchUX(binding.btnRequestPayment,binding.btnPaymentHistory));
        binding.btnPaymentHistory.setOnTouchListener(setOnTouchUX(binding.btnPaymentHistory,binding.btnRequestPayment));
        initView();

    }


    public void initView()
    {
        binding.tvLastLogin.setText(getString(R.string.label_last_login)+" "+ UserInformation.getInstance().getLastLogin());
        binding.tvName.setText(UserInformation.getInstance().getName());
        binding.tvMerchantID.setText(UserInformation.getInstance().getMerchantNo());
    }


    private View.OnTouchListener setOnTouchUX(final View view,final View viewDisable)
    {
        View.OnTouchListener touchListener = new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    view.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.colorTisPurple));
                    binding.frameLayout.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.colorTisPurple));
                    viewDisable.setEnabled(true);
                } else {
                    view.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.colorTisDarkPurple));
                    binding.frameLayout.setBackgroundColor(ContextCompat.getColor(HomeActivity.this,R.color.colorTisDarkPurple));
                    viewDisable.setEnabled(false);
                }
                return false;
            }
        };
        return touchListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_payment:
                requestCameraPermission();
                break;

            case R.id.btn_payment_history:
                Intent historyIntent = new Intent(HomeActivity.this, PaymentHistoryActivity.class);
                startActivity(historyIntent);
                break;

            case R.id.btn_logout:
                Utils.showAlertDialogTwoButton(HomeActivity.this, getResources().getString(R.string.logout),
                        getString(R.string.message_logout),
                        getResources().getString(R.string.logout_upper),
                        getString(R.string.cancel_upper),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                callServiceLogout();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                break;
        }
    }



    public void requestCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.CAMERA))
            {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

                return;
            }
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

            return;
        }

        Intent intent = new Intent(HomeActivity.this, RequestPaymentActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);







    }







    private void callServiceLogout()
    {
        Utils.showProgressDialog(HomeActivity.this);
        final LogoutRequestModel requestModel = LogoutRequestModel.newInstance(HomeActivity.this);


        TIS_SERVICE.Logout(HomeActivity.this, new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {

                Utils.dismissDialog();
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }

            @Override
            public void serviceDidFail(CommonResponseModel response)
            {
                Utils.dismissDialog();
                Utils.showErrorDialog(HomeActivity.this, response.responseMessage, response.responseCode, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
            }

            @Override
            public void serviceDidTimeout(CommonResponseModel response) {
                Utils.dismissDialog();
                sessionTimeout();
            }
        }, requestModel);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {


        switch (requestCode)
        {
            case MY_CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(HomeActivity.this, RequestPaymentActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed()
    {
        Utils.showAlertDialogTwoButton(HomeActivity.this, getResources().getString(R.string.logout),
                getString(R.string.message_logout),
                getResources().getString(R.string.logout_upper),
                getString(R.string.cancel_upper),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callServiceLogout();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }
}
