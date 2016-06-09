package com.hitherejoe.mondroid.injection.component;

import com.hitherejoe.mondroid.injection.PerActivity;
import com.hitherejoe.mondroid.injection.module.ActivityModule;
import com.hitherejoe.mondroid.ui.base.BaseActivity;
import com.hitherejoe.mondroid.ui.launcher.LauncherActivity;
import com.hitherejoe.mondroid.ui.main.MainActivity;
import com.hitherejoe.mondroid.ui.welcome.WelcomeActivity;

import dagger.Component;

/**
 * This component injects dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);
    void inject(LauncherActivity launcherActivity);
    void inject(WelcomeActivity welcomeActivity);

}