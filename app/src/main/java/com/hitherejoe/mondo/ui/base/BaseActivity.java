package com.hitherejoe.mondo.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hitherejoe.mondo.MondoApplication;
import com.hitherejoe.mondo.injection.component.ActivityComponent;
import com.hitherejoe.mondo.injection.component.DaggerActivityComponent;
import com.hitherejoe.mondo.injection.module.ActivityModule;

public class BaseActivity extends Activity {

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
                    .applicationComponent(MondoApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
