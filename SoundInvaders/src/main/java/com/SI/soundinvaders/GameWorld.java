package com.SI.soundinvaders;

import android.util.Log;

import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

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

    public static void processBeat(int intensity)
    {

    }

    public static enum GameObjectType
    {
        GREEN_BLOCK, RED_BLOCK, BLUE_BLOCK,
        PLAYER;
    }

    public static void initialise()
    {
        new GameObject(GameObjectType.PLAYER,2);

        new GameObject(GameObjectType.BLUE_BLOCK,2);
        new GameObject(GameObjectType.RED_BLOCK,1);
        new GameObject(GameObjectType.GREEN_BLOCK,3);
    }

    public static void updateScene()
    {
        for (GameObject block: blockQueue)
        {
            Graphics.moveObjPosition(0.0f,1.0f,block.getObj());
        }
        checkCollisions();
        randomSpawn();
    }

    public static void movePlayer(int direction)
    {
        int newColumn = playerObject.column + direction;

        if(newColumn > 0 && newColumn < 4)
        {
            playerObject.setColumn(newColumn);
            Log.d("James", "New column: "+String.valueOf(playerObject.column));
        }
    }

    public static void randomSpawn()
    {
        if (Math.random() > 0.95)
        {
            int col = 1 + (int)(Math.random() * ((3 - 1) + 1));
            int type = (int)(Math.random() * ((2) + 1));
            new GameObject(GameObjectType.values()[type],col);
        }
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
                if (playerY - blockY < 20.0f) // change this to the actual size of the objects
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

        // Do we still need this?
        public GameObject(Object3D obj, GameObjectType type, int column) {
            this.obj = obj;
            this.type = type;
            this.column = column;
        }

        public GameObject(GameObjectType type, int column)
        {
            // Has to be initialised to prevent a warning
            RGBColor colour = new RGBColor(0,0,0);

            switch (type)
            {
                case GREEN_BLOCK:
                    colour = greenColour;
                    break;
                case RED_BLOCK:
                    colour = redColour;
                    break;
                case BLUE_BLOCK:
                    colour = blueColour;
                    break;
                case PLAYER:
                    colour = playerColour;
                    break;
            }

            this.type = type;
            this.column = column;

            int xPos = 10 + (column-1)*30;

            if (type != GameObjectType.PLAYER)
            {
                this.obj = Graphics.addRect(xPos,0,colour);
                blockQueue.add(this);
            }
            else
            {
                this.obj = Graphics.addPlayer(xPos,Graphics.getHeight()-10.f,colour);
                playerObject = this;
            }
        }

        public void setColumn(int column)
        {
            final int xMovement = (column - this.column)*30;
            final int moveTime = 200;
            final Object3D obj = this.getObj();

            Timer moveTimer = new Timer();
            moveTimer.schedule(new TimerTask() {
                final int stepTime = 15;
                int i = stepTime;
                @Override
                public void run() {
                    float stepMovement = Easings.easeOutExpo(xMovement, i, moveTime) - Easings.easeOutExpo(xMovement, i-stepTime, moveTime);

                    Graphics.moveObjPosition(stepMovement, 0, obj);

                    i += stepTime;
                    if (i>=moveTime) cancel();
                }

            }, 0, 15);

            this.column = column;
//            Graphics.moveObjPosition(xMovement*30,0,this.getObj());
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
