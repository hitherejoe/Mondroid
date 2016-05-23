package com.hitherejoe.mondo.injection.component;

import com.hitherejoe.mondo.injection.PerActivity;
import com.hitherejoe.mondo.injection.module.ActivityModule;
import com.hitherejoe.mondo.ui.base.BaseActivity;
import com.hitherejoe.mondo.ui.welcome.WelcomeActivity;

import dagger.Component;

/**
 * This component injects dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
    void inject(WelcomeActivity welcomeActivity);

}