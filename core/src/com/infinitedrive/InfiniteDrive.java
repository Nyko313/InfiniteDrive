package com.infinitedrive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infinitedrive.scene.GameScene;

public class InfiniteDrive extends Game {
	public static InfiniteDrive INSTANCE;
	private SpriteBatch batch;

	private int screenWidth;
	private int screenHeight;
	public BitmapFont font;

	public InfiniteDrive(){
		INSTANCE = this;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	@Override
	public void create () {
		this.screenHeight = Gdx.graphics.getHeight();
		this.screenWidth = Gdx.graphics.getWidth();

		batch = new SpriteBatch();
		font = new BitmapFont();

		//DataHandler.CreateNPCVehicle("NPCVehicle1", 40, 30, 29, 48, 1.5f, "Vehicles\\NPCVehicle1" );

		this.setScreen(new GameScene(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
