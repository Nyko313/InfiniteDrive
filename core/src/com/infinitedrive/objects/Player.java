package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.infinitedrive.*;
import com.infinitedrive.helpers.BodyCreator;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.helpers.DataHandler;
import com.infinitedrive.objects.rocket.Rocket;

public class Player extends Gameobject{
    public static Player INSTANCE;

    private Vehicle vehicle;
    private float distanceTraveled;
    private int currentLane = 1;
    private float currentVelocity;
    private float targetVelocity;
    private float oldTargetVelocity;

    private boolean isBreaking;
    private float brakeDurability;

    private boolean crashed;

    private int rocketAmount = 3;

    // Graphics
    private Texture texture;
    private float width;
    private float height;

    // Physics
    private Body body;

    public int getRocketAmount() {
        return rocketAmount;
    }
    public void setRocketAmount(int rocketAmount) {
        this.rocketAmount = rocketAmount;
    }
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
        renderPriority = 3;
        initialize(this);

        INSTANCE = this;
        this.world = world;

        // Load the vehicle
        vehicle = DataHandler.loadPlayerVehicle("Car1");

        // Sprite setup
        width = vehicle.getWidth() * vehicle.getSizeMultiplier();
        height = vehicle.getHeight() * vehicle.getSizeMultiplier();
        texture = new Texture(vehicle.getTexture());
        batch = InfiniteDrive.INSTANCE.getBatch();

        targetVelocity = vehicle.getStartingVelocity() + 20;
        brakeDurability = vehicle.getBrakeDurability();

        body = BodyCreator.createBoxBody(world, InfiniteDrive.INSTANCE.getScreenWidth() / 2, InfiniteDrive.INSTANCE.getScreenHeight() / 2,width,height,this,true, false);

    }

    @Override
    public void update(){


        if(!crashed){
            movement();
            brake();
            shootRocket();
            targetVelocity += 0.02f;
        }

        // Update the travelled distance
        distanceTraveled += Gdx.graphics.getDeltaTime() * currentVelocity  * 0.2f;

        // Smooth accelerate/decelerate
        if(currentVelocity < targetVelocity && targetVelocity - currentVelocity <0.3f || currentVelocity > targetVelocity && currentVelocity - targetVelocity <0.3f){
            currentVelocity = targetVelocity;
        }
        if(currentVelocity != targetVelocity){
            float delta = targetVelocity - currentVelocity;
            delta *= Gdx.graphics.getDeltaTime() * 2;
            currentVelocity += delta;
        }
    }

    @Override
    public void render(){
        // Texture render
        batch.draw(texture, body.getPosition().x - (width / 2), body.getPosition().y - (height / 2), width, height);
    }

    @Override
    public void dispose(){
        texture.dispose();
    }

    public void crash(){
        if(!crashed){
            // Instantiate particles
            new Particle(body.getPosition().x, body.getPosition().y, true, "explosion.party");
            new Particle(body.getPosition().x, body.getPosition().y+30, true, "smoke.party");

            // Set target velocity
            targetVelocity = 0;

            crashed = true;
        }

    }

    private void movement(){
        // Switch lane when left/right button is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && currentLane <2){
            currentLane++;
            body.setTransform(new Vector2(body.getPosition().x + Const.LANES_DISTANCE,body.getPosition().y),0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && currentLane >0){
            currentLane--;
            body.setTransform(new Vector2(body.getPosition().x - Const.LANES_DISTANCE,body.getPosition().y),0);
        }
    }

    // Reduce player velocity
    private void brake(){

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && brakeDurability > 0.2f){
            if(!isBreaking){
                oldTargetVelocity = targetVelocity;
            }
            isBreaking = true;
            targetVelocity = 30;
            brakeDurability -= Const.PLAYER_BRAKE_FORCE;
        }else if(isBreaking){
            isBreaking = false;
            targetVelocity = oldTargetVelocity;
        }
    }

    private void shootRocket(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rocketAmount>0)
        {
            // Instantiate new rocket
            new Rocket(body.getPosition().x, body.getPosition().y + 30);
            rocketAmount --;
        }

    }
}
