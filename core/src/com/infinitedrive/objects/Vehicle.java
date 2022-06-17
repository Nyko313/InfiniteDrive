package com.infinitedrive.objects;

public class Vehicle {
    private String name;
    private float startingVelocity;
    private float maxVelocity;
    private float brakeDurability;
    private int price;
    private int width;
    private int height;
    private float sizeMultiplier;
    private String texture;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSizeMultiplier() {
        return sizeMultiplier;
    }

    public String getName() {
        return name;
    }

    public String getTexture() {
        return texture;
    }

    public float getStartingVelocity() {
        return startingVelocity;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getBrakeDurability() {
        return brakeDurability;
    }

    public int getPrice() {
        return price;
    }

    public Vehicle(String name, float startingVelocity, float maxVelocity, float brakeDurability, int price, int width, int height, float sizeMultiplier, String texture) {
        this.name = name;
        this.startingVelocity = startingVelocity;
        this.maxVelocity = maxVelocity;
        this.brakeDurability = brakeDurability;
        this.price = price;
        this.width = width;
        this.height = height;
        this.sizeMultiplier = sizeMultiplier;
        this.texture = texture;
    }

    public Vehicle(){

    }
}
