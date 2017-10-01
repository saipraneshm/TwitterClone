package com.codepath.assignment.mytweets.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by saip92 on 9/26/2017.
 */

public class RetrofitClient {

   // private volatile static Retrofit sRetrofit = null;
    private static final String CONSUMER_KEY = "2LW6qNf8mYuleM9Zv1Eo2W6V8";
    private static final String CONSUMER_SECRET = "5eJnnnmHHfnMFkQyOJ8U9I21QSUvVCX6DtxV1ftRzJWgr3IufL";
    private static final String TOKEN_KEY = "147812816-2q5NBpwu2A8HFsKM9DDBxVgDBqIDtChe7bqo9inL";
    private static final String TOKEN_SECRET = "OKtuQFMyVbjVPrF9PKffIcbYYzpl6E7lV3Bxpc0hdc4OX";
    private static final String BASE_URL = "https://api.twitter.com/1.1/";

    private Retrofit mRetrofit = null;

    public  RetrofitClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN_KEY, TOKEN_SECRET);
        httpClient.addInterceptor(new SigningInterceptor(consumer));

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
