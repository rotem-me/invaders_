package com.example.myinvadersfinal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Level extends Activity {

    private RelativeLayout relativeLayout;
    private Player player;
    private View upperScreen;
    private int enemyAnimCounter = 0;
    private boolean actionMove = false;
    private Enemy[] enemies;
    private MediaPlayer ring;
    boolean once = true;

    SharedPreferences pref;
    SharedPreferences levelPref;
    SharedPreferences conceptPref;
    SharedPreferences audioPref;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor levelEditor;

    String audio;

    int Level;


    @Override
    protected void onPause() {
        ring.pause();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Level = bundle.getInt("id");
        Log.i("level", ""+Level);
        pref = getApplicationContext().getSharedPreferences("MyScore", MODE_PRIVATE);
        editor = pref.edit();

        ring = MediaPlayer.create(this, R.raw.background);
        audioPref = getApplicationContext().getSharedPreferences("audioPref", MODE_PRIVATE);
        audio = audioPref.getString("audio", null);
        if(!audio.equals("0")) {
            Log.i("sjbsjkjd", audio);
            ring.start();
        }

        if (pref.getString("num", null) == null) {
            Log.i("num", "is null");
            editor.putString("num", "0");
            editor.commit();
        }

        conceptPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int backgroundDrawable = R.drawable.ic_vision_background;
        if (conceptPref.getString("backgroundPref", null) != null)
            switch (conceptPref.getString("backgroundPref", null)) {
                case "space":
                    backgroundDrawable = R.drawable.ic_vision_background;
                    break;
                case "ocean":
                    backgroundDrawable = R.drawable.ic_purple_turquoise_blue;
                    break;
                case "wild_west":
                    backgroundDrawable = R.drawable.ic_desert;
                    break;
            }

        int playerDrawable = R.drawable.ic_space_player;
        if (conceptPref.getString("playerPref", null) != null)
            switch (conceptPref.getString("playerPref", null)) {
                case "space":
                    playerDrawable = R.drawable.ic_space_player;
                    break;
                case "ocean":
                    playerDrawable = R.drawable.ic_submarine;
                    break;
                case "wild_west":
                    playerDrawable = R.drawable.ic_sheriff;
                    break;
            }

        int enemyDrawable = R.drawable.ic_spaceship_new;
        if (conceptPref.getString("enemyPref", null) != null) {
            switch (conceptPref.getString("enemyPref", null)) {
                case "space":
                    enemyDrawable = R.drawable.ic_spaceship_new;
                    break;
                case "ocean":
                    enemyDrawable = R.drawable.ic_jellyfish;
                    break;
                case "wild_west":
                    enemyDrawable = R.drawable.ic_thief;
                    break;
            }
        }

        relativeLayout = new MyLayout(this, backgroundDrawable);
        upperScreen = getLayoutInflater().inflate(R.layout.upper_screen, null);
        upperScreen.setId(View.generateViewId());
        setContentView(relativeLayout);

        player = new Player(this, playerDrawable, relativeLayout);
        player.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        player.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (Level){
            case 1:
                enemies = new Enemy[3];
                break;
            case 2:
                enemies = new Enemy[4];
                break;
            case 3:
                enemies = new Enemy[5];
                break;
        }

        for (int i = 0; i< enemies.length; i++){
            enemies[i] = new Enemy(this, enemyDrawable, relativeLayout);
            enemies[i].getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            enemies[i].setId(View.generateViewId());
        }

        enemies[0].getLayoutParams().addRule(RelativeLayout.BELOW, upperScreen.getId());
        enemies[1].getLayoutParams().addRule(RelativeLayout.BELOW, enemies[0].getId());
        enemies[2].getLayoutParams().addRule(RelativeLayout.BELOW, enemies[1].getId());
        if (Level >= 2)
            enemies[3].getLayoutParams().addRule(RelativeLayout.BELOW, enemies[2].getId());
        if (Level >= 3)
            enemies[4].getLayoutParams().addRule(RelativeLayout.BELOW, enemies[3].getId());


        relativeLayout.addView(upperScreen);
        for (Enemy enemy : enemies)
            relativeLayout.addView(enemy, enemy.getLayoutParams());

        int j = 0;
        final Handler handler = new Handler();
        for (final Enemy enemy : enemies)
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startEnemyAnimation(enemy);
                }
            }, 1500*j);
            j++;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                return false;
                            case MotionEvent.ACTION_MOVE:
                                actionMove = true;
                                v.setX(event.getRawX() - player.getWidth()/2);
                                v.setY(event.getRawY() - player.getHeight()/2);
                                break;
                            case MotionEvent.ACTION_UP:
                                if (!actionMove){
                                    actionMove = true;
                                    playerShoot();
                                }
                                else
                                    actionMove = false;
                                break;
                        }
                        return true;
                    }
                });
            }
        }, 1500*(j-1));

    }

    private void startEnemyAnimation(final Enemy enemy)
    {
        enemy.setObjectAnimator(ObjectAnimator.ofFloat(enemy, "translationX", 0, 1000));
        enemy.getObjectAnimator().setRepeatMode(ValueAnimator.REVERSE);
        enemy.getObjectAnimator().setRepeatCount(ValueAnimator.INFINITE);
        enemy.getObjectAnimator().setDuration(3000);
        enemy.getObjectAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                enemyAnimCounter++;
                if (Level >= 1){
                    if (enemyAnimCounter % 300 == 0) {
                        Bullet bullet = new Bullet(Level.this, R.drawable.ic_ufo);
                        bullet.setX(enemy.getX());
                        bullet.setY(enemy.getY());
                        relativeLayout.addView(bullet);
                        startBulletAnimation(bullet, relativeLayout.getBottom());
                    }
                }
                if (Level >= 2) {
                    if (enemyAnimCounter % 400 == 0) {
                        ImageView coin = new ImageView(Level.this);
                        coin.setY(relativeLayout.getTop());
                        Random rand = new Random();
                        int coinX = rand.nextInt(relativeLayout.getRight());
                        coin.setX(coinX);
                        coin.setImageResource(R.drawable.ic_silver_coin);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
                        relativeLayout.addView(coin, layoutParams);
                        startGiftAnimation(coin, "silver");
                    }
                }
                if (Level >= 3){
                    if( enemyAnimCounter % 500 == 0 )
                    {
                        ImageView goldCoin = new ImageView(Level.this);
                        goldCoin.setY(relativeLayout.getTop());
                        Random rand = new Random();
                        int coinX = rand.nextInt(relativeLayout.getRight());
                        goldCoin.setX(coinX);
                        goldCoin.setImageResource(R.drawable.ic_coin);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
                        relativeLayout.addView(goldCoin, layoutParams);
                        startGiftAnimation(goldCoin, "gold");
                    }
                }


            }
        });
        enemy.getObjectAnimator().start();

    }

    private void startBulletAnimation(final Bullet bullet, final int whereTo)
    {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bullet, "translationY", whereTo);
        objectAnimator.setDuration(4000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (whereTo == relativeLayout.getBottom())
                    if ((float)animation.getAnimatedValue() >= player.getY() &&
                            (float)animation.getAnimatedValue() < player.getY() + player.getHeight()
                            && bullet.getX() >= player.getX()
                            && bullet.getX() < player.getX() + player.getWidth()
                            && bullet.getVisibility() != View.GONE)
                    {
                        bullet.setVisibility(View.GONE);
                        int lives = player.getLives() - 1;
                        player.setLives(lives);
                        switch (player.getLives()) {
                            case (2):
                                ImageView life3 = upperScreen.findViewById(R.id.life3);
                                life3.setVisibility(View.GONE);
                                break;
                            case (1):
                                ImageView life2 = upperScreen.findViewById(R.id.life2);
                                life2.setVisibility(View.GONE);
                                break;
                            case (0):
                                ImageView life1 = upperScreen.findViewById(R.id.life1);
                                life1.setVisibility(View.GONE);
                                break;
                        }

                        if ( player.getLives() == 0 && once)
                        {
                            for (Enemy someEnemy : enemies)
                                someEnemy.getObjectAnimator().cancel();
                            View view = getLayoutInflater().inflate(R.layout.game_over, null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Level.this);
                            builder.setView(view).show();
                            Button startBtn = view.findViewById(R.id.startOverBtn);
                            startBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Level.this, Level.class);
                                    intent.putExtra("id", Level);
                                    startActivity(intent);
                                }
                            });
                            Button quitBtn = view.findViewById(R.id.quitBtn);
                            quitBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Level.this, OpenMain.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    }

            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bullet.setVisibility(View.GONE);

            }
        });
        objectAnimator.start();
    }

    private void startGiftAnimation(final ImageView coin, final String type)
    {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(coin, "translationY", relativeLayout.getBottom());
        objectAnimator.setDuration(4000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if ((float)animation.getAnimatedValue() >= player.getY() &&
                        (float)animation.getAnimatedValue() < player.getY() + player.getHeight()
                        && coin.getX() >= player.getX()
                        && coin.getX() < player.getX() + player.getWidth()
                        && coin.getVisibility()!= View.GONE && once)
                {
                    coin.setVisibility(View.GONE);
                    switch (type)
                    {
                        case "gold":
                            Log.i("points", ""+player.getPoints());
                            player.setPoints(player.getPoints() + 10);
                            break;
                        case "silver":
                            Log.i("points", ""+player.getPoints());
                            player.setPoints(player.getPoints() + 5);
                            break;
                        case "life":

                            break;
                    }
                    TextView pointNum = upperScreen.findViewById(R.id.pointNum);
                    pointNum.setText("" + player.getPoints());
                    int pointsTowin = 0;
                    switch (Level){
                        case 1 :
                            pointsTowin = 70;
                            break;
                        case 2:
                            pointsTowin = 120;
                            break;
                        case 3:
                            pointsTowin = 180;
                            break;
                    }
                    if (player.getPoints() >= pointsTowin && once){
                        once = false;
                        Log.i("won points", ""+pointsTowin);
                        wonLevel();
                    }
                }

            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                coin.setVisibility(View.GONE);

            }
        });

        objectAnimator.start();

    }

    private void wonLevel()
    {
        levelPref = getApplicationContext().getSharedPreferences("levelPref", MODE_PRIVATE);
        if (Level != 3) {
            if (levelPref.getString("level", null) != ("3")) {
                levelEditor = levelPref.edit();
                int nextLevel = Level + 1;
                levelEditor.putString("level", "" + nextLevel);
                levelEditor.commit();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Level.this);
        View underView = getLayoutInflater().inflate(R.layout.won_game_under, null);
        final AlertDialog underMyDialog  = builder.setView(underView).show();
        final Button nextLevelBtn =  underView.findViewById(R.id.nextLevelBtn);
        switch (Level){
            case 3:
                nextLevelBtn.setVisibility(View.GONE);
                break;
            case 2:
                nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        underMyDialog.dismiss();
                        Intent intent = new Intent(Level.this, Level.class);
                        intent.putExtra("id", 3);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        underMyDialog.dismiss();
                        Intent intent = new Intent(Level.this, Level.class);
                        intent.putExtra("id", 2);
                        startActivity(intent);
                    }
                });
                break;
        }
        Button scoreBoardBtn = underView.findViewById(R.id.scoreBoardBtn);
        scoreBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underMyDialog.dismiss();
                Intent intent = new Intent(Level.this, ScoreBoard.class);
                startActivity(intent);
            }
        });
        Button quitBtn = underView.findViewById(R.id.quitBtn2);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underMyDialog.dismiss();
                Intent intent = new Intent(Level.this, OpenMain.class);
                startActivity(intent);
            }
        });
        View view = getLayoutInflater().inflate(R.layout.won_game, null);
        final EditText nameEt = view.findViewById(R.id.yourNameEt);
        Button saveScoreBtn = view.findViewById(R.id.saveScoreBtn);
        TextView scoreNumTv = view.findViewById(R.id.scoreNumTv);
        scoreNumTv.setText("" + player.getPoints());
        final AlertDialog mMyDialog  = builder.setView(view).show();
        saveScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yy");
                String datetime = dateformat.format(c.getTime());
                String numVal = pref.getString("num", null);
                int intNum = Integer.parseInt(numVal);
                intNum++;
                editor.putString("num", "" + intNum);
                editor.putString("score"+intNum, ""+intNum+";"+nameEt.getText()+";"+ player.getPoints()+";"+ datetime);
                editor.commit();
                mMyDialog.dismiss();
            }
        });
        Button unsaveScoreBtn = view.findViewById(R.id.unsaveScoreBtn);
        unsaveScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.dismiss();

            }
        });


    }

    private void playerShoot()
    {

        final Bullet playerBullet = new Bullet(Level.this, R.drawable.ic_rocket);
        playerBullet.setX(player.getX() + player.getWidth()/4);
        playerBullet.setY(player.getY());
        relativeLayout.addView(playerBullet);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(playerBullet, "translationY", relativeLayout.getTop());
        objectAnimator.setDuration(1000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for(final Enemy someEnemy : enemies)
                    if ((float)animation.getAnimatedValue() > someEnemy.getY()-someEnemy.getHeight() &&
                            (float)animation.getAnimatedValue() <= someEnemy.getY()
                            && playerBullet.getX() > someEnemy.getX()
                            && playerBullet.getX() < someEnemy.getX() + someEnemy.getWidth()
                            && someEnemy.isAlive())
                    {
                        someEnemy.setAlive(false);
                        someEnemy.setImageResource(R.drawable.ic_explosion);
                        MediaPlayer explosion = MediaPlayer.create(Level.this, R.raw.explosion);
                        explosion.start();
                        Handler handler = new Handler();
                        if (someEnemy.getObjectAnimator() != null)
                            someEnemy.getObjectAnimator().cancel();
                        player.setPoints(player.getPoints() + 15);
                        Log.i("points", ""+player.getPoints());
                        TextView pointNum = upperScreen.findViewById(R.id.pointNum);
                        pointNum.setText("" + player.getPoints());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                someEnemy.setVisibility(View.INVISIBLE);
                                int i = 0 ;
                                for (Enemy enemy :enemies) {
                                    if (!enemy.isAlive()) {
                                        i++;
                                    }
                                }
                                if (i == enemies.length && once) {
                                    once = false;
                                    Log.i("won", ""+i);
                                    wonLevel();
                                }
                            }
                        }, 200);


                    }


            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerBullet.setVisibility(View.GONE);
                actionMove = false;
            }
        });

        objectAnimator.start();


    }


}

