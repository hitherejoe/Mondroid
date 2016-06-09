package com.hitherejoe.mondroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.hitherejoe.mondroid.data.remote.AccessToken;
import com.hitherejoe.mondroid.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "mondroid-preferences";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clearAll() {
        mPref.edit().clear().apply();
    }

    public void putAccessToken(AccessToken accessToken) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, new Gson().toJson(accessToken)).apply();
    }

    @Nullable
    public AccessToken getAccessToken() {
        String token = mPref.getString(PREF_KEY_ACCESS_TOKEN, null);
        if (token != null) {
            return new Gson().fromJson(token, AccessToken.class);
        }
        return null;
    }

}