package com.tis.merchant.app.paymenthistory;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.databinding.ActivityPaymentHistoryBinding;
import com.tis.merchant.app.views.CustomActionBar;

public class PaymentHistoryActivity extends BaseActivity {

    Toolbar toolbar;
    public CustomActionBar actionBar;
    ActivityPaymentHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment_history);
        binding = DataBindingUtil.setContentView(PaymentHistoryActivity.this,R.layout.activity_payment_history);

        toolbar = binding.included.toolbar ;
        setSupportActionBar(toolbar);
        actionBar = new CustomActionBar(this, getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled();
        replaceFragment(PaymentHistoryFragment.newInstance(),"PaymentHistoryFragment");
    }
}
