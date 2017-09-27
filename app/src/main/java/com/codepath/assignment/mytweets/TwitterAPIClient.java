package com.codepath.assignment.mytweets;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by saip92 on 9/26/2017.
 */

public interface TwitterAPIClient {

    @GET("statuses/home_timeline.json")
    Call<List<TwitterResponse>> getResponse();

}
