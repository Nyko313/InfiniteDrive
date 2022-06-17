package com.infinitedrive.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.infinitedrive.DataHandler;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.NPCVehicleSpawner;
import com.infinitedrive.objects.Player;
import com.infinitedrive.objects.StreetSpawner;
import com.infinitedrive.objects.Vehicle;

import javax.xml.crypto.Data;

public class GameScene implements Screen {

    final InfiniteDrive game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private StreetSpawner streetSpawner;
    private NPCVehicleSpawner npcVehicleSpawner;

    private int laneDistance;

    public GameScene(final InfiniteDrive game){

        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.INSTANCE.getScreenWidth(),game.INSTANCE.getScreenHeight());
        this.batch = game.getBatch();

        //DataHandler.CreatePlayerVehicle("Car1", 50, 70, 20, 100, 29, 48, 1.5f,"Vehicles/car02.png");

        this.player = new Player();
        this.streetSpawner = new StreetSpawner();
        this.npcVehicleSpawner = new NPCVehicleSpawner();
    }

    @Override
    public void show(){

    }

    private void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        player.update();
        streetSpawner.update();
        npcVehicleSpawner.update();
    }


    @Override
    public void render(float delta){
        ScreenUtils.clear(0.144f,0.144f,0.144f,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render
        batch.begin();

        streetSpawner.render();
        player.render();
        game.font.draw(batch,String.format("%.0f", player.getDistanceTraveled()) + "m", InfiniteDrive.INSTANCE.getScreenWidth() / 2 , InfiniteDrive.INSTANCE.getScreenHeight() - 20);
        npcVehicleSpawner.render();
        batch.end();

        // Game Loop
        update();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        player.dispose();
    }
}
