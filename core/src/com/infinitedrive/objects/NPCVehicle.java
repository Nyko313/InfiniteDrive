package com.infinitedrive.objects;

public class NPCVehicle {

    protected String name;
    protected int maxVelocity;
    protected int minVelocity;

    // Graphic
    protected String texture;
    protected float width;
    protected float height;
    protected float sizeMultiplier;


    public String getName() {
        return name;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public int getMinVelocity() {
        return minVelocity;
    }

    public float getWidth() {
        return width ;
    }

    public float getHeight() {
        return height;
    }
    public float getSizeMultiplier() {
        return sizeMultiplier;
    }

    public String getTexturePath() {
        return texture;
    }


    public NPCVehicle(String name, int maxVelocity, int minVelocity, int width, int height, float sizeMultiplier, String texture) {
        this.name = name;
        this.maxVelocity = maxVelocity;
        this.minVelocity = minVelocity;
        this.width = width;
        this.height = height;
        this.sizeMultiplier = sizeMultiplier;
        this.texture = texture;
    }

    public NPCVehicle(){

    }




}
