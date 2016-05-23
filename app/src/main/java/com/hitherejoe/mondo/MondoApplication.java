package com.hitherejoe.mondo;

import android.app.Application;
import android.content.Context;

import com.hitherejoe.mondo.injection.component.ApplicationComponent;
import com.hitherejoe.mondo.injection.component.DaggerApplicationComponent;
import com.hitherejoe.mondo.injection.module.ApplicationModule;

import timber.log.Timber;

public class MondoApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static MondoApplication get(Context context) {
        return (MondoApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
