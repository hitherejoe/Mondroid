package com.hitherejoe.mondo.ui.transactions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitherejoe.mondo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionsFragment extends Fragment implements TransactionsMvpView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        ButterKnife.bind(view);
        return view;
    }

}