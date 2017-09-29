package com.codepath.assignment.mytweets.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.assignment.mytweets.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFeedFragment extends Fragment {


    public TwitterFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter_feed, container, false);
    }

}
