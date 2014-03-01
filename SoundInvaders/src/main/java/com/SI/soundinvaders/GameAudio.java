package com.SI.soundinvaders;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.appcompat.R;

import com.musicg.wave.Wave;

import java.io.InputStream;

/**
 * Created by Sam on 01/03/14.
 */
public class GameAudio {
    static MediaPlayer mediaPlayerx = new MediaPlayer();
    static double bpm = 104.993; //beat per minute
    static double spb = 60.0/bpm; //seconds per beat
    static int intro = 250;
    static double mean = 0;
    static Context c;
    static InputStream is;
    static Wave wave;
    static short[] amps;
    static double upperThreshold;
    static double lowerThreshold;

    public static void init(Context con)
    {
        //set context
        c = con;

        //get wave and means of amplitudes
        is = c.getResources().openRawResource(com.SI.soundinvaders.R.raw.eiawav);
        wave = new Wave(is);
        amps = wave.getSampleAmplitudes();

        //quite long
        mean = (amps[0] + amps[1])/2.0;
        for(int i=2; i<amps.length; i++)
        {
            if (amps[i] < 0) amps[i] *= -1;
            mean = (mean*(i) + amps[i]) / (i+1);
        }

        //calculate thresholds
        upperThreshold = mean*2.0;
        lowerThreshold = mean/2.0;
    }

    public static void startMedia()
    {
        mediaPlayerx = MediaPlayer.create(c, com.SI.soundinvaders.R.raw.eiawav);
        mediaPlayerx.start();
    }

    public static void pauseMedia()
    {
        mediaPlayerx.pause();
    }

    public static void stopMedia()
    {
        mediaPlayerx.stop();
        mediaPlayerx.release();
        mediaPlayerx = MediaPlayer.create(c, com.SI.soundinvaders.R.raw.eiawav);
    }

    public static double plzGetBeatFraction()
    {
        int curPos = mediaPlayerx.getCurrentPosition()-intro ;
        double frac = (curPos % spb*1000.0)/(spb*1000.0);
        return frac;
    }


    public static int plzGetIntenseValue()
    {

        double curPos = mediaPlayerx.getCurrentPosition()-intro;
        double percent = curPos/(mediaPlayerx.getDuration()-intro);
        double amplitude = amps[(int)(percent * amps.length)];


        if(amplitude < lowerThreshold)
            return 1;
        else if(amplitude < mean)
            return 2;
        else if(amplitude < upperThreshold)
            return 3;
        else
            return 4;
    }

}