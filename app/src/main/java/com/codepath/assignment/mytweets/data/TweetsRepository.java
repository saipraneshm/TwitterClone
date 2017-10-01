package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codepath.assignment.mytweets.data.remote.TweetsRemoteDataSource;
import com.codepath.assignment.mytweets.model.Tweet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saip92 on 9/28/2017.
 */

public class TweetsRepository implements TweetsDataSource {

    private static TweetsRepository INSTANCE = null;

    private static final String TAG = TweetsRepository.class.getSimpleName();

    private final TweetsDataSource mTweetsRemoteDataSource;

    private final TweetsDataSource mTweetsLocalDataSource;


    Map<String, Tweet> mCachedTweets;

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
    public void getMoreTweets(@NonNull final LoadTweetsCallback callback) {

        if(mCachedTweets != null && !mCacheIsDirty){
            callback.onTweetsLoaded(new ArrayList<>(mCachedTweets.values()));
            return;
        }

        if(mCacheIsDirty){
            getTweetsFromRemoteDataSource(callback);
        }else{
            mTweetsLocalDataSource.getMoreTweets(new LoadTweetsCallback() {
                @Override
                public void onTweetsLoaded(List<Tweet> tweets) {
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

    @Override
    public void getMoreTweets(String maxId, String sinceId, @NonNull final LoadTweetsCallback callback) {
        mTweetsRemoteDataSource.getMoreTweets(maxId, sinceId, new LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                Log.d(TAG, "Loading more tweets : " + tweets);
                callback.onTweetsLoaded(new ArrayList<>(tweets));
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"Load more data failed");
                callback.onDataNotAvailable();
            }
        });
    }

    private void getTweetsFromRemoteDataSource(@NonNull final LoadTweetsCallback callback) {
        mTweetsRemoteDataSource.getMoreTweets(new LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                Log.d(TAG,"Getting tweets from remote source : " + tweets);
                refreshCache(tweets);
                refreshLocalDatabase(tweets);
                callback.onTweetsLoaded(new ArrayList<>(tweets));
            }



            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"Loading data for first time failed");
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDatabase(List<Tweet> tweets) {
        mTweetsLocalDataSource.deleteAllTweets();
        for(Tweet tweet: tweets){
            mTweetsLocalDataSource.saveTweet(tweet);
        }
    }

    private void refreshCache(List<Tweet> tweets) {
        if(mCachedTweets == null){
            mCachedTweets = new LinkedHashMap<>();
        }

        mCachedTweets.clear();
        for(Tweet tweet: tweets){
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
    public void saveTweet(@NonNull Tweet tweet) {

    }

    @Override
    public void postTweet(String tweetMessage, final GetTweetCallback callback) {
        mTweetsRemoteDataSource.postTweet(tweetMessage, new GetTweetCallback() {
            @Override
            public void onTweetLoaded(Tweet tweet) {
                callback.onTweetLoaded(tweet);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void refreshTweets() {
        mCacheIsDirty = true;
    }


}
