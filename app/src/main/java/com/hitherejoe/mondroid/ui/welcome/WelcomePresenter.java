package com.hitherejoe.mondroid.ui.welcome;

import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.remote.AccessToken;
import com.hitherejoe.mondroid.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class WelcomePresenter extends BasePresenter<WelcomeMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public WelcomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
    }

    public void getAccessToken(String code) {
        checkViewAttached();
        getMvpView().showLoadingState(true);
        mSubscription = mDataManager.getAccessToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<AccessToken>() {
                    @Override
                    public void onSuccess(AccessToken accessToken) {
                        getMvpView().showLoadingState(false);
                        getMvpView().launchMainActivity();
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().showLoadingState(false);
                        Timber.e(error, "There was a problem retrieving the access token");
                        getMvpView().showAccessTokenError();
                    }
                });
    }

}
