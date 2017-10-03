package com.codepath.assignment.mytweets.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;

import com.codepath.assignment.mytweets.R;

/**
 * Created by saip92 on 10/3/2017.
 */

public class ThemedSnackBar {

    public static Snackbar make(View view, CharSequence text, int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        snackbar.getView().setBackgroundColor(getAttribute(view.getContext(), R.attr.colorAccent));
        return snackbar;
    }


    public static Snackbar make(View view, int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }


    private static int getAttribute(Context context, int resId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue.data;
    }
}
