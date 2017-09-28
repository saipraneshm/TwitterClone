package com.codepath.assignment.mytweets.network;

import android.util.Log;

import com.codepath.assignment.mytweets.application.TwitterApp;
import com.codepath.assignment.mytweets.model.TwitterResponse;

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
        final Call<List<TwitterResponse>> response = client.getResponse();
        response.enqueue(new Callback<List<TwitterResponse>>() {
            @Override
            public void onResponse(Call<List<TwitterResponse>> call, Response<List<TwitterResponse>> response) {
                List<TwitterResponse> arrayList = response.body();
                if(arrayList != null && arrayList.size() > 0)
                    for(TwitterResponse res : arrayList){
                        Log.d("RESPONSE",res.toString());
                    }
                //Log.d("RESPONSE", arrayList.toString() + " , " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<TwitterResponse>> call, Throwable t) {
                Log.e("RESPONSE","Failure",t);
            }
        });
    }
}
