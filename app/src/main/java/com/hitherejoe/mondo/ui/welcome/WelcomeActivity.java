package com.hitherejoe.mondo.ui.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.hitherejoe.mondo.BuildConfig;
import com.hitherejoe.mondo.R;
import com.hitherejoe.mondo.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        activityComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith("redirecturi")) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
                // Retrieve access token
            } else if (uri.getQueryParameter("error") != null) {
                // Handle error
            }
        }
    }

    @OnClick(R.id.button_login)
    void onLoginButtonClick() {
        String authUrl = BuildConfig.MONDO_API_AUTH_URL + "client_id=" + "" + "&redirect_uri=" + ""
                + "*response_type=code" + "&state=" + "";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
        startActivity(intent);
    }

}
