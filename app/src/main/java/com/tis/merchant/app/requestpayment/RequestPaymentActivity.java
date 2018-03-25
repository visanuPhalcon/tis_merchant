package com.tis.merchant.app.requestpayment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.databinding.ActivityRequestPaymentBinding;
import com.tis.merchant.app.views.CustomActionBar;

public class RequestPaymentActivity extends BaseActivity {

    Toolbar toolbar;
    public CustomActionBar actionBar;
    ActivityRequestPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_request_payment);
        binding = DataBindingUtil.setContentView(RequestPaymentActivity.this,R.layout.activity_request_payment);
        replaceFragment(RequestQrFragment.newInstance(),"RequestPaymentFragment");
    }



}
