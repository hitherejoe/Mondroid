package com.hitherejoe.mondo.ui.main.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitherejoe.mondo.R;
import com.hitherejoe.mondo.data.model.Balance;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BalanceView extends RelativeLayout {

    @Bind(R.id.text_balance)
    TextView mBalanceText;

    @Bind(R.id.text_spent_today)
    TextView mSpentTodayText;

    private Currency mCurrency;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        Balance balance = new Balance();
        balance.currency = "GBP";
        balance.balance = 2344;
        balance.spentToday = 5018;
        setBalance(balance);
    }

    public void setBalance(Balance balance) {
        setCurrency(Currency.getInstance(balance.currency));
        setBalance(balance.balance);
        setSpentToday(balance.spentToday);
    }

    public void setBalance(double balance) {
        mBalanceText.setText(formatMoneyText(balance));
    }

    public void setSpentToday(double balance) {
        mSpentTodayText.setText(formatMoneyText(balance));
    }

    public void setCurrency(Currency currency) {
        mCurrency = currency;
    }

    private Spannable formatMoneyText(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(mCurrency);
        String amountString = format.format(amount);

        int decimalIndex = amountString.indexOf(".");
        Spannable span = new SpannableString(amountString);
        span.setSpan(new RelativeSizeSpan(0.7f), 0, 1, 0);
        span.setSpan(new RelativeSizeSpan(1f), 1, decimalIndex, 0);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.white)), 0, decimalIndex, 0);
        span.setSpan(new RelativeSizeSpan(0.4f), decimalIndex, amountString.length(), 0);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.white_trans_80)), decimalIndex, amountString.length(), 0);
        return span;
    }
}
