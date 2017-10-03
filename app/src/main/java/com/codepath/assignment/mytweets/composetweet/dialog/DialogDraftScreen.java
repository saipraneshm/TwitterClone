package com.codepath.assignment.mytweets.composetweet.dialog;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.data.model.TweetMessage;
import com.codepath.assignment.mytweets.databinding.DialogDraftMessagesBinding;
import com.codepath.assignment.mytweets.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;



public class DialogDraftScreen extends DialogFragment {

    private static final String ARGS_MESSAGES = "ARGS_MESSAGES";
    public static final String EXTRA_SELECTED_TWEET_MESSAGE = "EXTRA_SELECTED_TWEET_MESSAGE";

    private DialogDraftMessagesBinding mDraftMessagesBinding;
    private DraftScreenAdapter mAdapter;
    private List<TweetMessage> mTweetMessages;

    public static DialogDraftScreen newInstance(ArrayList<TweetMessage> messages){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGS_MESSAGES,messages);
        DialogDraftScreen dialogDraftScreen = new DialogDraftScreen();
        dialogDraftScreen.setArguments(bundle);
        return dialogDraftScreen;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getDialog().getWindow() != null)
            getDialog().getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimationLeftAndRight;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);
        if(getArguments() != null){
            mTweetMessages = getArguments().getParcelableArrayList(ARGS_MESSAGES);
            mAdapter = new DraftScreenAdapter(getActivity(),mTweetMessages);
        }else{
            mTweetMessages = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(getDialog().getWindow() != null)
            getDialog().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    private void updateUI() {
        if(mTweetMessages != null){
            mDraftMessagesBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            mDraftMessagesBinding.rvDraftMessages
                    .setLayoutManager(new LinearLayoutManager(getActivity()));
            mDraftMessagesBinding.rvDraftMessages
                    .setAdapter(mAdapter);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);

            ItemClickSupport.addTo(mDraftMessagesBinding.rvDraftMessages).setOnItemClickListener
                    (new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    sendResult(mAdapter.getTweetMessage(position));
                    dismiss();
                }
            });
            mDraftMessagesBinding.rvDraftMessages.addItemDecoration(itemDecoration);
        }

    }

    private void sendResult(TweetMessage tweetMessage) {
        if(getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_TWEET_MESSAGE,tweetMessage);
        getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDraftMessagesBinding = DataBindingUtil
                .inflate(inflater, R.layout.dialog_draft_messages,container,false);

        return mDraftMessagesBinding.getRoot();
    }
}
