package com.hitherejoe.mondo.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondo.data.DataManager;
import com.hitherejoe.mondo.data.remote.MondoService;
import com.hitherejoe.mondo.injection.ApplicationContext;
import com.hitherejoe.mondo.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    MondoService mondoService();
}
