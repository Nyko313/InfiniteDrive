package com.infinitedrive.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.Gameobject;

public class GameobjectsManager {
    public static GameobjectsManager INSTANCE;

    private World world;
    private SpriteBatch batch;
    private final Array<Gameobject> gameobjects;
    private Array<Gameobject> gameobjectsToDestroy;

    public GameobjectsManager(World world, SpriteBatch batch){
        INSTANCE = this;
        this.world = world;
        this.batch = batch;
        gameobjects = new Array<>();
        gameobjectsToDestroy = new Array<>();

    }


    public void initializeGameobject(Gameobject gameobject){
        gameobjects.add(gameobject);

        if(gameobject.getRenderPriority() == -1) gameobject.setRenderPriority(gameobjects.size);
        gameobject.setBatch(batch);
        gameobject.setWorld(world);

        sortRenderPriorities();
    }

    public void update(){
        for(int i = 0; i < gameobjects.size; i++){
            gameobjects.get(i).update();
        }

        // Destroy the flagged gameobjects
        if(!world.isLocked()){
            for(int i = 0; i < gameobjectsToDestroy.size; i++){
                if(gameobjectsToDestroy.get(i).getBody() != null){
                    world.destroyBody(gameobjectsToDestroy.get(i).getBody());
                }
                gameobjects.removeValue(gameobjectsToDestroy.get(i), true);
            }
            gameobjectsToDestroy.clear();
        }

    }

    public void render(){
        // update the render methods
        for(int i = 0; i < gameobjects.size; i++){
            gameobjects.get(i).render();
        }
    }

    public void destroyGameobject(Gameobject go){
        gameobjectsToDestroy.add(go);
    }

    public void dispose(){
        for(int i = 0; i < gameobjects.size; i++){
            gameobjects.get(i).dispose();
        }
    }

    private void sortRenderPriorities(){
        Gameobject tmp;
        for(int i = 0; i < gameobjects.size; i++){
            for (int x = 0; x < gameobjects.size - 1; x++){
                if(gameobjects.get(x).getRenderPriority() > gameobjects.get(x + 1).getRenderPriority()){
                    tmp = gameobjects.get(x);
                    gameobjects.set(x, gameobjects.get(x + 1));
                    gameobjects.set(x + 1, tmp);
                }
            }
        }
    }


}
