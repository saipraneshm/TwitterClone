package com.codepath.assignment.mytweets.composetweet;

import android.net.Uri;

import com.codepath.assignment.mytweets.BasePresenter;
import com.codepath.assignment.mytweets.BaseView;

/**
 * Created by saip92 on 10/2/2017.
 */

public interface ComposeTweetContract {


    interface View extends BaseView<Presenter>{

        void showTweetTitle(String title);

        void showTweetMessage(String message);

        void showTweetUri(Uri uri);

        void postTweet(String message);

        void closeTweet();

        void showSaveAsDraftDialog();

        void showDraftTweets();

        void enableUserProfile(boolean enable);

        void enableSendBtn(boolean enable);

        void enableDraftsIcon(boolean enable);

        boolean shouldAskForDialog();

        void closeFragment();

    }

    interface Presenter extends BasePresenter{

        void postTweet(String message);

        void showUserProfile();

        void saveTweetToDisk(String message, String userId);

        void getTweetsFromDisk();

        void dismissDialog();


    }
}
