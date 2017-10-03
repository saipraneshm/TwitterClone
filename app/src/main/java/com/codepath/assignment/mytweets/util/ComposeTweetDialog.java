package com.codepath.assignment.mytweets.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.FragmentComposeTweetBinding;

/**
 * Created by saip92 on 9/30/2017.
 */

public class ComposeTweetDialog extends DialogFragment {


    private static final String TAG = ComposeTweetDialog.class.getSimpleName();
    public static final String EXTRA_SAVE_AS_DRAFT = "EXTRA_SAVE_AS_DRAFT";
    public static final String EXTRA_TWEET_MESSAGE = "EXTRA_TWEET_MESSAGE";
    private FragmentComposeTweetBinding mComposeTweetBinding;

    private boolean valuesChanged = false;

    private static final int MAX_TWEETS_CHAR = 140;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getDialog().getWindow() != null)
            getDialog().getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);

        mComposeTweetBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shouldAskForDialog()){
                    showSaveDraftDialog();
                }else{
                    dismiss();
                }
            }
        });

        mComposeTweetBinding.etTweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int currentCount = MAX_TWEETS_CHAR - charSequence.length();
                mComposeTweetBinding.tvCharCount.setText(String.valueOf(currentCount));
                mComposeTweetBinding.tvCharCount.setTextColor(ContextCompat.getColor(getActivity(),
                                currentCount < 0 ? android.R.color.holo_red_dark
                                        : android.R.color.darker_gray));
                if(currentCount< 0)
                    mComposeTweetBinding.btnSendTweet.setEnabled(false);
                else
                    mComposeTweetBinding.btnSendTweet.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mComposeTweetBinding.btnSendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(false);
                dismiss();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mComposeTweetBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_compose_tweet,container,false);
        return mComposeTweetBinding.getRoot();
    }

    private boolean shouldAskForDialog(){
        return (mComposeTweetBinding.etTweetBody.getText().length() > 0);
    }

    private void showSaveDraftDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setMessage(R.string.save_draft_message)
                .setPositiveButton(R.string.save_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(true);
                        dismiss();
                    }
                }).setNegativeButton(R.string.delete_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dismiss();
                    }
                });

         dialog.create().show();
    }

    private void sendResult(boolean saveAsDraft){
        if(getTargetFragment() == null) return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SAVE_AS_DRAFT, saveAsDraft);
        intent.putExtra(EXTRA_TWEET_MESSAGE, mComposeTweetBinding.etTweetBody.getText() + "");

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
