package com.tis.merchant.app.template;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tis.merchant.app.R;
import com.tis.merchant.app.base.BaseFragment;
import com.tis.merchant.app.databinding.FragmentTemplateBinding;

/**
 * Created by prewsitthirat on 7/17/2017 AD.
 */

public class FragmentTemplate extends BaseFragment {

    FragmentTemplateBinding binding;

    public static FragmentTemplate newInstance() {

        Bundle args = new Bundle();
        FragmentTemplate fragment = new FragmentTemplate();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
