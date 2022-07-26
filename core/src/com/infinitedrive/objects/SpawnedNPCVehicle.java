package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.Const;

public class SpawnedNPCVehicle extends NPCVehicle{

    private NPCVehicleSpawner npcVehicleSpawner;
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


    public SpawnedNPCVehicle(NPCVehicle vehicle, World world, float posX, float posY){

        this.name = vehicle.getName();
        this.maxVelocity = vehicle.getMaxVelocity();
        this.minVelocity = vehicle.getMinVelocity();
        this.width = vehicle.getMinVelocity();
        this.height = vehicle.getHeight();
        this.width = vehicle.getWidth();
        this.sizeMultiplier = vehicle.getSizeMultiplier();
        this.texture = new Texture(vehicle.getTexturePath());
        this.world = world;

        npcVehicleSpawner = NPCVehicleSpawner.INSTANCE;

        currentVelocity = minVelocity;

        createBody(posX, posY);
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

    // Create the rigid body
    public void createBody(float posX, float posY){
        // Set up the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        // Create the fixture and the box shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width-10, height- 10);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        // Active body physic
        body.setSleepingAllowed(false);
        // Dispose unused shape
        shape.dispose();

        //Set as sensor
        for(Fixture fix :body.getFixtureList()){
            fix.setSensor(true);
        }

        // Set a name for identification
        body.setUserData("NPCVehicle");
    }
}
