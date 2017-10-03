package com.codepath.assignment.mytweets.composetweet;

import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.twitterusertimeline.TweetsContract;

/**
 * Created by saip92 on 10/2/2017.
 */

 class ComposeTweetPresenter implements ComposeTweetContract.Presenter {


    private final TweetsRepository mTweetsRepository;
    private final ComposeTweetContract.View mComposeTweetView;

    private static final String TAG = ComposeTweetPresenter.class.getSimpleName();
    private boolean hasInternet = true;

    ComposeTweetPresenter(@NonNull TweetsRepository tweetsRepository,
                                 @NonNull ComposeTweetContract.View composeTweetView){
        mTweetsRepository = tweetsRepository;
        mComposeTweetView = composeTweetView;
        mComposeTweetView.setPresenter(this);
    }

    @Override
    public void start() {
        mComposeTweetView.enableSendBtn(false);
    }

    @Override
    public void postTweet(String message) {
        mComposeTweetView.postTweet(message);
    }

    @Override
    public void showUserProfile() {

    }

    @Override
    public void saveTweetToDisk(String message, String userId) {
        mTweetsRepository.storeTweetMessage(userId,message);
    }

    @Override
    public void getTweetsFromDisk() {

    }

    @Override
    public void dismissDialog() {
        if(mComposeTweetView.shouldAskForDialog()){
            mComposeTweetView.showSaveAsDraftDialog();
        }else{
            mComposeTweetView.closeFragment();
        }

    }
}
