package com.tis.merchant.app.paymenthistory;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.FragmentHistoryDetailBinding;
import com.tis.merchant.app.models.HistoryModel;

import org.parceler.Parcels;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class HistoryDetailFragment extends BaseFragment implements View.OnClickListener {

    FragmentHistoryDetailBinding binding;
    HistoryModel historyModel;

    public static HistoryDetailFragment newInstance(HistoryModel historyModel) {

        Bundle args = new Bundle();
        HistoryDetailFragment fragment = new HistoryDetailFragment();
        args.putParcelable("historyModel", Parcels.wrap(historyModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyModel = Parcels.unwrap(getArguments().getParcelable("historyModel"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvAmount.setText(String.valueOf(historyModel.getAmount())+" JPY");
//        binding.tvDate.setText(historyModel.getDate());
//        binding.tvTime.setText(historyModel.getTime());

        binding.btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                getActivity().onBackPressed();
                break;
        }
    }
}
