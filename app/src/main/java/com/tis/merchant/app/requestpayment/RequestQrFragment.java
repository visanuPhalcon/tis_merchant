package com.tis.merchant.app.requestpayment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.FragmentRequestQrBinding;
import com.tis.merchant.app.databinding.FragmentTemplateBinding;
import com.tis.merchant.app.login.HomeActivity;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.LoginUserNameRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentHistoryRequestModel;
import com.tis.merchant.app.network.requestModel.PaymentReqRequestModel;
import com.tis.merchant.app.network.responseModel.PaymentReqResponseModel;
import com.tis.merchant.app.utils.QRCodeGenerator;
import com.tis.merchant.app.utils.Utils;
import com.tis.merchant.app.views.CustomActionBar;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class RequestQrFragment extends BaseFragment implements View.OnClickListener {

    final static String TAG = "Prew";
    FragmentRequestQrBinding binding;
    static RequestQrFragment fragment;
    Handler handler = new Handler();
    PaymentReqResponseModel responseModel;
    PaymentReqRequestModel requestModel ;
    String qrCode ;


    public static RequestQrFragment newInstance()
    {

        Bundle args = new Bundle();
        
        fragment = new RequestQrFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_qr, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initToolBar();
        scanQR();


//        callServicePaymentRequest();



    }


    public void initToolBar()
    {
        Toolbar toolbar;
        CustomActionBar actionBar;
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

    public void scanQR()
    {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setTimeout(8000);
        integrator.setBarcodeImageEnabled(true);
        integrator.forSupportFragment(this).initiateScan();
    }


    private void callServicePaymentRequest()
    {
        Utils.showProgressDialog(getActivity());
        requestModel = PaymentReqRequestModel.newInstance(getActivity());
        requestModel.setQrRefNo(qrCode);


        TIS_SERVICE.PaymentRequest(getActivity(), new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {
                Utils.dismissDialog();

                if(response!=null) {
                    responseModel = (PaymentReqResponseModel) response;
                    transactionComplete();
                }



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
                        getActivity().finish();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                getActivity().finish();
//                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.e(TAG, "onActivityResult: " );
                qrCode=result.getContents() ; // Whatever you need to encode in the QR code
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try
                {
                    BitMatrix bitMatrix = multiFormatWriter.encode(qrCode, BarcodeFormat.QR_CODE,1200,1200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    binding.imageQr.setImageBitmap(bitmap);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callServicePaymentRequest();
                        }
                    }, 1000);


                }
                catch (WriterException e)
                {
                    e.printStackTrace();
                }
            }
        } else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void transactionComplete()
    {
        boolean status = false;
        ((RequestPaymentActivity) getActivity()).getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ((RequestPaymentActivity) getActivity())
                .replaceFragment(RequestPaymentFragment.newInstance(responseModel.getConsumerName(),requestModel.getQrRefNo()), "RequestPaymentFragment");
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_cancel:
//                getActivity().onBackPressed();
//                break;
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
