package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.infinitedrive.Const;
import com.infinitedrive.DataHandler;
import com.infinitedrive.InfiniteDrive;

public class Player{
    public static Player INSTANCE;

    private Vehicle vehicle;
    private float distanceTraveled;
    private int currentLane;

    public Rectangle getRectangle() {
        return rectangle;
    }

    // Graphics
    private Rectangle rectangle;
    private Texture texture;
    private SpriteBatch batch;

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public Player(){
        INSTANCE = this;
        currentLane = 1;
        vehicle = DataHandler.LoadPlayerVehicle("Car1");

        rectangle = new Rectangle();
        rectangle.x = InfiniteDrive.INSTANCE.getScreenWidth() / 2;
        rectangle.y = InfiniteDrive.INSTANCE.getScreenHeight() / 2;
        rectangle.width = vehicle.getWidth() * vehicle.getSizeMultiplier();
        rectangle.height = vehicle.getHeight() * vehicle.getSizeMultiplier();
        texture = new Texture(vehicle.getTexture());
        batch = InfiniteDrive.INSTANCE.getBatch();

    }

    public void update(){
        movement();

        distanceTraveled += 10 * Gdx.graphics.getDeltaTime();
    }

    public void render(){
        // Texture render
        batch.draw(texture, rectangle.x - (rectangle.width / 2), rectangle.y - (rectangle.height / 2), rectangle.width, rectangle.height);
    }

    public void dispose(){
        texture.dispose();
    }

    private void movement(){
        // Change lane when left or right button is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && currentLane <2){
            currentLane++;
            rectangle.x += Const.LANES_DISTANCE;
            System.out.println(currentLane);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && currentLane >0){
            currentLane--;
            rectangle.x -= Const.LANES_DISTANCE;
            System.out.println(currentLane);
        }
    }
}
