package com.infinitedrive.objects.npcvehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.helpers.BodyCreator;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.objects.Player;

public class SpawnedNPCVehicle extends NPCVehicle{

    private float currentVelocity;
    private boolean isFlaggedForDelete;

    // Graphic
    private Texture texture;

    // Physics
    private World world;
    private Body body;


    public float getCurrentVelocity() {
        return currentVelocity;
    }
    public void setCurrentVelocity(float currentVelocity) {
        this.currentVelocity = currentVelocity;
    }
    public Body getBody() {
        return body;
    }
    public Texture getTexture() {
        return texture;
    }


    public SpawnedNPCVehicle(NPCVehicle vehicle, World world, float x, float y){

        this.name = vehicle.getName();
        this.maxVelocity = vehicle.getMaxVelocity();
        this.minVelocity = vehicle.getMinVelocity();
        this.height = vehicle.getHeight() * vehicle.getSizeMultiplier();
        this.width = vehicle.getWidth()  * vehicle.getSizeMultiplier();
        this.sizeMultiplier = vehicle.getSizeMultiplier();
        this.texture = new Texture(vehicle.getTexturePath());
        this.world = world;

        currentVelocity = minVelocity;

        body = BodyCreator.createBoxBody(world, x,y,width,height,this, true, true);
    }

    public void update(){
        // Move
        body.setTransform(new Vector2(body.getPosition().x,body.getPosition().y - (Player.INSTANCE.getCurrentVelocity() - currentVelocity ) * Const.VEHICLES_VELOCITY_MULTIPLIER* Gdx.graphics.getDeltaTime()), 0 );

        // Delete vehicle
        if(body.getPosition().y < -200){
           destroy();
        }

        if(isFlaggedForDelete){
            if(!world.isLocked()){
                NPCVehicleSpawner.INSTANCE.destroyVehicle(this);
            }
        }
    }

    public void destroy(){
        isFlaggedForDelete = true;
    }

    public void dispose(){
        texture.dispose();
    }
}
