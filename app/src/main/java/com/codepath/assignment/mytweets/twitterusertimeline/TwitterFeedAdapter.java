package com.codepath.assignment.mytweets.twitterusertimeline;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.TweetItemLayoutBinding;
import com.codepath.assignment.mytweets.data.model.Tweet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public class TwitterFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinkedList<Tweet> mTweets;

    private Context mContext;

    TwitterFeedAdapter(Context context, LinkedList<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        TweetItemLayoutBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.tweet_item_layout, parent,false);
        return new TwitterFeedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TwitterFeedViewHolder)holder).bind(mTweets.get(position));
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void appendTweets(List<Tweet> tweets){
        int oldSize = mTweets.size();
        mTweets.addAll(tweets);
        notifyItemRangeChanged(oldSize, mTweets.size());
    }

    public void addAllTweets(List<Tweet> tweets){
        if(tweets.size() > 0){
            mTweets.clear();
            mTweets.addAll(tweets);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addAllToFirst(List<Tweet> tweets){
       for(int i = tweets.size() - 1; i >=0 ; i--){
           addToFirst(tweets.get(i));
       }
      // notifyItemRangeInserted(0,tweets.size());
        notifyDataSetChanged();
    }

    public void addToFirst(Tweet tweet){
        mTweets.addFirst(tweet);
        notifyItemInserted(0);
    }

    public Tweet getTweet(int position) {
        return mTweets.get(position);
    }


    private class TwitterFeedViewHolder extends RecyclerView.ViewHolder{

        private final TweetItemLayoutBinding binding;

        public TwitterFeedViewHolder(TweetItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tweet tweet){

            Glide.with(mContext)
                    .load(tweet.getUser().getProfileImageUrl().replace("normal","bigger"))
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(binding.ivAvatar){
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmap
                                    = RoundedBitmapDrawableFactory.create(mContext.getResources(),resource);
                            circularBitmap.setCircular(true);
                            binding.ivAvatar.setImageDrawable(circularBitmap);
                        }
                    });

            binding.tvUserName.setText(String.format("@%s", tweet.getUser().getScreenName()));
            binding.tvName.setText(tweet.getUser().getName());
            binding.imgBtnLike.setText(String.valueOf(tweet.getFavoriteCount()));
            binding.imgBtnReTweet.setText(String.valueOf(tweet.getRetweetCount()));
            binding.tvTweetTime.setText(tweet.getRelativeTimeAgo());
            //Log.d("REALTIVEDATE",tweet.getRelativeTimeAgo());
         //   binding.imgBtnComments.setText(tweet.getUser().getStatusesCount());
            binding.tvTweetContent.setText(tweet.getText());
            binding.executePendingBindings();
        }
    }

}
