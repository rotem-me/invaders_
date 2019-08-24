package com.example.myinvadersfinal;

import android.content.Context;
import android.widget.RelativeLayout;

public class MyLayout extends RelativeLayout {

    int resId;

    public MyLayout(Context context, int resId) {
        super(context);
        setBackgroundResource(resId);
    }
}
