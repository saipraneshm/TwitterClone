package com.codepath.assignment.mytweets.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codepath.assignment.mytweets.application.TwitterApp;
import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.model.TwitterResponse;
import com.codepath.assignment.mytweets.network.TwitterAPIClient;
import com.codepath.assignment.mytweets.network.TwitterApiController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by saip92 on 9/28/2017.
 */

public class TweetsRemoteDataSource implements TweetsDataSource {

    private static TweetsRemoteDataSource INSTANCE;

    private static Retrofit mRetrofit;

    private static TwitterAPIClient mTwitterClient;

    private TwitterResponse postedTweet;


    private TweetsRemoteDataSource(){
        mRetrofit = TwitterApp.getRetrofit();
        mTwitterClient = mRetrofit.create(TwitterAPIClient.class);
    }

    public static TweetsRemoteDataSource getInstance(){
        if( INSTANCE == null){
            INSTANCE = new TweetsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getTweets(@NonNull final LoadTweetsCallback callback) {
        final Call<List<TwitterResponse>> response = mTwitterClient.getResponse();
        response.enqueue(new Callback<List<TwitterResponse>>() {
            @Override
            public void onResponse(Call<List<TwitterResponse>> call,
                                   Response<List<TwitterResponse>> response) {
                List<TwitterResponse> arrayList = response.body();
                if(arrayList != null && arrayList.size() > 0){
                    callback.onTweetsLoaded(arrayList);
                    for(TwitterResponse res : arrayList){
                        Log.d("RESPONSE",res.toString());
                    }
                }

                //Log.d("RESPONSE", arrayList.toString() + " , " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<TwitterResponse>> call, Throwable t) {
                Log.e("RESPONSE","Failure",t);
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback) {

    }

    @Override
    public void deleteAllTweets() {

    }

    @Override
    public void saveTweet(TwitterResponse tweet) {

    }

    @Override
    public TwitterResponse postTweet(String tweetMessage) {
        postedTweet = new TwitterResponse();
        final Call<TwitterResponse> response = mTwitterClient.postResponse(tweetMessage);
        response.enqueue(new Callback<TwitterResponse>() {
            @Override
            public void onResponse(Call<TwitterResponse> call, Response<TwitterResponse> response) {
                postedTweet = response.body();
            }

            @Override
            public void onFailure(Call<TwitterResponse> call, Throwable t) {
                postedTweet = null;
            }
        });
        return postedTweet;
    }

    @Override
    public void refreshTweets() {

    }
}
