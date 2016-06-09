package com.hitherejoe.mondroid;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hitherejoe.mondroid.test.common.TestComponentRule;
import com.hitherejoe.mondroid.test.common.TestDataFactory;
import com.hitherejoe.mondroid.data.DataManager;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.ui.main.MainActivity;
import com.hitherejoe.mondroid.util.DateUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import rx.Completable;
import rx.Single;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void checkBalanceDisplays() {
        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), Collections.<Transaction>emptyList()
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        main.launchActivity(null);

        onView(withText(R.string.title_balance))
                .check(matches(isDisplayed()));
        onView(withText(R.string.title_spent_today))
                .check(matches(isDisplayed()));

        String balance = DateUtil.formatMoneyText(
                accountData.balance.balance, Currency.getInstance("GBP"));
        String spentToday = DateUtil.formatMoneyText(
                accountData.balance.spentToday, Currency.getInstance("GBP"));

        onView(withText(balance))
                .check(matches(isDisplayed()));
        onView(withText(spentToday))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkTransactionsDisplay() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TestDataFactory.makeTransaction("0", false));
        transactions.add(TestDataFactory.makeTransaction("1", true));

        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), transactions
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        main.launchActivity(null);

        checkTransactionsDisplay(accountData.transactions);
    }

    @Test
    public void noTransactionsMessageDisplaysWhenEmptyTransactionsListReturned() {
        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), Collections.<Transaction>emptyList()
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        main.launchActivity(null);

        onView(withText(R.string.text_no_transactions))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_message))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickingCheckAgainButtonReloadsTransactions() {
        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), Collections.<Transaction>emptyList()
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        main.launchActivity(null);

        onView(withText(R.string.text_no_transactions))
                .check(matches(isDisplayed()));

        accountData.transactions = TestDataFactory.makeTransactions(1);
        stubDataManagerGetAccountDetails(Single.just(accountData));

        onView(withId(R.id.button_message))
                .perform(click());

        checkTransactionsDisplay(accountData.transactions);
    }

    @Test
    public void clickingSignOutNavigatesToWelcomeActivity() {
        when(component.getMockDataManager().signOut())
                .thenReturn(Completable.complete());

        DataManager.AccountData accountData = new DataManager.AccountData(
                TestDataFactory.makeBalance(), Collections.<Transaction>emptyList()
        );
        stubDataManagerGetAccountDetails(Single.just(accountData));
        main.launchActivity(null);

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext());
        onView(withText(R.string.action_logout))
                .perform(click());

        onView(withId(R.id.image_logo))
                .check(matches(isDisplayed()));
    }

    private void checkTransactionsDisplay(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            onView(withText(transaction.merchant.name))
                    .check(matches(isDisplayed()));

            DecimalFormat format = new DecimalFormat("+#,##0.00;-#");
            format.setCurrency(Currency.getInstance(transaction.currency));

            double amount = transaction.amount / (100.0);

            String amountString = format.format(amount);
            onView(withText(amountString))
                    .check(matches(isDisplayed()));

            onView(withText(DateUtil.formatDate(InstrumentationRegistry.getTargetContext(), transaction.created)))
                    .check(matches(isDisplayed()));
        }
    }

    private void stubDataManagerGetAccountDetails(Single<DataManager.AccountData> single) {
        when(component.getMockDataManager().getAccountDetails())
                .thenReturn(single);
    }

}