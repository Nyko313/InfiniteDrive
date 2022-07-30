package com.infinitedrive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.infinitedrive.GameContactListener;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.*;
import com.infinitedrive.objects.npcvehicles.NPCVehicleSpawner;

public class GameScreen implements Screen {

    final InfiniteDrive game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private StreetSpawner streetSpawner;
    private NPCVehicleSpawner npcVehicleSpawner;
    private TilesSpawner tileSpawner;
    private HUD hud;
    private World world;
    private Box2DDebugRenderer b2dr;
    private GameobjectsManager gameobjectsManager;

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
        gameobjectsManager = new GameobjectsManager(world, batch);
        this.player = new Player(world);
        this.streetSpawner = new StreetSpawner();
        this.tileSpawner = new TilesSpawner();
        this.npcVehicleSpawner = new NPCVehicleSpawner(world);
        this.hud = new HUD();
    }

    @Override
    public void show(){

    }

    private void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        gameobjectsManager.update();
    }


    @Override
    public void render(float delta){
        ScreenUtils.clear(0.144f,0.144f,0.144f,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Game Loop
        update();

        // Render
        batch.begin();

        gameobjectsManager.render();

        // HUD render


        batch.end();

        // Debug render
        //b2dr.render(world,camera.combined);



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
        gameobjectsManager.dispose();
        world.dispose();

    }
}
