package com.hitherejoe.mondroid.ui.main;

import com.hitherejoe.mondroid.test.common.TestDataFactory;
import com.hitherejoe.mondroid.data.BusEvent;
import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.util.RxEventBus;
import com.hitherejoe.mondroid.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import rx.Completable;
import rx.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mMainPresenter;
    private RxEventBus mEventBus;

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mEventBus = new RxEventBus();
        mMainPresenter = new MainPresenter(mMockDataManager, mEventBus);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void detachView() {
        mMainPresenter.detachView();
    }

    @Test
    public void getAccountDataSucceedsAndShowsAccountData() {
        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), TestDataFactory.makeTransactions(5)
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        mMainPresenter.getAccountData();

        verify(mMockMainMvpView).showProgress(true);
        verify(mMockMainMvpView).showBalance(accountData.balance);
        verify(mMockMainMvpView).showTransactions(accountData.transactions);
        verify(mMockMainMvpView).showProgress(false);
    }

    @Test
    public void getAccountDataWithEmptyTransactionsShowsNoTransactionsMessage() {
        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), Collections.<Transaction>emptyList()
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        mMainPresenter.getAccountData();

        verify(mMockMainMvpView).showProgress(true);
        verify(mMockMainMvpView).showBalance(accountData.balance);
        verify(mMockMainMvpView).showEmptyTransactions();
        verify(mMockMainMvpView).showProgress(false);
    }

    @Test
    public void getAccountDataFailsAndShowsErrorMessage() {
        stubDataManagerGetAccountDetails(Single.<DataManager.AccountData>error(new RuntimeException()));
        mMainPresenter.getAccountData();

        verify(mMockMainMvpView).showProgress(true);
        verify(mMockMainMvpView).showAccountError();
        verify(mMockMainMvpView, never()).showEmptyTransactions();
        verify(mMockMainMvpView, never()).showBalance(any(Balance.class));
        verify(mMockMainMvpView, never()).showTransactions(anyListOf(Transaction.class));
        verify(mMockMainMvpView).showProgress(false);
    }

    @Test
    public void signOutLaunchesWelcomeActivity() {
        stubDataManagerSignOut();
        mMainPresenter.signOut();

        verify(mMockMainMvpView).launchWelcomeActivity();
    }

    @Test
    public void receivingAuthenticationErrorEventLaunchesWelcomeActivity() {
        mEventBus.post(new BusEvent.AuthenticationError());

        verify(mMockMainMvpView).launchWelcomeActivity();
    }

    private void stubDataManagerGetAccountDetails(Single<DataManager.AccountData> single) {
        when(mMockDataManager.getAccountDetails())
                .thenReturn(single);
    }

    private void stubDataManagerSignOut() {
        when(mMockDataManager.signOut())
                .thenReturn(Completable.complete());
    }

}