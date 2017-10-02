package com.codepath.assignment.mytweets.twitterfeed;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.FragmentTwitterFeedBinding;

import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.util.ComposeTweetDialog;
import com.codepath.assignment.mytweets.util.ConnectivityBroadcastReceiver;
import com.codepath.assignment.mytweets.twitterdetailscreen.TwitterDetailActivity;
import com.codepath.assignment.mytweets.util.AppUtils;
import com.codepath.assignment.mytweets.util.EndlessRecyclerViewScrollListener;
import com.codepath.assignment.mytweets.util.ItemClickSupport;
import com.codepath.assignment.mytweets.util.VisibleFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFeedFragment extends VisibleFragment implements TweetsContract.View {


    private static final String TAG = TwitterFeedFragment.class.getSimpleName();
    private TweetsContract.Presenter mPresenter;

    private TwitterFeedAdapter mAdapter;

    FragmentTwitterFeedBinding mTwitterFeedBinding;

    private EndlessRecyclerViewScrollListener mScrollListener;

    private LinkedList<Tweet> mTweets = new LinkedList<>();
    private boolean hasInternet = true;


    public TwitterFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new TwitterFeedAdapter(getActivity(),mTweets);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected BroadcastReceiver createConnectivityBroadcastReceiver() {
        ConnectivityBroadcastReceiver receiver = new ConnectivityBroadcastReceiver();
        receiver.setListener(new ConnectivityBroadcastReceiver.OnNetworkChangeListener() {
            @Override
            public void onNetworkChange(boolean isConnected) {
                if(hasInternet == isConnected){
                    return;
                }
                if(isConnected){
                    hasInternet = true;
                    showInternetConnectedSnackBar();
                    //Log.d(TAG,"Connected to the internet");
                }else{
                  //  mPresenter.saveTweets(mTweets);
                    hasInternet = false;
                    AppUtils.showNoInternetDialog(getActivity());
                    //Log.d(TAG,"Is not connected to the internet");
                }

                mPresenter.internetStatus(hasInternet);
            }
        });


        return receiver;
    }

    private void showInternetConnectedSnackBar() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, " OnSaveInstanceState");
       // mPresenter.saveTweets(mTweets);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode,resultCode,data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTwitterFeedBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_twitter_feed,container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTwitterFeedBinding.rvTwitterFeed.setLayoutManager(layoutManager);
        mTwitterFeedBinding.rvTwitterFeed.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);

        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(hasInternet)
                    mPresenter.loadMoreTweets(mTweets.get(mTweets.size()-1).getIdStr()
                           ,1 + "", false);
            }
        };

        mTwitterFeedBinding.rvTwitterFeed.addOnScrollListener(mScrollListener);
        mTwitterFeedBinding.rvTwitterFeed.addItemDecoration(itemDecoration);

        ItemClickSupport.addTo(mTwitterFeedBinding.rvTwitterFeed).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mPresenter.showTweetDetailScreen(mAdapter.getTweet(position));
            }
        });

        mTwitterFeedBinding.swipeContainer.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG,"calling onRefresh");
                if(hasInternet){
                    if(mTweets != null && mTweets.size() > 0){
                        mPresenter.loadMoreTweets(null,mTweets.getFirst().getIdStr(),true);
                    }else{

                        mPresenter.start();
                    }
                }
                else
                    mPresenter.loadMoreTweets(null,null,true);

            }
        });

        mTwitterFeedBinding.swipeContainer.setColorSchemeColors
                (ContextCompat.getColor(getActivity(),android.R.color.holo_blue_bright),
                        ContextCompat.getColor(getActivity(),android.R.color.holo_green_light),
                        ContextCompat.getColor(getActivity(),android.R.color.holo_orange_light),
                        ContextCompat.getColor(getActivity(),android.R.color.holo_red_light));



        mPresenter.start();

        return mTwitterFeedBinding.getRoot();
    }



    @Override
    public void setPresenter(TweetsContract.Presenter presenter) {
        if(presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mTwitterFeedBinding.swipeContainer.setRefreshing(active);
    }

    @Override
    public void showTweets(List<Tweet> tweets) {
        mAdapter.addAllTweets(tweets);
    }

    @Override
    public void showComposeTweetDialog(int requestCode, String tag) {

        Log.d(TAG, "composing new tweet dialog");
        ComposeTweetDialog tweetDialog = new ComposeTweetDialog();
        tweetDialog.setTargetFragment(TwitterFeedFragment.this, requestCode);
        tweetDialog.show(getFragmentManager(),tag);
    }

    @Override
    public void showLoadingTweetsError() {

    }

    @Override
    public void showNoTweets() {

    }

    @Override
    public void showNewTweetsSinceLastLoad(List<Tweet> tweets) {
        Log.d(TAG,"showNewTweets has been called with" + tweets);
        mAdapter.addAllToFirst(tweets);
    }

    @Override
    public void showSuccessfullyPostedTweetMessage() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void showMoreTweets(List<Tweet> tweets) {
        mAdapter.appendTweets(tweets);
    }

    @Override
    public void postNewTweetToTimeline(Tweet tweet) {
        mAdapter.addToFirst(tweet);
        mTwitterFeedBinding.rvTwitterFeed.smoothScrollToPosition(0);
    }

    @Override
    public void showTweetDetailScreen(Tweet tweet) {
        startActivity(TwitterDetailActivity.newIntent(getActivity(),tweet));
    }

    @Override
    public void refreshTweets() {
        mAdapter.clear();
    }

}
