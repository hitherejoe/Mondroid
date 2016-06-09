package com.hitherejoe.mondroid.ui.main.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitherejoe.mondroid.R;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.util.MoneyUtil;

import java.util.Currency;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BalanceView extends LinearLayout {

    @Bind(R.id.text_balance)
    TextView mBalanceText;

    @Bind(R.id.text_spent_today)
    TextView mSpentTodayText;

    private Currency mCurrency;

    public BalanceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public BalanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BalanceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BalanceView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_balance, this, true);
        ButterKnife.bind(this, view);
        setOrientation(HORIZONTAL);
    }

    public void setBalance(Balance balance) {
        setCurrency(Currency.getInstance(balance.currency));
        setBalance(balance.balance);
        setSpentToday(balance.spentToday);
    }

    public void setBalance(double balance) {
        mBalanceText.setText(MoneyUtil.formatMoneyText(balance, mCurrency));
    }

    public void setSpentToday(double spentToday) {
        mSpentTodayText.setText(MoneyUtil.formatMoneyText(spentToday, mCurrency));
    }

    public void setCurrency(Currency currency) {
        mCurrency = currency;
    }

}
