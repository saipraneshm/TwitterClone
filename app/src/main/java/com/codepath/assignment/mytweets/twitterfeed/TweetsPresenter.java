package com.codepath.assignment.mytweets.twitterfeed;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.model.Tweet;

import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public class TweetsPresenter implements TweetsContract.Presenter {

    private final TweetsRepository mTweetsRepository;

    private final TweetsContract.View mTweetsView;

    private boolean mFirstLoad = true;


    public TweetsPresenter(@NonNull TweetsRepository tweetsRepository,
                           @NonNull TweetsContract.View tweetsView){
        mTweetsRepository = tweetsRepository;
        mTweetsView = tweetsView;
        mTweetsView.setPresenter(this);
    }


    @Override
    public void start() {
        loadTweets(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTweets(boolean forceUpdate) {
        loadTweets(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreTweets(String maxId, String sinceId, boolean swipeToRefresh) {
        loadTweets(maxId, sinceId, true, swipeToRefresh);
    }

    private void loadTweets(String maxId, String sinceId, final boolean showLoadingUI,
                            final boolean swipeToRefresh) {
        if(showLoadingUI){
            mTweetsView.setLoadingIndicator(true);
        }

        mTweetsRepository.getMoreTweets(maxId, sinceId, new TweetsDataSource.LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                if(!mTweetsView.isActive()) return;

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);

                if(tweets.isEmpty()){
                    mTweetsView.showNoTweets();
                }else{
                    if(swipeToRefresh)
                        mTweetsView.showNewTweetsSinceLastLoad(tweets);
                    else
                        mTweetsView.showMoreTweets(tweets);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);
                if(!mTweetsView.isActive())
                    return;

                mTweetsView.showLoadingTweetsError();
            }
        });
    }

    private void loadTweets(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI){
            mTweetsView.setLoadingIndicator(true);
        }

        if(forceUpdate){
            mTweetsRepository.refreshTweets();
        }

        mTweetsRepository.getMoreTweets(new TweetsDataSource.LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {

                if(!mTweetsView.isActive()) return;

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);

                processTweets(tweets);
            }

            @Override
            public void onDataNotAvailable() {

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);

                if(!mTweetsView.isActive())
                    return;

                mTweetsView.showLoadingTweetsError();

            }
        });
    }

    private void processTweets(List<Tweet> tweets) {
        if(tweets.isEmpty()){
            mTweetsView.showNoTweets();
        }else{
            mTweetsView.showTweets(tweets);
        }
    }

    @Override
    public void composeNewTweet() {

    }
}
