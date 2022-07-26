package com.infinitedrive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.infinitedrive.DataHandler;
import com.infinitedrive.GameContactListener;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.objects.GameobjectsManager;
import com.infinitedrive.objects.NPCVehicleSpawner;
import com.infinitedrive.objects.Player;
import com.infinitedrive.objects.StreetSpawner;

import java.util.Iterator;

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
    private GameobjectsManager gameobjectsManager;

    // HUD
    private Texture backgroundSprite;
    private Texture rocketTexture;
    private BitmapFont pixelFont;
    private GlyphLayout glyphLayout;

    public GameScreen(final InfiniteDrive game){
        this.game = game;
        backgroundSprite = new Texture("HUDBackground.png");
        rocketTexture = new Texture("Missile.png");

        glyphLayout = new GlyphLayout();
        // Font setup
        pixelFont = new BitmapFont(Gdx.files.internal("PixelatedFont.fnt"));
        pixelFont.getData().setScale(0.8f);

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
        this.npcVehicleSpawner = new NPCVehicleSpawner(world);
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
        batch.draw(backgroundSprite,0,-100);

        //      Brake
        pixelFont.setColor(Color.RED);
        pixelFont.draw(batch,"brk/", 35, 155);
        pixelFont.setColor(Color.WHITE);
        pixelFont.draw(batch, String.format("%.0f", player.getBrakeDurability()), 75, 120);
        //      Distance
        pixelFont.setColor(Color.GREEN);
        pixelFont.draw(batch,"dst/", InfiniteDrive.INSTANCE.getScreenWidth() / 2 +85, 155);
        pixelFont.setColor(Color.WHITE);
        pixelFont.draw(batch,String.format("%.0f", player.getDistanceTraveled()) + "m", InfiniteDrive.INSTANCE.getScreenWidth() / 2  +140, 120);
        //      Speed
        pixelFont.setColor(Color.BLUE);
        pixelFont.draw(batch,"spd/", InfiniteDrive.INSTANCE.getScreenWidth() / 2 -75, 155);
        pixelFont.setColor(Color.WHITE);
        pixelFont.draw(batch, String.format("%.0f", player.getCurrentVelocity()) + "km", InfiniteDrive.INSTANCE.getScreenWidth() / 2 -40, 120);

        // Rocket
        if(Player.INSTANCE.getRocketAmount()>= 1) batch.draw(rocketTexture, 20, 20, 20, 55);
        if(Player.INSTANCE.getRocketAmount()>= 2) batch.draw(rocketTexture, 40, 20, 20, 55);
        if(Player.INSTANCE.getRocketAmount()>= 3) batch.draw(rocketTexture, 60, 20, 20, 55);
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

    }
}
