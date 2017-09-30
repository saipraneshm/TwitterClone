package com.codepath.assignment.mytweets.twitterfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codepath.assignment.mytweets.Injection;
import com.codepath.assignment.mytweets.activity.abs.SingleFragmentActivity;


public class TwitterFeedActivity extends SingleFragmentActivity {

    TweetsPresenter mTweetsPresenter;

    TwitterFeedFragment mTwitterFeedFragment;

    @Override
    protected Fragment createFragment() {
        return mTwitterFeedFragment;
    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, TwitterFeedActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mTwitterFeedFragment = new TwitterFeedFragment();
        mTweetsPresenter = new TweetsPresenter(Injection.provideTweetsRepository(),
                mTwitterFeedFragment);
        super.onCreate(savedInstanceState);
    }
}
