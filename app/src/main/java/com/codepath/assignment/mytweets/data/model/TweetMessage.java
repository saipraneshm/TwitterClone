package com.codepath.assignment.mytweets.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.assignment.mytweets.data.local.TweetsDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by saip92 on 10/1/2017.
 */
@Table(database = TweetsDatabase.class)
public class TweetMessage extends BaseModel implements Parcelable {

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


    @Override
    public String toString() {
        return "TweetMessage{" +
                "message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.userId);
        dest.writeValue(this.tweetId);
    }

    public TweetMessage() {
    }

    protected TweetMessage(Parcel in) {
        this.message = in.readString();
        this.userId = in.readString();
        this.tweetId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TweetMessage> CREATOR = new Parcelable.Creator<TweetMessage>() {
        @Override
        public TweetMessage createFromParcel(Parcel source) {
            return new TweetMessage(source);
        }

        @Override
        public TweetMessage[] newArray(int size) {
            return new TweetMessage[size];
        }
    };
}
