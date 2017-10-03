package com.codepath.assignment.mytweets.composetweet.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.data.model.TweetMessage;
import com.codepath.assignment.mytweets.databinding.DraftItemLayoutBinding;

import java.util.List;

/**
 * Created by saip92 on 10/3/2017.
 */

public class DraftScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TweetMessage> mTweetMessages;
    private Context mContext;

    DraftScreenAdapter(Context context, List<TweetMessage> messages){
        mContext = context;
        mTweetMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        DraftItemLayoutBinding draftItemLayoutBinding =
                DataBindingUtil.inflate(inflater, R.layout.draft_item_layout,parent,false);
        return new DraftScreenViewHolder(draftItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DraftScreenViewHolder)holder).bindMessage(mTweetMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mTweetMessages.size();
    }



    private class DraftScreenViewHolder extends RecyclerView.ViewHolder{

        DraftItemLayoutBinding mItemLayoutBinding;

        private DraftScreenViewHolder(DraftItemLayoutBinding itemView) {
            super(itemView.getRoot());
            mItemLayoutBinding = itemView;
        }

        void bindMessage(TweetMessage message){
            mItemLayoutBinding.tvDraftMessage.setText(message.getMessage());
        }
    }
}
