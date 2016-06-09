package com.hitherejoe.mondroid.ui.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hitherejoe.mondroid.R;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.util.DateUtil;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> mTransactions;

    @Inject
    public TransactionAdapter() {
        mTransactions = Collections.emptyList();
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
        Collections.sort(mTransactions);
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Transaction transaction = mTransactions.get(position);
        holder.mObjective = transaction;

        String text = transaction.merchant == null ?
                transaction.description : transaction.merchant.name;
        holder.nameText.setText(text);

        DecimalFormat format = new DecimalFormat("+#,##0.00;-#");
        format.setCurrency(Currency.getInstance(transaction.currency));

        double amount = transaction.amount / (100.0);

        String amountString = format.format(amount);
        holder.amountText.setText(amountString);
        int red = ContextCompat.getColor(holder.itemView.getContext(), R.color.red);
        int green = ContextCompat.getColor(holder.itemView.getContext(), R.color.green);
        holder.amountText.setTextColor(amount < 0 ? red : green);

        if (transaction.created != null) {
            String dateText =
                    DateUtil.formatDate(holder.itemView.getContext(), transaction.created);
            if (dateText != null) {
                holder.dateText.setText(dateText);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_date) TextView dateText;
        @Bind(R.id.text_name) TextView nameText;
        @Bind(R.id.text_amount) TextView amountText;

        public Transaction mObjective;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}