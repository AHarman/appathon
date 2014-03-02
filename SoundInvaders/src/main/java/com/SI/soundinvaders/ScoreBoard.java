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
    private static Context appContext;

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
                for(int i=100; i<=1000; i+=100)
                    outyougo.write(("Beat Me;" + i + "\n").getBytes());
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
    private static int checkHighScore(int newScore)
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

    public static boolean newScore(int score, String name)
    {
        int position = checkHighScore(score);
        if(position < 0)
            return false;

        for(int i = 9; i > position; i--)
        {
            scores.set(i, scores.get(i - 1));
            players.set(i, players.get(i - 1));
        }
        scores.set(position, score);
        players.set(position, "player1");

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

    private static boolean writeScores(){

        try{
            FileOutputStream outyougo = appContext.openFileOutput("scores.txt", Context.MODE_APPEND);
            for(int i = 0; i < 10; i++)
                outyougo.write((players.get(i) + ";" + scores.get(i) + "\n").getBytes());
            outyougo.close();
        } catch (Exception e){
            Log.e("files", "why gaiuhsdlouydsfbuf " + e.toString());
        }
        return true;
    }
}
