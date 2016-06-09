package com.hitherejoe.mondroid;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.mondroid.test.common.TestComponentRule;
import com.hitherejoe.mondroid.ui.welcome.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final IntentsTestRule<WelcomeActivity> main =
            new IntentsTestRule<>(WelcomeActivity.class);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void checkViewsDisplay() {
        onView(withId(R.id.image_logo))
                .check(matches(isDisplayed()));
        onView(withText(R.string.app_name))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_login))
                .check(matches(isDisplayed()));
    }

    //TODO: Test intents used for oauth work as expected

}