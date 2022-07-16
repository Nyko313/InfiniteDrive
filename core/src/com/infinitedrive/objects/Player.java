package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.infinitedrive.Const;
import com.infinitedrive.DataHandler;
import com.infinitedrive.InfiniteDrive;

import java.text.DecimalFormat;

public class Player{
    public static Player INSTANCE;

    private Vehicle vehicle;
    private float distanceTraveled;
    private int currentLane;
    private float currentVelocity;
    private float targetVelocity;
    private float oldTargetVelocity;

    private boolean isBreaking;
    private float brakeDurability;

    private boolean crashed;

    // Graphics
    private Texture texture;
    private SpriteBatch batch;
    private float width;
    private float height;

    // Physics
    private World world;
    private Body body;


    public float getCurrentVelocity() {
        return currentVelocity;
    }
    public void setCurrentVelocity(float currentVelocity) {
        this.currentVelocity = currentVelocity;
    }
    public float getBrakeDurability() {
        return brakeDurability;
    }
    public float getDistanceTraveled() {
        return distanceTraveled;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }


    public Player(World world){
        INSTANCE = this;
        this.world = world;
        currentLane = 1;

        // Load a vehicle
        vehicle = DataHandler.loadPlayerVehicle("Car1");

        // Sprite setup
        width = vehicle.getWidth() * vehicle.getSizeMultiplier();
        height = vehicle.getHeight() * vehicle.getSizeMultiplier();
        texture = new Texture(vehicle.getTexture());
        batch = InfiniteDrive.INSTANCE.getBatch();

        targetVelocity = vehicle.getStartingVelocity();
        brakeDurability = vehicle.getBrakeDurability();


        createBody();
    }

    public void update(){
        if(!crashed){
            movement();
            brake();
        }


        // Update the travelled distance
        distanceTraveled += Gdx.graphics.getDeltaTime() * currentVelocity  * 0.2f;

        // Smooth accelerate and decelerate
        if(currentVelocity < targetVelocity && targetVelocity - currentVelocity <0.3f || currentVelocity > targetVelocity && currentVelocity - targetVelocity <0.3f){
            currentVelocity = targetVelocity;
        }
        if(currentVelocity != targetVelocity){
            float delta = targetVelocity - currentVelocity;
            delta *= Gdx.graphics.getDeltaTime();
            currentVelocity += delta;
        }


    }

    public void render(){
        // Texture render
        batch.draw(texture, body.getPosition().x - (width / 2), body.getPosition().y - (height / 2), width, height);
    }

    public void dispose(){
        texture.dispose();
    }

    public void crash(){
        targetVelocity = 0;
        crashed = true;
    }

    private void movement(){
        // Change lane when left or right button is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && currentLane <2){
            currentLane++;
            body.setTransform(new Vector2(body.getPosition().x + Const.LANES_DISTANCE,body.getPosition().y),0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && currentLane >0){
            currentLane--;
            body.setTransform(new Vector2(body.getPosition().x - Const.LANES_DISTANCE,body.getPosition().y),0);
        }
    }

    private void brake(){

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && brakeDurability > 0){
            if(!isBreaking){
                oldTargetVelocity = targetVelocity;
            }
            isBreaking = true;
            targetVelocity = 30;
            //brakeDurability -= Const.PLAYER_BRAKE_FORCE;
        }else if(isBreaking){
            isBreaking = false;
            targetVelocity = oldTargetVelocity;
            System.out.println(targetVelocity);
        }

    }

    // Create the rigid body
    private void createBody(){
        // Set up the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(InfiniteDrive.INSTANCE.getScreenWidth() / 2, InfiniteDrive.INSTANCE.getScreenHeight() / 2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        // Create the fixture and the box shape
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width-17, height- 30);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        // Active body physic
        body.setSleepingAllowed(false);
        // Dispose unused shape
        shape.dispose();
        // Set a name for identification
        body.setUserData("Player");
    }
}
