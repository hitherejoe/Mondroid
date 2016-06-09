package com.hitherejoe.mondroid;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondroid.injection.component.ApplicationComponent;
import com.hitherejoe.mondroid.injection.component.DaggerApplicationComponent;
import com.hitherejoe.mondroid.injection.module.ApplicationModule;

import timber.log.Timber;

public class MondroidApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static MondroidApplication get(Context context) {
        return (MondroidApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
