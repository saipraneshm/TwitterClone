package com.codepath.assignment.mytweets.util.network;

import android.util.Log;

import com.codepath.assignment.mytweets.util.TwitterApp;
import com.codepath.assignment.mytweets.data.model.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saip92 on 9/27/2017.
 */

public class TwitterApiController {


    public void getTwitterResponse(){
        TwitterAPIClient client = TwitterApp.getRetrofit().create(TwitterAPIClient.class);
        final Call<List<Tweet>> response = client.getResponse();
        response.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                List<Tweet> arrayList = response.body();
                if(arrayList != null && arrayList.size() > 0)
                    for(Tweet res : arrayList){
                        Log.d("RESPONSE",res.toString());
                    }
                //Log.d("RESPONSE", arrayList.toString() + " , " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Log.e("RESPONSE","Failure",t);
            }
        });
    }


}
