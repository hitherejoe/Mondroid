package com.hitherejoe.mondo.data;

import com.hitherejoe.mondo.data.remote.MondoService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final MondoService mMondoService;

    @Inject
    public DataManager(MondoService mondoService) {
        mMondoService = mondoService;
    }

}