package com.hitherejoe.mondroid.data.remote;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access_token")
    public String token;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("client_id")
    public String clientId;
    @SerializedName("expires_in")
    public long expiresIn;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("user_id")
    public String userId;

    public AccessToken(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

}