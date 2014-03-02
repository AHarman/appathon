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
        //Log.d("Dosomething", "hello3");
        boolean isBeat = (GameAudio.plzGetBeatFraction() < 0.5);// || GameAudio.plzGetBeatFraction() > 0.99);
        //Log.d("Dosomething", Double.toString(GameAudio.plzGetBeatFraction()));
        if(isBeat && !justDone)
        {
            //Log.d("Dosomething", "Hello");
            if(cur==0)
            {
                justDone = true;
                GameWorld.processBeat(GameAudio.plzGetIntenseValue());
                //Log.d("Dosomething", "Hello1");
            }

            cur = (cur+1) % n;
        }
        else if(!isBeat)
        {
            justDone = false;
        }

        // temporary values that switch between modes
        final double curTime = GameAudio.getCurTime() % 15000;
        if (curTime > 5000 && curTime < 10000 && GameWorld.currentMode != GameWorld.GameMode.MODE_FIRST_PERSON)
        {
            GameWorld.changeMode(GameWorld.GameMode.MODE_FIRST_PERSON);
        }
        else if (curTime > 10000 && GameWorld.currentMode != GameWorld.GameMode.MODE_NORMAL)
        {
            GameWorld.changeMode(GameWorld.GameMode.MODE_NORMAL);
        }
    }
}
