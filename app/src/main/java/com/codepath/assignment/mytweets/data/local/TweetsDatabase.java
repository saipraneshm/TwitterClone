package com.codepath.assignment.mytweets.data.local;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by saip92 on 9/28/2017.
 */

@Database(name = TweetsDatabase.NAME, version = TweetsDatabase.VERSION)
public class TweetsDatabase {


    public static final String NAME = "TweetsDatabase";

    public static final int VERSION = 1;

}
