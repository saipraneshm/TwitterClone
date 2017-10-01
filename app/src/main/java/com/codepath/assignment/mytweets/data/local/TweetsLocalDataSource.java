package com.codepath.assignment.mytweets.data.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.model.Tweet;
import com.codepath.assignment.mytweets.model.Tweet_Table;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

/**
 * Created by saip92 on 9/28/2017.
 */

public class TweetsLocalDataSource implements TweetsDataSource {

    private static final String TAG = TweetsLocalDataSource.class.getSimpleName();
    private static TweetsLocalDataSource INSTANCE;

    private TweetsLocalDataSource(){}

    public static TweetsLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TweetsLocalDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getMoreTweets(@NonNull final LoadTweetsCallback callback) {
        SQLite.select()
                .from(Tweet.class)
                .async().queryListResultCallback
                (new QueryTransaction.QueryResultListCallback<Tweet>() {
            @Override
            public void onListQueryResult
                    (QueryTransaction transaction, @NonNull List<Tweet> tResult) {
                if(!tResult.isEmpty())
                    callback.onTweetsLoaded(tResult);
                else
                    callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMoreTweets(String maxId, String sinceId, @NonNull LoadTweetsCallback callback) {

    }

    @Override
    public void getTweet(@NonNull String tweetId, @NonNull final GetTweetCallback callback) {
        SQLite.select()
                .from(Tweet.class)
                .where(Tweet_Table.idStr.is(tweetId))
                .async()
                .querySingleResultCallback
                        (new QueryTransaction.QueryResultSingleCallback<Tweet>() {
                    @Override
                    public void onSingleQueryResult(QueryTransaction transaction,
                                                    @Nullable Tweet tweet) {
                         if(tweet != null){
                             callback.onTweetLoaded(tweet);
                         }else
                             callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void deleteAllTweets() {
        SQLite.delete()
                .from(Tweet.class);
    }

    @Override
    public void saveTweet(final Tweet tweet) {
        DatabaseDefinition database = FlowManager.getDatabase(TweetsDatabase.class);
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                tweet.save();
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.d(TAG,"Tweet Saved successfully" + tweet);
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG,"Tweet Couldn't be saved" + tweet,error);
            }
        }).build();

        transaction.execute(); // execute

    }

    @Override
    public void postTweet(String tweetMessage, GetTweetCallback callback) {

    }

    @Override
    public void refreshTweets() {

    }
}
