package com.codepath.assignment.mytweets.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.assignment.mytweets.data.local.TweetsDatabase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = TweetsDatabase.class)
public class User extends BaseModel implements Parcelable {

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profile_sidebar_fill_color")
    @Expose
    private String profileSidebarFillColor;
    @SerializedName("profile_background_tile")
    @Expose
    private Boolean profileBackgroundTile;
    @SerializedName("profile_sidebar_border_color")
    @Expose
    private String profileSidebarBorderColor;

    @Column
    @SerializedName("profile_image_url")
    @Expose
    private String profileImageUrl;

    @Column
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @Column
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("follow_request_sent")
    @Expose
    private Boolean followRequestSent;

    @Column
    @SerializedName("id_str")
    @Expose
    private String idStr;
    @SerializedName("is_translator")
    @Expose
    private Boolean isTranslator;
    @SerializedName("profile_link_color")
    @Expose
    private String profileLinkColor;
    @SerializedName("default_profile")
    @Expose
    private Boolean defaultProfile;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("contributors_enabled")
    @Expose
    private Boolean contributorsEnabled;

    @Column
    @SerializedName("favourites_count")
    @Expose
    private Integer favouritesCount;
    @SerializedName("utc_offset")
    @Expose
    private Object utcOffset;
    @SerializedName("profile_image_url_https")
    @Expose
    private String profileImageUrlHttps;

    @PrimaryKey
    @Column
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("listed_count")
    @Expose
    private Integer listedCount;
    @SerializedName("profile_use_background_image")
    @Expose
    private Boolean profileUseBackgroundImage;
    @SerializedName("profile_text_color")
    @Expose
    private String profileTextColor;
    @SerializedName("followers_count")
    @Expose
    private Integer followersCount;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("protected")
    @Expose
    private Boolean _protected;
    @SerializedName("geo_enabled")
    @Expose
    private Boolean geoEnabled;
    @SerializedName("notifications")
    @Expose
    private Boolean notifications;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("profile_background_color")
    @Expose
    private String profileBackgroundColor;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("time_zone")
    @Expose
    private Object timeZone;
    @SerializedName("profile_background_image_url_https")
    @Expose
    private String profileBackgroundImageUrlHttps;
    @SerializedName("statuses_count")
    @Expose
    private Integer statusesCount;
    @SerializedName("profile_background_image_url")
    @Expose
    private String profileBackgroundImageUrl;
    @SerializedName("default_profile_image")
    @Expose
    private Boolean defaultProfileImage;
    @SerializedName("friends_count")
    @Expose
    private Integer friendsCount;
    @SerializedName("following")
    @Expose
    private Boolean following;
    @SerializedName("show_all_inline_media")
    @Expose
    private Boolean showAllInlineMedia;

    @Column
    @SerializedName("screen_name")
    @Expose
    private String screenName;

    @SerializedName("entities")
    @Expose
    private Entities entities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    public Boolean getProfileBackgroundTile() {
        return profileBackgroundTile;
    }

    public void setProfileBackgroundTile(Boolean profileBackgroundTile) {
        this.profileBackgroundTile = profileBackgroundTile;
    }

    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFollowRequestSent() {
        return followRequestSent;
    }

    public void setFollowRequestSent(Boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Boolean getIsTranslator() {
        return isTranslator;
    }

    public void setIsTranslator(Boolean isTranslator) {
        this.isTranslator = isTranslator;
    }

    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }


    public Boolean getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(Boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Object getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Object utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getListedCount() {
        return listedCount;
    }

    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    public Boolean getProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    public String getProfileTextColor() {
        return profileTextColor;
    }

    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getProtected() {
        return _protected;
    }

    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Object getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Object timeZone) {
        this.timeZone = timeZone;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    public Boolean getDefaultProfileImage() {
        return defaultProfileImage;
    }

    public void setDefaultProfileImage(Boolean defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", idStr='" + idStr + '\'' +
                ", url='" + url + '\'' +
                ", entities=" + entities +
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
        dest.writeString(this.name);
        dest.writeString(this.profileImageUrl);
        dest.writeValue(this.id);
        dest.writeString(this.screenName);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.createdAt = in.readString();
        this.idStr = in.readString();
        this.name = in.readString();
        this.profileImageUrl = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.screenName = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
