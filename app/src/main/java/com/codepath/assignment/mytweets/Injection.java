package com.codepath.assignment.mytweets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.codepath.assignment.mytweets.data.TweetsRepository;
import com.codepath.assignment.mytweets.data.local.TweetsLocalDataSource;
import com.codepath.assignment.mytweets.data.remote.TweetsRemoteDataSource;

/**
 * Created by saip92 on 9/29/2017.
 */

public class Injection {

    public static TweetsRepository provideTweetsRepository(){
        return TweetsRepository.getInstance(TweetsRemoteDataSource.getInstance(),
                TweetsLocalDataSource.getInstance());
    }


}
