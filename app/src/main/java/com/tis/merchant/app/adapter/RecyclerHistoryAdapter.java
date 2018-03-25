package com.tis.merchant.app.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tis.merchant.app.R;
import com.tis.merchant.app.databinding.ItemHistoryBinding;
import com.tis.merchant.app.models.HistoryModel;
import com.tis.merchant.app.models.PaymentHistoryModel;
import com.tis.merchant.app.network.responseModel.PaymentHistoryResponseModel;
import com.tis.merchant.app.utils.FormatUtility;

/**
 * Created by prewsitthirat on 7/31/2017 AD.
 */

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.HistoryViewHolder> {

    Context context;
    ItemHistoryBinding binding;
    PaymentHistoryResponseModel paymentHistoryModel;


    public RecyclerHistoryAdapter(PaymentHistoryResponseModel paymentHistoryModel , Context context )
    {
        this.paymentHistoryModel = paymentHistoryModel;
        this.context = context ;
    }

    public interface AdapterListener {
        void onItemClickListener(HistoryModel historyModel);
    }

    AdapterListener listener;

    public void setOnItemClickListener(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_history,parent,false);
        return new HistoryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.setDataItem(position);
    }

    @Override
    public int getItemCount()
    {
        if (paymentHistoryModel == null) {
            return 0;
        }

        return paymentHistoryModel.getTransList().size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder
    {

        public HistoryViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClickListener(paymentHistoryModel.getTransList().get(getAdapterPosition()));
//                }
//            });
        }

        public void setDataItem(int position)
        {
//            Log.e("jay", "getName: "+paymentHistoryModel.getTransList().get(position).getName() );

            Log.e("setDataItem", "getAmount: "+ paymentHistoryModel.getTransList().get(position).getAmount() );

            binding.tvMoney.setText(FormatUtility.currencyFormat(paymentHistoryModel.getTransList().get(position).getAmount()));
//            binding.tvMoney.setText(FormatUtility.currencyFormat("4000"));
            binding.tvName.setText(paymentHistoryModel.getTransList().get(position).getName());
            binding.tvStatus.setText(R.string.label_pay_from);
            binding.tvTransactionRef.setText(context.getString(R.string.label_transaction_ref)+" "+paymentHistoryModel.getTransList().get(position).getTransactionRef());
            binding.tvDate.setText(context.getString(R.string.date)+" "+paymentHistoryModel.getTransList().get(position).getDate());
        }
    }
}
