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

    ArrayList<Object3D> objs = new ArrayList<Object3D>();

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

    public int addRect(SimpleVector v)
    {
        Object3D obj = Primitives.getCube(1.0f);
        obj.compile();
        v.z = 0;
        //obj.addTriangle(SimpleVector.create(0, 0, 0), 0, 0, SimpleVector.create(1, 0, 0), 0, 0, SimpleVector.create(0, 1, 0), 0, 0);
        obj.translate(v);
        objs.add(obj);
        world.addObject(obj);
        return objs.size() -1;
    }

}
