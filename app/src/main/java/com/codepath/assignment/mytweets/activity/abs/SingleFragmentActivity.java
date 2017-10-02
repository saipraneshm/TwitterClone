package com.codepath.assignment.mytweets.activity.abs;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.assignment.mytweets.R;
import com.codepath.assignment.mytweets.databinding.ActivityContainerBinding;

/**
 * Created by saip92 on 9/29/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {


    ViewDataBinding mBinding;

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        }

    }

    protected abstract Fragment createFragment();

    protected ViewDataBinding getBinding(){
        return mBinding;
    }


}

