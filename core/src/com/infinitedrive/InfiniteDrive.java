package com.infinitedrive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infinitedrive.screens.GameScreen;
import com.infinitedrive.screens.MenuScreen;
import com.infinitedrive.screens.PreferencesScreen;

public class InfiniteDrive extends Game {
	public static InfiniteDrive INSTANCE;
	private SpriteBatch batch;
	private AppPreferences appPreferences;

	private int screenWidth;
	private int screenHeight;


	public AppPreferences getAppPreferences() {
		return appPreferences;
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

	public InfiniteDrive(){
		INSTANCE = this;
		appPreferences = new AppPreferences();
	}

	@Override
	public void create () {
		this.screenHeight = Gdx.graphics.getHeight();
		this.screenWidth = Gdx.graphics.getWidth();

		batch = new SpriteBatch();

		//DataHandler.CreateNPCVehicle("NPCVehicle1", 40, 30, 29, 48, 1.5f, "vehicles\\NPCVehicle1" );

		//Load Game screen
		if(this.screen != null) {
			this.screen.dispose();
		}
		this.setScreen(new MenuScreen(this));
	}

	public void LoadGame(){
		//Load Game screen
		if(this.screen != null) {
			this.screen.dispose();
		}
		this.setScreen(new GameScreen(this));
	}

	public void LoadPreferences(){
		//Load Game screen
		if(this.screen != null) {
			this.screen.dispose();
		}
		this.setScreen(new PreferencesScreen(this));
	}

	public void LoadMenu(){

		//Load Game screen

		if(this.screen != null) {
			this.screen.dispose();
		}
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.R) ){
			LoadGame();
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
