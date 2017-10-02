package com.codepath.assignment.mytweets.model;

import com.codepath.assignment.mytweets.data.local.TweetsDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by saip92 on 10/1/2017.
 */
@Table(database = TweetsDatabase.class)
public class TweetMessage extends BaseModel{

    @Column
    String message;

    @Column
    String userId;

    @PrimaryKey(autoincrement = true)
    Integer tweetId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
