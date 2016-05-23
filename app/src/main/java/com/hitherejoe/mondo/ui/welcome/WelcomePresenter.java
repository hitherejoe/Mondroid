package com.hitherejoe.mondo.ui.welcome;

import com.hitherejoe.mondo.data.DataManager;
import com.hitherejoe.mondo.ui.base.BasePresenter;

import javax.inject.Inject;

public class WelcomePresenter<T extends WelcomeMvpView> extends BasePresenter<T> {

    DataManager mDataManager;

    @Inject
    public WelcomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}
