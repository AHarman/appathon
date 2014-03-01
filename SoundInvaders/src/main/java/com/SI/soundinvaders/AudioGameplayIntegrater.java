package com.SI.soundinvaders;

import android.text.method.BaseKeyListener;

/**
 * Created by James on 01/03/14.
 */

class GameAudio
{
    public static float plzGetBeatFraction()
    {
        return 0.0f;
    }
    public static int plzGetIntenseValue()
    {
        return 0;
    }
}

public class AudioGameplayIntegrater
{
    private static final int n = 2;
    private static int cur = 0;

    private static boolean justDone = false;

    public static void audioTick()
    {
        boolean isBeat = (GameAudio.plzGetBeatFraction() < 0.01 || GameAudio.plzGetBeatFraction() > 0.99);

        if(isBeat && !justDone)
        {
            if(cur==0)
            {
                justDone = true;
                GameWorld.nextBeat(GameAudio.plzGetIntenseValue());
            }

            cur = (cur+1) % n;
        }
        else if(!isBeat)
        {
            justDone = false;
        }
    }
}
