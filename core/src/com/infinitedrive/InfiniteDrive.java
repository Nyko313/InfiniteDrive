package com.infinitedrive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infinitedrive.screens.GameScreen;

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

		//DataHandler.CreateNPCVehicle("NPCVehicle1", 40, 30, 29, 48, 1.5f, "vehicles\\NPCVehicle1" );

		//Load Game screen
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.R) ){
			create();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
