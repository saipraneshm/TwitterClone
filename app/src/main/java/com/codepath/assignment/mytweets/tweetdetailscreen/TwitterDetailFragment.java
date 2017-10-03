package com.codepath.assignment.mytweets.tweetdetailscreen;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.FragmentTwitterDetailBinding;
import com.codepath.assignment.mytweets.data.model.Tweet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwitterDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterDetailFragment extends Fragment implements TweetDetailContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TWEET = "ARG_TWEET";
    private static final String TAG = TwitterDetailFragment.class.getSimpleName();

    private TweetDetailContract.Presenter mPresenter;

    private FragmentTwitterDetailBinding mTwitterDetailBinding;

    // TODO: Rename and change types of parameters
    private Tweet mTweet;


    public TwitterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename and change types and number of parameters
    public static TwitterDetailFragment newInstance(Tweet tweet) {
        TwitterDetailFragment fragment = new TwitterDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TWEET,tweet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTweet = getArguments().getParcelable(ARG_TWEET);
            Log.d(TAG,"Got the tweet: " + mTweet);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTwitterDetailBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_twitter_detail,container,false);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mTwitterDetailBinding.toolbar);

        mPresenter.start();
        // Inflate the layout for this fragment
        return mTwitterDetailBinding.getRoot();
    }

    @Override
    public void setPresenter(TweetDetailContract.Presenter presenter) {
        if(presenter != null){
            Log.d(TAG,"Presenter is not null");
            mPresenter = presenter;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showName(String name) {
        mTwitterDetailBinding.tvName.setText(name);
    }

    @Override
    public void showUsername(String username) {
        mTwitterDetailBinding.tvUserName.setText(username);
    }

    @Override
    public void showTweetContent(String description) {
        mTwitterDetailBinding.tvTweetContent.setText(description);
    }

    @Override
    public void showProfileImage(String url) {
        Glide.with(getActivity())
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(mTwitterDetailBinding.ivAvatar){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmap
                                = RoundedBitmapDrawableFactory.create(getResources(),resource);
                        circularBitmap.setCircular(true);
                        mTwitterDetailBinding.ivAvatar.setImageDrawable(circularBitmap);
                    }
                });
    }

    @Override
    public void showReTweetCount(String count) {
        mTwitterDetailBinding.imgBtnReTweet.setText(count);
    }

    @Override
    public void showFavoritesCount(String favCount) {
        mTwitterDetailBinding.imgBtnLike.setText(favCount);
    }

    @Override
    public void showTweetLoadingError() {

    }

    @Override
    public void showPublishedTime(String time) {
        mTwitterDetailBinding.tvTweetTime.setText(time);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
