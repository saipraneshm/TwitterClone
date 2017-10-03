package com.codepath.assignment.mytweets.tweetdetailscreen;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.assignment.mytweets.util.Injection;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.ActivityTwitterDetailBinding;
import com.codepath.assignment.mytweets.util.ActivityUtils;
import com.codepath.assignment.mytweets.data.model.Tweet;

public class TwitterDetailActivity extends AppCompatActivity {


    private static final String EXTRA_TWEET = "EXTRA_TWEET";

    private TwitterDetailFragment mTwitterDetailFragment;
    private TweetDetailPresenter mTweetDetailPresenter;
    private ActivityTwitterDetailBinding mBinding;


    public static Intent newIntent(Context context, Tweet tweet){
        Intent intent = new Intent(context, TwitterDetailActivity.class);
        intent.putExtra(EXTRA_TWEET, tweet);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_twitter_detail);
        setSupportActionBar(mBinding.toolbar);
        Tweet tweet = getIntent().getParcelableExtra(EXTRA_TWEET);

        FragmentManager fm = getSupportFragmentManager();
        mTwitterDetailFragment =(TwitterDetailFragment)fm.findFragmentById(R.id.fragment_container);

        if(mTwitterDetailFragment == null){
            mTwitterDetailFragment = TwitterDetailFragment.newInstance(tweet);
            ActivityUtils.addFragmentToActivity(fm,mTwitterDetailFragment,R.id.fragment_container);
        }

        mTweetDetailPresenter = new TweetDetailPresenter(tweet,
                Injection.provideTweetsRepository(),
                mTwitterDetailFragment);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
