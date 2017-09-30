package com.codepath.assignment.mytweets.twitterfeed;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.model.TwitterResponse;

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
        loadTasks(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI){
            mTweetsView.setLoadingIndicator(true);
        }

        if(forceUpdate){
            mTweetsRepository.refreshTweets();
        }

        mTweetsRepository.getTweets(new TweetsDataSource.LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<TwitterResponse> tweets) {

                if(!mTweetsView.isActive()) return;

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);

                processTweets(tweets);
            }

            @Override
            public void onDataNotAvailable() {

                if(!mTweetsView.isActive())
                    return;

                mTweetsView.showLoadingTweetsError();

            }
        });
    }

    private void processTweets(List<TwitterResponse> tweets) {
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
