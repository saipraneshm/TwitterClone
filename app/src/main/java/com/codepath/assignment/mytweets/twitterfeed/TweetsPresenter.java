package com.codepath.assignment.mytweets.twitterfeed;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.fragment.ComposeTweetDialog;
import com.codepath.assignment.mytweets.model.Tweet;

import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public class TweetsPresenter implements TweetsContract.Presenter {

    private final TweetsRepository mTweetsRepository;
    private final TweetsContract.View mTweetsView;
    private boolean mFirstLoad = true;
    private static final String TAG = TweetsRepository.class.getSimpleName();

    private static final String REQUEST_COMPOSE_TWEET = "requestComposeTweet";
    private static final int COMPOSE_TWEET_REQUEST_CODE = 200;
    private boolean hasInternet = true;


    public TweetsPresenter(@NonNull TweetsRepository tweetsRepository,
                           @NonNull TweetsContract.View tweetsView){
        mTweetsRepository = tweetsRepository;
        mTweetsView = tweetsView;
        mTweetsView.setPresenter(this);
    }


    @Override
    public void start() {
      //  mTweetsRepository.refreshTweets();
        Log.d(TAG,"Start called");
        loadMoreTweets(null, null, false);
    }



    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == COMPOSE_TWEET_REQUEST_CODE && data != null){
            String message = data.getStringExtra(ComposeTweetDialog.EXTRA_TWEET_MESSAGE);
            boolean saveAsDraft = data.getBooleanExtra(ComposeTweetDialog.EXTRA_SAVE_AS_DRAFT,false);
            if(!saveAsDraft){

                mTweetsRepository.postTweet(message, new TweetsDataSource.GetTweetCallback() {
                    @Override
                    public void onTweetLoaded(Tweet tweet) {
                        mTweetsView.postNewTweetToTimeline(tweet);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mTweetsView.showNoTweets();
                    }
                });
            }
        }

    }

    @Override
    public void loadTweets(boolean forceUpdate) {
       // loadTweets(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreTweets(String maxId, String sinceId, boolean swipeToRefresh) {
        Log.d(TAG,"loadMoreTweets called");
        loadTweets(maxId, sinceId, true, swipeToRefresh);
    }

    private void loadTweets(String maxId, String sinceId, final boolean showLoadingUI,
                            final boolean swipeToRefresh) {
        if(showLoadingUI){
            Log.d(TAG,"setting loading indicator to true");
            mTweetsView.setLoadingIndicator(true);
        }

        Log.d(TAG,"making network call");
        mTweetsRepository.getTweets(maxId, sinceId, new TweetsDataSource.LoadTweetsCallback() {
            @Override
            public void onTweetsLoaded(List<Tweet> tweets) {
                Log.d(TAG,"inside tweetsrepo loadTweets " + tweets);
                if(!mTweetsView.isActive()) return;

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);
                if(tweets.isEmpty()){
                    mTweetsView.showNoTweets();
                }else{
                    if(swipeToRefresh){
                       // mTweetsRepository.refreshTweets();
                        mTweetsView.showNewTweetsSinceLastLoad(tweets);
                    }
                    else{
                        mTweetsView.showMoreTweets(tweets);
                    }

                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG,"inside tweetsrepo loadTweets not available");
                if(!mTweetsView.isActive())
                    return;

                if(showLoadingUI) mTweetsView.setLoadingIndicator(false);
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
        mTweetsView.showComposeTweetDialog(COMPOSE_TWEET_REQUEST_CODE,REQUEST_COMPOSE_TWEET);
    }

    @Override
    public void internetStatus(boolean hasInternet) {
        this.hasInternet = hasInternet;
        mTweetsRepository.internetStatus(hasInternet);
    }

    @Override
    public void saveTweets(List<Tweet> tweets) {
        mTweetsRepository.saveAllTweets(tweets);
    }

    @Override
    public void showTweetDetailScreen(Tweet tweet) {
        mTweetsView.showTweetDetailScreen(tweet);
    }
}
