package com.codepath.assignment.mytweets.application;

import android.app.Application;
import android.content.Context;

import com.codepath.assignment.mytweets.network.RetrofitClient;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.twitter.sdk.android.core.Twitter;

import retrofit2.Retrofit;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApp.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApp extends Application {
	private static Retrofit sRetrofit;

	@Override
	public void onCreate() {
		super.onCreate();
		Twitter.initialize(this);

		FlowManager.init(new FlowConfig.Builder(this).build());
		FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
		sRetrofit = new RetrofitClient().getRetrofit();
	}


	public static Retrofit getRetrofit(){
		return sRetrofit;
	}
}