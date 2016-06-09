package com.hitherejoe.mondroid.data.remote;

import android.support.annotation.Nullable;

import com.hitherejoe.mondroid.data.model.Account;
import com.hitherejoe.mondroid.data.model.Balance;
import com.hitherejoe.mondroid.data.model.Transaction;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Single;

public interface MondroidService {

    @FormUrlEncoded

    @POST("/oauth2/token")
    Single<AccessToken> getAccessToken(
            @Nullable @Header("Authorization") String auth,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code);

    @GET("/accounts")
    Single<AccountsResponse> getAccounts(@Header("Authorization") String auth);

    @GET("/balance")
    Single<Balance> getBalance(@Header("Authorization") String auth,
                               @Query("account_id") String accountId);

    @GET("/transactions")
    Single<TransactionResponse> getTransactions(@Header("Authorization") String auth,
                                                @Query("account_id") String accountId,
                                                @Query("expand[]") String expand);

    class TransactionResponse {
        public List<Transaction> transactions;
    }

    class AccountsResponse {
        public List<Account> accounts;
    }
}
