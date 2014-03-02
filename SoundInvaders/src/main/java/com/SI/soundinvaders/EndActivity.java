package com.SI.soundinvaders;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by alexander on 01/03/2014.
 */

public class EndActivity extends Activity {
    public static TextView menuTitle;
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("endgame", "in oncreate");
        super.onCreate(savedInstanceState);

        ScoreBoard.newScore(GameWorld.score, "Player1");
        setContentView(R.layout.activity_end);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bol.ttf");

        menuTitle = (TextView)findViewById(R.id.menuTitle);

        TextView playButton = (TextView) findViewById(R.id.playButton);
        menuTitle.setTypeface(myTypeface);
        playButton.setTypeface(myTypeface);

        TextView tv;

        tv = (TextView)findViewById(R.id.score0); tv.setText(Integer.toString(ScoreBoard.getScore(0)));
        tv = (TextView)findViewById(R.id.score1); tv.setText(Integer.toString(ScoreBoard.getScore(1)));
        tv = (TextView)findViewById(R.id.score2); tv.setText(Integer.toString(ScoreBoard.getScore(2)));
        tv = (TextView)findViewById(R.id.score3); tv.setText(Integer.toString(ScoreBoard.getScore(3)));
        tv = (TextView)findViewById(R.id.score4); tv.setText(Integer.toString(ScoreBoard.getScore(4)));
        tv = (TextView)findViewById(R.id.score5); tv.setText(Integer.toString(ScoreBoard.getScore(5)));
        tv = (TextView)findViewById(R.id.score6); tv.setText(Integer.toString(ScoreBoard.getScore(6)));
        tv = (TextView)findViewById(R.id.score7); tv.setText(Integer.toString(ScoreBoard.getScore(7)));
        tv = (TextView)findViewById(R.id.score8); tv.setText(Integer.toString(ScoreBoard.getScore(8)));
        tv = (TextView)findViewById(R.id.score9); tv.setText(Integer.toString(ScoreBoard.getScore(9)));
        tv = (TextView)findViewById(R.id.scoreName0); tv.setText(ScoreBoard.getName(0));
        tv = (TextView)findViewById(R.id.scoreName1); tv.setText(ScoreBoard.getName(1));
        tv = (TextView)findViewById(R.id.scoreName2); tv.setText(ScoreBoard.getName(2));
        tv = (TextView)findViewById(R.id.scoreName3); tv.setText(ScoreBoard.getName(3));
        tv = (TextView)findViewById(R.id.scoreName4); tv.setText(ScoreBoard.getName(4));
        tv = (TextView)findViewById(R.id.scoreName5); tv.setText(ScoreBoard.getName(5));
        tv = (TextView)findViewById(R.id.scoreName6); tv.setText(ScoreBoard.getName(6));
        tv = (TextView)findViewById(R.id.scoreName7); tv.setText(ScoreBoard.getName(7));
        tv = (TextView)findViewById(R.id.scoreName8); tv.setText(ScoreBoard.getName(8));
        tv = (TextView)findViewById(R.id.scoreName9); tv.setText(ScoreBoard.getName(9));
    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public static void getScore(int score) {
//        menuTitle.setText(Integer.toString(score));
    }

}
