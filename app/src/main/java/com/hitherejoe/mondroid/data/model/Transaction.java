package com.hitherejoe.mondroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Transaction implements Comparable<Transaction> {
    @SerializedName("account_balance")
    public long accountBalance;
    public long amount;
    public String created;
    public String currency;
    public String description;
    public String id;
    public Merchant merchant;

    @Override
    public int compareTo(Transaction another) {
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date d = null;
        try {
            d = sdf.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dd = null;
        try {
            dd = sdf.parse(another.created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d != null && dd != null) {
            return dd.compareTo(d);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (accountBalance != that.accountBalance) return false;
        if (amount != that.amount) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return merchant != null ? merchant.equals(that.merchant) : that.merchant == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (accountBalance ^ (accountBalance >>> 32));
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (merchant != null ? merchant.hashCode() : 0);
        return result;
    }
}