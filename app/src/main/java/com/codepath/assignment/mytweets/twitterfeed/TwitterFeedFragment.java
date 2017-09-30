package com.codepath.assignment.mytweets.twitterfeed;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.FragmentTwitterFeedBinding;
import com.codepath.assignment.mytweets.model.TwitterResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFeedFragment extends Fragment implements TweetsContract.View {


    private TweetsContract.Presenter mPresenter;

    private TwitterFeedAdapter mAdapter;

    FragmentTwitterFeedBinding mTwitterFeedBinding;


    public TwitterFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TwitterFeedAdapter(getActivity(),new ArrayList<TwitterResponse>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode,resultCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTwitterFeedBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_twitter_feed,container, false);

        mTwitterFeedBinding.rvTwitterFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTwitterFeedBinding.rvTwitterFeed.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);

        mTwitterFeedBinding.rvTwitterFeed.addItemDecoration(itemDecoration);

        mPresenter.loadTasks(true);

        return mTwitterFeedBinding.getRoot();
    }

    @Override
    public void setPresenter(TweetsContract.Presenter presenter) {
        if(presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTweets(List<TwitterResponse> tweets) {
        mAdapter.addAllTweets(tweets);
    }

    @Override
    public void showComposeTweetDialog() {

    }

    @Override
    public void showLoadingTweetsError() {

    }

    @Override
    public void showNoTweets() {

    }

    @Override
    public void showSuccessfullyPostedTweetMessage() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
