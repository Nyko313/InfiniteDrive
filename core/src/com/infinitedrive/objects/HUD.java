package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;

public class HUD extends Gameobject {
    private  float brakesBarWidth;
    private Texture dashboard;
    private Texture brakesBar;
    private Texture dashboardBar;
    private Texture rocketTexture;
    private BitmapFont pixelFont;

    public HUD(){
        renderPriority = 50;
        initialize(this);

        dashboard = new Texture("sprites/gui/dashboard.png");
        dashboardBar = new Texture("sprites/gui/dashboardBar.png");
        brakesBar = new Texture("sprites/gui/brakesBar.png");
        rocketTexture = new Texture("sprites/rocket.png");

        // Font setup
        pixelFont = new BitmapFont(Gdx.files.internal("fonts/PixelatedFont.fnt"));
        pixelFont.getData().setScale(0.8f);
    }

    @Override
    public void update() {
        brakesBarWidth = Player.INSTANCE.getBrakeDurability() / 20 * 118;
    }

    @Override
    public void render() {
        batch.draw(dashboard,0,0);
        batch.draw(brakesBar, 365, 17, brakesBarWidth, 16);
        batch.draw(dashboardBar, 0, 0);


        // Distance
        pixelFont.setColor(Color.WHITE);
        pixelFont.getData().setScale(0.5f, 0.5f);
        pixelFont.draw(batch,String.format("%.0f", Player.INSTANCE.getDistanceTraveled()), InfiniteDrive.INSTANCE.getScreenWidth() / 2  -40, 70);
        // Speed
        pixelFont.setColor(Color.WHITE);
        pixelFont.getData().setScale(0.8f, 0.8f);
        pixelFont.draw(batch, String.format("%.0f", Player.INSTANCE.getCurrentVelocity()), InfiniteDrive.INSTANCE.getScreenWidth() / 2 -35, 123);

        // Rocket
        switch (Player.INSTANCE.getRocketAmount()){
            case 1:
                batch.draw(rocketTexture, 27, 17, 13, 40);
                break;
            case 2 :
                batch.draw(rocketTexture, 27, 17, 13, 40);
                batch.draw(rocketTexture, 50, 17, 13, 40);
                break;
            case 3:
                batch.draw(rocketTexture, 27, 17, 13, 40);
                batch.draw(rocketTexture, 50, 17, 13, 40);
                batch.draw(rocketTexture, 73, 17, 13, 40);
                break;
        }
    }

    @Override
    public void dispose() {
        dashboard.dispose();
        rocketTexture.dispose();
        pixelFont.dispose();
    }
}
