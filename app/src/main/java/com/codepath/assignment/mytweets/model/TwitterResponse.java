package com.codepath.assignment.mytweets.model;

import com.codepath.assignment.mytweets.data.local.TweetsDatabase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = TweetsDatabase.class)
public class TwitterResponse extends BaseModel {

        @SerializedName("truncated")
        @Expose
        private Boolean truncated;
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


    @Override
    public String toString() {
        return "TwitterResponse{" +
                "user=" + user.toString() +
                '}';
    }


}
