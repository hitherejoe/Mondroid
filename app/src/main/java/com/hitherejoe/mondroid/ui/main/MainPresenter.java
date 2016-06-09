package com.hitherejoe.mondroid.ui.main;

import com.hitherejoe.mondroid.data.BusEvent;
import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.ui.base.BasePresenter;
import com.hitherejoe.mondroid.util.RxEventBus;

import javax.inject.Inject;

import rx.Completable;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private final RxEventBus mEventBus;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    public MainPresenter(DataManager dataManager, RxEventBus rxEventBus) {
        mDataManager = dataManager;
        mEventBus = rxEventBus;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(mEventBus.filteredObservable(BusEvent.AuthenticationError.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BusEvent.AuthenticationError>() {
                    @Override
                    public void call(BusEvent.AuthenticationError event) {
                        getMvpView().launchWelcomeActivity();
                    }
                }));
    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }

    public void getAccountData() {
        checkViewAttached();
        getMvpView().showProgress(true);
        mCompositeSubscription.add(mDataManager.getAccountDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<DataManager.AccountData>() {
                    @Override
                    public void onSuccess(DataManager.AccountData accountData) {
                        getMvpView().showProgress(false);
                        if (accountData.transactions != null &&
                                !accountData.transactions.isEmpty()) {
                            getMvpView().showTransactions(accountData.transactions);
                        } else {
                            getMvpView().showEmptyTransactions();
                        }
                        getMvpView().showBalance(accountData.balance);
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().showProgress(false);
                        getMvpView().showAccountError();
                        error.printStackTrace();
                        Timber.e("There was an error retrieving the account data...");
                    }
                }));
    }

    public void signOut() {
        checkViewAttached();
        mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Completable.CompletableSubscriber() {
                    @Override
                    public void onCompleted() {
                        getMvpView().launchWelcomeActivity();
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().launchWelcomeActivity();
                        Timber.e(error, "There was an error performing sign out");
                    }

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        mCompositeSubscription.add(subscription);
                    }
                });
    }

}
