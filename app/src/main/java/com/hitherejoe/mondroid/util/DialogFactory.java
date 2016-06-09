package com.hitherejoe.mondroid.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.hitherejoe.mondroid.R;

public class DialogFactory {

    public static Dialog createSimpleOkErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_error_title))
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }
}