package com.hitherejoe.mondroid.data;

import com.hitherejoe.mondroid.BuildConfig;
import com.hitherejoe.mondroid.data.local.PreferencesHelper;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.data.remote.AccessToken;
import com.hitherejoe.mondroid.data.remote.MondroidService;
import com.hitherejoe.mondroid.data.remote.MondroidService.AccountsResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Completable;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

@Singleton
public class DataManager {

    private final MondroidService mMondroidService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(MondroidService mondroidService, PreferencesHelper preferencesHelper) {
        mMondroidService = mondroidService;
        mPreferencesHelper = preferencesHelper;
    }

    public Single<AccessToken> getAccessToken(String code) {
        return mMondroidService.getAccessToken(null, "authorization_code", BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET, BuildConfig.REDIRECT_URI, code)
                .doOnSuccess(new Action1<AccessToken>() {
                    @Override
                    public void call(AccessToken accessToken) {
                        mPreferencesHelper.putAccessToken(accessToken);
                    }
                });
    }

    public Single<Boolean> isUserSignedIn() {
        return Single.just(mPreferencesHelper.getAccessToken() != null);
    }

    public Completable signOut() {
        return Completable.defer(new Func0<Completable>() {
            @Override
            public Completable call() {
                mPreferencesHelper.clearAll();
                return Completable.complete();
            }
        });
    }

    public Single<AccountsResponse> getAccounts() {
        String token = buildAuthorization();
        return mMondroidService.getAccounts(token);
    }

    public Single<AccountData> getAccountDetails() {
        return getAccounts()
                .flatMap(new Func1<AccountsResponse, Single<? extends AccountData>>() {
                    @Override
                    public Single<? extends AccountData> call(AccountsResponse accountsResponse) {
                        if (!accountsResponse.accounts.isEmpty()) {
                            String accountId = accountsResponse.accounts.get(0).id;
                            return Single.zip(getBalance(accountId), getTransactions(accountId),
                                    new Func2<Balance, List<Transaction>, AccountData>() {
                                @Override
                                public AccountData call(Balance balance,
                                                        List<Transaction> transactions) {
                                    return new AccountData(balance, transactions);
                                }
                            });
                        }
                        return Single.just(new AccountData());
                    }
                });
    }

    public Single<Balance> getBalance(String accountId) {
        String token = buildAuthorization();
        return mMondroidService.getBalance(token, accountId);
    }

    public Single<List<Transaction>> getTransactions(String accountId) {
        String token = buildAuthorization();
        return mMondroidService.getTransactions(token, accountId, "merchant")
                .map(new Func1<MondroidService.TransactionResponse, List<Transaction>>() {
                    @Override
                    public List<Transaction> call(MondroidService.TransactionResponse transactionResponse) {
                        return transactionResponse.transactions;
                    }
                });
    }

    private String buildAuthorization() {
        AccessToken accessToken = mPreferencesHelper.getAccessToken();
        String token = accessToken != null ? accessToken.token : "";
        return " Bearer " + token;
    }

    public static class AccountData {
        public Balance balance;
        public List<Transaction> transactions;

        public AccountData() { }

        public AccountData(Balance balance, List<Transaction> transactions) {
            this.balance = balance;
            this.transactions = transactions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AccountData that = (AccountData) o;

            if (balance != null ? !balance.equals(that.balance) : that.balance != null)
                return false;
            return transactions != null ? transactions.equals(that.transactions) :
                    that.transactions == null;

        }

        @Override
        public int hashCode() {
            int result = balance != null ? balance.hashCode() : 0;
            result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
            return result;
        }
    }

}