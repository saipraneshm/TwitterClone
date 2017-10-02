package com.codepath.assignment.mytweets.twitterfeed;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.assignment.mytweets.util.Injection;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.util.ActivityUtils;
import com.codepath.assignment.mytweets.login.MainActivity;
import com.codepath.assignment.mytweets.util.activity.abs.SingleFragmentActivity;
import com.codepath.assignment.mytweets.databinding.ActivityContainerBinding;
import com.twitter.sdk.android.core.TwitterCore;


public class TwitterFeedActivity extends AppCompatActivity {

    TweetsPresenter mTweetsPresenter;

    TwitterFeedFragment mTwitterFeedFragment;
    private ActivityContainerBinding mBinding;


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, TwitterFeedActivity.class);
        return intent;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_container);

        setSupportActionBar(mBinding.toolbar);

        FragmentManager fm = getSupportFragmentManager();
        mTwitterFeedFragment = (TwitterFeedFragment) fm.findFragmentById(R.id.fragment_container);

        if(mTwitterFeedFragment == null){
            mTwitterFeedFragment = new TwitterFeedFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    ,mTwitterFeedFragment
                    ,R.id.fragment_container);
        }

        mTweetsPresenter = new TweetsPresenter(Injection.provideTweetsRepository(),
                mTwitterFeedFragment);

        mBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTweetsPresenter.composeNewTweet();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_logout){
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            Intent intent =new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
