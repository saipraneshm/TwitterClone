package com.codepath.assignment.mytweets.twitterfeed;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.TweetItemLayoutBinding;
import com.codepath.assignment.mytweets.model.TwitterResponse;

import java.util.List;

/**
 * Created by saip92 on 9/29/2017.
 */

public class TwitterFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TwitterResponse> mTweets;

    private Context mContext;

    TwitterFeedAdapter(Context context, List<TwitterResponse> tweets) {
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

    public void appendTweets(List<TwitterResponse> tweets){
        int oldSize = mTweets.size();
        mTweets.addAll(tweets);
        notifyItemRangeChanged(oldSize, mTweets.size() - oldSize);
    }

    public void addAllTweets(List<TwitterResponse> tweets){
        if(tweets.size() > 0){
            mTweets.clear();
            mTweets.addAll(tweets);
            notifyDataSetChanged();
        }
    }


    private class TwitterFeedViewHolder extends RecyclerView.ViewHolder{

        private final TweetItemLayoutBinding binding;

        public TwitterFeedViewHolder(TweetItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TwitterResponse twitterResponse){
            Glide.with(mContext)
                    .load(twitterResponse.getUser().getProfileImageUrl().replace("normal","bigger"))
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

            binding.tvUserName.setText(String.format("@%s", twitterResponse.getUser().getScreenName()));
            binding.tvName.setText(twitterResponse.getUser().getName());
            binding.imgBtnLike.setText(String.valueOf(twitterResponse.getFavoriteCount()));
            binding.imgBtnReTweet.setText(String.valueOf(twitterResponse.getRetweetCount()));
         //   binding.imgBtnComments.setText(twitterResponse.getUser().getStatusesCount());
            binding.tvTweetContent.setText(twitterResponse.getText());
            binding.executePendingBindings();
        }
    }

}
