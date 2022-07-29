package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;

public class StreetSpawner extends Gameobject {
    private Vector2 spawnPosition;
    private float sizeMultiplier = 1.8f;

    private float width = 100*sizeMultiplier;
    private float height = 120*sizeMultiplier;

    private Array<Texture> textures;
    private Array<Rectangle> streetTiles;
    private Array<Texture> spawnedTilesTexture;
    private Rectangle lastStreetSpawned;


    public StreetSpawner(){
        renderPriority = 1;
        initialize(this);

        textures = new Array<>();
        spawnedTilesTexture = new Array<>();

        for(int i = 0; i < Const.STREET_TILES_QUANTITY; i++){
            textures.add(new Texture("sprites/tiles/streetTile" + (i + 1) + ".png"));
        }
        streetTiles = new Array<Rectangle>();


        spawnPosition = new Vector2(InfiniteDrive.INSTANCE.getScreenWidth() / 2 - width / 2, InfiniteDrive.INSTANCE.getScreenHeight() + height);

        for(int i = 0; i < 10; i++){
            Rectangle newStreet = new Rectangle();
            newStreet.x = spawnPosition.x;
            newStreet.y = spawnPosition.y - height * i;
            newStreet.width = width;
            newStreet.height = height;
            streetTiles.add(newStreet);
            spawnedTilesTexture.add(textures.random());
            lastStreetSpawned = newStreet;

        }
        spawnStreet();
    }

    @Override
    public void update(){
        // Spawn new tile
        if(lastStreetSpawned.y < spawnPosition.y - height + 50){
            spawnStreet();
        }

        // Update street tiles
        for(int i = 0; i < streetTiles.size; i++){
            // Set the tile position
            streetTiles.get(i).y -= Player.INSTANCE.getCurrentVelocity() * Const.VEHICLES_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime();

            // Delete tile when off screen
            if(streetTiles.get(i).y < 0 - height){
                streetTiles.removeIndex(i);
                spawnedTilesTexture.removeIndex(i);
            }
        }
    }

    private void spawnStreet(){
        // Spawn new street
        Rectangle newStreet = new Rectangle();
        newStreet.x = spawnPosition.x;
        newStreet.y = spawnPosition.y;
        newStreet.width = width;
        newStreet.height = height;
        streetTiles.add(newStreet);
        spawnedTilesTexture.add(textures.random());
        lastStreetSpawned = newStreet;

    }

    @Override
    public void render(){
        for(int i = 0; i < streetTiles.size; i++){
            batch.draw(spawnedTilesTexture.get(i), streetTiles.get(i).x, streetTiles.get(i).y, width, height);
        }
    }

    @Override
    public void dispose() {
        for(Texture texture : textures){
            texture.dispose();
        }
    }
}
