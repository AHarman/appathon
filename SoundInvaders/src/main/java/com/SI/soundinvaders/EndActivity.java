package com.SI.soundinvaders;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        ImageView twitterBtn = (ImageView)findViewById(R.id.twitterbtn);
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.twitter.com/intent/tweet?text=I just got " + GameWorld.score + " on Sound Invaders!";
                                            Intent sendIntent = new Intent();
                                            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            sendIntent.setAction(Intent.ACTION_VIEW);
                                            sendIntent.setData(Uri.parse(url));
                                            getApplicationContext().startActivity(sendIntent);
            }
        });

        tv = (TextView)findViewById(R.id.score0); tv.setText(Integer.toString(ScoreBoard.getScore(0))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score1); tv.setText(Integer.toString(ScoreBoard.getScore(1))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score2); tv.setText(Integer.toString(ScoreBoard.getScore(2))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score3); tv.setText(Integer.toString(ScoreBoard.getScore(3))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score4); tv.setText(Integer.toString(ScoreBoard.getScore(4))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score5); tv.setText(Integer.toString(ScoreBoard.getScore(5))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score6); tv.setText(Integer.toString(ScoreBoard.getScore(6))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score7); tv.setText(Integer.toString(ScoreBoard.getScore(7))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score8); tv.setText(Integer.toString(ScoreBoard.getScore(8))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.score9); tv.setText(Integer.toString(ScoreBoard.getScore(9))); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName0); tv.setText(ScoreBoard.getName(0)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName1); tv.setText(ScoreBoard.getName(1)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName2); tv.setText(ScoreBoard.getName(2)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName3); tv.setText(ScoreBoard.getName(3)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName4); tv.setText(ScoreBoard.getName(4)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName5); tv.setText(ScoreBoard.getName(5)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName6); tv.setText(ScoreBoard.getName(6)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName7); tv.setText(ScoreBoard.getName(7)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName8); tv.setText(ScoreBoard.getName(8)); tv.setTypeface(myTypeface);
        tv = (TextView)findViewById(R.id.scoreName9); tv.setText(ScoreBoard.getName(9)); tv.setTypeface(myTypeface);

        hideSystemBars();
    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public static void getScore(int score) {
//        menuTitle.setText(Integer.toString(score));
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        // Immersive mode is only supported in Android KitKat and above
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            hideSystemBars();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void hideSystemBars()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

}
