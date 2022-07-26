package com.infinitedrive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.infinitedrive.objects.GameobjectsManager;

public class Gameobject {
    protected boolean isFlaggedForDelete;
    protected int renderPriority = -1;
    protected World world;
    protected SpriteBatch batch;
    protected Body body;

    public int getRenderPriority() {
        return renderPriority;
    }
    public void setRenderPriority(int renderPriority) {
        this.renderPriority = renderPriority;
    }

    public World getWorld() {
        return world;
    }
    public void setWorld(World world) {
        this.world = world;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
    public Body getBody() {
        return body;
    }
    public void setBody(Body body) {
        this.body = body;
    }

    public void update(){

    }

    public void render(){

    }

    public void destroy(){
        GameobjectsManager.INSTANCE.destroyGameobject(this);
        isFlaggedForDelete = true;
    }

    public void dispose(){

    }

    protected void initialize(Gameobject gameobject){
        GameobjectsManager.INSTANCE.initializeGameobject(gameobject);
    }

}
