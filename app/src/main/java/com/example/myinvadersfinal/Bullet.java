package com.example.myinvadersfinal;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Bullet extends AppCompatImageView {

    int width = 100;
    int height = 100;
    int resId;
    RelativeLayout.LayoutParams layoutParams;

    public Bullet(Context context, int resId) {
        super(context);
        this.resId = resId;
        setBackground(null);
        setScaleType(ScaleType.FIT_XY);
        setImageResource(resId);
        layoutParams = new RelativeLayout.LayoutParams(width, height);
        setLayoutParams(layoutParams);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        this.width = params.width;
        this.height = params.height;
    }
}
