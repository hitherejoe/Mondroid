package com.hitherejoe.mondroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitherejoe.mondroid.R;
import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.ui.base.BaseActivity;
import com.hitherejoe.mondroid.ui.main.widget.BalanceView;
import com.hitherejoe.mondroid.ui.welcome.WelcomeActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject DataManager mDataManager;
    @Inject MainPresenter mMainPresenter;
    @Inject TransactionAdapter mTransactionAdapter;

    @Bind(R.id.view_balance) BalanceView mBalanceView;
    @Bind(R.id.button_message) Button mMessageButton;
    @Bind(R.id.progress) ProgressBar mProgress;
    @Bind(R.id.toolbar_main) Toolbar mToolbar;
    @Bind(R.id.recycler_transactions) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.text_message) TextView mMessageText;
    @Bind(R.id.layout_error) View mErrorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);

        setSupportActionBar(mToolbar);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mMainPresenter.getAccountData();
            }
        });

        setupRecyclerView();
        mMainPresenter.getAccountData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mMainPresenter.signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button_message)
    public void onTryAgainButtonClick() {
        mErrorLayout.setVisibility(View.GONE);
        mMainPresenter.getAccountData();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTransactionAdapter);
    }

    @Override
    public void showBalance(Balance balance) {
        mSwipeRefreshLayout.setRefreshing(false);
        mBalanceView.setBalance(balance);
        mBalanceView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        mErrorLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mTransactionAdapter.setTransactions(transactions);
        mTransactionAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyTransactions() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mMessageText.setText(getString(R.string.text_no_transactions));
        mMessageButton.setText(getString(R.string.text_check_again));
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showAccountError() {
        mBalanceView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mMessageText.setText(getString(R.string.text_error_loading_account));
        mMessageButton.setText(getString(R.string.text_try_again));
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void launchWelcomeActivity() {
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        finish();
    }
}