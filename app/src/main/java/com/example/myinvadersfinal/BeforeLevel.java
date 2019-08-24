package com.example.myinvadersfinal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BeforeLevel extends Activity {

    SharedPreferences conceptPref;

    SharedPreferences levelPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        setContentView(new MyLayout(this, backgroundDrawable));

        levelPref = getApplicationContext().getSharedPreferences("levelPref", MODE_PRIVATE);

        View view = getLayoutInflater().inflate(R.layout.choose_level, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        RelativeLayout relativeLayout1 = view.findViewById(R.id.levelLayout1);
        RelativeLayout relativeLayout2 = view.findViewById(R.id.levelLayout2);
        RelativeLayout relativeLayout3 = view.findViewById(R.id.levelLayout3);
        ImageView lock2 = view.findViewById(R.id.levelLock2);
        ImageView lock3 = view.findViewById(R.id.levelLock3);
        if (levelPref.getString("level", null) != null)
            switch (levelPref.getString("level", null)){
                case "2":
                    relativeLayout2.setAlpha(1f);
                    lock2.setImageResource(R.drawable.ic_padlock_unlock);
                    break;
                case "3":
                    relativeLayout2.setAlpha(1f);
                    relativeLayout3.setAlpha(1f);
                    lock2.setImageResource(R.drawable.ic_padlock_unlock);
                    lock3.setImageResource(R.drawable.ic_padlock_unlock);
                    break;
            }

        final AlertDialog mMyDialog  = builder.setView(view).show();
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.dismiss();
                Intent intent = new Intent(BeforeLevel.this, Level.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        if (levelPref.getString("level", null) != null)
            switch (levelPref.getString("level", null)){
                case "2":
                    relativeLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog.dismiss();
                            //Intent intent = new Intent(BeforeLevel.this, Level2.class);
                            Intent intent = new Intent(BeforeLevel.this, Level.class);
                            intent.putExtra("id", 2);
                            startActivity(intent);
                        }
                    });
                    break;
                case "3":
                    relativeLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog.dismiss();
                            //Intent intent = new Intent(BeforeLevel.this, Level2.class);
                            Intent intent = new Intent(BeforeLevel.this, Level.class);
                            intent.putExtra("id", 2);
                            startActivity(intent);
                        }
                    });
                    relativeLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMyDialog.dismiss();
                            //Intent intent = new Intent(BeforeLevel.this, Level3.class);
                            Intent intent = new Intent(BeforeLevel.this, Level.class);
                            intent.putExtra("id", 3);
                            startActivity(intent);
                        }
                    });
                    break;
            }



    }
}
