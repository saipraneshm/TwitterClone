package com.codepath.assignment.mytweets.tweetdetailscreen;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codepath.assignment.mytweets.composetweet.dialog.DialogComposeTweet;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.data.model.Tweet;

/**
 * Created by saip92 on 10/1/2017.
 */

 class TweetDetailPresenter implements TweetDetailContract.Presenter {


    private static final String REQUEST_REPLY_TWEET_DIALOG = "REQUEST_REPLY_TWEET_DIALOG";
    private static final int REPLY_TWEET_DIALOG_REQUEST_CODE = 45;
    private final TweetsRepository mTweetsRepository;


    private final TweetDetailContract.View mTweetsDetailView;

    @Nullable
    private Tweet mTweet;

     TweetDetailPresenter(@Nullable Tweet tweet,
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

    @Override
    public void sendResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == REPLY_TWEET_DIALOG_REQUEST_CODE && data != null){
            String message = data.getStringExtra(DialogComposeTweet.EXTRA_TWEET_MESSAGE);
            String userId = data.getStringExtra(DialogComposeTweet.EXTRA_RESPONSE_USER_ID);
            if(message != null && userId != null){
                mTweetsRepository.replyToTweetMessage(message,userId);
                mTweetsDetailView.showResponseToTweetSnackBar();
            }
        }
    }

    @Override
    public void replyToTweet() {
        mTweetsDetailView.showReplyTweetDialog(REPLY_TWEET_DIALOG_REQUEST_CODE,
                REQUEST_REPLY_TWEET_DIALOG );
    }
}
