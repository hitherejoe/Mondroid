package com.hitherejoe.mondroid.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondroid.data.remote.MondroidService;
import com.hitherejoe.mondroid.data.remote.MondroidServiceFactory;
import com.hitherejoe.mondroid.data.remote.UnauthorizedInterceptor;
import com.hitherejoe.mondroid.injection.ApplicationContext;

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
    MondroidService provideMondroidService(UnauthorizedInterceptor unauthorizedInterceptor) {
        return MondroidServiceFactory.makeMondroidService(unauthorizedInterceptor);
    }

}