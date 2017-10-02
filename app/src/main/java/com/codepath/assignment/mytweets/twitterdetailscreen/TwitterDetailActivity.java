package com.codepath.assignment.mytweets.twitterdetailscreen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.assignment.mytweets.Injection;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.activity.abs.SingleFragmentActivity;
import com.codepath.assignment.mytweets.model.Tweet;

public class TwitterDetailActivity extends SingleFragmentActivity {


    private static final String EXTRA_TWEET = "EXTRA_TWEET";

    private TwitterDetailFragment mTwitterDetailFragment;
    private TweetDetailPresenter mTweetDetailPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_twitter_detail;
    }

    public static Intent newIntent(Context context, Tweet tweet){
        Intent intent = new Intent(context, TwitterDetailActivity.class);
        intent.putExtra(EXTRA_TWEET, tweet);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return mTwitterDetailFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Tweet tweet = getIntent().getParcelableExtra(EXTRA_TWEET);
        mTwitterDetailFragment = TwitterDetailFragment.newInstance(tweet);
        mTweetDetailPresenter = new TweetDetailPresenter(tweet,
                Injection.provideTweetsRepository(),
                mTwitterDetailFragment);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
