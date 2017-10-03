package com.codepath.assignment.mytweets.tweetdetailscreen;

import android.content.Intent;

import com.codepath.assignment.mytweets.BasePresenter;
import com.codepath.assignment.mytweets.BaseView;

/**
 * Created by saip92 on 10/1/2017.
 */

public interface TweetDetailContract {

    interface View extends BaseView<Presenter>{


        void setLoadingIndicator(boolean active);

        void showName(String name);

        void showUsername(String username);

        void showTweetContent(String description);

        void showProfileImage(String url);

        void showReTweetCount(String count);

        void showFavoritesCount(String favCount);

        void showTweetLoadingError();

        void showPublishedTime(String time);

        boolean isActive();

        void showResponseToTweetSnackBar();

        void showReplyTweetDialog(int requestCode, String tag);


    }

    interface Presenter extends BasePresenter{

        void sendResult(int requestCode, int resultCode, Intent data);

        void replyToTweet();

    }
}
