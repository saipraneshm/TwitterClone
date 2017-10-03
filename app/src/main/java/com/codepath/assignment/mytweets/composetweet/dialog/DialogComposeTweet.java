package com.codepath.assignment.mytweets.composetweet.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.data.model.TweetMessage;
import com.codepath.assignment.mytweets.databinding.FragmentComposeTweetBinding;
import com.codepath.assignment.mytweets.util.Injection;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saip92 on 9/30/2017.
 */

public class DialogComposeTweet extends DialogFragment {


    private static final String TAG = DialogComposeTweet.class.getSimpleName();
    public static final String EXTRA_SAVE_AS_DRAFT = "EXTRA_SAVE_AS_DRAFT";
    public static final String EXTRA_TWEET_MESSAGE = "EXTRA_TWEET_MESSAGE";
    private static final int REQUEST_DRAFT_SCREEN_DIALOG = 252;
    private static final String ARGS_TWEET_MESSAGE = "ARGS_TWEET_MESSAGE";
    public static final String EXTRA_RESPONSE_USER_ID = "EXTRA_RESPONSE_USER_ID";
    private FragmentComposeTweetBinding mComposeTweetBinding;
    private List<TweetMessage> mTweetMessages = null;
    private Tweet mResponseTweet;
    private String mUserId;

    TweetsRepository mTweetsRepository;

    private MenuItem draftItem;
    private MenuItem showProfileItem;

    private boolean isResponseToTweet = false;

    private static final int MAX_TWEETS_CHAR = 140;

    public static DialogComposeTweet newInstance(Tweet tweet){
        Bundle args = new Bundle();
        args.putParcelable(ARGS_TWEET_MESSAGE, tweet);

        DialogComposeTweet composeTweet = new DialogComposeTweet();
        composeTweet.setArguments(args);
        return composeTweet;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getDialog().getWindow() != null)
            getDialog().getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimationUpAndDown;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_AppCompat_Light_DarkActionBar);
        mTweetsRepository = Injection.provideTweetsRepository();
        mUserId = String.valueOf(TwitterCore
                .getInstance()
                .getSessionManager()
                .getActiveSession()
                .getUserId());

        if(getArguments() != null){
            isResponseToTweet = true;
            mResponseTweet = getArguments().getParcelable(ARGS_TWEET_MESSAGE);
        }else{
            isResponseToTweet = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(getDialog().getWindow() != null)
            getDialog().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
        if(isResponseToTweet){
            updateResponseTweetUI();
        }else{
            updateComposeTweetUI();
        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == REQUEST_DRAFT_SCREEN_DIALOG && data != null){
            TweetMessage message = data.getParcelableExtra(DialogDraftScreen.EXTRA_SELECTED_TWEET_MESSAGE);
            if(message != null){
                mComposeTweetBinding.etTweetBody.setText(message.getMessage());
                mTweetsRepository.deleteTweetMessage(message);
                mTweetMessages.remove(message);
                showOrHideDraftMenuIcon();
            }
        }
    }

    private void updateComposeTweetUI() {
        initializeUI();
        mTweetsRepository.getTweetMessage(mUserId, new TweetsDataSource.GetTweetMessageCallback() {
            @Override
            public void onTweetMessageLoaded(List<TweetMessage> tweetMessage) {
                mTweetMessages = tweetMessage;
                Log.d(TAG,tweetMessage + " " + "PLEASE CHECK THIS");
                showOrHideDraftMenuIcon();
            }
            @Override
            public void onDataNotAvailable() {
                mTweetMessages = null;
                Log.d(TAG,"No tweet messages to show " );
                showOrHideDraftMenuIcon();
            }
        });

    }


    private void updateResponseTweetUI() {
        initializeUI();
        showOrHideDraftMenuIcon();
        mComposeTweetBinding.etTweetBody.setHint(R.string.reply_tweet_hint);
        mComposeTweetBinding.btnSendTweet.setText(R.string.reply_to_tweet_btn);
        mComposeTweetBinding.tvResponseUserName.setVisibility(View.VISIBLE);
        mComposeTweetBinding.tvResponseUserName.setText(getString(R.string.response_tweet,
                String.format("@%s", mResponseTweet.getUser().getScreenName())));

    }


    private void initializeUI(){
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

        mComposeTweetBinding.toolbar.inflateMenu(R.menu.menu_compose_dialog);
        mComposeTweetBinding.toolbar.setOnMenuItemClickListener
                (new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        switch(itemId){
                            case R.id.action_show_drafts:
                                showDraftsDialog();
                                break;
                            case R.id.action_show_user_profile:
                                break;
                        }
                        return true;
                    }
                });
        Menu menu = mComposeTweetBinding.toolbar.getMenu();
        draftItem = menu.findItem(R.id.action_show_drafts);
        showProfileItem = menu.findItem(R.id.action_show_user_profile);
    }

    private void showDraftsDialog() {
        DialogDraftScreen dialogDraftScreen = DialogDraftScreen.newInstance
                (new ArrayList<>(mTweetMessages));
        dialogDraftScreen.setTargetFragment(this,REQUEST_DRAFT_SCREEN_DIALOG);
        dialogDraftScreen.show(getFragmentManager(),"DIALOG_DRAFTS_FRAGMENT");
    }

    private void showOrHideDraftMenuIcon() {
        if(mTweetMessages != null && mTweetMessages.size()>0){
            draftItem.setVisible(true);
        }else{
            draftItem.setVisible(false);
        }
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
        if(mResponseTweet != null)
            intent.putExtra(EXTRA_RESPONSE_USER_ID,  mResponseTweet.getIdStr());

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
