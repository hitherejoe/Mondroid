package com.hitherejoe.mondroid.data.remote;

import com.hitherejoe.mondroid.data.BusEvent;
import com.hitherejoe.mondroid.util.RxEventBus;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorizedInterceptor implements Interceptor {

    private RxEventBus mRxEventBus;

    @Inject
    public UnauthorizedInterceptor(RxEventBus rxEventBus) {
        mRxEventBus = rxEventBus;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 401) {
            mRxEventBus.post(new BusEvent.AuthenticationError());
        }
        return response;
    }

}