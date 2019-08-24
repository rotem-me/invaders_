package com.example.myinvadersfinal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Enemy extends AppCompatImageButton {

    int width = 300;
    int height = 150;
    int resId;
    Context context;
    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    ObjectAnimator objectAnimator;
    private boolean alive = true;

    public Enemy(Context context, int resId, RelativeLayout relativeLayout) {
        super(context);
        this.resId = resId;
        this.context = context;
        this.relativeLayout = relativeLayout;
        setImageResource(resId);
        setBackground(null);
        setScaleType(ImageView.ScaleType.FIT_XY);
        layoutParams = new RelativeLayout.LayoutParams(width, height);

    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public ObjectAnimator getObjectAnimator() {
        return objectAnimator;
    }

    public void setObjectAnimator(ObjectAnimator objectAnimator) {
        this.objectAnimator = objectAnimator;
    }


}