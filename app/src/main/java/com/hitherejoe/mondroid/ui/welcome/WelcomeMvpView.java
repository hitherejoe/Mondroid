package com.hitherejoe.mondroid.ui.welcome;

import com.hitherejoe.mondroid.ui.base.MvpView;

public interface WelcomeMvpView extends MvpView {

    void launchMainActivity();

    void showAccessTokenError();

    void showLoadingState(boolean loading);

}
