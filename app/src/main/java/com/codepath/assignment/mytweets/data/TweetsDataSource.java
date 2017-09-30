package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.model.Tweet;

import java.util.List;

/**
 * Created by saip92 on 9/28/2017.
 */

public interface TweetsDataSource {


    interface LoadTweetsCallback{
        void onTweetsLoaded(List<Tweet> tweets);
        void onDataNotAvailable();
    }

    interface GetTweetCallback{
        void onTweetLoaded(Tweet tweet);
        void onDataNotAvailable();
    }


    void getMoreTweets(@NonNull LoadTweetsCallback callback);

    void getMoreTweets(String maxId, String sinceId, @NonNull LoadTweetsCallback callback);

    void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback);

    void deleteAllTweets();

    void saveTweet(Tweet tweet);

    Tweet postTweet(String tweetMessage);

    void refreshTweets();


}
