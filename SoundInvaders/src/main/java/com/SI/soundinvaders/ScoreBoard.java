package com.SI.soundinvaders;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Created by alex on 01/03/14.
 */
public class ScoreBoard {


    private File dir = null;
    static private ArrayList<Integer> scores = new ArrayList();
    static private ArrayList<String> players = new ArrayList<String>();
    private Context appContext;

    ScoreBoard(Context contextual)
    {
        File scoreFile;
        FileOutputStream outyougo;
        appContext = contextual;
        try{
            dir = appContext.getFilesDir();
        } catch (Exception e){
            Log.e("files", "dir " + e.toString());
        }

        scoreFile = appContext.getFileStreamPath("scores.txt");
        if(!scoreFile.exists())
        {
            try{
                outyougo = appContext.openFileOutput("scores.txt", Context.MODE_APPEND);
                outyougo.write("Beat Me;100".getBytes());
                outyougo.close();
            } catch (Exception e){
                Log.e("files", "why gaiuhsdlouydsfbuf " + e.toString());
            }

        }
        Log.d("files", "FILE EXISTS!");
        readScores();
    }

    private boolean readScores(){
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
                Log.d("files", "name: " + nameScore[0]);
                Log.d("files", "score: " + nameScore[1]);
                players.add(i, nameScore[0]);
                scores.add(i, Integer.parseInt(nameScore[1]));
            }
            buff.close();
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
        writeScores();
        return true;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < scores.size(); i++)
            s += players.get(i) + ";" + scores.get(i) + "\n";
        return s;
    }

    public static int getScore(int i){
        return scores.get(i);
    }

    public static String getName(int i){
        return players.get(i);
    }

    private boolean writeScores(){
        return true;
    }
}
