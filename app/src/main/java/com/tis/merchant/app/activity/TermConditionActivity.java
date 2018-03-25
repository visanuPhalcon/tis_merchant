package com.tis.merchant.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseActivity;
import com.tis.merchant.app.databinding.ActivityTermConditionBinding;
import com.tis.merchant.app.login.LoginActivity;

public class TermConditionActivity extends BaseActivity implements View.OnClickListener {

    ActivityTermConditionBinding binding;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_term_condition);
        binding = DataBindingUtil.setContentView(TermConditionActivity.this, R.layout.activity_term_condition);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.btnAgree.setOnClickListener(this);
        binding.ivCheckbox.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            ContextCompat.getDrawable(TermConditionActivity.this,R.drawable.checkbox_uncheck).getConstantState()
            case R.id.iv_checkbox:
//                if (binding.ivCheckbox.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.checkbox_uncheck).getConstantState()))
                if (binding.ivCheckbox.getDrawable().getConstantState().equals(ContextCompat.getDrawable(TermConditionActivity.this,R.drawable.checkbox_uncheck).getConstantState()))
                {
                    binding.ivCheckbox.setImageResource(R.drawable.checkbox_check);
                    binding.btnAgree.setAlpha(1);
                    binding.btnAgree.setEnabled(true);
                } else
                    {
                    binding.ivCheckbox.setImageResource(R.drawable.checkbox_uncheck);
                    binding.btnAgree.setAlpha(0.6f);
                    binding.btnAgree.setEnabled(false);
                }
                break;

            case R.id.btn_agree:
                sp = getSharedPreferences(getString(R.string.label_preference_first_time), Context.MODE_PRIVATE);
                editor = sp.edit();
                editor.putBoolean(getString(R.string.label_preference_first_time), false);
                editor.commit();
                Intent intent = new Intent(TermConditionActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                break;
        }
    }


    @Override
    public void onBackPressed(){

    }

}
