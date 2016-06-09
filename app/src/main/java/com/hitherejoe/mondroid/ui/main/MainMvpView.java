package com.hitherejoe.mondroid.ui.main;

import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showBalance(Balance balance);

    void showTransactions(List<Transaction> transactions);

    void showEmptyTransactions();

    void showProgress(boolean show);

    void showAccountError();

    void launchWelcomeActivity();
}
