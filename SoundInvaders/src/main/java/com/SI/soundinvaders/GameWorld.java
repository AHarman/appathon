package com.SI.soundinvaders;

import com.threed.jpct.Object3D;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by andy on 01/03/2014.
 */
public class GameWorld {

    public Deque<GameObject> gameObjectsQueue = new ArrayDeque<GameObject>();
    //public GameObject playerObject = new GameObject();

    public enum GameObjectType
    {
        GREEN_BLOCK, RED_BLOCK, BLUE_BLOCK,
        PLAYER
    }

    public GameWorld()
    {
        initialiseGameWorld();
    }

    public void initialiseGameWorld()
    {
        
    }


    public void checkCollisions()
    {
        for (GameObject go : gameObjectsQueue)
        {

            switch (go.type)
            {
                case GREEN_BLOCK:
                    break;
                case RED_BLOCK:
                    break;
                case BLUE_BLOCK:
                    break;
                case PLAYER:
                    // do nothing
                    break;
            }
        }
    }

    public class GameObject {
        public Object3D obj;
        public GameObjectType type;

        public GameObject(Object3D obj, GameObjectType type) {
            this.obj = obj;
            this.type = type;
        }

        public Object3D getObj() {
            return obj;
        }

        public void setObj(Object3D obj) {
            this.obj = obj;
        }
    }

}
