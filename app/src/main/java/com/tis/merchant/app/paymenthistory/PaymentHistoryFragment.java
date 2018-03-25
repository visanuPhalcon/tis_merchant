package com.tis.merchant.app.paymenthistory;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.promptnow.susanoo.handler.iServiceEventHandler;
import com.promptnow.susanoo.model.CommonResponseModel;
import com.tis.merchant.app.R;
import com.tis.merchant.app.adapter.RecyclerHistoryAdapter;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.FragmentPaymentHistoryBinding;
import com.tis.merchant.app.login.HomeActivity;
import com.tis.merchant.app.models.HistoryModel;
import com.tis.merchant.app.models.PaymentHistoryModel;
import com.tis.merchant.app.network.TIS_SERVICE;
import com.tis.merchant.app.network.requestModel.PaymentHistoryRequestModel;
import com.tis.merchant.app.network.responseModel.PaymentHistoryResponseModel;
import com.tis.merchant.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class PaymentHistoryFragment extends BaseFragment {

    FragmentPaymentHistoryBinding binding;
    RecyclerHistoryAdapter adapter;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    PaymentHistoryResponseModel responseModel = new PaymentHistoryResponseModel();
    LinearLayoutManager layoutManager;
    int index = 0;
    boolean loadMore = true;




    public static PaymentHistoryFragment newInstance() {

        Bundle args = new Bundle();
        PaymentHistoryFragment fragment = new PaymentHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_history, container, false);
        Drawable backArrow = ContextCompat.getDrawable(getActivity(),R.drawable.ic_arrow_back_white_24dp);
        backArrow.setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        ((PaymentHistoryActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(backArrow);

        ((PaymentHistoryActivity) getActivity()).actionBar.setColorBar(ContextCompat.getColor( getActivity() ,R.color.fontColorWhite));
        ((PaymentHistoryActivity) getActivity()).actionBar.setColorTitle(ContextCompat.getColor( getActivity() ,R.color.colorPrimary));
        ((PaymentHistoryActivity) getActivity()).actionBar.setTitle(getString(R.string.payment_history));

        ((PaymentHistoryActivity) getActivity()).actionBar.setColorBar(ContextCompat.getColor( getActivity() ,R.color.fontColorWhite));
        ((PaymentHistoryActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor( getActivity() ,R.color.fontColorWhite)));

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerHistory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.recyclerHistory.setLayoutManager(layoutManager);
        initInstance();
        callServicePaymentHistory(index);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }


    public void initInstance()
    {
        binding.btnLoadMore.setVisibility(View.GONE);
        binding.btnLoadMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(loadMore==true)
                {
                        index++;
                        callServicePaymentHistory(index);


                }
            }
        });


//        binding.recyclerHistory.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
//            {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//            {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if( !binding.recyclerHistory.canScrollVertically(1) )
//                {
//                    visibleItemCount = binding.recyclerHistory.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//
//
//                    if(loadMore==true)
//                    {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            Log.e("jay", "reach bottom: ");
//                            index++;
//                            callServicePaymentHistory(index);
//
//                        }
//                    }
//                }
//
//            }
//        });


    }


    private void callServicePaymentHistory(int index)
    {
        Utils.showProgressDialog(getActivity());
        final PaymentHistoryRequestModel requestModel = PaymentHistoryRequestModel.newInstance(getActivity());
        requestModel.setIndex( String.valueOf(index) );


        TIS_SERVICE.PaymentHistory(getActivity(), new iServiceEventHandler()
        {
            @Override
            public void serviceDidFinish(CommonResponseModel response)
            {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Utils.dismissDialog();
                    }
                }, 1500);

                if(response!=null  )
                {
                    if( ((PaymentHistoryResponseModel) response).getTransList().size()>0 )
                    {
                        binding.btnLoadMore.setVisibility(View.VISIBLE);
                        Log.e("jay", "load more: " );
                        int size = ((PaymentHistoryResponseModel) response).getTransList().size();
                        for (int i = 0; i < size; i++)
                            responseModel.getTransList().add(((PaymentHistoryResponseModel) response).getTransList().get(i));

                        adapter = new RecyclerHistoryAdapter(responseModel, getActivity());
                        binding.recyclerHistory.setAdapter(adapter);
//                        adapter.setOnItemClickListener(new RecyclerHistoryAdapter.AdapterListener() {
//                            @Override
//                            public void onItemClickListener(HistoryModel historyModel) {
//                                ((PaymentHistoryActivity)getActivity()).replaceFragmentWithBackStack(HistoryDetailFragment.newInstance(historyModel),
//                                        "HistoryDetailFragment",null);
//                            }
//                        });
                    }

                    if( responseModel.getTransList().size()==0 )
                        binding.tvHistory.setVisibility(View.VISIBLE);


                    if( ((PaymentHistoryResponseModel) response).getIndex().equals("") )
                    {
                        binding.btnLoadMore.setVisibility(View.GONE);
                        binding.btnLoadMore.setEnabled(false);
                        loadMore = false;
                    }

                    if(loadMore==false && responseModel.getTransList().size()<=10 )
                        binding.btnLoadMore.setVisibility(View.GONE);








                }




                Log.e("jay", "size: "+responseModel.getTransList().size() );


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
                ((PaymentHistoryActivity)getActivity()).sessionTimeout();

            }
        }, requestModel);



    }



}
