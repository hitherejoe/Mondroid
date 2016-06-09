package com.hitherejoe.mondroid.test.common;

import com.hitherejoe.mondroid.data.model.Account;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Merchant;
import com.hitherejoe.mondroid.data.model.Transaction;
import com.hitherejoe.mondroid.data.remote.AccessToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final Random sRandom = new Random();

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static AccessToken makeAccessToken() {
        return new AccessToken(randomUuid(), randomUuid());
    }

    public static List<Account> makeAccounts(int count) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            accounts.add(makeAccount(String.valueOf(i)));
        }
        return accounts;
    }

    public static Account makeAccount(String unique) {
        Account account = new Account();
        account.id = randomUuid();
        account.created = randomUuid();
        account.description = unique;
        return account;
    }

    public static Balance makeBalance() {
        Balance balance = new Balance();
        balance.currency = "GBP";
        balance.balance = sRandom.nextInt(10000);
        balance.spentToday = sRandom.nextInt(10000);
        return balance;
    }

    public static List<Transaction> makeTransactions(int count) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            transactions.add(makeTransaction(String.valueOf(i), false));
        }
        return transactions;
    }

    public static Transaction makeTransaction(String unique, boolean isToday) {
        Transaction transaction = new Transaction();
        transaction.id = randomUuid();
        transaction.amount = sRandom.nextInt(10000);
        transaction.accountBalance = sRandom.nextInt(10000);
        transaction.currency = "GBP";

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, isToday ? 1 : 0);
        transaction.created = dateFormat.format(c.getTime());
        transaction.description = unique;
        transaction.merchant = makeMerchant();
        return transaction;
    }

    public static Merchant makeMerchant() {
        Merchant merchant = new Merchant();
        merchant.name = randomUuid();
        return merchant;
    }

}