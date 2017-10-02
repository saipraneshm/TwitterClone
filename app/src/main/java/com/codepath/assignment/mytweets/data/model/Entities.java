package com.codepath.assignment.mytweets.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saip92 on 9/28/2017.
 */

class Entities {
    @SerializedName("url")
    @Expose
    private Url url;

    @SerializedName("description")
    @Expose
    private Description description;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Entities{" +
                "url=" + url +
                '}';
    }
}
