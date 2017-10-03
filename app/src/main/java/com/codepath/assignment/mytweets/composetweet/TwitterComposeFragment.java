package com.codepath.assignment.mytweets.composetweet;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.FragmentComposeTweetBinding;
import com.twitter.sdk.android.core.TwitterCore;


public class TwitterComposeFragment extends Fragment implements ComposeTweetContract.View {


    private static final String TAG = TwitterComposeFragment.class.getSimpleName();
    private ComposeTweetContract.Presenter mPresenter;
    private FragmentComposeTweetBinding mBinding;
    public static final String EXTRA_TWEET_MESSAGE = "EXTRA_TWEET_MESSAGE";
    private static final int MAX_TWEETS_CHAR = 140;

    public TwitterComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_compose_tweet,container,false);
        mBinding.etTweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int currentCount = MAX_TWEETS_CHAR - charSequence.length();
                mBinding.tvCharCount.setText(String.valueOf(currentCount));
                mBinding.tvCharCount.setTextColor(ContextCompat.getColor(getActivity(),
                        currentCount < 0 ? android.R.color.holo_red_dark
                                : android.R.color.darker_gray));
                if(currentCount<= 0)
                    mBinding.btnSendTweet.setEnabled(false);
                else
                    mBinding.btnSendTweet.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.btnSendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.postTweet(mBinding.etTweetBody.getText().toString());
            }
        });

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.dismissDialog();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().getWindow() != null){
            getActivity().getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(ComposeTweetContract.Presenter presenter) {
        if(presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void showTweetTitle(String title) {
        mBinding.toolbar.setTitle(title);
    }

    @Override
    public void showTweetMessage(String message) {
        mBinding.etTweetBody.setText(message);
    }

    @Override
    public void showTweetUri(Uri Uri) {

    }

    @Override
    public void postTweet(String message) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TWEET_MESSAGE, message);
        getActivity().setResult(Activity.RESULT_OK,intent);
    }

    @Override
    public void closeTweet() {
        getActivity().finish();
    }

    @Override
    public void showSaveAsDraftDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setMessage(R.string.save_draft_message)
                .setPositiveButton(R.string.save_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //sendResult(true);
                        mPresenter.saveTweetToDisk
                                (mBinding.etTweetBody.getText().toString(),
                                        String.valueOf(TwitterCore.getInstance()
                                                .getSessionManager()
                                                .getActiveSession()
                                                .getUserId()));
                       closeFragment();
                    }
                }).setNegativeButton(R.string.delete_message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                closeFragment();
            }
        });

        dialog.create().show();
    }

    @Override
    public void showDraftTweets() {

    }

    @Override
    public void enableUserProfile(boolean enable) {

    }

    @Override
    public void enableSendBtn(boolean enable) {
        mBinding.btnSendTweet.setEnabled(enable);
    }

    @Override
    public void enableDraftsIcon(boolean enable) {

    }

    @Override
    public boolean shouldAskForDialog() {
        return mBinding.etTweetBody.getText().length() > 0;
    }

    @Override
    public void closeFragment() {
        getActivity().finish();
    }
}
