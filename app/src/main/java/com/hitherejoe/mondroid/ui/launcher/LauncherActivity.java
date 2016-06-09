package com.hitherejoe.mondroid.ui.launcher;

import android.content.Intent;
import android.os.Bundle;

import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.ui.base.BaseActivity;
import com.hitherejoe.mondroid.ui.main.MainActivity;
import com.hitherejoe.mondroid.ui.welcome.WelcomeActivity;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LauncherActivity extends BaseActivity {

    @Inject DataManager mDataManager;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mSubscription = mDataManager.isUserSignedIn()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<Boolean>() {
                    @Override
                    public void onSuccess(Boolean signedIn) {
                        Intent intent;
                        if (signedIn) {
                            intent = new Intent(LauncherActivity.this, MainActivity.class);
                        } else {
                            intent = new Intent(LauncherActivity.this, WelcomeActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e("There was an error getting the signed in state");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}