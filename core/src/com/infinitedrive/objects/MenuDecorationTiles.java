package com.infinitedrive.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.helpers.Const;

public class MenuDecorationTiles {

    private Array<Texture> streetTiles;
    private Array<Texture> grassTile;
    private Texture billboardTexture;
    private Texture title;
    private SpriteBatch batch;

    public MenuDecorationTiles(){

        batch = InfiniteDrive.INSTANCE.getBatch();

        billboardTexture = new Texture("sprites/billboard.png");
        title = new Texture("sprites/title.png");

        streetTiles = new Array<>();
        grassTile = new Array<>();

        for(int i = 0; i < Const.STREET_TILES_QUANTITY; i++){
            streetTiles.add(new Texture("sprites/tiles/streetTile" + (i + 1) + ".png"));
        }
        for(int i = 0; i < Const.GRASS_TILES_QUANTITY; i++){
            grassTile.add(new Texture("sprites/tiles/grassTile" + (i + 1) + ".png"));
        }
    }

    public void render(){
        int pos = 0;
        // Spawn Tiles
        batch.draw(grassTile.get(1), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 250, 300 *  pos);
        pos++;
        batch.draw(grassTile.get(0), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 250, 300 *  pos);
        pos++;
        batch.draw(grassTile.get(2), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 250, 300 *  pos);
        pos++;

        // Spawn Street
        pos = 0;
        for(int i = 0; i < 2; i++){
            batch.draw(streetTiles.get(0), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50, 100 *  pos);
            pos++;
        }
        batch.draw(streetTiles.get(1), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50,100 *  pos);
        pos++;
        for(int i = 0; i < 2; i++){
            batch.draw(streetTiles.get(0), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50, 100 *  pos);
            pos++;
        }
        batch.draw(streetTiles.get(3), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50, 100 *  pos);
        pos++;
        batch.draw(streetTiles.get(2), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50, 100 *  pos);
        pos++;
        for(int i = 0; i < 4; i++){
            batch.draw(streetTiles.get(0), InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 50, 100 *  pos);
            pos++;
        }

        batch.draw(billboardTexture, 330, 650);
        batch.draw(title, Gdx.graphics.getWidth() / 2 - 485 / 2, 800);
    }
}
