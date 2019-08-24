package com.example.myinvadersfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OpenMain extends AppCompatActivity{

    private MediaPlayer ring;
    SharedPreferences.Editor conceptEditor;
    SharedPreferences conceptPref;
    SharedPreferences.Editor audioEditor;
    SharedPreferences audioPref;

    @Override
    protected void onPause() {
        ring.pause();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        conceptPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        conceptEditor = conceptPref.edit();

        if (conceptPref.getString("playerPref", null) == null) {
            conceptEditor.putString("playerPref", "space");
            conceptEditor.commit();
        }
        if (conceptPref.getString("enemyPref", null) == null) {
            conceptEditor.putString("enemyPref", "space");
            conceptEditor.commit();
        }
        if (conceptPref.getString("backgroundPref", null) == null) {
            conceptEditor.putString("backgroundPref", "space");
            conceptEditor.commit();
        }

        CheckBox mute_btn = findViewById(R.id.sound_btn);
        audioPref = getApplicationContext().getSharedPreferences("audioPref", MODE_PRIVATE);
        audioEditor = audioPref.edit();
        ring = MediaPlayer.create(this, R.raw.background);

        if (audioPref.getString("audio", null) == null ||
                audioPref.getString("audio", null).equals("100")){
            audioEditor.putString("audio", "100");
            audioEditor.commit();
            ring.start();
        }
        else {
            mute_btn.setChecked(true);
        }

         mute_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ring.pause();
                    audioEditor.putString("audio", "0");
                    audioEditor.commit();
                }
                else {
                    ring.start();
                    audioEditor.putString("audio", "100");
                    audioEditor.commit();
                }

            }
        });
        Button play_btn = findViewById(R.id.play_btn);
        play_btn.setBackgroundResource(R.drawable.btn_background);
        AnimationDrawable frameAnimation = (AnimationDrawable) play_btn.getBackground();
        frameAnimation.start();

        Button score_btn = findViewById(R.id.score_btn);
        score_btn.setBackgroundResource(R.drawable.btn_background2);
        AnimationDrawable frameAnimation2 = (AnimationDrawable) score_btn.getBackground();
        frameAnimation2.start();

        Button theme_btn = findViewById(R.id.theme_open_btn);/***************888*/
        theme_btn.setBackgroundResource(R.drawable.btn_background3);
        AnimationDrawable frameAnimation3 = (AnimationDrawable) theme_btn.getBackground();
        frameAnimation3.start();

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenMain.this, BeforeLevel.class);

                startActivity(intent);

            }
        });

        score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenMain.this, ScoreBoard.class);
                ring.pause();
                startActivity(intent);

            }
        });

        theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.theme_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(OpenMain.this);
                final AlertDialog mMyDialog  = builder.setView(view).show();


                conceptPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                RadioButton backgroundRd;
                RadioButton playerRd;
                RadioButton enemyRd;
                if (conceptPref.getString("backgroundPref", null) != null)
                    switch (conceptPref.getString("backgroundPref", null)) {
                        case "space":
                            backgroundRd = view.findViewById(R.id.backgroundOption1);
                            backgroundRd.setChecked(true);
                            break;
                        case "ocean":
                            backgroundRd = view.findViewById(R.id.backgroundOption2);
                            backgroundRd.setChecked(true);
                            break;
                        case "wild_west":
                            backgroundRd = view.findViewById(R.id.backgroundOption3);
                            backgroundRd.setChecked(true);
                            break;
                    }

                if (conceptPref.getString("playerPref", null) != null)
                    switch (conceptPref.getString("playerPref", null)) {
                        case "space":
                            playerRd = view.findViewById(R.id.playerOption1);
                            playerRd.setChecked(true);
                            break;
                        case "ocean":
                            playerRd = view.findViewById(R.id.playerOption2);
                            playerRd.setChecked(true);
                            break;
                        case "wild_west":
                            playerRd = view.findViewById(R.id.playerOption3);
                            playerRd.setChecked(true);
                            break;
                    }

                if (conceptPref.getString("enemyPref", null) != null) {
                    switch (conceptPref.getString("enemyPref", null)) {
                        case "space":
                            enemyRd = view.findViewById(R.id.enemyOption1);
                            enemyRd.setChecked(true);
                            break;
                        case "ocean":
                            enemyRd = view.findViewById(R.id.enemyOption2);
                            enemyRd.setChecked(true);
                            break;
                        case "wild_west":
                            enemyRd = view.findViewById(R.id.enemyOption3);
                            enemyRd.setChecked(true);
                            break;
                    }
                }


                final RadioGroup playerRadioGroup = view.findViewById(R.id.radioGroup1);
                final RadioGroup enemyRadioGroup = view.findViewById(R.id.radioGroup2);
                final RadioGroup backgroundRadioGroup = view.findViewById(R.id.radioGroup3);

                Button themeSetBtn = view.findViewById(R.id.themeSetBtn);
                themeSetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedPlayerId = playerRadioGroup.getCheckedRadioButtonId();
                        switch (selectedPlayerId) {
                            case (R.id.playerOption1):
                                conceptEditor.putString("playerPref", "space");
                                break;
                            case (R.id.playerOption2):
                                conceptEditor.putString("playerPref", "ocean");
                                break;
                            case (R.id.playerOption3):
                                conceptEditor.putString("playerPref", "wild_west");
                                break;
                        }
                        int selectedEnemyId = enemyRadioGroup.getCheckedRadioButtonId();
                        switch (selectedEnemyId) {
                            case (R.id.enemyOption1):
                                conceptEditor.putString("enemyPref", "space");
                                break;
                            case (R.id.enemyOption2):
                                conceptEditor.putString("enemyPref", "ocean");
                                break;
                            case (R.id.enemyOption3):
                                conceptEditor.putString("enemyPref", "wild_west");
                                break;
                        }
                        int selectedBackgroundId = backgroundRadioGroup.getCheckedRadioButtonId();
                        switch (selectedBackgroundId) {
                            case (R.id.backgroundOption1):
                                conceptEditor.putString("backgroundPref", "space");
                                break;
                            case (R.id.backgroundOption2):
                                conceptEditor.putString("backgroundPref", "ocean");
                                break;
                            case (R.id.backgroundOption3):
                                conceptEditor.putString("backgroundPref", "wild_west");
                                break;
                        }
                        conceptEditor.commit();
                        mMyDialog.dismiss();
                    }
                });

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                //Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                View view = getLayoutInflater().inflate(R.layout.how_to_play, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(OpenMain.this);
                final AlertDialog mMyDialog  = builder.setView(view).show();
                Button gotItBtn = view.findViewById(R.id.gotItBtn);
                gotItBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMyDialog.dismiss();
                    }
                });
                return true;
            /*case R.id.item2:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
