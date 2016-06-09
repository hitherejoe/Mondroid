package com.hitherejoe.mondroid.data.remote;

import com.google.gson.Gson;
import com.hitherejoe.mondroid.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provide "make" methods to create instances of {@link MondroidService}
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
public class MondroidServiceFactory {

    public static MondroidService makeMondroidService(UnauthorizedInterceptor unauthorizedInterceptor) {
        OkHttpClient okHttpClient = makeOkHttpClient(
                makeLoggingInterceptor(),
                unauthorizedInterceptor);
        return makeMondroidService(okHttpClient);
    }

    public static MondroidService makeMondroidService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.MONDO_API_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(MondroidService.class);
    }

    public static OkHttpClient makeOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                                UnauthorizedInterceptor unauthorizedInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(unauthorizedInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Accept", "application/json")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }
}
