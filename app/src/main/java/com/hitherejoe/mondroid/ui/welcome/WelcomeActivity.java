package com.hitherejoe.mondroid.ui.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hitherejoe.mondroid.BuildConfig;
import com.hitherejoe.mondroid.R;
import com.hitherejoe.mondroid.data.remote.AuthUtil;
import com.hitherejoe.mondroid.ui.base.BaseActivity;
import com.hitherejoe.mondroid.ui.main.MainActivity;
import com.hitherejoe.mondroid.util.DialogFactory;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class WelcomeActivity extends BaseActivity implements WelcomeMvpView {

    @Inject WelcomePresenter mWelcomePresenter;

    @Bind(R.id.button_login) Button mLoginButton;
    @Bind(R.id.image_logo) ImageView mLogoImage;
    @Bind(R.id.progress) ProgressBar mProgress;
    @Bind(R.id.text_title) TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        mWelcomePresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(BuildConfig.REDIRECT_URI)) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
                mWelcomePresenter.getAccessToken(code);
            } else if (uri.getQueryParameter("error") != null) {
                DialogFactory.createSimpleOkErrorDialog(
                        this, getString(R.string.dialog_error_authentication)).show();
                Timber.e("There was an error whilst trying to perform authentication");
            }
        }
    }

    @OnClick(R.id.button_login)
    void onLoginButtonClick() {
        String authUrl = AuthUtil.buildAuthenticationUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
        startActivity(intent);
    }

    @Override
    public void launchMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showAccessTokenError() {
        DialogFactory.createSimpleOkErrorDialog(
                this, getString(R.string.dialog_error_authentication)).show();
    }

    @Override
    public void showLoadingState(boolean loading) {
        mLoginButton.setVisibility(loading ? View.GONE : View.VISIBLE);
        mTitleText.setVisibility(loading ? View.GONE : View.VISIBLE);
        mLogoImage.setVisibility(loading ? View.GONE : View.VISIBLE);
        mProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
