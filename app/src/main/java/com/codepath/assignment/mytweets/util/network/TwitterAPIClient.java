package com.codepath.assignment.mytweets.util.network;


import com.codepath.assignment.mytweets.data.model.Tweet;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by saip92 on 9/26/2017.
 */

public interface TwitterAPIClient {

    @GET("statuses/home_timeline.json")
    Call<List<Tweet>> getResponse();

    @POST("statuses/update.json")
    Call<Tweet> postResponse(@Query("status") String body);

    @GET("statuses/home_timeline.json")
    Call<List<Tweet>> getResponse(@QueryMap Map<String, String> queryParams);

}
