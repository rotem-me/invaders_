package com.example.myinvadersfinal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreBoard extends Activity {

    GridView gridView;

    String[] scores;

    /*String[] nums;
    String[] names;
    Map scores;
    String[] dates;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyScore", MODE_PRIVATE);
        if (pref.getString("num", null) != null) {
            String numVal = pref.getString("num", null);
            int intNum = Integer.parseInt(numVal);
            //Log.i("scoreNum", ""+ numVal);
            scores = new String[intNum * 4];
            /*nums = new String[intNum];
            names = new String[intNum];
            scores = new HashMap();
            dates = new String[intNum];
            */int j = 4;
            for (int i = 1; i <= intNum; i++) {
                String tmp = pref.getString("score" + i, null);
                String[] tmpArr = tmp.split(";", 5);

                for (String tmpVal : tmpArr) {
                    scores[i * 4 - j] = tmpVal;
                    j--;
                }
                j = 4;
            }


            gridView = findViewById(R.id.gridView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.custom_grid_layout, scores);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(getApplicationContext(),
                            ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Log.i("scoreNum", "else");

    }



}
