package com.codepath.assignment.mytweets.twitterfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.assignment.mytweets.Injection;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.activity.MainActivity;
import com.codepath.assignment.mytweets.activity.abs.SingleFragmentActivity;
import com.codepath.assignment.mytweets.databinding.ActivityContainerBinding;
import com.codepath.assignment.mytweets.fragment.abs.VisibleFragment;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


public class TwitterFeedActivity extends SingleFragmentActivity {

    TweetsPresenter mTweetsPresenter;

    TwitterFeedFragment mTwitterFeedFragment;
    private ActivityContainerBinding mBinding;

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

        mBinding =((ActivityContainerBinding)getBinding());

        setSupportActionBar(mBinding.toolbar);

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
