package com.SI.soundinvaders;

import android.util.Log;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.Primitives;

import java.util.ArrayList;

/**
 * Created by James on 01/03/14.
 */
public class Graphics {
    public static Camera cam;
    public static FrameBuffer fb;
    public static World world = null;

    //public static ArrayList<Object3D> objs = new ArrayList<Object3D>();

    private static final float depth = 1.0f;

    static
    {

    }

    public static void init()
    {

    }

    public static void setCamera(Camera c)
    {
        cam = c;
    }

    public static void setFrameBuffer(FrameBuffer b)
    {
        fb = b;
    }

    public static void setWorld(World w)
    {
        world = w;
    }

    private static float translateX(float x)
    {
        return x - getWidth()/2.0f;
    }
    private static float translateY(float y)
    {
        return y - getHeight()/2.0f;
    }

    private static float backTranslateX(float x)
    {
        return x + getWidth()/2.0f;
    }

    private static float backTranslateY(float y)
    {
        return y + getHeight()/2.0f;
    }

    public static float getWidth()
    {
        return 80.0f;
    }

    public static float getHeight()
    {
        return 120.0f;
    }

    public static float getBoxSize()
    {
        return 10.0f;
    }

    public static Object3D addRect(float x, float y, RGBColor colour)
    {
        x = translateX(x);
        y = translateY(y);

        Object3D obj=new Object3D(12);

        float scale = 0.75f;

        SimpleVector upperLeftFront=new SimpleVector(-1,-1,-1*scale);
        SimpleVector upperRightFront=new SimpleVector(1,-1,-1*scale);
        SimpleVector lowerLeftFront=new SimpleVector(-1,1,-1*scale);
        SimpleVector lowerRightFront=new SimpleVector(1,1,-1*scale);

        SimpleVector upperLeftBack = new SimpleVector( -1, -1, 1*scale);
        SimpleVector upperRightBack = new SimpleVector(1, -1, 1*scale);
        SimpleVector lowerLeftBack = new SimpleVector( -1, 1, 1*scale);
        SimpleVector lowerRightBack = new SimpleVector(1, 1, 1*scale);

        // Front
        obj.addTriangle(upperLeftFront,0,0, lowerLeftFront,0,1, upperRightFront,1,0);
        obj.addTriangle(upperRightFront,1,0, lowerLeftFront,0,1, lowerRightFront,1,1);

        // Back
        obj.addTriangle(upperLeftBack,0,0, upperRightBack,1,0, lowerLeftBack,0,1);
        obj.addTriangle(upperRightBack,1,0, lowerRightBack,1,1, lowerLeftBack,0,1);

        // Upper
        obj.addTriangle(upperLeftBack,0,0, upperLeftFront,0,1, upperRightBack,1,0);
        obj.addTriangle(upperRightBack,1,0, upperLeftFront,0,1, upperRightFront,1,1);

        // Lower
        obj.addTriangle(lowerLeftBack,0,0, lowerRightBack,1,0, lowerLeftFront,0,1);
        obj.addTriangle(lowerRightBack,1,0, lowerRightFront,1,1, lowerLeftFront,0,1);

        // Left
        obj.addTriangle(upperLeftFront,0,0, upperLeftBack,1,0, lowerLeftFront,0,1);
        obj.addTriangle(upperLeftBack,1,0, lowerLeftBack,1,1, lowerLeftFront,0,1);

        // Right
        obj.addTriangle(upperRightFront,0,0, lowerRightFront,0,1, upperRightBack,1,0);
        obj.addTriangle(upperRightBack,1,0, lowerRightFront, 0,1, lowerRightBack,1,1);

        //obj.setTexture("base");
        obj.build();

        obj.scale(10.0f);

        //obj.setOrigin(SimpleVector.create(0, 0, 10.0f));

        //obj.rotateY((float) (-Math.PI/4.0));
        //obj.rotateY((float) (-Math.PI/2.0));
        //obj.rotateX((float) (-Math.PI/2.0));
        //obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        //obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        obj.setAdditionalColor(colour);
        obj.compile();
        obj.strip();

        SimpleVector m = SimpleVector.create(x, y, depth);

        obj.translate(m);
        //objs.add(obj);
        world.addObject(obj);

        return obj;
    }

    public static Object3D addPlayer(float x, float y, RGBColor colour)
    {
        x = translateX(x);
        y = translateY(y);

        Object3D obj = new Object3D(1);

        obj.addTriangle(SimpleVector.create(0, -1.0f, 0), SimpleVector.create(-1.0f, 1.0f, 0), SimpleVector.create(1.0f, 1.0f, 0));
        obj.build();

        obj.scale(10.0f);

        //obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        //obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        obj.setAdditionalColor(colour);
        obj.compile();
        obj.strip();

        SimpleVector m = SimpleVector.create(x, y, depth);

        obj.translate(m);
        //objs.add(obj);
        world.addObject(obj);

        return obj;
    }

    public static void setColour(int r, int g, int b, Object3D obj)
    {
        obj.setAdditionalColor(r, g, b);
    }

    public static void setObjPosition(float x, float y, Object3D obj)
    {
        //x = translateX(x);
        //y = translateY(y);

        SimpleVector v = obj.getTransformedCenter();
        SimpleVector f = new SimpleVector();

        f.x = -v.x;
        f.y = -v.y;
        f.z = -v.z;

        obj.translate(f);
        obj.translate(x, y, depth);
    }

    public static void moveObjPosition(float x, float y, Object3D obj)
    {
        SimpleVector v = SimpleVector.create(x, y, 0.0f);

        obj.translate(v);
    }
      
    public static float getObjX(Object3D obj)
    {
        return backTranslateX(obj.getTransformedCenter().x);
    }
    public static float getObjY(Object3D obj)
    {
        return backTranslateY(obj.getTransformedCenter().y);
    }

    public static void removeObject(Object3D obj)
    {
        world.removeObject(obj);
    }
}
