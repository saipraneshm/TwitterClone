package com.codepath.assignment.mytweets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by saip92 on 9/28/2017.
 */

public class Url {
    @SerializedName("urls")
    @Expose
    private List<Url_> urls = null;

    public List<Url_> getUrls() {
        return urls;
    }

    public void setUrls(List<Url_> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Url{" +
                "urls=" + urls +
                '}';
    }
}
