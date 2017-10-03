package com.codepath.assignment.mytweets.data;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.data.model.TweetMessage;

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

    interface GetTweetMessageCallback{
        void onTweetMessageLoaded(List<TweetMessage> tweetMessage);
        void onDataNotAvailable();
    }


    void getTweets(String maxId, String sinceId, @NonNull LoadTweetsCallback callback);

    void getTweet(@NonNull String tweetId, @NonNull GetTweetCallback callback);

    void deleteAllTweets();

    void saveTweet(Tweet tweet);

    void postTweet(String tweetMessage, GetTweetCallback callback);

    void refreshTweets();

    void saveAllTweets(List<Tweet> tweets);

    void internetStatus(boolean hasInternet);

    void storeTweetMessage(String userId, String message);

    void getTweetMessage(String userId, @NonNull GetTweetMessageCallback callback);

    void deleteTweetMessage(TweetMessage message);

    void replyToTweetMessage(String tweetMessage, String userId);


}
