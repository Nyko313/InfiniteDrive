package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.infinitedrive.Const;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;

import java.util.Iterator;

public class StreetSpawner extends Gameobject {
    private Vector2 spawnPosition;
    private Vector2 size;
    private float sizeMultiplier = 1.8f;
    private Texture texture;
    private Array<Rectangle> streetTiles;
    private Rectangle lastStreetSpawned;
    private Player player;
    private Vehicle vehicle;


    public StreetSpawner(){
        super.renderPriority = 0;
        initialize(this);

        texture = new Texture("Street.png");
        streetTiles = new Array<Rectangle>();


        size = new Vector2(100*sizeMultiplier, 100*sizeMultiplier);
        spawnPosition = new Vector2(InfiniteDrive.INSTANCE.getScreenWidth() / 2 - size.x / 2, InfiniteDrive.INSTANCE.getScreenHeight() + size.y);

        vehicle = Player.INSTANCE.getVehicle();

        for(int i = 0; i < 10; i++){
            Rectangle newStreet = new Rectangle();
            newStreet.x = spawnPosition.x;
            newStreet.y = spawnPosition.y - size.y * i;
            newStreet.width = size.x;
            newStreet.height = size.y;
            streetTiles.add(newStreet);
            lastStreetSpawned = newStreet;

        }
        spawnStreet();
    }

    public void update(){
        // Spawn new tile
        if(lastStreetSpawned.y < spawnPosition.y - size.y + 14){
            spawnStreet();
        }

        // Update street tiles
        for(Iterator<Rectangle> iter = streetTiles.iterator(); iter.hasNext();){
            Rectangle street = iter.next();
            // Set the tile position
            street.y -= Player.INSTANCE.getCurrentVelocity() * Const.VEHICLES_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime();

            // Delete tile when off screen
            if(street.y < 0 - size.y)
                iter.remove();
        }
    }

    private void spawnStreet(){
        // Spawn new street
        Rectangle newStreet = new Rectangle();
        newStreet.x = spawnPosition.x;
        newStreet.y = spawnPosition.y;
        newStreet.width = size.x;
        newStreet.height = size.y;
        streetTiles.add(newStreet);
        lastStreetSpawned = newStreet;

    }

    public void render(){
         for(Rectangle street : streetTiles){
             batch.draw(texture, street.x, street.y, street.width, street.height);
         }
    }


}
