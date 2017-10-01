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
    private static final String CONSUMER_KEY = "IKymi60wU6SlFGnBQkhZ43Zi2";
    private static final String CONSUMER_SECRET = "MV9JkCRLSmWSa9WiGAXwI3s68DItR2dGFPaAQXa5TjiyNL8xGE";
    private static final String TOKEN_KEY = "914555476012077056-j8hAyoYVI31cpE5eXqNNJBDvwQAJFYh";
    private static final String TOKEN_SECRET = "nCfRzGvS9vwy5701ZukDfQtcI1arWKAxUh5TZQvUCvYh9";
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
