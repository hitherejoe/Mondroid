package com.hitherejoe.mondroid.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.remote.MondroidService;
import com.hitherejoe.mondroid.injection.ApplicationContext;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {
    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return Mockito.mock(DataManager.class);
    }

    @Provides
    @Singleton
    MondroidService provideMondroidService() {
        return Mockito.mock(MondroidService.class);
    }
}
