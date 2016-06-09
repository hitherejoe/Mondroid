package com.hitherejoe.mondroid.data;

import com.hitherejoe.mondroid.data.remote.MondroidService;
import com.hitherejoe.mondroid.test.common.TestDataFactory;
import com.hitherejoe.mondroid.data.local.PreferencesHelper;
import com.hitherejoe.mondroid.data.model.Account;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.data.remote.AccessToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    MondroidService mMockMondroidService;
    @Mock PreferencesHelper mMockPreferencesHelper;

    DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockMondroidService, mMockPreferencesHelper);
    }

    @Test
    public void getAccessTokenCompletesAndEmitsAccessToken() {
        AccessToken accessToken = TestDataFactory.makeAccessToken();
        stubMondoServiceGetAccessToken(Single.just(accessToken));

        TestSubscriber<AccessToken> testSubscriber = new TestSubscriber<>();
        mDataManager.getAccessToken("code").subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(accessToken);
    }

    @Test
    public void getAccessTokenSavesAccessTokenInPreferences() {
        AccessToken accessToken = TestDataFactory.makeAccessToken();
        stubMondoServiceGetAccessToken(Single.just(accessToken));

        TestSubscriber<AccessToken> testSubscriber = new TestSubscriber<>();
        mDataManager.getAccessToken("code").subscribe(testSubscriber);

        verify(mMockPreferencesHelper).putAccessToken(accessToken);
    }

    @Test
    public void isUserSignedInReturnsTrue() {
        AccessToken accessToken = TestDataFactory.makeAccessToken();
        stubPreferencesHelperGetAccessToken(accessToken);

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        mDataManager.isUserSignedIn().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(true);
    }

    @Test
    public void isUserSignedInReturnsFalse() {
        stubPreferencesHelperGetAccessToken(null);

        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        mDataManager.isUserSignedIn().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(false);
    }

    @Test
    public void getAccountsCompletesAndEmitsAccounts() {
        stubPreferencesHelperGetAccessToken(TestDataFactory.makeAccessToken());

        List<Account> accounts = TestDataFactory.makeAccounts(5);
        MondroidService.AccountsResponse accountsResponse = new MondroidService.AccountsResponse();
        accountsResponse.accounts = accounts;
        stubMondoServiceGetAccounts(Single.just(accountsResponse));

        TestSubscriber<MondroidService.AccountsResponse> testSubscriber = new TestSubscriber<>();
        mDataManager.getAccounts().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(accountsResponse);
    }

    @Test
    public void getBalanceCompletesAndEmitsBalance() {
        stubPreferencesHelperGetAccessToken(TestDataFactory.makeAccessToken());
        Balance balance = TestDataFactory.makeBalance();
        stubMondoServiceGetBalance(Single.just(balance));

        TestSubscriber<Balance> testSubscriber = new TestSubscriber<>();
        mDataManager.getBalance("account_id").subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(balance);
    }

    @Test
    public void getTransactionsCompletesAndEmitsTransactions() {
        stubPreferencesHelperGetAccessToken(TestDataFactory.makeAccessToken());

        List<Transaction> transactions = TestDataFactory.makeTransactions(5);
        MondroidService.TransactionResponse transactionResponse = new MondroidService.TransactionResponse();
        transactionResponse.transactions = transactions;
        stubMondoServiceGetTransactions(Single.just(transactionResponse));

        TestSubscriber<List<Transaction>> testSubscriber = new TestSubscriber<>();
        mDataManager.getTransactions("account_id").subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(transactions);
    }

    @Test
    public void getAccountDetailsCompletesAndEmitsAccountDetails() {
        stubPreferencesHelperGetAccessToken(TestDataFactory.makeAccessToken());

        List<Account> accounts = TestDataFactory.makeAccounts(1);
        MondroidService.AccountsResponse accountsResponse = new MondroidService.AccountsResponse();
        accountsResponse.accounts = accounts;
        stubMondoServiceGetAccounts(Single.just(accountsResponse));

        Balance balance = TestDataFactory.makeBalance();
        stubMondoServiceGetBalance(Single.just(balance));

        List<Transaction> transactions = TestDataFactory.makeTransactions(5);
        MondroidService.TransactionResponse transactionResponse = new MondroidService.TransactionResponse();
        transactionResponse.transactions = transactions;
        stubMondoServiceGetTransactions(Single.just(transactionResponse));

        DataManager.AccountData accountData = new DataManager.AccountData(balance, transactions);

        TestSubscriber<DataManager.AccountData> testSubscriber = new TestSubscriber<>();
        mDataManager.getAccountDetails().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(accountData);
    }

    private void stubMondoServiceGetAccessToken(Single<AccessToken> single) {
        when(mMockMondroidService.getAccessToken(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(single);
    }

    private void stubMondoServiceGetAccounts(Single<MondroidService.AccountsResponse> single) {
        when(mMockMondroidService.getAccounts(anyString()))
                .thenReturn(single);
    }

    private void stubMondoServiceGetBalance(Single<Balance> single) {
        when(mMockMondroidService.getBalance(anyString(), anyString()))
                .thenReturn(single);
    }

    private void stubMondoServiceGetTransactions(Single<MondroidService.TransactionResponse> single) {
        when(mMockMondroidService.getTransactions(anyString(), anyString(), anyString()))
                .thenReturn(single);
    }

    private void stubPreferencesHelperGetAccessToken(AccessToken accessToken) {
        when(mMockPreferencesHelper.getAccessToken()).thenReturn(accessToken);
    }
}
