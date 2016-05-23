package com.hitherejoe.mondo.ui.main;

import com.hitherejoe.mondo.data.DataManager;
import com.hitherejoe.mondo.ui.base.BasePresenter;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainMvpView> {

    DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}
