package com.hitherejoe.mondroid.data.remote;

import com.hitherejoe.mondroid.BuildConfig;

import java.util.UUID;

public class AuthUtil {

    public static String buildAuthenticationUrl() {
        return BuildConfig.MONDO_API_AUTH_URL +
                "?client_id=" + BuildConfig.CLIENT_ID +
                "&redirect_uri=" + BuildConfig.REDIRECT_URI +
                "&response_type=code" + "&state=" + UUID.randomUUID().toString();
    }
}
