package com.codepath.assignment.mytweets.twitterfeed;

import com.codepath.assignment.mytweets.BasePresenter;
import com.codepath.assignment.mytweets.BaseView;
import com.codepath.assignment.mytweets.model.Tweet;

import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public interface TweetsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTweets(List<Tweet> tweets);

        void showComposeTweetDialog();

        void showLoadingTweetsError();

        void showNoTweets();

        void showNewTweetsSinceLastLoad(List<Tweet> tweets);

        void showSuccessfullyPostedTweetMessage();

        boolean isActive();

        void showMoreTweets(List<Tweet> tweets);

    }


    interface Presenter extends BasePresenter {


        void result(int requestCode, int resultCode);

        void loadTweets(boolean forceUpdate);

        void loadMoreTweets(String maxId, String sinceId, boolean swipeToRefresh);

        void composeNewTweet();

    }
}
