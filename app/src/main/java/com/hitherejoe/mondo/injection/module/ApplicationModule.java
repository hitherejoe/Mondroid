package com.hitherejoe.mondo.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondo.data.remote.AccessToken;
import com.hitherejoe.mondo.data.remote.MondoService;
import com.hitherejoe.mondo.data.remote.MondoServiceFactory;
import com.hitherejoe.mondo.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MondoService provideBourbonService() {
        AccessToken accessToken = new AccessToken("", "");
        return MondoServiceFactory.makeBourbonService(accessToken);
    }

}