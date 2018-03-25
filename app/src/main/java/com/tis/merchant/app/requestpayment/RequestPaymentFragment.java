package com.tis.merchant.app.requestpayment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.FragmentRequestPaymentBinding;
import com.tis.merchant.app.login.HomeActivity;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.PaymentConfirmRequestModel;
import com.tis.merchant.app.network.responseModel.PaymentConfirmResponseModel;
import com.tis.merchant.app.utils.FormatUtility;
import com.tis.merchant.app.utils.UtilView;
import com.tis.merchant.app.utils.Utils;
import com.tis.merchant.app.views.CustomActionBar;
import com.tis.merchant.app.views.NumberTextWatcherForThousand;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class RequestPaymentFragment extends BaseFragment implements View.OnClickListener {

    FragmentRequestPaymentBinding binding;
    Toolbar toolbar;
    CustomActionBar actionBar;
    static RequestPaymentFragment fragment;
    private String consumerName;
    private String qrCodeRef;
    private PaymentConfirmResponseModel responseModel;

    public static RequestPaymentFragment newInstance(String consumerName , String qrCodeRef )
    {

        Bundle args = new Bundle();
        fragment = new RequestPaymentFragment();
        fragment.consumerName = consumerName;
        fragment.qrCodeRef = qrCodeRef;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_payment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolBar();
        binding.btnConfirm.setEnabled(false);
        binding.btnConfirm.setAlpha(0.5f);

        binding.tvName.setText(consumerName);
        binding.cardview.setClipToOutline(true);
//        binding.etAmount.getBackground().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorTisPurple), PorterDuff.Mode.SRC_IN);
        binding.btnConfirm.setOnClickListener(this);
        binding.etAmount.addTextChangedListener(new NumberTextWatcherForThousand(binding.etAmount,binding.btnConfirm));







    }





    public void initToolBar()
    {

        toolbar = binding.included.toolbar ;
        ((RequestPaymentActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar = new CustomActionBar(getActivity(), ((RequestPaymentActivity)getActivity()).getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled();

        Drawable backArrow = ContextCompat.getDrawable(getActivity(),R.drawable.ic_arrow_back_white_24dp);
        backArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        ((RequestPaymentActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(backArrow);

        actionBar.setColorBar(ContextCompat.getColor( getActivity() ,R.color.fontColorWhite));
        actionBar.setColorTitle(ContextCompat.getColor( getActivity() ,R.color.colorPrimary));
        actionBar.setTitle(getString(R.string.label_request_payment));

        ((RequestPaymentActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor( getActivity() ,R.color.fontColorWhite)));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                callServicePaymentConfirm();
                break;

        }
    }



    private void callServicePaymentConfirm()
    {

        Utils.showProgressDialog(getActivity());
        final PaymentConfirmRequestModel requestModel = PaymentConfirmRequestModel.newInstance(getActivity());
        requestModel.setQrRefNo(qrCodeRef);
        requestModel.setAmount(binding.etAmount.getText().toString().replace(",",""));


        TIS_SERVICE.PaymentConfirm(getActivity(), new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {
                Utils.dismissDialog();
                responseModel = (PaymentConfirmResponseModel) response;
                success();

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
                        fail();
                    }
                });
            }

            @Override
            public void serviceDidTimeout(CommonResponseModel response) {
                Utils.dismissDialog();
                ((RequestPaymentActivity)getActivity()).sessionTimeout();

            }
        }, requestModel);

    }



    public void success()
    {

        binding.btnConfirm.setEnabled(false);
        ((RequestPaymentActivity) getActivity()).getSupportActionBar().hide();
        View inflatedView = binding.viewStubTransactionComplete.getViewStub().inflate();
        UtilView.showElements(inflatedView);

        TextView tvTitle = (TextView) inflatedView.findViewById(R.id.tvTitle);
        TextView tvTransactionNo = (TextView) inflatedView.findViewById(R.id.tvTransactionNo);
        TextView tvName = (TextView) inflatedView.findViewById(R.id.tvCustomerName);
        TextView tvTotalAmount = (TextView) inflatedView.findViewById(R.id.tvTotalAmount);
        TextView tvDate = (TextView) inflatedView.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) inflatedView.findViewById(R.id.tvTime);





        tvName.setText( responseModel.getConsumerName() );
        tvDate.setText( responseModel.getTransDate());
        tvTime.setText( responseModel.getTransTime());
        tvTransactionNo.setText( responseModel.getTransNo() );
        tvTotalAmount.setText(FormatUtility.currencyFormat(binding.etAmount.getText().toString().replace(",",""))+" "+getString(R.string.label_currency_japan) );
        tvTitle.setText(getString(R.string.label_you_have_received)+" "+ FormatUtility.currencyFormat(binding.etAmount.getText().toString().replace(",",""))
        +" "+getString(R.string.label_currency_japan)+"\n"+getString(R.string.label_pay_from)+" "+responseModel.getConsumerName() );



        Button btnOk = (Button) inflatedView.findViewById(R.id.btnBackToHome);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity() , HomeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                getActivity().finish();


            }
        });
    }


    public void fail()
    {

        binding.btnConfirm.setEnabled(false);
        ((RequestPaymentActivity) getActivity()).getSupportActionBar().hide();
        View inflatedView = binding.viewStubTransactionFail.getViewStub().inflate();
        UtilView.showElements(inflatedView);


        Button btnOk = (Button) inflatedView.findViewById(R.id.btnBackToHome);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , HomeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                getActivity().finish();


            }
        });
    }

}
