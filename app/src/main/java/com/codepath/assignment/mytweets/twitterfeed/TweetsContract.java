package com.codepath.assignment.mytweets.twitterfeed;

import com.codepath.assignment.mytweets.BasePresenter;
import com.codepath.assignment.mytweets.BaseView;
import com.codepath.assignment.mytweets.model.TwitterResponse;

import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public interface TweetsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTweets(List<TwitterResponse> tweets);

        void showComposeTweetDialog();

        void showLoadingTweetsError();

        void showNoTweets();

        void showSuccessfullyPostedTweetMessage();

        boolean isActive();

    }


    interface Presenter extends BasePresenter {


        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void composeNewTweet();

    }
}
