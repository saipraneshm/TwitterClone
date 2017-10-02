package com.codepath.assignment.mytweets.twitterdetailscreen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.data.model.Tweet;

/**
 * Created by saip92 on 10/1/2017.
 */

public class TweetDetailPresenter implements TweetDetailContract.Presenter {


    private final TweetsRepository mTweetsRepository;


    private final TweetDetailContract.View mTweetsDetailView;

    @Nullable
    private Tweet mTweet;

    public TweetDetailPresenter(@Nullable Tweet tweet,
                                @NonNull TweetsRepository tweetsRepository,
                                @NonNull TweetDetailContract.View tweetsDetailView){
        mTweet = tweet;
        mTweetsRepository = tweetsRepository;
        mTweetsDetailView = tweetsDetailView;
        mTweetsDetailView.setPresenter(this);
    }


    @Override
    public void start() {
        updateUI();
    }

    private void updateUI() {
        if(mTweet == null){
            mTweetsDetailView.showTweetLoadingError();
            return;
        }

        mTweetsDetailView.showName(mTweet.getUser().getName());
        mTweetsDetailView.showUsername("@" + mTweet.getUser().getScreenName());
        mTweetsDetailView.showFavoritesCount(String.valueOf(mTweet.getFavoriteCount()));
        mTweetsDetailView.showReTweetCount(String.valueOf(mTweet.getRetweetCount()));
        mTweetsDetailView.showPublishedTime(mTweet.getDetailScreenTimeFormat());
        mTweetsDetailView.showTweetContent(mTweet.getText());
        mTweetsDetailView.showProfileImage(mTweet.getUser().getProfileImageUrl()
                .replace("normal","bigger"));
    }
}
