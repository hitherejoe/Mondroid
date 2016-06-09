package com.hitherejoe.mondroid.util;

import java.text.NumberFormat;
import java.util.Currency;

public class MoneyUtil {

    public static String formatMoneyText(double amount, Currency currency) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        return format.format(amount / 100).replaceAll("-", "");
    }
}
