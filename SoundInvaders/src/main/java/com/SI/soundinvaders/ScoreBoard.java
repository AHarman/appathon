package com.SI.soundinvaders;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by alex on 01/03/14.
 */
public class ScoreBoard {

    File scoreFile;
    private ArrayList scores = new ArrayList();
    private ArrayList<String> players = new ArrayList<String>();

    ScoreBoard(Context appContext){
        scoreFile = new File(appContext.getFilesDir(), "scores.txt");
    }
}
