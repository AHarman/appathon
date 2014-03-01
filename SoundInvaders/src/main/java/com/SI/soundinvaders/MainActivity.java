package com.SI.soundinvaders;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.ITextureEffect;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

public class MainActivity extends Activity {

    // Used to handle pause and resume...
    private static MainActivity master = null;

    private GLSurfaceView mGLView;
    private MyRenderer renderer = null;
    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(50, 50, 100);

    private float touchTurn = 0;
    private float touchTurnUp = 0;

    private float xpos = -1;
    private float ypos = -1;

    private Texture font = null;

    private Object3D plane;
    private Light light;

    private GLSLShader shader = null;

    private float scale = 0.05f;

    protected void onCreate(Bundle savedInstanceState) {
        Logger.log("onCreate");
        Logger.setLogLevel(Logger.LL_DEBUG);

        if (master != null) {
            copy(master);
        }

        super.onCreate(savedInstanceState);
        mGLView = new GLSurfaceView(getApplication());

        // Enable the OpenGL ES2.0 context
        mGLView.setEGLContextClientVersion(2);

        renderer = new MyRenderer();
        mGLView.setRenderer(renderer);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        Logger.log("onPause");
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

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            xpos = me.getX();
            ypos = me.getY();
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_UP) {
            xpos = -1;
            ypos = -1;
            touchTurn = 0;
            touchTurnUp = 0;
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
            float xd = me.getX() - xpos;
            float yd = me.getY() - ypos;

            xpos = me.getX();
            ypos = me.getY();

            touchTurn = xd / -100f;
            touchTurnUp = yd / -100f;
            return true;
        }

        try {
            Thread.sleep(15);
        } catch (Exception e) {
            // No need for this...
        }

        return super.onTouchEvent(me);
    }

    protected boolean isFullscreenOpaque() {
        return true;
    }

    class MyRenderer implements GLSurfaceView.Renderer {

        private boolean hasToCreateBuffer = false;
        private GL10 lastInstance = null;
        private int w = 0;
        private int h = 0;
        private int fps = 0;
        private int lfps = 0;

        Graphics graphics = new Graphics();

        private long time = System.currentTimeMillis();

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

                plane = Primitives.getPlane(1, 0.001f);

                //graphics.setCamera(camera);
                graphics.setWorld(world);
                Object3D obj = graphics.addRect(10.0f, 10.0f);
                graphics.setObjPosition(-25.0f, 0, obj);

                world.addObject(plane);


                light = new Light(world);
                light.enable();

                light.setIntensity(60, 50, 50);
                light.setPosition(SimpleVector.create(-10, -50, -100));

                world.setAmbientLight(10, 10, 10);

                Camera cam = world.getCamera();

                cam.moveCamera(Camera.CAMERA_MOVEOUT, 70);
                cam.lookAt(plane.getTransformedCenter());

                MemoryHelper.compact();

                world.compileAllObjects();

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

        public void onDrawFrame(GL10 gl) {

            if (this.hasToCreateBuffer) {
                Logger.log("Recreating buffer...");
                hasToCreateBuffer = false;
                fb = new FrameBuffer(w, h);
            }

            if (touchTurn != 0) {
                plane.rotateY(touchTurn);
                touchTurn = 0;
            }

            if (touchTurnUp != 0) {
                plane.rotateX(touchTurnUp);
                touchTurnUp = 0;
            }

            //shader.setUniform("heightScale", scale);

            fb.clear(back);
            world.renderScene(fb);
            world.draw(fb);
            blitNumber(lfps, 5, 5);
            fb.display();

            if (System.currentTimeMillis() - time >= 1000) {
                lfps = fps;
                fps = 0;
                time = System.currentTimeMillis();
            }
            fps++;
        }

        private void blitNumber(int number, int x, int y) {
            if (font != null) {
                String sNum = Integer.toString(number);

                for (int i = 0; i < sNum.length(); i++) {
                    char cNum = sNum.charAt(i);
                    int iNum = cNum - 48;
                    fb.blit(font, iNum * 5, 0, x, y, 5, 9, 5, 9, 10, true, null);
                    x += 5;
                }
            }
        }
    }
}
