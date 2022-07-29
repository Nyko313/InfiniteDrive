package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.Gameobject;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.helpers.Const;

public class TilesSpawner extends Gameobject {
    private float height = 300;
    private float width = 500;
    private Vector2 spawnPosition;
    private Rectangle lastTileSpawned;

    private float lastBiomeDistance;
    private float lastBillboardDistance;

    private Texture billboardTexture;
    private Rectangle billboardRect;
    private Array<Texture> currentTileTexture;
    private Array<Texture> desertTileTextures;
    private Array<Texture> grassTileTextures;
    private Array<Rectangle> tiles;
    private Array<Texture> tileTextureIndex;

    private enum Biomes {
        Grass,
        Desert
    }

    private Biomes biomes = Biomes.Grass;

    public TilesSpawner(){
        renderPriority = 0;
        initialize(this);

        desertTileTextures = new Array<>();
        grassTileTextures = new Array<>();
        currentTileTexture = grassTileTextures;
        tileTextureIndex = new Array<>();
        tiles = new Array<>();
        billboardTexture = new Texture("sprites/billboard.png");

        for(int i = 0; i < Const.DESERT_TILES_QUANTITY; i++){
            desertTileTextures.add(new Texture("sprites/tiles/desertTile" + (i + 1) + ".png"));
        }

        for(int i = 0; i < Const.GRASS_TILES_QUANTITY; i++){
            grassTileTextures.add(new Texture("sprites/tiles/grassTile" + (i + 1) + ".png"));
        }

        spawnPosition = new Vector2(InfiniteDrive.INSTANCE.getScreenWidth() / 2 - width / 2, InfiniteDrive.INSTANCE.getScreenHeight() + height);


        for(int i = 0; i < 10; i++){
            Rectangle newTile = new Rectangle();
            newTile.x = spawnPosition.x;
            newTile.y = spawnPosition.y - height * i;
            newTile.width = width;
            newTile.height = height;
            tiles.add(newTile);
            tileTextureIndex.add(currentTileTexture.random());
            lastTileSpawned = newTile;

        }
        spawnStreet();
    }

    @Override
    public void update() {
        // Spawn new tile
        if(lastTileSpawned.y < spawnPosition.y - height + 30){
            spawnStreet();
        }

        if(Player.INSTANCE.getDistanceTraveled() - lastBiomeDistance >= Const.BIOME_DISTANCE){
            biomes = Biomes.Desert;
            lastBiomeDistance = Player.INSTANCE.getDistanceTraveled();
        }

        if(Player.INSTANCE.getDistanceTraveled() - lastBillboardDistance >= 200){
            spawnBillboard();
            lastBillboardDistance = Player.INSTANCE.getDistanceTraveled();
        }

        if(billboardRect !=null){
            billboardRect.y -= Player.INSTANCE.getCurrentVelocity() * Const.VEHICLES_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime();
            if( billboardRect.y < -30){
                billboardRect = null;
            }
        }

        switch (biomes){
            case Grass:
                currentTileTexture = grassTileTextures;
                break;
            case Desert:
                currentTileTexture = desertTileTextures;
                break;
        }

        // Update street tiles
        for(int i = 0; i < tiles.size; i++){
            // Set the tile position
            tiles.get(i).y -= Player.INSTANCE.getCurrentVelocity() * Const.VEHICLES_VELOCITY_MULTIPLIER * Gdx.graphics.getDeltaTime();

            // Delete tile when off screen
            if(tiles.get(i).y < 0 - height){
                tiles.removeIndex(i);
                tileTextureIndex.removeIndex(i);
            }
        }
    }

    private void spawnStreet(){
        // Spawn new street
        Rectangle newTile = new Rectangle();
        newTile.x = spawnPosition.x;
        newTile.y = spawnPosition.y;
        newTile.width = width;
        newTile.height = height;
        tiles.add(newTile);
        tileTextureIndex.add(currentTileTexture.random());
        lastTileSpawned = newTile;
    }

    private void spawnBillboard(){
        // Spawn new street
        billboardRect = new Rectangle();
        billboardRect.x = 350;
        billboardRect.y = InfiniteDrive.INSTANCE.getScreenHeight() + height;
        billboardRect.width = 150;
        billboardRect.height = 100;
    }

    @Override
    public void render() {
        for(int i = 0; i < tiles.size; i++){
            batch.draw(tileTextureIndex.get(i), tiles.get(i).x, tiles.get(i).y);
        }

        if(billboardRect != null){
            batch.draw(billboardTexture, billboardRect.x, billboardRect.y);
        }
    }

    @Override
    public void dispose() {
        for(Texture texture : grassTileTextures){
            texture.dispose();
        }

        for(Texture texture : desertTileTextures){
            texture.dispose();
        }
    }
}
