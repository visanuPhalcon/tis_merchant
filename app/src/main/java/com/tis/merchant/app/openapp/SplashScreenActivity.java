package com.tis.merchant.app.openapp;

import com.tis.merchant.app.BuildConfig;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.activity.TermConditionActivity;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.databinding.ActivitySplashScreenBinding;
import com.tis.merchant.app.login.LoginActivity;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.UpdataeVersionRequestModel;
import com.tis.merchant.app.network.responseModel.UpdateVersionResponseModel;
import com.tis.merchant.app.utils.Utils;

public class SplashScreenActivity extends BaseActivity {

    ActivitySplashScreenBinding binding;
    SharedPreferences sharedPreferences;
    UpdateVersionResponseModel responseModel;
    private static final int READ_PHONE_STATE_REQUEST_CODE = 11110;
    String versionName = BuildConfig.VERSION_NAME;


    @Override
    protected void onResume() {
        super.onResume();
//        GotoLogin();
        networkAvailable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashScreenActivity.this,R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

    private void networkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            methodRequestPermission();
        else
        {
            Utils.showMessageDialogOneButton(this, getString(R.string.message_no_internet),
                    "Network is not available. please try again later",
                    getString(R.string.ok_upper), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    });
        }

    }


    private boolean isFirstTime()
    {
        sharedPreferences = getSharedPreferences(getString(R.string.label_preference_first_time), MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.label_preference_first_time), true);
    }

    public void GotoLogin()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (isFirstTime())
                {
                    Intent intent = new Intent(SplashScreenActivity.this, TermConditionActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }




    private void callServiceUpdateVersion()
    {
        Utils.showProgressDialog(SplashScreenActivity.this);

        final UpdataeVersionRequestModel requestModel = UpdataeVersionRequestModel.newInstance(SplashScreenActivity.this);
        requestModel.setVersion(versionName);


        TIS_SERVICE.UpdateVersion(SplashScreenActivity.this, new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {
                Utils.dismissDialog();
                responseModel = (UpdateVersionResponseModel) response;

                if (responseModel.getUpdateFlag().equals("NOUPDATE"))
                {
                            GotoLogin();
                }
                else if (responseModel.getUpdateFlag().equals("NOFORCE"))
                {
                    Utils.showAlertDialogTwoButton(SplashScreenActivity.this,
                            getString(R.string.update_version_title),
                            getString(R.string.update_version_noforce),
                            getString(R.string.label_update),
                            getString(R.string.label_decline),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {

                                    goToAppStore();
                                }
                            },
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
//                                    checkUsernameAndStatus(responseModel);
                                }
                            });
                }
                else if (responseModel.getUpdateFlag().equals("FORCE"))
                {
                    Utils.showMessageDialogOneButton(SplashScreenActivity.this,
                            getString(R.string.update_version_title),
                            getString(R.string.update_version_force),
                            getString(R.string.label_update),
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {

                                    goToAppStore();
                                }
                            });
                }
            }

            @Override
            public void serviceDidFail(CommonResponseModel response)
            {
                Utils.dismissDialog();
                Utils.showErrorDialog(SplashScreenActivity.this, response.responseMessage, response.responseCode, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        SplashScreenActivity.this.finishAffinity();
                    }
                });
            }

            @Override
            public void serviceDidTimeout(CommonResponseModel response) {
                Utils.dismissDialog();
            }
        }, requestModel);

    }




    private void goToAppStore()
    {

        final String appPackageName = SplashScreenActivity.this.getPackageName(); // getPackageName() from Context or Activity object
        try
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(responseModel.getUrl() + appPackageName)));
        }
    }



    private void methodRequestPermission()
    {

        if (ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(SplashScreenActivity.this, Manifest.permission.READ_PHONE_STATE))
            {
                ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);

                return;
            }
            ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);

            return;
        }

        callServiceUpdateVersion();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {


        switch (requestCode)
        {
            case READ_PHONE_STATE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callServiceUpdateVersion();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
