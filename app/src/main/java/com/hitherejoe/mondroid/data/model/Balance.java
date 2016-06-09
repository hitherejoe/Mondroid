package com.hitherejoe.mondroid.data.model;

import com.google.gson.annotations.SerializedName;

public class Balance {
    public long balance;
    public String currency;
    @SerializedName("spend_today")
    public long spentToday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance1 = (Balance) o;

        if (balance != balance1.balance) return false;
        if (spentToday != balance1.spentToday) return false;
        return currency != null ? currency.equals(balance1.currency) : balance1.currency == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (balance ^ (balance >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (int) (spentToday ^ (spentToday >>> 32));
        return result;
    }
}
