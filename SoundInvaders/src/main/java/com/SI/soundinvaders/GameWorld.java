package com.SI.soundinvaders;

import android.util.Log;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by andy on 01/03/2014.
 */
public class GameWorld {

    public static Deque<GameObject> blockQueue = new ArrayDeque<GameObject>();
    public static GameObject playerObject;

    public static final RGBColor blueColour = new RGBColor(41, 128, 185);
    public static final RGBColor redColour = new RGBColor(192, 57, 43);
    public static final RGBColor greenColour = new RGBColor(46, 204, 113);

    public static final RGBColor playerColour = new RGBColor(230, 126, 34);

    public static void nextBeat(int intensity) {

    }


    public static enum GameObjectType
    {
        GREEN_BLOCK, RED_BLOCK, BLUE_BLOCK,
        PLAYER;
    }
    public static void initialise()
    {
        // start the player in the middle of the screen
        playerObject = new GameObject(Graphics.addPlayer(Graphics.getWidth() / 2, Graphics.getHeight() - 10.0f, playerColour),GameObjectType.PLAYER, 2);

        Graphics.addRect(10.0f, 10.0f, redColour);
        Graphics.addRect(35.0f, 10.0f, blueColour);
        Graphics.addRect(55.0f, 10.0f, greenColour);
    }

    public static void updateScene()
    {

    }

    public static void checkCollisions()
    {
        float playerY = Graphics.getObjY(playerObject.getObj());
        Iterator<GameObject> iterator = blockQueue.iterator();
        while (iterator.hasNext())
        {
            GameObject block = iterator.next();
            float blockY = Graphics.getObjY(block.getObj());
            int col = block.getColumn();

            // check if the block has passed off the bottom of the screen
            if (blockY > 120.0f + 10.0f/2) // screen height / block height
                block.remove(iterator);

            if (col == playerObject.getColumn())
            {
                if (playerY - blockY < 10.0f) // change this to the actual size of the objects
                {
                    switch (block.type)
                    {
                        case GREEN_BLOCK:
                            // add loads of point to score, fast mode?
                            Log.d("SOUNDINVADERS", "green collision");
                            block.remove(iterator);
                        case RED_BLOCK:
                            // end the game :(
                            Log.d("SOUNDINVADERS", "red collision");
                            break;
                        case BLUE_BLOCK:
                            // add small number of points
                            Log.d("SOUNDINVADERS", "blue collision");
                            block.remove(iterator);
                            break;
                        default:
                            Log.d("SOUNDINVADERS", "WHAT THE SHIT IS GOING ON?!?!?!?!");
                    }
                }
            }
        }
    }

    public static class GameObject {
        public Object3D obj;
        public GameObjectType type;
        int column;

        public GameObject(Object3D obj, GameObjectType type, int column) {
            this.obj = obj;
            this.type = type;
            this.column = column;
        }

        public Object3D getObj() {
            return obj;
        }

        public void setObj(Object3D obj) {
            this.obj = obj;
        }

        public int getColumn()
        {
            return column;
        }

        public void remove(Iterator<GameObject> iter)
        {
            iter.remove();
            Graphics.world.removeObject(obj);
        }
    }

}
