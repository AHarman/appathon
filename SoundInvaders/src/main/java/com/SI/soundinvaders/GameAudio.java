package com.SI.soundinvaders;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.appcompat.R;
import android.util.Log;

import com.musicg.wave.Wave;

import java.io.InputStream;

/**
 * Created by Sam on 01/03/14.
 */
public class GameAudio {
    static MediaPlayer mediaPlayerx = new MediaPlayer();

    static double bpm = 148.003; //beat per minute
    static double spb = 60.0/bpm; //seconds per beat

    static int intro = 1346;
    static double mean = 0;
    static Context c;
    static InputStream is;
    static Wave wave;
    static short[] amps;
    static int soundId = com.SI.soundinvaders.R.raw.eiawav7;

    static double upperThreshold;
    static double upper2Threshold;
    static double upper3Threshold;
    static double upper4Threshold;
    static double lowerThreshold;
    static double lower2Threshold;
    static double lower3Threshold;
    static double lower4Threshold;

    static long stime = System.nanoTime();

    public static boolean isInit = false;

    public static void init(Context con)
    {
        //set context
        c = con;

        //get wave and means of amplitudes

        is = c.getResources().openRawResource(soundId);
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


        upperThreshold = mean*1.25;
        upper2Threshold = mean*1.5;
        upper3Threshold = mean*1.75;
        upper4Threshold = mean*2.0;
        lowerThreshold = mean/1.25;
        lower2Threshold = mean/1.5;
        lower3Threshold = mean/1.75;
        lower4Threshold = mean/2.0;

        isInit = true;
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
        mediaPlayerx = null;
    }

    public static double plzGetBeatFraction()
    {
        double abpm = bpm / 60.0f;
        abpm = 1.0 / abpm;
        double curPos = ((System.nanoTime() - stime)/(1000.0*1000.0)) - intro;

        float r = (float) (curPos / (abpm*1000.0));

        float f = (float) Math.floor(r);

        r -= f;

        float frac = r;

        return frac;
    }

    public static boolean isGoing()
    {
        return mediaPlayerx.isPlaying();
    }


    public static int plzGetIntenseValue()
    {
        double curPos = mediaPlayerx.getCurrentPosition()-intro;
        double percent = curPos/(mediaPlayerx.getDuration()-intro);

        int startpoint = (int)(percent * amps.length);
        if(startpoint < 0)
        {
            return 0;
        }
        if (startpoint == amps.length) startpoint = startpoint - 1;
        double amplitude = amps[startpoint];

        if (amplitude < 0) amplitude = amplitude * -1;
        double meanamp = (amps[startpoint] + amps[startpoint + 1])/2.0;
        double amptemp;

        for(int i=2; i<44100; i++)
        {
            if (i + startpoint == amps.length) startpoint = amps.length - 1 - i;
            amptemp = amps[i + startpoint];
            if (amptemp < 0) amptemp *= -1;
            meanamp = (meanamp*(i) + amptemp) / (i+1);
        }
        //Log.d("DEBUGSAM", "single amplitude = " + amplitude);


        if (meanamp < 0) meanamp = meanamp * -1;
        //Log.d("DEBUGSAM","averaged amplitude = " + meanamp);
        amplitude = meanamp;
        if(amplitude < lower4Threshold)
            return 1;
        else if(amplitude < lower3Threshold)
            return 2;
        else if(amplitude < lower2Threshold)
            return 3;
        else if(amplitude < lowerThreshold)
            return 4;
        else if(amplitude < mean)

            return 5;
        else if(amplitude < upperThreshold)

            return 6;
        else if(amplitude < upper2Threshold)
            return 7;
        else if(amplitude < upper3Threshold)
            return 8;
        else if(amplitude < upper4Threshold)
            return 9;
        else

            return 10;
    }

}