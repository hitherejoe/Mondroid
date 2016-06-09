package com.hitherejoe.mondroid.injection.component;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.local.PreferencesHelper;
import com.hitherejoe.mondroid.data.remote.MondroidService;
import com.hitherejoe.mondroid.injection.ApplicationContext;
import com.hitherejoe.mondroid.injection.module.ApplicationModule;
import com.hitherejoe.mondroid.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    MondroidService mondoService();
    PreferencesHelper preferencesHelper();
    RxEventBus rxEventBus();
}
