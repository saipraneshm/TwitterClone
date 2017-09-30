package com.codepath.assignment.mytweets.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.twitterfeed.TwitterFeedActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.TwitterSessionVerifier;


public class MainActivity extends AppCompatActivity {


    TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginButton = (TwitterLoginButton) findViewById(R.id.btnTwitterLogin);

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if(session != null){
            startActivity(TwitterFeedActivity.getIntent(MainActivity.this));
        }

        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                startActivity(TwitterFeedActivity.getIntent(MainActivity.this));
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("RESPONSE","sign in failure");
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginButton.onActivityResult(requestCode,resultCode,data);
    }


}
