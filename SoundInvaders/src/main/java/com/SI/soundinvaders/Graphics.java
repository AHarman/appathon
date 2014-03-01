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
    public Camera cam;
    public FrameBuffer fb;
    public World world = null;

    public ArrayList<Object3D> objs = new ArrayList<Object3D>();

    private final float depth = 1.5f;

    Graphics()
    {

    }

    public void setCamera(Camera c)
    {
        cam = c;
    }

    public void setFrameBuffer(FrameBuffer b)
    {
        fb = b;
    }

    public void setWorld(World w)
    {
        world = w;
    }

    public Object3D addRect(float x, float y)
    {
        Object3D obj = Primitives.getCube(10.0f);
        obj.rotateY((float) (-Math.PI/4.0));
        obj.setLighting(Object3D.LIGHTING_NO_LIGHTS);
        obj.setAdditionalColor(255, 0, 0);
        obj.compile();

        SimpleVector m = SimpleVector.create(x, y, depth);

        obj.translate(m);
        objs.add(obj);
        world.addObject(obj);

        return obj;
    }

    public void setObjPosition(float x, float y, Object3D obj)
    {
        SimpleVector v = obj.getTransformedCenter();
        SimpleVector f = new SimpleVector();
        f.x = -v.x;
        f.y = -v.y;
        f.z = -v.z;

        obj.translate(f);
        obj.translate(x, y, depth);
    }

}
