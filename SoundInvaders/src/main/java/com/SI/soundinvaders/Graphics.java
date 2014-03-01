package com.SI.soundinvaders;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
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

    public static ArrayList<Object3D> objs = new ArrayList<Object3D>();

    private static final float depth = 1.0f;

    static{}

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

    public static Object3D addRect(float x, float y)
    {
        x = translateX(x);
        y = translateY(y);

        Object3D obj = Primitives.getBox(10.0f, 0.1f);
        obj.rotateY((float) (-Math.PI/4.0));
        obj.rotateY((float) (-Math.PI/2.0));
        obj.rotateX((float) (-Math.PI/2.0));
        obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        obj.setAdditionalColor(255, 0, 0);
        obj.compile();
        //obj.setBillboarding(true);

        SimpleVector m = SimpleVector.create(x, y, depth);

        obj.translate(m);
        objs.add(obj);
        world.addObject(obj);

        return obj;
    }

    public static void setColour(int r, int g, int b, Object3D obj)
    {
        obj.setAdditionalColor(r, g, b);
    }

    public static void setObjPosition(float x, float y, Object3D obj)
    {
        x = translateX(x);
        y = translateY(y);

        SimpleVector v = obj.getTransformedCenter();
        SimpleVector f = new SimpleVector();

        f.x = -v.x;
        f.y = -v.y;
        f.z = -v.z;

        obj.translate(f);
        obj.translate(x, y, depth);
    }
      
    public static float getObjX(Object3D obj)
    {
        return backTranslateX(obj.getTransformedCenter().x);
    }
    public static float getObjY(Object3D obj)
    {
        return backTranslateY(obj.getTransformedCenter().y);
    }
}
