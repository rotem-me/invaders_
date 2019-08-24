package com.example.myinvadersfinal;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Player extends AppCompatImageButton {

    int width = 200;
    int height = 200;
    int resId;
    Context context;
    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    int points = 0;
    private int lives;

    public Player(Context context, int resId, RelativeLayout relativeLayout) {
        super(context);
        setLives(3);
        layoutParams = new RelativeLayout.LayoutParams(width, height);
        this.setLayoutParams(layoutParams);
        this.setImageResource(resId);
        this.context = context;
        this.resId = resId;
        this.relativeLayout = relativeLayout;
        setBackground(null);
        setScaleType(ImageView.ScaleType.FIT_XY);
        relativeLayout.addView(this);
    }

    @Override
    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    @Override
    public boolean performClick() {
        return true;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
