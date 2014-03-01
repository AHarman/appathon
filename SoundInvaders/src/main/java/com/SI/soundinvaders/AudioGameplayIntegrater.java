package com.SI.soundinvaders;

import android.text.method.BaseKeyListener;
import android.util.Log;

/**
 * Created by James on 01/03/14.
 */

public class AudioGameplayIntegrater
{
    private static final int n = 1;
    private static int cur = 0;

    private static boolean justDone = false;

    public static void audioTick()
    {
        boolean isBeat = (GameAudio.plzGetBeatFraction() < 0.5);// || GameAudio.plzGetBeatFraction() > 0.99);
        //Log.d("OstrichReallyalot", "D1");
        if(isBeat && !justDone)
        {
            //Log.d("OstrichReallyalot", "D2");
            if(cur==0)
            {
                justDone = true;
                GameWorld.processBeat(GameAudio.plzGetIntenseValue());
                //Log.d("OstrichReallyalot", "D3");
            }

            cur = (cur+1) % n;
        }
        else if(!isBeat)
        {
            //Log.d("OstrichReallyalot", "D4");
            justDone = false;
        }
    }
}
