package com.hitherejoe.mondroid.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hitherejoe.mondroid.MondroidApplication;
import com.hitherejoe.mondroid.injection.component.ActivityComponent;
import com.hitherejoe.mondroid.injection.component.DaggerActivityComponent;
import com.hitherejoe.mondroid.injection.module.ActivityModule;
import com.hitherejoe.mondroid.util.RxEventBus;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    @Inject RxEventBus mEventBus;

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MondroidApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
