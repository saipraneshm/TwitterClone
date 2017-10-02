package com.codepath.assignment.mytweets.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.Log;

import com.codepath.assignment.mytweets.data.local.TweetsDatabase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Table(database = TweetsDatabase.class)
public class Tweet extends BaseModel implements Parcelable {

        @SerializedName("truncated")
        @Expose
        private Boolean truncated;

        @Column
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        @SerializedName("favorited")
        @Expose
        private Boolean favorited;

        @PrimaryKey
        @SerializedName("id_str")
        @Expose
        private String idStr;
        @SerializedName("in_reply_to_user_id_str")
        @Expose
        private Object inReplyToUserIdStr;

        @Column
        @SerializedName("text")
        @Expose
        private String text;

        @SerializedName("contributors")
        @Expose
        private Object contributors;

        @Column
        @PrimaryKey
        @SerializedName("id")
        @Expose
        private Long id;

        @Column
        @SerializedName("retweet_count")
        @Expose
        private Integer retweetCount;

        @SerializedName("in_reply_to_status_id_str")
        @Expose
        private Object inReplyToStatusIdStr;

        @SerializedName("geo")
        @Expose
        private Object geo;

        @SerializedName("retweeted")
        @Expose
        private Boolean retweeted;

        @SerializedName("in_reply_to_user_id")
        @Expose
        private Object inReplyToUserId;

        @SerializedName("place")
        @Expose
        private Object place;

        @SerializedName("source")
        @Expose
        private String source;

        @Column
        @ForeignKey(saveForeignKeyModel = true)
        @SerializedName("user")
        @Expose
        private User user;

        @SerializedName("in_reply_to_screen_name")
        @Expose
        private Object inReplyToScreenName;
        @SerializedName("in_reply_to_status_id")
        @Expose
        private Object inReplyToStatusId;

        @SerializedName("entities")
        @Expose
        private Entities entities;

        @Column
        @SerializedName("favorite_count")
        @Expose
        private Integer favoriteCount;


        public Integer getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(Integer favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public Entities getEntities() {
                return entities;
            }

            public void setEntities(Entities entities) {
                this.entities = entities;
            }

        public Boolean getTruncated() {
                return truncated;
            }

        public void setTruncated(Boolean truncated) {
            this.truncated = truncated;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Boolean getFavorited() {
            return favorited;
        }

        public void setFavorited(Boolean favorited) {
            this.favorited = favorited;
        }

        public String getIdStr() {
            return idStr;
        }

        public void setIdStr(String idStr) {
            this.idStr = idStr;
        }

        public Object getInReplyToUserIdStr() {
            return inReplyToUserIdStr;
        }

        public void setInReplyToUserIdStr(Object inReplyToUserIdStr) {
            this.inReplyToUserIdStr = inReplyToUserIdStr;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Object getContributors() {
            return contributors;
        }

        public void setContributors(Object contributors) {
            this.contributors = contributors;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getRetweetCount() {
            return retweetCount;
        }

        public void setRetweetCount(Integer retweetCount) {
            this.retweetCount = retweetCount;
        }

        public Object getInReplyToStatusIdStr() {
            return inReplyToStatusIdStr;
        }

        public void setInReplyToStatusIdStr(Object inReplyToStatusIdStr) {
            this.inReplyToStatusIdStr = inReplyToStatusIdStr;
        }

        public Object getGeo() {
            return geo;
        }

        public void setGeo(Object geo) {
            this.geo = geo;
        }

        public Boolean getRetweeted() {
            return retweeted;
        }

        public void setRetweeted(Boolean retweeted) {
            this.retweeted = retweeted;
        }

        public Object getInReplyToUserId() {
            return inReplyToUserId;
        }

        public void setInReplyToUserId(Object inReplyToUserId) {
            this.inReplyToUserId = inReplyToUserId;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

       public Object getInReplyToScreenName() {
            return inReplyToScreenName;
        }

        public void setInReplyToScreenName(Object inReplyToScreenName) {
            this.inReplyToScreenName = inReplyToScreenName;
        }

        public Object getInReplyToStatusId() {
            return inReplyToStatusId;
        }

        public void setInReplyToStatusId(Object inReplyToStatusId) {
            this.inReplyToStatusId = inReplyToStatusId;
        }

        public String getRelativeTimeAgo(){
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);

            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(createdAt).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
                String[] time = relativeDate.split(" ");
                Log.d("REALTIVEDATE", time[0] + " " + time[1]);
                return time[0] + time[1].charAt(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return relativeDate;
        }

        public String getDetailScreenTimeFormat(){
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("h:mm a - d MMM yy",Locale.ENGLISH);
            sf.setLenient(true);
            String formatedDate = "";
            try {
                Date date = sf.parse(createdAt);
                formatedDate = targetFormat.format(date);

                Log.d("REALTIVEDATE",  formatedDate);
                return formatedDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formatedDate;
        }


    @Override
    public String toString() {
        return "Tweet{" +
                "id=" +idStr +
                "user=" + user.toString() +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeString(this.idStr);
        dest.writeString(this.text);
        dest.writeValue(this.id);
        dest.writeValue(this.retweetCount);
        dest.writeParcelable(this.user, flags);
        dest.writeValue(this.favoriteCount);
    }

    public Tweet() {
    }

    protected Tweet(Parcel in) {
        this.createdAt = in.readString();
        this.idStr = in.readString();
        this.text = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.retweetCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.favoriteCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
