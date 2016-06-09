package com.hitherejoe.mondroid.ui.welcome;

import com.hitherejoe.mondroid.test.common.TestDataFactory;
import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.remote.AccessToken;
import com.hitherejoe.mondroid.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Single;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTest {

    private WelcomePresenter mWelcomePresenter;

    @Mock WelcomeMvpView mMockWelcomeMvpView;
    @Mock DataManager mMockDataManager;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mWelcomePresenter = new WelcomePresenter(mMockDataManager);
        mWelcomePresenter.attachView(mMockWelcomeMvpView);
    }

    @After
    public void detachView() {
        mWelcomePresenter.detachView();
    }

    @Test
    public void getAccessTokenSucceedsAndLaunchesMainActivity() {
        AccessToken accessToken = TestDataFactory.makeAccessToken();
        stubDataManagerGetAccessToken(Single.just(accessToken));
        mWelcomePresenter.getAccessToken("code");

        verify(mMockWelcomeMvpView).showLoadingState(true);
        verify(mMockWelcomeMvpView).launchMainActivity();
        verify(mMockWelcomeMvpView).showLoadingState(false);
    }

    @Test
    public void getAccountDataFailsAndShowsErrorMessage() {
        stubDataManagerGetAccessToken(Single.<AccessToken>error(new RuntimeException()));
        mWelcomePresenter.getAccessToken("code");

        verify(mMockWelcomeMvpView).showLoadingState(true);
        verify(mMockWelcomeMvpView).showAccessTokenError();
        verify(mMockWelcomeMvpView, never()).launchMainActivity();
        verify(mMockWelcomeMvpView).showLoadingState(false);
    }

    private void stubDataManagerGetAccessToken(Single<AccessToken> single) {
        when(mMockDataManager.getAccessToken(anyString()))
                .thenReturn(single);
    }

}
