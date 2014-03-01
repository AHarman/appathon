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
public class MenuActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // skip the main menu for testing purposes
        startGame(null);

        setContentView(R.layout.activity_menu);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bol.ttf");
        TextView menuTitle = (TextView)findViewById(R.id.menuTitle);
        TextView playButton = (TextView) findViewById(R.id.playButton);
        menuTitle.setTypeface(myTypeface);
        playButton.setTypeface(myTypeface);

        hideSystemBars();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideSystemBars()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
