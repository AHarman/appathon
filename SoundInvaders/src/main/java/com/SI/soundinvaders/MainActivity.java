package com.SI.soundinvaders;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;


public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    // Used to handle pause and resume...
    private static MainActivity master = null;


    private GLSurfaceView mGLView;
    private MyRenderer renderer = null;
    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(44, 62, 80);

    private GestureDetector mDetector;

    private float touchTurn = 0;
    private float touchTurnUp = 0;

    private float xpos = -1;
    private float ypos = -1;

    private static TextView pointTextViews[] = {null, null, null};
    public static int currentStreak = 0;
    public static int pointsShowing[] = {0, 0, 0};
    public static int streakTimer;

    private Texture font = null;

    private Object3D plane;
    private Light light;

    public static Context con;

    private int screenWidth;

    public static Boolean gameOver = false;

    private GLSLShader shader = null;
    private boolean gameOverNotTriggered = true;

    public static void endGame()
    {

        Intent intent = new Intent(master, EndActivity.class);
        master.startActivity(intent);

    }

    protected void onCreate(Bundle savedInstanceState) {

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point sharp = new Point();

        con  = this.getApplicationContext();

        if(Build.VERSION.SDK_INT >= 10)
        {
            display.getRealSize(sharp);
            screenWidth = sharp.x;
        }
        else
        {
            screenWidth = display.getWidth();
        }

        GameAudio.init(getApplicationContext());


        Logger.log("onCreate");
        Logger.setLogLevel(Logger.LL_DEBUG);

        if (master != null) {
            copy(master);
        }

        mDetector = new GestureDetector(this,this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mGLView = (GLSurfaceView)findViewById(R.id.openGlView);

        // Enable the OpenGL ES2.0 context
        mGLView.setEGLContextClientVersion(2);

        renderer = new MyRenderer();
        mGLView.setRenderer(renderer);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bol.ttf");
        TextView tvScore = (TextView)findViewById(R.id.tvScore);
        tvScore.setTypeface(myTypeface);

        pointTextViews[0] = (TextView)findViewById(R.id.leftBox);
        pointTextViews[1] = (TextView)findViewById(R.id.centreBox);
        pointTextViews[2] = (TextView)findViewById(R.id.rightBox);
        pointTextViews[0].setTypeface(myTypeface);
        pointTextViews[1].setTypeface(myTypeface);
        pointTextViews[2].setTypeface(myTypeface);

        hideSystemBars();

        new ScoreBoard(this.getApplicationContext());
        updateScore(0);

    }


    void updateScore(int score)
    {
        TextView tvScore = (TextView)findViewById(R.id.tvScore);
        tvScore.setText(String.valueOf(score));
    }

    public static void drawPoints(int col, int val){
        if(pointTextViews[col - 1] == null)
            return;

        if(val > 0)
        {
            pointTextViews[col - 1].setText("+" + val);
            pointsShowing[col - 1] = 20;
        }
        else
        {
            pointTextViews[col - 1].setText("");
        }
    }


    @Override
    protected void onPause() {
        Logger.log("onPause");
        GameAudio.pauseMedia();
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        Logger.log("onResume");
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop() {
        Logger.log("onStop");
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        // Immersive mode is only supported in Android KitKat and above
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            hideSystemBars();
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        if(velocityX < 0)
            GameWorld.movePlayer(-1);
        else if(velocityX > 0)
            GameWorld.movePlayer(1);
        return true;
    }

    public boolean onDown(MotionEvent event) {return true;}
    public void onLongPress(MotionEvent event) {}
    public void onShowPress(MotionEvent event) {}

    public boolean onDoubleTap(MotionEvent event) {return true;}
    public boolean onDoubleTapEvent(MotionEvent event) {return true;}
    public boolean onSingleTapConfirmed(MotionEvent event) {return true;}
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return true;}

    public boolean onSingleTapUp(MotionEvent event)
    {
        //showScores();
        if(event.getX() > (int)(screenWidth/2))
            GameWorld.movePlayer(1);
        else
            GameWorld.movePlayer(-1);

        return true;
    }


    private void copy(Object src) {
        try {
            Logger.log("Copying data from master Activity!");
            Field[] fs = src.getClass().getDeclaredFields();
            for (Field f : fs) {
                f.setAccessible(true);
                f.set(this, f.get(src));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean onTouchEvent(MotionEvent me) {

        mDetector.onTouchEvent(me);
        return super.onTouchEvent(me);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideSystemBars()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

    class MyRenderer implements GLSurfaceView.Renderer {

        private boolean hasToCreateBuffer = false;
        private GL10 lastInstance = null;
        private int w = 0;
        private int h = 0;

        GLSLShader shader;

        public MyRenderer() {
            Texture.defaultToMipmapping(true);
            Texture.defaultTo4bpp(true);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Resources res = getResources();

            if (master == null) {

                Logger.log("Initializing buffer...");
                fb = new FrameBuffer(width, height);

                Logger.log("Initializing game...");
                world = new World();

                font = new Texture(res.openRawResource(R.raw.numbers));
                font.setMipmap(false);

                
                plane = Primitives.getPlane(1, 10000.0f);
                plane.setOrigin(SimpleVector.create(0, 0, 2000));
                plane.setAdditionalColor(back);

                shader = new GLSLShader(Loader.loadTextFile(res.openRawResource(R.raw.vertex_shader)), Loader.loadTextFile(res.openRawResource(R.raw.fragment_shader)));
                shader.setUniform("windowSize", SimpleVector.create(fb.getWidth(), fb.getHeight(), 0.0f));
                plane.setShader(shader);

                plane.setAdditionalColor(0, 255, 0);

                plane.setVisibility(false);

                shader.setUniform("boxNum", 0);

                Camera cam = world.getCamera();
                //cam.moveCamera(Camera.CAMERA_MOVEOUT, 70);
                cam.lookAt(plane.getTransformedCenter());

                Graphics.setCamera(cam);
                Graphics.setFrameBuffer(fb);
                Graphics.setWorld(world);

                Graphics.init();

                Graphics.setShader(shader);

                world.addObject(plane);

                light = new Light(world);
                light.enable();

                light.setIntensity(60, 50, 50);
                light.setPosition(SimpleVector.create(0, 0, -100));
                world.setAmbientLight(10, 10, 10);

                cam.moveCamera(Camera.CAMERA_MOVEOUT, 70);
                cam.lookAt(plane.getTransformedCenter());


                MemoryHelper.compact();

                world.compileAllObjects();

                GameWorld.initialise(getApplicationContext());

                GameAudio.startMedia();

                if (master == null) {
                    Logger.log("Saving master Activity!");
                    master = MainActivity.this;
                }
            } else {
                if (lastInstance != gl) {
                    Logger.log("Setting buffer creation flag...");
                    this.hasToCreateBuffer = true;
                    w = width;
                    h = height;
                }
            }
            lastInstance = gl;

        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Logger.log("onSurfaceCreated");
        }

        private float beatFracAvg = 0;

        int c = 0;
        boolean b = false;

        public void onDrawFrame(GL10 gl) {

            if(GameAudio.isGoing())
            {
                if(GameAudio.isInit)
                    AudioGameplayIntegrator.audioTick();

                beatFracAvg = (float)GameAudio.plzGetBeatFraction();

                if(beatFracAvg < 0.5 && !b)
                {
                    c++;
                    b = true;
                }
                if(beatFracAvg > 0.5)
                {
                    b = false;
                }

                if(shader!=null)
                    shader.setUniform("beatFrac", beatFracAvg);
                //Log.d("SoundInvaders", Float.toString(beatFracAvg));
            }
            else
            {
                //Game Finished
                GameWorld.endGame();
            }

            if (this.hasToCreateBuffer) {
                Logger.log("Recreating buffer...");
                hasToCreateBuffer = false;
                fb = new FrameBuffer(w, h);
            }

            if (touchTurn != 0) {
                //plane.rotateY(touchTurn);
                //plane.rotateY(touchTurn);
                touchTurn = 0;
            }

            if (touchTurnUp != 0) {
                //plane.rotateX(touchTurnUp);
                //plane.rotateX(touchTurnUp);
                touchTurnUp = 0;
            }

            GameWorld.updateScene();
            //shader.setUniform("heightScale", scale);

            fb.clear(back);
            world.renderScene(fb);
            world.draw(fb);

            fb.display();

            master.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    master.updateScore(GameWorld.score);
                }
            });
            master.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 3; i++)
                    {
                        if(pointsShowing[i] == 50){
                            master.drawPoints(i+1, currentStreak);
                            pointsShowing[i]--;
                        }
                        else if(pointsShowing[i] > 0)
                        {
                            pointsShowing[i]--;
                            if(pointsShowing[i] == 0)
                                master.drawPoints(i+1, -1);
                        }
                    }

                    streakTimer--;
                    if(streakTimer == 0)
                        currentStreak = 0;
                }
            });
        }

    }
}
