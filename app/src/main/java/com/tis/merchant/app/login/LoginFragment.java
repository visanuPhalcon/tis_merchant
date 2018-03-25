package com.tis.merchant.app.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.databinding.FragmentLoginBinding;
import com.tis.merchant.app.models.SingleTon.Singleton;
import com.tis.merchant.app.models.SingleTon.UserInformation;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.LoginUserNameRequestModel;
import com.tis.merchant.app.network.responseModel.LoginUserNameResponseModel;
import com.tis.merchant.app.utils.Utils;

import java.util.regex.Pattern;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class LoginFragment extends Fragment implements View.OnClickListener , TextWatcher {

    FragmentLoginBinding binding;
    private Pattern pattern = Pattern.compile("^[a-zA-Z0-9 -]{1,20}$");
    LoginUserNameResponseModel responseModel;


    @Override
    public void onResume()
    {
        super.onResume();

    }

    public static LoginFragment newInstance()
    {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.etUsername.addTextChangedListener(this);
        binding.etPassword.addTextChangedListener(this);

//        binding.btnLogin.setEnabled(false);
//        binding.btnLogin.setAlpha(0.5f);
        binding.etUsername.setText("merchant8");
        binding.etPassword.setText("12345");


        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_login:
                callServiceLogin();
                break;
        }
    }



    private void callServiceLogin()
    {
        Utils.showProgressDialog(getActivity());
        final LoginUserNameRequestModel requestModel = LoginUserNameRequestModel.newInstance(getActivity());
        requestModel.setUserName(binding.etUsername.getText().toString());
        requestModel.setPassword(binding.etPassword.getText().toString());


        //        requestModel.setUsernameFromEditText(binding.etUsername.getText().toString());
//        requestModel.setPushToken("12wsdswd");
        //        requestModel.setPushToken(FirebaseInstanceId.getInstance().getToken());


        TIS_SERVICE.LoginUsername(getActivity(), new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {

                Utils.dismissDialog();
                responseModel = (LoginUserNameResponseModel) response;


                UserInformation.getInstance().setName(responseModel.getName());
                UserInformation.getInstance().setMerchantNo(responseModel.getMerchantNo());
                UserInformation.getInstance().setLastLogin(responseModel.getLastLogin());


                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                getActivity().finish();

            }

            @Override
            public void serviceDidFail(CommonResponseModel response)
            {
                Utils.dismissDialog();
                Utils.showErrorDialog(getActivity(), response.responseMessage, response.responseCode, new DialogInterface.OnClickListener()
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
                ((LoginActivity)getActivity()).sessionTimeout();

//                Utils.dismissDialog();
            }
        }, requestModel);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s)
    {

        binding.btnLogin.setEnabled( validateInput() );

        if(binding.btnLogin.isEnabled()==true)
            binding.btnLogin.setAlpha(1f);
        else
            binding.btnLogin.setAlpha(0.5f);


    }

    public boolean validateInput()
    {


        return  pattern.matcher(binding.etUsername.getText().toString()).find()  &&
                pattern.matcher(binding.etPassword.getText().toString()).find()  &&
                ( binding.etUsername.length()>=4 && binding.etPassword.length()>=4 );
    }


}
