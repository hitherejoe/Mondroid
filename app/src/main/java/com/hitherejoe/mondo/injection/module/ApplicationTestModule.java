package com.hitherejoe.mondo.injection.module;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondo.data.DataManager;
import com.hitherejoe.mondo.data.remote.MondoService;
import com.hitherejoe.mondo.injection.ApplicationContext;

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
    MondoService provideBourbonService() {
        return Mockito.mock(MondoService.class);
    }
}
