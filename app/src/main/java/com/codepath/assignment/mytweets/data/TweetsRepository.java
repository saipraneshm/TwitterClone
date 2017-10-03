package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.data.model.TweetMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saip92 on 9/28/2017.
 */

public class TweetsRepository implements TweetsDataSource {

    private static TweetsRepository INSTANCE = null;

    private static final String TAG = TweetsRepository.class.getSimpleName();

    private final TweetsDataSource mTweetsRemoteDataSource;

    private final TweetsDataSource mTweetsLocalDataSource;

    private boolean hasInternet = true;

    private static int count = 0;



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
    public void getTweets(String maxId, String sinceId,
                          @NonNull final LoadTweetsCallback callback) {
        Log.d(TAG,hasInternet + " checking internet status in tweets repo");
        if(hasInternet){
            Log.d(TAG,"calling remote data source");
            getTweetsFromRemoteDataSource(maxId,sinceId,callback);
        }else{
            Log.d(TAG,"calling local data source");
            getTweetsFromLocalDataSource(callback);
        }
    }

    private void getTweetsFromLocalDataSource(@NonNull final LoadTweetsCallback callback){
        mTweetsLocalDataSource.getTweets(null,null,new LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                Log.d(TAG,"OnTweetsLoaded from local data source" + tweets);
                callback.onTweetsLoaded(tweets);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"OnTweetsNot loaded from local data source");
                callback.onDataNotAvailable();
            }
        });
    }

    private void getTweetsFromRemoteDataSource(String maxId, String sinceId,
                                              @NonNull final LoadTweetsCallback callback){
        mTweetsRemoteDataSource.getTweets(maxId, sinceId, new LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                ++count;
                Log.d(TAG,"TweetsFromRemoteDatasource: count - " + count + ", tweets " + tweets );
                callback.onTweetsLoaded(new ArrayList<>(tweets));
                saveAllTweets(tweets);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"Load more data failed");
                callback.onDataNotAvailable();
            }
        });
    }


    private void refreshLocalDatabase(List<Tweet> tweets) {
        if(hasInternet){
            mTweetsLocalDataSource.deleteAllTweets();

            for(Tweet tweet: tweets){
                mTweetsLocalDataSource.saveTweet(tweet);
            }
        }
    }

    @Override
    public void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback) {

    }

    @Override
    public void deleteAllTweets() {
        mTweetsLocalDataSource.deleteAllTweets();
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
        Log.d(TAG,"calling delete all tweets" + hasInternet);
        if(hasInternet)
            mTweetsLocalDataSource.deleteAllTweets();
    }

    @Override
    public void saveAllTweets(List<Tweet> tweets) {
        mTweetsLocalDataSource.saveAllTweets(tweets);
    }

    @Override
    public void internetStatus(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    @Override
    public void storeTweetMessage(String userId, String message) {
        mTweetsLocalDataSource.storeTweetMessage(userId,message);
    }

    @Override
    public void getTweetMessage(String userId, @NonNull final GetTweetMessageCallback callback) {
        Log.d(TAG,"Querying local database for tweet messages for user : " + userId);
        mTweetsLocalDataSource.getTweetMessage(userId, new GetTweetMessageCallback() {
            @Override
            public void onTweetMessageLoaded(List<TweetMessage> tweetMessage) {
                Log.d(TAG,"got tweet messages: " + tweetMessage);
                callback.onTweetMessageLoaded(tweetMessage);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"got no tweet messages: ");
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteTweetMessage(TweetMessage tweetMessage) {
        mTweetsLocalDataSource.deleteTweetMessage(tweetMessage);
    }

    @Override
    public void replyToTweetMessage(String tweetMessage, String userId) {
        mTweetsRemoteDataSource.replyToTweetMessage(tweetMessage,userId);
    }


}
