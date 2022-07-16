package com.infinitedrive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.infinitedrive.DataHandler;
import com.infinitedrive.GameContactListener;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.NPCVehicleSpawner;
import com.infinitedrive.objects.Player;
import com.infinitedrive.objects.StreetSpawner;

public class GameScreen implements Screen {

    final InfiniteDrive game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private StreetSpawner streetSpawner;
    private NPCVehicleSpawner npcVehicleSpawner;

    private int laneDistance;
    private World world;
    private Box2DDebugRenderer b2dr;

    public GameScreen(final InfiniteDrive game){
        this.game = game;

        // Physics setup
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameContactListener());
        b2dr = new Box2DDebugRenderer();

        // Camera setup
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.INSTANCE.getScreenWidth(),game.INSTANCE.getScreenHeight());
        this.batch = game.getBatch();

        //DataHandler.CreatePlayerVehicle("Car1", 60, 70, 0.1f, 20, 100, 29, 48, 1.5f,"Vehicles/car02.png");

        this.player = new Player(world);
        this.streetSpawner = new StreetSpawner();
        this.npcVehicleSpawner = new NPCVehicleSpawner(world);
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
        npcVehicleSpawner.render();
        player.render();

        // HUD render
        game.font.draw(batch, String.format("%.0f", player.getBrakeDurability()), 30, 30);
        game.font.draw(batch,String.format("%.0f", player.getDistanceTraveled()) + "m", InfiniteDrive.INSTANCE.getScreenWidth() / 2 - 40 , InfiniteDrive.INSTANCE.getScreenHeight() - 20);
        game.font.draw(batch, player.getCurrentVelocity() + "km/h", InfiniteDrive.INSTANCE.getScreenWidth() / 2 + 40 , InfiniteDrive.INSTANCE.getScreenHeight() - 20);
        batch.end();

        // Debug render
        b2dr.render(world,camera.combined);

        // Game Loop
        update();

        //Physics step
        world.step(delta, 6, 2);
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
