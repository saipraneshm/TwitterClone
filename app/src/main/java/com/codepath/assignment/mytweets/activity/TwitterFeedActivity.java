package com.codepath.assignment.mytweets.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codepath.assignment.mytweets.activity.abs.SingleFragmentActivity;
import com.codepath.assignment.mytweets.fragment.TwitterFeedFragment;


public class TwitterFeedActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TwitterFeedFragment();
    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, TwitterFeedActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
