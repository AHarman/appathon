package com.SI.soundinvaders;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Created by alex on 01/03/14.
 */
public class ScoreBoard {


    private File dir = null;
    private ArrayList<Integer> scores = new ArrayList();
    private ArrayList<String> players = new ArrayList<String>();
    private Context appContext;

    ScoreBoard(Context contextual)
    {
        appContext = contextual;
        try{
            dir = appContext.getFilesDir();
        } catch (Exception e){
            Log.e("files", "dir " + e.toString());
        }
        File scoreFile = appContext.getFileStreamPath("scores.txt");
        if(scoreFile.exists())
        {
            Log.d("files", "FILE EXISTS!");
            readScores();
        }
        else
        {
            try{
                appContext.openFileOutput("scores.txt", Context.MODE_APPEND);
            } catch (Exception e){
                Log.e("files", "why gaiuhsdlouydsfbuf " + e.toString());
            }
        }
        Log.d("files", toString() + "boom");
    }

    private boolean readScores(){
        //File scoreFile = new File(dir, "scores.txt");

        String line;
        String[] nameScore;
        BufferedReader buff;
        /*File file = new File(appContext.getFileStreamPath());
            if(!file.exists())
                return false;*/
        try
        {
            buff = new BufferedReader(new InputStreamReader(appContext.openFileInput("scores.txt")));
        }
        catch(Exception e)
        {
            Log.d("files", "No file to read");
            return false;
        }
        try{
            for(int i = 0; i < 10; i++)
            {
                line = buff.readLine();
                if(line == null)
                    return true;
                nameScore = line.split(";");
                scores.add(i, Integer.parseInt(nameScore[0]));
                players.add(i, nameScore[1]);
            }
        } catch(Exception e){
            Log.e("files", "reading " + e.toString());
        }
        return true;
    }

    //Return between 0 an 9 if true, -1 if false
    private int checkHighScore(int newScore)
    {
        if(scores.size() < 10)
            return scores.size();

        for(int i = 0; i < 10; i++)
        {
            if(scores.get(i) < newScore)
                return i;
        }
        return -1;
    }

    public boolean newScore(int score, String name)
    {
        int position = checkHighScore(score);
        if(position < 0)
            return false;

        scores.set(position, score);
        players.set(position, name);
        return true;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < scores.size(); i++)
            s += players.get(i) + ";" + scores.get(i) + "\n";
        return s;
    }

    public int getScore(int i){
        return scores.get(i);
    }
}
