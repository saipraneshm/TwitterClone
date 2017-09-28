package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.model.TwitterResponse;

import java.util.List;

/**
 * Created by saip92 on 9/28/2017.
 */

public interface TweetsDataSource {


    interface LoadTweetsCallback{
        void onTweetsLoaded(List<TwitterResponse> tweets);
        void onDataNotAvailable();
    }

    interface GetTweetCallback{
        void onTweetLoaded(TwitterResponse tweet);
        void onDataNotAvailable();
    }


    void getTweets(@NonNull LoadTweetsCallback callback);

    void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback);

    void deleteAllTweets();

    void saveTweet(TwitterResponse tweet);

    TwitterResponse postTweet(String tweetMessage);

    void refreshTweets();


}
