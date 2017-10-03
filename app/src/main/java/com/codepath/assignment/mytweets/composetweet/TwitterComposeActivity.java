package com.codepath.assignment.mytweets.composetweet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.ActivityTwitterDetailBinding;
import com.codepath.assignment.mytweets.util.ActivityUtils;
import com.codepath.assignment.mytweets.util.Injection;

public class TwitterComposeActivity extends AppCompatActivity{


    private ActivityTwitterDetailBinding mBinding;
    private ComposeTweetPresenter mComposeTweetPresenter;

    private TwitterComposeFragment mTwitterComposeFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_twitter_detail);

        FragmentManager fm = getSupportFragmentManager();
        mTwitterComposeFragment = (TwitterComposeFragment)
                fm.findFragmentById(R.id.fragment_container);

        if(mTwitterComposeFragment == null){
            mTwitterComposeFragment = new TwitterComposeFragment();
            ActivityUtils.addFragmentToActivity(fm,mTwitterComposeFragment,R.id.fragment_container);
        }

        mComposeTweetPresenter = new ComposeTweetPresenter
                (Injection.provideTweetsRepository(),
                mTwitterComposeFragment);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)){
                String titleOfPage = intent.getStringExtra(Intent.EXTRA_SUBJECT);
                String urlOfPage = intent.getStringExtra(Intent.EXTRA_TEXT);
                Uri imageUriOfPage = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            }
        }
    }
}
