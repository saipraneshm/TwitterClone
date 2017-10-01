package com.codepath.assignment.mytweets.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.codepath.assignment.mytweets.application.TwitterApp;
import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.model.Tweet;
import com.codepath.assignment.mytweets.network.TwitterAPIClient;

import java.io.IOException;
import java.util.HashMap;
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

    private Tweet postedTweet;

    private static final String TAG = TweetsRemoteDataSource.class.getSimpleName();


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
    public void getMoreTweets(@NonNull final LoadTweetsCallback callback) {
        final Call<List<Tweet>> response = mTwitterClient.getResponse();
        response.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call,
                                   Response<List<Tweet>> response) {
                Log.d(TAG,"inside on Response firstGetMore: " + response.body() + ", " + response.errorBody() + " , " + response.headers());
                List<Tweet> arrayList = response.body();
                if(arrayList != null && arrayList.size() > 0){
                    callback.onTweetsLoaded(arrayList);
                    for(Tweet res : arrayList){
                        Log.d("RESPONSE",res.toString());
                    }
                }else{
                    callback.onDataNotAvailable();
                }

                //Log.d("RESPONSE", arrayList.toString() + " , " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Log.e("RESPONSE","Failure",t);
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMoreTweets(String maxId, String sinceId, @NonNull final LoadTweetsCallback callback) {
        Map<String,String> queryParams = new HashMap<>();

        if(maxId != null && !TextUtils.isEmpty(maxId)){
            queryParams.put("max_id",maxId);
        }

        if(sinceId != null && !TextUtils.isEmpty(sinceId)){
            queryParams.put("since_id",sinceId);
        }


        if(maxId == null && sinceId == null){
           /* getMoreTweets(callback);
            return;*/
           queryParams.put("count","20");
        }
        Log.d(TAG, queryParams + " values in hashMap " + maxId + " , " + sinceId);
        final Call<List<Tweet>> response = mTwitterClient.getResponse(queryParams);
        response.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                Log.d(TAG,"inside on Response sdecondGetMore" + response.body() + ", " + response.errorBody() + " , " + response.headers() + " , " + response.code());
                List<Tweet> arrayList = response.body();
                if(arrayList != null && arrayList.size() > 0){
                    callback.onTweetsLoaded(arrayList);
                }else{
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
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
    public void saveTweet(Tweet tweet) {

    }

    @Override
    public void postTweet(String tweetMessage, final GetTweetCallback callback) {
        postedTweet = null;
        final Call<Tweet> response = mTwitterClient.postResponse(tweetMessage);
        response.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                postedTweet = response.body();
                callback.onTweetLoaded(postedTweet);
                Log.d(TAG,"on post response " + response.body());
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void refreshTweets() {

    }
}
