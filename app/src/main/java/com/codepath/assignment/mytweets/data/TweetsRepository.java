package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.model.TwitterResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saip92 on 9/28/2017.
 */

public class TweetsRepository implements TweetsDataSource {

    private static TweetsRepository INSTANCE = null;

    private final TweetsDataSource mTweetsRemoteDataSource;

    private final TweetsDataSource mTweetsLocalDataSource;


    Map<String, TwitterResponse> mCachedTweets;

    boolean mCacheIsDirty = false;

    private TweetsRepository(@NonNull TweetsDataSource tweetsRemoteDataSource,
                            @NonNull TweetsDataSource tweetsLocalDataSource) {
        mTweetsRemoteDataSource = tweetsRemoteDataSource;
        mTweetsLocalDataSource = tweetsLocalDataSource;
    }

    public static TweetsRepository getInstance(TweetsDataSource tweetsRemoteDataSource,
                                               TweetsDataSource tweetsLocalDataSource){
        if(INSTANCE == null){
            INSTANCE = new TweetsRepository(tweetsRemoteDataSource,tweetsLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){ INSTANCE = null; }


    @Override
    public void getTweets(@NonNull final LoadTweetsCallback callback) {

        if(mCachedTweets != null && !mCacheIsDirty){
            callback.onTweetsLoaded(new ArrayList<>(mCachedTweets.values()));
            return;
        }

        if(mCacheIsDirty){
            getTweetsFromRemoteDataSource(callback);
        }else{
            mTweetsLocalDataSource.getTweets(new LoadTweetsCallback() {
                @Override
                public void onTweetsLoaded(List<TwitterResponse> tweets) {
                    refreshCache(tweets);
                    callback.onTweetsLoaded(new ArrayList<>(tweets));
                }

                @Override
                public void onDataNotAvailable() {
                    getTweetsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void getTweetsFromRemoteDataSource(@NonNull final LoadTweetsCallback callback) {
        mTweetsRemoteDataSource.getTweets(new LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<TwitterResponse> tweets) {
                refreshCache(tweets);
                refreshLocalDatabase(tweets);
                callback.onTweetsLoaded(new ArrayList<>(mCachedTweets.values()));
            }



            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDatabase(List<TwitterResponse> tweets) {
        mTweetsLocalDataSource.deleteAllTweets();
        for(TwitterResponse tweet: tweets){
            mTweetsLocalDataSource.saveTweet(tweet);
        }
    }

    private void refreshCache(List<TwitterResponse> tweets) {
        if(mCachedTweets == null){
            mCachedTweets = new LinkedHashMap<>();
        }

        mCachedTweets.clear();
        for(TwitterResponse tweet: tweets){
            mCachedTweets.put(tweet.getIdStr(),tweet);
        }

        mCacheIsDirty = false;
    }

    @Override
    public void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback) {

    }

    @Override
    public void deleteAllTweets() {
        mTweetsLocalDataSource.deleteAllTweets();

        if(mCachedTweets == null){
            mCachedTweets = new LinkedHashMap<>();
        }

        mCachedTweets.clear();

    }

    @Override
    public void saveTweet(@NonNull TwitterResponse tweet) {

    }

    @Override
    public TwitterResponse postTweet(String tweetMessage) {
       TwitterResponse tweet = mTweetsRemoteDataSource.postTweet(tweetMessage);
        if(tweet == null) return null;
       mTweetsLocalDataSource.saveTweet(tweet);

        if(mCachedTweets == null){
            mCachedTweets = new LinkedHashMap<>();
        }

        mCachedTweets.put(tweet.getIdStr(),tweet);
       return tweet;
    }

    @Override
    public void refreshTweets() {
        mCacheIsDirty = true;
    }


}
