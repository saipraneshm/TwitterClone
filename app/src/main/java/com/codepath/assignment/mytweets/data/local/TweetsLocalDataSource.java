package com.codepath.assignment.mytweets.data.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.assignment.mytweets.data.TweetsDataSource;
import com.codepath.assignment.mytweets.data.model.Tweet;
import com.codepath.assignment.mytweets.data.model.TweetMessage;
import com.codepath.assignment.mytweets.data.model.TweetMessage_Table;
import com.codepath.assignment.mytweets.data.model.Tweet_Table;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;
import java.util.UUID;

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
    public void getTweets(String maxId, String sinceId, @NonNull final LoadTweetsCallback callback) {
        /*SQLite.select()
                .from(Tweet.class)
                .async().queryListResultCallback
                (new QueryTransaction.QueryResultListCallback<Tweet>() {
                    @Override
                    public void onListQueryResult
                            (QueryTransaction transaction, @NonNull List<Tweet> tResult) {
                        if(!tResult.isEmpty()){
                            Log.d(TAG,"Loaded results from local database : " + tResult);
                            callback.onTweetsLoaded(tResult);
                        }
                        else{
                            Log.d(TAG,"couldn't load results from localdatasource ");
                            callback.onDataNotAvailable();
                        }

                    }
                });*/

        List<Tweet> tweets = SQLite.select().from(Tweet.class).queryList();
        if(tweets.size() > 0){
            callback.onTweetsLoaded(tweets);
        }else{
            callback.onDataNotAvailable();
        }
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
        SQLite.delete(Tweet.class)
                .async()
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        Log.d(TAG,"Tweets deleted from database");
                    }
                }).error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        Log.d(TAG,"Tweets were not deleted from the database");
                    }
                })
                .execute();
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

    @Override
    public void saveAllTweets(final List<Tweet> tweets) {
        /*FastStoreModelTransaction<Tweet> tweetFastStoreModelTransaction = FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(Tweet.class))
                .addAll(tweets)
                .build();

        DatabaseDefinition database = FlowManager.getDatabase(TweetsDatabase.class);
        database.beginTransactionAsync(tweetFastStoreModelTransaction)
                .build().execute();*/

        DatabaseDefinition database = FlowManager.getDatabase(TweetsDatabase.class);
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for(Tweet tweet : tweets){
                    Tweet check = SQLite.select()
                            .from(Tweet.class)
                            .where(Tweet_Table.idStr.is(tweet.getIdStr())).querySingle();
                    if(check == null){
                        tweet.save();
                    }else{
                        tweet.delete();
                        check.save();
                    }
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.d(TAG,"Tweets are Saved successfully");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG,"Tweets Couldn't be saved" ,error);
            }
        }).build();

        transaction.execute(); // execute
    }

    @Override
    public void internetStatus(boolean hasInternet) {

    }

    @Override
    public void storeTweetMessage(final String userId, final String message) {

        DatabaseDefinition database = FlowManager.getDatabase(TweetsDatabase.class);
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                TweetMessage tweetMessage = new TweetMessage();
                tweetMessage.setTweetId(UUID.randomUUID().toString());
                tweetMessage.setMessage(message);
                tweetMessage.setUserId(userId);
                Log.d(TAG,message + " " + userId);
                tweetMessage.save();
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.d(TAG,"TweetMessage Saved successfully");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG,"TweetMessage Couldn't be saved" ,error);
            }
        }).build();

        transaction.execute(); // execute
    }

    @Override
    public void getTweetMessage(String userId, @NonNull final GetTweetMessageCallback callback) {
        /*SQLite.select()
                .from(TweetMessage.class)
                .where(TweetMessage_Table.userId.is(userId))
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<TweetMessage>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction,
                                                  @NonNull List<TweetMessage> tResult) {
                        Log.d(TAG,tResult + " Got this from the database");
                        if(tResult.size() == 0){
                            callback.onDataNotAvailable();
                        }else{
                            callback.onTweetMessageLoaded(tResult);
                        }
                    }
                }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.d(TAG,"successfull fetched tweet messages from the database");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG,"unable to fetch messages from the database");
            }
        });*/

        List<TweetMessage> messages = SQLite.select()
                .from(TweetMessage.class)
                .where(TweetMessage_Table.userId.is(userId))
                .queryList();

        if(messages.size() > 0){
            callback.onTweetMessageLoaded(messages);
        }else{
            callback.onDataNotAvailable();
        }

        Log.d(TAG,"List of messages from db: " + messages);

    }

    @Override
    public void deleteTweetMessage(TweetMessage tweetMessage) {
        tweetMessage.delete();
    }
}
