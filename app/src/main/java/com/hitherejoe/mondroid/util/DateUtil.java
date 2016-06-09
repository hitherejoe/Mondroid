package com.hitherejoe.mondroid.util;

import android.content.Context;
import android.text.format.DateUtils;

import com.hitherejoe.mondroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class DateUtil {

    public static String formatDate(Context context, String date) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        try {
            Date transactionDate = dateFormat.parse(date);
            if (DateUtils.isToday(transactionDate.getTime())) {
                return context.getResources().getString(R.string.label_today);
            } else {
                return displayFormat.format(transactionDate);
            }
        } catch (ParseException e) {
            Timber.e("There was a problem parsing the given date...");
        }
        return null;
    }

}
