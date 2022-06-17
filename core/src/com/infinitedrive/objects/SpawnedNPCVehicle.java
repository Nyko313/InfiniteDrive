package com.infinitedrive.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class SpawnedNPCVehicle extends NPCVehicle{
    private Texture texture;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public SpawnedNPCVehicle(String name, int maxVelocity, int minVelocity, int width, int height, float sizeMultiplier, String texture, Rectangle rectangle) {
        this.name = name;
        this.maxVelocity = maxVelocity;
        this.minVelocity = minVelocity;
        this.width = width;
        this.height = height;
        this.sizeMultiplier = sizeMultiplier;
        this.texture = new Texture(texture);
        this.rectangle = rectangle;
    }

    public SpawnedNPCVehicle(NPCVehicle vehicle, Rectangle rectangle){
        this.name = vehicle.getName();
        this.maxVelocity = vehicle.getMaxVelocity();
        this.minVelocity = vehicle.getMinVelocity();
        this.width = vehicle.getMinVelocity();
        this.height = vehicle.getHeight();
        this.width = vehicle.getWidth();
        this.sizeMultiplier = vehicle.getSizeMultiplier();
        this.texture = new Texture(vehicle.getTexturePath());
        this.rectangle = rectangle;
    }
}
