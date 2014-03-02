package com.SI.soundinvaders;

import android.app.Activity;
import android.util.Log;
import 	android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import android.content.Context;

import com.threed.jpct.Camera;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;

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
    public static GameObject playerObject = null;

    public static final RGBColor blueColour = new RGBColor(41, 128, 185);
    public static final RGBColor redColour = new RGBColor(192, 57, 43);
    public static final RGBColor greenColour = new RGBColor(46, 204, 113);

//    public static final RGBColor playerColour = new RGBColor(230, 126, 34);
    public static final RGBColor playerColour = new RGBColor(211, 84, 0);

    public static int score = 0;

    public static float ySpeed = 1.0f;

    private static int ticksSinceLastRed = 0;

    private static int lastColumn = 0;

    private static float lastSpeed = 0;

    public static boolean isCameraMoving = false;

    public static boolean menupressed = false;

    static Context c;
    public static enum GameMode {
        MODE_NORMAL, MODE_FIRST_PERSON
    }

    public static GameMode currentMode = GameMode.MODE_NORMAL;

    public static void changeMode(GameMode mode)
    {
        // don't change mode if we're already in the same mode
        if (mode == currentMode) return;

        final Camera camera = Graphics.cam;
        final int stepTime = 16;
        final int totalSteps = 2000;



        final int totalYMove = 64; // move back
        final int totalZMove = 60; // move down
        final int totalXMove = (playerObject.getColumn() - 2) * 25;

        Timer moveTimer = new Timer();
        Timer safeModeTimer = new Timer();
        safeModeTimer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                isCameraMoving = false;
            }
        }, totalSteps/stepTime + 2000);

        currentMode = mode;
        isCameraMoving = true;

        switch (mode)
        {
            case MODE_NORMAL:
                Log.d("GameWorld", "Setting mode to normal person");
                // switch back to normal mode
                moveTimer.scheduleAtFixedRate(new TimerTask() {
                    public final float totalRotation = (float) (Math.PI / 2);
                    final SimpleVector startPos = camera.getPosition();

                    public float i = 0;

                    @Override
                    public void run()
                    {
                        float rotation = Easings.easeInOutExpo(totalRotation, i, totalSteps) - Easings.easeInOutExpo(totalRotation, i - stepTime, totalSteps);
                        camera.rotateAxis(new SimpleVector(1, 0, 0), -rotation);
                        SimpleVector position = camera.getPosition();
                        float posx = Easings.easeInOutExpo(totalXMove, i, totalSteps);
                        float posy = Easings.easeInOutExpo(totalYMove, i, totalSteps);
                        float posz = Easings.easeInOutExpo(totalZMove, i, totalSteps);
                        position.set(startPos.x - posx, startPos.y - posy, startPos.z - posz);
                        camera.setPosition(position);
                        i += stepTime;
                        if (i >= totalSteps)
                        {
                            camera.setPosition(Graphics.cameraDefault);
                            cancel();
                        }
                    }
                }, 0, stepTime); // 0 = delay
                break;
            case MODE_FIRST_PERSON:
                Log.d("GameWorld", "Setting mode to first person");
                // switch into the awesome FIRST PERSON MODEn
                moveTimer.scheduleAtFixedRate(new TimerTask() {
                    public final float totalRotation = (float) (Math.PI / 2);
                    final SimpleVector startPos = camera.getPosition();

                    public float i = 0;

                    @Override
                    public void run()
                    {

                        float rotation = Easings.easeInOutExpo(totalRotation, i, totalSteps) - Easings.easeInOutExpo(totalRotation, i - stepTime, totalSteps);
                        camera.rotateAxis(new SimpleVector(1, 0, 0), rotation);
                        SimpleVector position = camera.getPosition();
                        float posx = Easings.easeInOutExpo(totalXMove, i, totalSteps);
                        float posy = Easings.easeInOutExpo(totalYMove, i, totalSteps);
                        float posz = Easings.easeInOutExpo(totalZMove, i, totalSteps);
                        position.set(startPos.x + posx, startPos.y + posy, startPos.z + posz);
                        camera.setPosition(position);
                        i += stepTime;
                        if (i >= totalSteps)
                        {
                            isCameraMoving = false;
                            cancel();
                        }
                    }
                }, 0, stepTime); // 0 = delay
                break;
            default:
                safeModeTimer.cancel();
        }
    }

    public static void processBeat(int intensity)
    {
        if(intensity >= 1 && intensity <= 3)
        {
            ySpeed = (float) 0.9;
        }

        if(intensity >= 4 && intensity <= 7)
        {
            ySpeed = (float) 1.5;
        }

        if(intensity >= 8 && intensity <= 10)
        {
            ySpeed = (float) 2.7;
        }

        float greenWeight = (float) 0.1;
        float redWeight = (float) 0.3;
        float blueWeight = (float) 0.6;

        float bType = (float) Math.random();

        int type = 0;

        if(bType < greenWeight)
        {
            type = 0;
        }
        else if(bType < redWeight + greenWeight)
        {
            type = 1;
        }
        else
        {
            type = 2;
        }

        if(type==1 && ticksSinceLastRed == 0)
        {
            type = 2;
        }

        if(type==1)
        {
            ticksSinceLastRed = 0;
        }

        if(type!=1)
        {
            ticksSinceLastRed++;
            if(ticksSinceLastRed > 4)
            {
                type = 1;
                ticksSinceLastRed = 0;
            }
        }

        int column = (int) (Math.random() * 3) + 1;

        if(ySpeed != lastSpeed)
        {
            column = (lastColumn % 3) + 1;
        }

        lastColumn = column;
        lastSpeed = ySpeed;

        new GameObject(GameObjectType.values()[type], column);

        //Log.d("Dosomething", "Hello2");

    }

    public static enum GameObjectType
    {
        GREEN_BLOCK, RED_BLOCK, BLUE_BLOCK, PLAYER
    }

    public static void increaseScore(int inc)
    {
        if(!gameOver)
        {
            score += inc;
            if (score < 0) score = 0;
        }
    }

    public static void initialise(Context con)
    {
        new GameObject(GameObjectType.PLAYER,2);
        c = con;
    }

    static float[] valsx = new float[32];
    static float[] valsy = new float[32];

    public static void updateScene()
    {
        int num = 0;

        float beat = 0;
        if(GameAudio.isInit)
            beat = (float) GameAudio.plzGetBeatFraction();

        beat = (float) Math.sin(beat*Math.PI*2) + 1;
        beat /= 2.5f;

        for (GameObject block : blockQueue)
        {
            Graphics.moveObjPosition(0.0f,ySpeed,0.0f,block.getObj());
            valsx[num] = block.obj.getTransformedCenter().x;
            valsy[num] = block.obj.getTransformedCenter().y;
            block.obj.setScale(9.0f + beat);

            num++;
        }

        //Graphics.updateShader(valsx, valsy, num);

        checkCollisions();
        //randomSpawn();
        increaseScore(1);
    }

    public static boolean gameOver = false;
    public static void endGame(int reason)
    {
        //reasons
        //0: end of song
        //1: red block
        Log.d("JAHHH", "In end game");
        if(!gameOver)
        {
            Log.d("JAMESS", "endgame by end of song");
            //fly out of screen
            playerObject.moveObject(0, -200, playerObject.getObj(), 4000);

            MainActivity.endGame();
            gameOver = true;
        }
    }


    public static void movePlayer(int direction)
    {
        if (isCameraMoving || playerObject == null) return;

        int newColumn = playerObject.column + direction;

        if(newColumn > 0 && newColumn < 4)
        {
            playerObject.setColumn(newColumn);
        }
    }

    public static void randomSpawn()
    {
        if (Math.random() > 0.95)
        {
            int col = 1 + (int)(Math.random() * 3);
            int type = (int)(Math.random() * 3);
            //new GameObject(GameObjectType.values()[type],col);
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
            if (blockY > Graphics.getHeight() + 50.0f) // screen height / block height
            {
                block.remove(iterator);
                continue;
            }

            if (!block.isAlive())
                continue;

            if (col == playerObject.getColumn())
            {
                if ((playerY - blockY < 15.0f) && (playerY - blockY > -15.0f)) // change this to the actual size of the objects
                {
                    block.kill();
                    switch (block.type)
                    {
                        case GREEN_BLOCK:
                            // add loads of point to score, fast mode?
                            Log.d("SOUNDINVADERS", "green collision");
                            increaseScore(1000);
                            MainActivity.currentStreak += 1000;
                            MainActivity.streakTimer = 60;
                            MainActivity.pointsShowing[col - 1] = 50;
                            //block.remove(iterator);
                            break;

                        case RED_BLOCK:
                            // end the game :(
                            if (isCameraMoving) break;
                            Iterator<GameObject> it = blockQueue.iterator();
                            while(it.hasNext())
                            {
                                GameObject bl = it.next();
                                bl.kill();
                            }
                            increaseScore(-3000);
                            Log.d("REDC", "red collision");
                            break;
                        case BLUE_BLOCK:
                            // add small number of points
                            Log.d("SOUNDINVADERS", "blue collision");
                            increaseScore(500);
                            MainActivity.currentStreak += 500;
                            MainActivity.streakTimer = 60;
                            MainActivity.pointsShowing[col - 1] = 50;
                            //block.remove(iterator);
                            break;
                        default:
                            Log.d("SOUNDINVADERS", "WHAT THE S*** IS GOING ON?!?!?!?!");
                    }
                }
                //endGame();
            }
        }
    }

    public static class GameObject {
        public Object3D obj;
        public GameObjectType type;
        boolean alive = true;

        public boolean isAlive() {
            return alive;
        }

        public void kill() {
            if (!isAlive())
                return;

            alive = false;

            Log.d("SOUNDINVADERS","Kill");

            final Object3D obj = this.getObj();
            final GameObject gameObject = this;
            final int moveTime = 500;
            //blockQueue.remove(gameObject);
            Timer moveTimer = new Timer();
            moveTimer.schedule(new TimerTask() {
                final int stepTime = 15;
                int i = stepTime;
                @Override
                public void run() {
                    float stepMovement = (2000/(moveTime/stepTime));

                    Graphics.moveObjPosition(0,0,stepMovement, obj);

                    i += stepTime;
                    if (i>=moveTime)
                    {
                        Graphics.moveObjPosition(0.0f,100.0f,-3000.0f, obj);
                        //gameObject.removeFromWorld();
                        cancel();
                    }
                }

            }, 0, 15);
        }

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

            int xPos = 15 + (column-1)*25;

            if (type != GameObjectType.PLAYER)
            {
                this.obj = Graphics.addRect(xPos,-50,colour);
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
            final int xMovement = (column - this.column)*25;
            final Object3D obj = this.getObj();
            moveObject(xMovement, 0, obj, 200);
            this.column = column;
        }

        public void moveObject(final float newx, final float newy, final Object3D obj, final int moveTime)
        {
            final Camera camera = Graphics.cam;

            Timer moveTimer = new Timer();
            moveTimer.schedule(new TimerTask() {
                final int stepTime = 15;
                int i = stepTime;
                @Override
                public void run() {
                    float stepMovementx = Easings.easeOutExpo(newx, i, moveTime) - Easings.easeOutExpo(newx, i-stepTime, moveTime);
                    float stepMovementy = Easings.easeInCirc(i, newy, moveTime) - Easings.easeInCirc(i - stepTime, newy, moveTime);

                    Graphics.moveObjPosition(stepMovementx, stepMovementy, 0, obj);

                    // moves the camera with the
                    if (currentMode == GameMode.MODE_FIRST_PERSON)
                    {
                        SimpleVector pos = camera.getPosition();
                        camera.setPosition(pos.x+stepMovementx, pos.y, pos.z);
                    }

                    i += stepTime;
                    if (i>=moveTime) cancel();
                }

            }, 0, 15);

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

        private void removeFromWorld() {
            Graphics.world.removeObject(obj);
        }

        public void remove(Iterator<GameObject> iter)
        {
            iter.remove();
            removeFromWorld();
        }
    }

}
